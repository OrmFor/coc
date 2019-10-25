package com.yinmimoney.web.p2pnew.enums;

/**
 * 
 * @Description token状态枚举类
 * @author wzq
 * @date 2018年6月7日 上午11:34:43
 */
public enum EnumApiTokenStatus {

	/** 有效 **/
	STATUS_NORMAL(0, "有效"),
	/** 过期 **/
	STATUS_EXPIRED(1, "过期"),
	/** 失效 **/
	STATUS_DISABLED(2, "失效"),
	/** 另外设备登录 **/
	STATUS_OTHER_DEVICE_LOGIN(3, "另外设备登录");

	private int status;

	private String name;

	private EnumApiTokenStatus(int status, String name) {
		this.name = name;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public int getStatus() {
		return status;
	}

}
