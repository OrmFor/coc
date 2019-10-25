package com.yinmimoney.web.p2pnew.enums;

/**
 * 
 * @Description 用户状态枚举类
 * @author wzq
 * @date 2018年6月7日 上午11:34:43
 */
public enum EnumUserStatus {

	/** 已注册 **/
	STATUS_REGISTER(0, "已注册"),
	/** 已激活 **/
	STATUS_OAUTH(1, "已认证");

	private int status;

	private String name;

	private EnumUserStatus(int status, String name) {
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
