package bid.xukun.seckill.dto;

import bid.xukun.seckill.entity.SeckillSuccess;
import bid.xukun.seckill.enums.SeckillStateEnum;

public class SeckillExecution {

	private int id;
	// 执行秒杀结果的状态
	private int state;
	// 状态表示
	private String stateInfo;
	// 秒杀成功对象
	private SeckillSuccess seckillSuccess;

	public SeckillExecution(int id, SeckillStateEnum seckillStateEnum, SeckillSuccess seckillSuccess) {
		super();
		this.id = id;
		this.state = seckillStateEnum.getState();
		this.stateInfo = seckillStateEnum.getStateinfo();
		this.seckillSuccess = seckillSuccess;
	}

	public SeckillExecution(int id, SeckillStateEnum seckillStateEnum) {
		super();
		this.id = id;
		this.state = seckillStateEnum.getState();
		this.stateInfo = seckillStateEnum.getStateinfo();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SeckillSuccess getSeckillSuccess() {
		return seckillSuccess;
	}

	public void setSeckillSuccess(SeckillSuccess seckillSuccess) {
		this.seckillSuccess = seckillSuccess;
	}

	@Override
	public String toString() {
		return "SeckillExecution [id=" + id + ", state=" + state + ", stateInfo=" + stateInfo + ", seckillSuccess="
				+ seckillSuccess + "]";
	}

}
