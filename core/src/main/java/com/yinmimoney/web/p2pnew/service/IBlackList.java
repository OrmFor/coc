package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.dto.BlackPermissionsDto;
import com.yinmimoney.web.p2pnew.enums.EnumBlackPermissionsType;
import com.yinmimoney.web.p2pnew.pojo.BlackList;
import cc.s2m.web.utils.webUtils.service.BaseService;

import java.util.List;

public interface IBlackList extends BaseService<BlackList, Integer> {
    /**
     *@description 获取用户黑名单权限列表
     *@param userCode 用户编码
     *@return
     */
     List<BlackPermissionsDto> getBlackPermissions(String userCode);

     /**
      *@description 判断用户某一项权限是否在黑名单
      *@param userCode 用户编码
      *@param permissionNid EnumBlackPermissionsType nid
      *@see EnumBlackPermissionsType
      *@return
      */
     boolean isBlackPermission(String userCode,String permissionNid);
}