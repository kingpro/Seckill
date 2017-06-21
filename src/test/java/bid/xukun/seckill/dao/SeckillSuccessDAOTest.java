package bid.xukun.seckill.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bid.xukun.seckill.entity.SeckillSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SeckillSuccessDAOTest {

	@Resource
	private SeckillSuccessDAO seckillSuccessDAO;

	@Test
	public void testInsertSeckillSuccess() {
		int id = 1;
		String userphone = "13923456789";
		int insertCode = seckillSuccessDAO.insertSeckillSuccess(id, userphone);
		System.out.println("insertCode:" + insertCode);
	}

	@Test
	public void testQueryByIdWithSeckill() {
		int id = 1;
		String userphone = "13923456789";
		SeckillSuccess seckillSuccess = seckillSuccessDAO.queryByIdWithSeckill(id, userphone);
		System.out.println(seckillSuccess);
		System.out.println(seckillSuccess.getSeckill());
	}

}
