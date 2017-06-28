package bid.xukun.seckill.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import bid.xukun.seckill.dao.SeckillDAO;
import bid.xukun.seckill.dao.SeckillSuccessDAO;
import bid.xukun.seckill.dao.cache.RedisDAO;
import bid.xukun.seckill.dto.Exposer;
import bid.xukun.seckill.dto.SeckillExecution;
import bid.xukun.seckill.entity.Seckill;
import bid.xukun.seckill.entity.SeckillSuccess;
import bid.xukun.seckill.enums.SeckillStateEnum;
import bid.xukun.seckill.exception.RepeatKillException;
import bid.xukun.seckill.exception.SeckillCloseException;
import bid.xukun.seckill.exception.SeckillException;
import bid.xukun.seckill.service.SeckillService;

@Service
public class SeckillServiceImpl implements SeckillService {
	private Logger Logger = LoggerFactory.getLogger(this.getClass());

	// 注入Service依赖
	@Autowired
	private SeckillDAO seckillDAO;

	@Autowired
	private SeckillSuccessDAO seckillSuccessDAO;

	@Autowired
	private RedisDAO redisDAO;

	// MD5盐值字符串，用于混淆MD5
	private String slat = "fjaksln^&$%^#(*&JDFAKLDe472389";

	@Override
	public List<Seckill> getSeckillList() {
		return seckillDAO.queryAll(0, 4);
	}

	@Override
	public Seckill getSeckillById(int id) {
		// 缓存优化
		// 1.访问Redis
		Seckill seckill = redisDAO.getSeckill(id);
		if (seckill == null) {
			// 2.访问数据库
			seckill = seckillDAO.queryById(id);
			if (seckill != null) {
				// 3.放入Redis
				redisDAO.putSeckill(seckill);
			}
		}
		return seckill;
	}

	@Override
	public Exposer exportSeckillUrl(int id) {
		Seckill seckill = getSeckillById(id);
		if (null == seckill) {
			return new Exposer(false, id);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();
		if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, id, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		String md5 = getMD5(id);
		return new Exposer(true, md5, id);
	}

	private String getMD5(int id) {
		String base = id + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	@Override
	@Transactional
	/**
	 * 使用注解控制事务方法的优点 1.开发团队达成一致约定，明确标注事务方法的编程风格。
	 * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作PRC/HTTP请求或者剥离到事务方法外部。
	 * 3.不是所有的方法都需要事务，如只要一条修改操作，只读操作不需要事务控制
	 */
	public SeckillExecution executeSeckill(int id, String userPhone, String md5) {
		if (null != md5 && !"".equals(md5) && !md5.equals(getMD5(id))) {
			throw new SeckillException("seckill data rewrite");
		}
		// 执行秒杀逻辑：减库存+记录购买行为
		try {
			// 记录购买行为
			int insertCount = seckillSuccessDAO.insertSeckillSuccess(id, userPhone);
			if (insertCount <= 0) {
				// 重复秒杀
				throw new RepeatKillException("seckill repeat");
			} else {
				// 减库存
				Date nowTime = new Date();
				int updateCount = seckillDAO.reduceNumber(id, nowTime);
				if (updateCount <= 0) {
					// 没有更新到记录，秒杀结束
					throw new SeckillCloseException("seckill is closed");
				} else {
					// 秒杀成功
					SeckillSuccess seckillSuccess = seckillSuccessDAO.queryByIdWithSeckill(id, userPhone);
					return new SeckillExecution(id, SeckillStateEnum.SUCCESS, seckillSuccess);
				}
			}
		} catch (SeckillCloseException e1) {
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			// 所有编译期的异常，转换为运行期异常
			throw new SeckillException("seckill inner error:" + e.getMessage());
		}
	}

	/**
	 * 调用存储过程
	 */
	@Override
	public SeckillExecution executeSeckillProcedure(int id, String userPhone, String md5) {
		if (null != md5 && !"".equals(md5) && !md5.equals(getMD5(id))) {
			return new SeckillExecution(id, SeckillStateEnum.DATA_REWRITE);
		}
		Date killTime = new Date();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("seckillId", id);
		paramMap.put("userPhone", userPhone);
		paramMap.put("killTime", killTime);
		paramMap.put("result", null);
		// 执行存储过程，result被赋值
		try {
			seckillDAO.killByProcedure(paramMap);
			// 获取result
			int result = MapUtils.getInteger(paramMap, "result", -2);
			if (result == 1) {
				SeckillSuccess seckillSuccess = seckillSuccessDAO.queryByIdWithSeckill(id, userPhone);
				return new SeckillExecution(id, SeckillStateEnum.SUCCESS, seckillSuccess);
			} else {
				return new SeckillExecution(id, SeckillStateEnum.stateOf(result));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new SeckillExecution(id, SeckillStateEnum.INNER_ERROR);
		}
	}

}
