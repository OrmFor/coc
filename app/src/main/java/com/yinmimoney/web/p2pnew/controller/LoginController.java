package com.yinmimoney.web.p2pnew.controller;

import cc.s2m.util.IDGenerator;
import cc.s2m.util.IpUtil;
import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.enums.EnumApiTokenStatus;
import com.yinmimoney.web.p2pnew.exception.BussinessException;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;
import com.yinmimoney.web.p2pnew.pojo.User;
import com.yinmimoney.web.p2pnew.requestbody.LoginRequestBody;
import com.yinmimoney.web.p2pnew.service.IApiToken;
import com.yinmimoney.web.p2pnew.service.IUser;
import com.yinmimoney.web.p2pnew.util.DateUtil;
import com.yinmimoney.web.p2pnew.view.LoginInfo;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/1.0")
public class LoginController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    @Autowired
    private IApiToken apiTokenService;
    @Autowired
    private IUser userService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultCode login4Pwd(@Valid @RequestBody LoginRequestBody loginRequestBody, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = getErrors(result);
            return new ResultCode(StatusCode.ERROR_LACK_PARAM, errors);
        }

        LOGGER.info("deviceId={}，deviceName={}，email={}，pwd={}", loginRequestBody.getDeviceId(),
                loginRequestBody.getDeviceName(), loginRequestBody.getEmail(), loginRequestBody.getPwd());

        // app登录处理
        LoginInfo loginInfo = apiTokenService.processApiToken(getIp(), loginRequestBody.getDeviceId(),
                loginRequestBody.getDeviceName(), loginRequestBody.getEmail(), loginRequestBody.getPwd());

        return new ResultCode(StatusCode.SUCCESS, loginInfo);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResultCode logout(HttpServletRequest request) {
        ApiToken apiToken = getApiToken();
        if (apiToken != null) {
            apiTokenService.updateApiTokenStatus(apiToken, EnumApiTokenStatus.STATUS_DISABLED);
        }

        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }

        return new ResultCode(StatusCode.SUCCESS);
    }

    @RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
    @ResponseBody
    public ResultCode refreshToken() {
        ApiToken apiToken = getApiToken();
        String token = IDGenerator.uuid();
        ApiToken update = new ApiToken();
        update.setId(apiToken.getId());
        update.setToken(token);
        update.setDateUpdate(DateUtil.getNow());
        apiTokenService.updateByPrimaryKeySelective(update);
        // 加密token给前端
        JSONObject json = new JSONObject();
        json.put("deviceId", apiToken.getDeviceId());
        json.put("token", token);
        String signToken = null;
        try {
            signToken = new String(Base64.encodeBase64(json.toJSONString().getBytes()), "UTF-8");
        } catch (Exception e) {
            throw new BussinessException(StatusCode.ERROR);
        }
        return new ResultCode(StatusCode.SUCCESS, signToken);
    }


    @RequestMapping(value = "/processMoneyButtonOauth", method = RequestMethod.POST)
    public ResultCode processOauth(HttpServletRequest request, HttpServletResponse response) {

        JSONObject json = getJsonFromRequest();
        String id = json.getString("id");
        String name = json.getString("name");
        String deviceId = json.getString("deviceId");
        String deviceName = json.getString("deviceName");
        if(StringUtils.isBlank(id) || StringUtils.isBlank(name)
                || StringUtils.isBlank(deviceId) || StringUtils.isBlank(deviceName)){
            return new ResultCode(StatusCode.ERROR_OAUTH_TOKEN_PROCESS);
        }

        User condi = new User();
        condi.setUserCode(id);
        User user = userService.getByCondition(condi);
        if(user == null) {
            User bean = new User();
            bean.setUserCode(id);
            bean.setUserName(name);
            userService.insert(bean);
        }else{
            try{
                if(!user.getUserName().equals(name)){
                    User update = new User();
                    update.setUserName(name);
                    User condiUser = new User();
                    condiUser.setUserCode(id);
                    userService.updateByCondition(update,condiUser);
                }
            }catch (Exception e){

            }

        }
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        session.setAttribute("auth",id);
        session.setMaxInactiveInterval( 1000 * 60 * 60 * 24 * 5 );
        LOGGER.info("sessionId第一次为{}",sessionId);
        LoginInfo loginInfo = apiTokenService.processApiToken(IpUtil.getIp(request), deviceId,
                deviceName, id, name);

        return new ResultCode(StatusCode.SUCCESS,loginInfo);

    }




}
