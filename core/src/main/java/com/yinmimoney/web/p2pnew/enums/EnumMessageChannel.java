package com.yinmimoney.web.p2pnew.enums;

/**
 * 
 * @Description 短信渠道枚举类
 * @author wzq
 * @date 2018年6月27日 上午11:50:12
 */
public enum EnumMessageChannel {

	/** 助通 **/
	TYPE_SMS_ZT(1, "助通"),
	/** 中聚元 **/
	TYPE_SMS_ZJY(2, "中聚元"),
	/** 梦网 **/
	TYPE_SMS_MW(3, "梦网");

	private int type;

	private String name;

	private EnumMessageChannel(int type, String name) {
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
