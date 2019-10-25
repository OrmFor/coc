package com.yinmimoney.web.p2pnew.enums;

/**
 * 
 * @Description 生成订单号类型枚举类
 * @author wzq
 * @date 2018年6月7日 上午11:34:43
 */
public enum EnumOrderNoType {

	/** 购买订单号 **/
	TYPE_BUY_NO("by", "购买订单号");

	private String type;

	private String name;

	private EnumOrderNoType(String type, String name) {
		this.name = name;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

}
