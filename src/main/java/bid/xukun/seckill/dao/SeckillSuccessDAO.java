package bid.xukun.seckill.dao;

import org.apache.ibatis.annotations.Param;

import bid.xukun.seckill.entity.SeckillSuccess;

public interface SeckillSuccessDAO {

	/**
	 * 插入购买明细，可重复过滤
	 * 
	 * @param seckillId
	 * @param killTime
	 * @return
	 */
	int insertSeckillSuccess(@Param("seckillId") int seckillId, @Param("userphone") String userphone);

	/**
	 * 根据id查询秒杀成功对象
	 * 
	 * @param seckillId
	 * @return
	 */
	SeckillSuccess queryByIdWithSeckill(@Param("seckillId") int seckillId, @Param("userphone") String userphone);

}
