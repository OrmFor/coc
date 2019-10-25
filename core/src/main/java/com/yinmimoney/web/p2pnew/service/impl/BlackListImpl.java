package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.BlackListMapper;
import com.yinmimoney.web.p2pnew.dto.BlackPermissionsDto;
import com.yinmimoney.web.p2pnew.pojo.BlackList;
import com.yinmimoney.web.p2pnew.service.IBlackList;
import com.yinmimoney.web.p2pnew.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BlackListImpl extends BaseServiceImpl<BlackList, BlackListMapper, Integer> implements IBlackList {
    @Autowired
    private BlackListMapper blackListMapper;

    protected BlackListMapper getDao() {
        return blackListMapper;
    }

    @Override
    public List<BlackPermissionsDto> getBlackPermissions(String userCode) {
        List<Map<String, Object>> blackPermissions = blackListMapper.getBlackPermissions(userCode);
        List<BlackPermissionsDto> blackPermissionsDtos= new ArrayList();
        blackPermissions.forEach(x->{
            BlackPermissionsDto blackPermissionsDto = new BlackPermissionsDto();
            blackPermissionsDto.setName((String) x.get("name"));
            blackPermissionsDto.setNid((String) x.get("nid"));
            Object isValid = x.get("isValid");
            if(isValid==null||(Integer)isValid==1) {
                blackPermissionsDto.setIsOpen(1);
            }else{
                blackPermissionsDto.setIsOpen(0);
            }
            blackPermissionsDtos.add(blackPermissionsDto);
        });
        return blackPermissionsDtos;
    }

    @Override
    public boolean isBlackPermission(String userCode, String permissionNid) {
        if(StringUtil.isAnyBlank(userCode,permissionNid)){
            return false;
        }
        BlackList cond = new BlackList();
        cond.setUserCode(userCode);
        cond.setPermissionsNid(permissionNid);
        cond.setIsValid(0);
        BlackList byCondition = getByCondition(cond);
        if(byCondition!=null){
            return true;
        }
        return false;
    }
}