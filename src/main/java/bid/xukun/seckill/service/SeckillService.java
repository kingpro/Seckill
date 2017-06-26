package bid.xukun.seckill.service;

import java.util.List;

import bid.xukun.seckill.dto.Exposer;
import bid.xukun.seckill.dto.SeckillExecution;
import bid.xukun.seckill.entity.Seckill;
import bid.xukun.seckill.exception.RepeatKillException;
import bid.xukun.seckill.exception.SeckillCloseException;
import bid.xukun.seckill.exception.SeckillException;

/**
 * 业务接口：站在“使用者”角度设计接口
 * 
 * @author XK
 *
 */
public interface SeckillService {

	/**
	 * 查询所有秒杀记录
	 * 
	 * @return
	 */
	List<Seckill> getSeckillList();

	/**
	 * 根据id查询秒杀记录
	 * 
	 * @param id
	 * @return
	 */
	Seckill getSeckillById(int id);

	/**
	 * 秒杀开启时输出秒杀地址 否则输出系统时间和秒杀时间
	 * 
	 * @param id
	 * @return
	 */
	Exposer exportSeckillUrl(int id);

	/**
	 * 
	 * @param id
	 * @param userPhone
	 * @param md5
	 * @return
	 */
	SeckillExecution executeSeckill(int id, String userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException;
}
