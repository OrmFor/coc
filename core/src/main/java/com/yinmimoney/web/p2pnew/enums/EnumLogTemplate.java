package com.yinmimoney.web.p2pnew.enums;

/**
 * 
 * @Description 日志模板枚举类
 * @author wzq
 * @date 2018年6月27日 上午11:50:12
 */
public enum EnumLogTemplate {

	;

	private String key;

	private String name;

	private EnumLogTemplate(String key, String name) {
		this.key = key;
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
