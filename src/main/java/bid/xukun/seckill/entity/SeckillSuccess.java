package bid.xukun.seckill.entity;

import java.util.Date;

public class SeckillSuccess {

	private int seckillId;
	private String userphone;
	private short state;
	private Date createTime;
	private Seckill seckill;

	public String getUserphone() {
		return userphone;
	}

	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}

	public Seckill getSeckill() {
		return seckill;
	}

	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}

	public int getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(int seckillId) {
		this.seckillId = seckillId;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "seckillSuccess [seckillId=" + seckillId + ", userphone=" + userphone + ", state=" + state
				+ ", createTime=" + createTime + "]";
	}

}
