package bid.xukun.seckill.entity;

import java.io.Serializable;
import java.util.Date;

public class Seckill implements Serializable {

	private int id;
	private String name;
	private int number;
	private Date startTime;
	private Date endTime;
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "seckill [id=" + id + ", name=" + name + ", number=" + number + ", startTime=" + startTime + ", endTime="
				+ endTime + ", createTime=" + createTime + "]";
	}

}
