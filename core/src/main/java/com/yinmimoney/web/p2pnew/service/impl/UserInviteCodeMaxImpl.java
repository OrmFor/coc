package com.yinmimoney.web.p2pnew.service.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.dao.UserInviteCodeMaxMapper;
import com.yinmimoney.web.p2pnew.exception.BussinessException;
import com.yinmimoney.web.p2pnew.pojo.UserInviteCodeMax;
import com.yinmimoney.web.p2pnew.service.IUserInviteCodeMax;

import cc.s2m.util.IDGenerator;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class UserInviteCodeMaxImpl extends BaseServiceImpl<UserInviteCodeMax, UserInviteCodeMaxMapper, java.lang.Integer> implements IUserInviteCodeMax {

	private static Random RND_INVITE_CODE_BASE = new Random(System.currentTimeMillis());

	static {
		IDGenerator.initMinUrlMem("123456789ABCDEFGHJKLMNPQRSTUVWXYZ");
	}

    @Autowired
    private UserInviteCodeMaxMapper userInviteCodeMaxMapper;

    protected UserInviteCodeMaxMapper getDao() {
        return userInviteCodeMaxMapper;
    }

	public String processInviteCodeMetch() {
		int rndNumber = RND_INVITE_CODE_BASE.nextInt(100) + 1;
		UserInviteCodeMax condition = new UserInviteCodeMax();
		condition.setId(1);
		int rows = updateIncreateNumbers(condition, new String[] { "cur_number" }, new Integer[] { rndNumber });
		if (rows != 1) {
			throw new BussinessException(StatusCode.ERROR);
		}
		int inviteCode = selectByPrimaryKey(1).getCurNumber().intValue();
		String inviteCodeStr = IDGenerator.minUrlId(inviteCode);
		return inviteCodeStr;
	}

}