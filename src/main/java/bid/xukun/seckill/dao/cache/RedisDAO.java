package bid.xukun.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import bid.xukun.seckill.entity.Seckill;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDAO {
	private JedisPool jedisPool;
	private String password;
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

	public RedisDAO(String host, int port, String password) {
		jedisPool = new JedisPool(host, port);
		this.password = password;
	}

	public Seckill getSeckill(int seckillId) {
		// Redis操作逻辑
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.auth(password);
			String key = "seckill:" + seckillId;
			// 并没有实现内部序列化操作
			// get->byte[]->反序列化->Object(Seckill)
			// 采用自定义序列化
			byte[] bytes = jedis.get(key.getBytes());
			// 缓存获取到了
			if (bytes != null) {
				// 空对象
				Seckill seckill = schema.newMessage();
				ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
				// seckill被反序列化
				return seckill;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}

	public String putSeckill(Seckill seckill) {
		// set Object(Seckill) -> 序列化 -> byte[]
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.auth(password);
			String key = "seckill:" + seckill.getId();
			byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
					LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
			int timeout = 60 * 60;// 一小时
			String result = jedis.setex(key.getBytes(), timeout, bytes);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}
}
