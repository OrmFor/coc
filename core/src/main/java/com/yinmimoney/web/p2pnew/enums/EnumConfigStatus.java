package com.yinmimoney.web.p2pnew.enums;

/**
 * 
 * @Description 配置状态枚举类
 * @author wzq
 * @date 2018年6月7日 上午11:34:43
 */
public enum EnumConfigStatus {

	/** 禁用 **/
	STATUS_CLOSE(0, "禁用"),
	/** 启用 **/
	STATUS_OPEN(1, "启用");

	private int status;

	private String name;

	private EnumConfigStatus(int status, String name) {
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
