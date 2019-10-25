package com.yinmimoney.web.p2pnew.enums;

/**
 * 
 * @Description 后台管理员状态枚举类
 * @author wzq
 * @date 2018年6月7日 上午11:34:43
 */
public enum EnumAdminStatus {

	/** 正常 **/
	STATUS_NORMAL(0, "正常"),
	/** 锁定 **/
	STATUS_LOCK(1, "锁定");

	private int status;

	private String name;

	private EnumAdminStatus(int status, String name) {
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
