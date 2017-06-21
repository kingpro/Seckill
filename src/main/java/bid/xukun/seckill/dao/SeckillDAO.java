package bid.xukun.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import bid.xukun.seckill.entity.Seckill;

public interface SeckillDAO {

	/**
	 * 减库存
	 * 
	 * @param seckillId
	 * @param killTime
	 * @return
	 */
	int reduceNumber(@Param("seckillId") int seckillId, @Param("killTime") Date killTime);

	/**
	 * 根据id查询秒杀对象
	 * 
	 * @param seckillId
	 * @return
	 */
	Seckill queryById(int seckillId);

	/**
	 * 根据偏移量查询秒杀商品列表
	 * 
	 * @param offer
	 * @param limit
	 * @return
	 */
	List<Seckill> queryAll(@Param("offer") int offer, @Param("limit") int limit);
}
