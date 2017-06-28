package bid.xukun.seckill.dao.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import bid.xukun.seckill.entity.Seckill;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDAO {
	private JedisPool jedisPool;
	private String password;

	public RedisDAO(String host, int port, String password) {
		jedisPool = new JedisPool(host, port);
		this.password = password;
	}

	public Seckill getSeckill(int seckillId) {
		// Redis操作逻辑
		Jedis jedis = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
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
				Seckill seckill = null;
				bis = new ByteArrayInputStream(bytes);
				ois = new ObjectInputStream(bis);
				seckill = (Seckill) ois.readObject();
				// seckill被反序列化
				return seckill;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
				if (bis != null) {
					bis.close();
				}
				if (jedis != null) {
					jedis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public String putSeckill(Seckill seckill) {
		// set Object(Seckill) -> 序列化 -> byte[]
		Jedis jedis = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			jedis = jedisPool.getResource();
			jedis.auth(password);
			String key = "seckill:" + seckill.getId();
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(seckill);
			byte[] bytes = bos.toByteArray();
			int timeout = 60 * 60;// 一小时
			String result = jedis.setex(key.getBytes(), timeout, bytes);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
				if (bos != null) {
					bos.close();
				}
				if (jedis != null) {
					jedis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
