package com.yinmimoney.web.p2pnew.enums;

/**
 * 
 * @Description 生成用户编号类型枚举类
 * @author wzq
 * @date 2018年6月7日 上午11:34:43
 */
public enum EnumUserCodeType {

	/** 用户 **/
	TYPE_USER("u", "用户"),
	/** 管理员 **/
	TYPE_ADMIN("a", "管理员"),
	/** 视频 **/
	TYPE_MOVIE("m", "视频");

	private String type;

	private String name;

	private EnumUserCodeType(String type, String name) {
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
