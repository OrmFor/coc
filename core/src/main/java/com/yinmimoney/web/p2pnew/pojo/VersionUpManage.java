package com.yinmimoney.web.p2pnew.pojo;

import com.yinmimoney.web.p2pnew.pojo.entity.VersionUpManageEntity;

public class VersionUpManage extends VersionUpManageEntity {
	private static final long serialVersionUID = 1L;

	private String versionLevelStr; // 版本等级，1：低，2：中，3：高

	public String getVersionLevelStr() {
		switch (getVersionLevel()) {
		case 1:
			versionLevelStr = "低";
			break;
		case 2:
			versionLevelStr = "中";
			break;
		case 3:
			versionLevelStr = "高";
			break;
		default:
			break;
		}
		return versionLevelStr;
	}

	public void setVersionLevelStr(String versionLevelStr) {
		this.versionLevelStr = versionLevelStr;
	}

}