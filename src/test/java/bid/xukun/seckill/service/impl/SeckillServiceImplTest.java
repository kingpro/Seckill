package bid.xukun.seckill.service.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bid.xukun.seckill.dto.Exposer;
import bid.xukun.seckill.dto.SeckillExecution;
import bid.xukun.seckill.entity.Seckill;
import bid.xukun.seckill.exception.RepeatKillException;
import bid.xukun.seckill.exception.SeckillCloseException;
import bid.xukun.seckill.exception.SeckillException;
import bid.xukun.seckill.service.SeckillService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })
public class SeckillServiceImplTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;

	@Test
	public void testGetSeckillList() {
		List<Seckill> seckillList = seckillService.getSeckillList();
		logger.info("list={}", seckillList);
	}

	@Test
	public void testGetSeckillById() {
		Seckill seckill = seckillService.getSeckillById(1);
		logger.info("seckill={}", seckill);
	}

	// 测试代码完整逻辑
	@Test
	public void testExportSeckillUrl() {
		int id = 1;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if (exposer.isExposed()) {
			logger.info("exposer={}", exposer);
			String userphone = "15976481234";
			String md5 = exposer.getMd5();
			testExecuteSeckill(id, userphone, md5);
		} else {
			// 秒杀未开启
			logger.warn("exposer={}", exposer);
		}
	}

	public void testExecuteSeckill(int id, String userphone, String md5) {
		try {
			SeckillExecution seckillExecution = seckillService.executeSeckill(id, userphone, md5);
			logger.info("seckillExecution={}", seckillExecution);
		} catch (RepeatKillException e) {
			logger.error(e.getMessage());
		} catch (SeckillCloseException e) {
			logger.error(e.getMessage());
		} catch (SeckillException e) {
			logger.error(e.getMessage());
		}
	}

}
