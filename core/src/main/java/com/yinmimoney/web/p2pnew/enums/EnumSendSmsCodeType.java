package com.yinmimoney.web.p2pnew.enums;

/**
 * 
 * @Description 短信验证码类型枚举类
 * @author wzq
 * @date 2018年6月27日 上午11:50:12
 */
public enum EnumSendSmsCodeType {

	/** 注册验证码 **/
	TYPE_REGISTER(1, "注册验证码"),
	/** 找回密码 **/
	TYPE_RESET_PASSWORD(2, "找回密码"),
	/** 快捷登录 **/
	TYPE_QUICK_LOGIN(3, "快捷登录");

	private int type;

	private String name;

	private EnumSendSmsCodeType(int type, String name) {
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

	public static EnumSendSmsCodeType getEnum(Integer type) {
		if (type == null)
			return null;
		EnumSendSmsCodeType[] items = EnumSendSmsCodeType.values();
		if (items != null && items.length > 0) {
			for (int i = 0; i < items.length; i++) {
				if (type == items[i].getType()) {
					return items[i];
				}
			}
		}
		return null;
	}

}
