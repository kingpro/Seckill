package bid.xukun.seckill.dto;

/**
 * 暴露秒杀地址DTO
 * 
 * @author XK
 *
 */
public class Exposer {

	// 是否开启秒杀
	private boolean isExposed;

	// 一种加密措施
	private String md5;

	// id
	private int id;

	// 系统当前时间（毫秒）
	private long now;

	// 秒杀开始时间（毫秒）
	private long start;

	// 秒杀结束时间（毫秒）
	private long end;

	public Exposer(boolean isExposed, String md5, int id) {
		super();
		this.isExposed = isExposed;
		this.md5 = md5;
		this.id = id;
	}

	public Exposer(boolean isExposed, int id, long now, long start, long end) {
		super();
		this.isExposed = isExposed;
		this.id = id;
		this.now = now;
		this.start = start;
		this.end = end;
	}

	public Exposer(boolean isExposed, int id) {
		super();
		this.isExposed = isExposed;
		this.id = id;
	}

	public boolean isExposed() {
		return isExposed;
	}

	public void setExposed(boolean isExposed) {
		this.isExposed = isExposed;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getNow() {
		return now;
	}

	public void setNow(long now) {
		this.now = now;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Exposer [isExposed=" + isExposed + ", md5=" + md5 + ", id=" + id + ", now=" + now + ", start=" + start
				+ ", end=" + end + "]";
	}

}
