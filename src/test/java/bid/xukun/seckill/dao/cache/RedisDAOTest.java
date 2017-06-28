package bid.xukun.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bid.xukun.seckill.dao.SeckillDAO;
import bid.xukun.seckill.entity.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class RedisDAOTest {

	private int id = 1;

	@Autowired
	private RedisDAO redisDAO;

	@Autowired
	private SeckillDAO seckillDAO;

	@Test
	public void test() {
		Seckill seckill = redisDAO.getSeckill(id);
		if (seckill == null) {
			seckill = seckillDAO.queryById(id);
			if (seckill != null) {
				String result = redisDAO.putSeckill(seckill);
				System.out.println(result);
				seckill = redisDAO.getSeckill(id);
			}
		}
		System.out.println(seckill);
	}

}
