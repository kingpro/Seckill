package bid.xukun.seckill.enums;

/**
 * 使用枚举表示常量数据字段
 * 
 * @author XK
 *
 */
public enum SeckillStateEnum {
	SUCCESS(1, "秒杀成功"), END(0, "秒杀关闭"), PEPEAT_KILL(-1, "重复秒杀"), INNER_ERROR(-2, "系统异常"), DATA_REWRITE(-3, "数据篡改");

	private int state;

	private String stateinfo;

	private SeckillStateEnum(int state, String stateinfo) {
		this.state = state;
		this.stateinfo = stateinfo;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateinfo() {
		return stateinfo;
	}

	public void setStateinfo(String stateinfo) {
		this.stateinfo = stateinfo;
	}

	public static SeckillStateEnum stateOf(int index) {
		for (SeckillStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
