package bid.xukun.seckill.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bid.xukun.seckill.entity.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SeckillDAOTest {

	@Resource
	private SeckillDAO seckillDAO;

	@Test
	public void testReduceNumber() {
		Date killTime = new Date();
		int i = seckillDAO.reduceNumber(1, killTime);
		System.out.println(i + "");
	}

	@Test
	public void testQueryById() {
		int id = 1;
		Seckill seckill = seckillDAO.queryById(id);
		System.out.println(seckill);
	}

	@Test
	public void testQueryAll() {
		List<Seckill> seckillList = seckillDAO.queryAll(0, 100);
		for (Seckill seckill : seckillList) {
			System.out.println(seckill);
		}
	}

}
