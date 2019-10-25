package com.yinmimoney.web.p2pnew.enums;

/**
 * 
 * @Description 所属系统类型枚举类
 * @author wzq
 * @date 2018年6月27日 上午11:50:12
 */
public enum EnumSysType {

	/** 后台系统 **/
	TYPE_ADMING_SYS(0, "后台系统"),
	/** APP系统 **/
	TYPE_APP_SYS(1, "APP系统");

	private int type;

	private String name;

	private EnumSysType(int type, String name) {
		this.type = type;
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
