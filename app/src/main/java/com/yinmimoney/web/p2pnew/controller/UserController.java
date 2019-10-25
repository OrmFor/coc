package com.yinmimoney.web.p2pnew.controller;

import cc.s2m.util.IpUtil;
import cc.s2m.util.Page;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.dto.CityPictureDto;
import com.yinmimoney.web.p2pnew.dto.SumMoneyDto;
import com.yinmimoney.web.p2pnew.enums.EnumCitySellStatus;
import com.yinmimoney.web.p2pnew.pojo.*;
import com.yinmimoney.web.p2pnew.requestbody.ModifyCityDetailRequestBody;
import com.yinmimoney.web.p2pnew.requestbody.ModifyUrlAndMessageRequestBody;
import com.yinmimoney.web.p2pnew.requestbody.ResetPwdResquestBody;
import com.yinmimoney.web.p2pnew.requestbody.SaveAddressAndPriceRequestBody;
import com.yinmimoney.web.p2pnew.responsebody.MyCitiesResponseBody;
import com.yinmimoney.web.p2pnew.responsebody.MyCityDetailResponseBody;
import com.yinmimoney.web.p2pnew.responsebody.UserInfoResponseBody;
import com.yinmimoney.web.p2pnew.service.*;
import com.yinmimoney.web.p2pnew.util.BeanCovertUtil;
import com.yinmimoney.web.p2pnew.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("api_UserController")
@RequestMapping("/api/1.0")
public class UserController extends BaseController {
    private static final String MODULE_NAME = "用户列表";

    private static final Logger logger = LogManager.getLogger(UserController.class);


    @Autowired
    private IUser userService;
    @Autowired
    private IOrderDetail orderDetailService;
    @Autowired
    private ICity cityService;
    @Autowired
    private ICityDetail cityDetailService;

    @Autowired
    private INotice noticeService;

    @Autowired
    private IMessageFee messageFeeService;

    @Autowired
    private IChatHouse chatHouseService;

    @Autowired
    private ICityPicture cityPictureService;
    @Autowired
    private ILikeLog likeLogService;


    @Deprecated
    @RequestMapping(value = "/oauth", method = RequestMethod.POST)
    public ResultCode oauth() {
        JSONObject json = getJsonFromRequest();
        String code = json.getString("code");
        return userService.processOauth(code, getApiToken().getUserCode());
    }

    @Deprecated
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    public ResultCode resetPwd(@Valid @RequestBody ResetPwdResquestBody body, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = getErrors(result);
            return new ResultCode(StatusCode.ERROR_LACK_PARAM, errors);
        }
        return userService.processResetPwd(body);
    }

    @Deprecated
    @RequestMapping(value = "/sendMailToResetPwd", method = RequestMethod.POST)
    public ResultCode sendMailToResetPwd() {
        JSONObject json = getJsonFromRequest();
        String email = json.getString("email");
        if (StringUtils.isBlank(email)) {
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        User user = userService.selectByEmail(email);
        if (user == null) {
            return new ResultCode(StatusCode.ERROR_EMAIL_NOT_EXISTS);
        }
        String userCode = user.getUserCode();
        noticeService.sendEamilToResetPwd(userCode, email, IpUtil.getIp(getRequest()));
        return new ResultCode(StatusCode.SUCCESS);
    }

    @Deprecated
    @RequestMapping(value = "/sendMailToOauth", method = RequestMethod.POST)
    public ResultCode sendMailToOauth() {
        JSONObject json = getJsonFromRequest();
        String email = json.getString("email");
        if (StringUtils.isBlank(email)) {
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        User user = userService.selectByEmail(email);
        if (user == null) {
            return new ResultCode(StatusCode.ERROR_EMAIL_NOT_EXISTS);
        }
        String userCode = user.getUserCode();
        noticeService.sendEamilToOauth(userCode, email, IpUtil.getIp(getRequest()));
        return new ResultCode(StatusCode.SUCCESS);
    }


    @RequestMapping("/userInfo")
    public ResultCode userInfo() {
        ApiToken apiToken = getApiToken();
        User user = userService.selectByUserCode(apiToken.getUserCode());
        SumMoneyDto money = orderDetailService.getSumAmount(apiToken.getUserCode());

        UserInfoResponseBody body = BeanCovertUtil.beanCovert(user, UserInfoResponseBody.class);
        HashMap<String, Object> data = Maps.newHashMap();
        data.put("money", money == null ? new SumMoneyDto() : money);
        data.put("user", body);
        return new ResultCode(StatusCode.SUCCESS, data);
    }


    /**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 修改钱包地址
     * @Date 17:00 2019/6/24
     * @Param [walletAddress]
     **/
    @Deprecated
    @RequestMapping(value = "/modifyAddress", method = RequestMethod.POST)
    public ResultCode modifyAddress() {
        JSONObject json = getJsonFromRequest();
        String walletAddress = json.getString("walletAddress");
        String pwd = json.getString("pwd");
        if (StringUtils.isBlank(walletAddress) || StringUtils.isBlank(pwd)) {
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }

        String userCode = getApiToken().getUserCode();

        userService.modifyAddress(pwd, walletAddress, userCode);
        return new ResultCode(StatusCode.SUCCESS);

    }

    /**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 添加钱包地址
     * @Date 17:00 2019/6/24
     * @Param [walletAddress]
     **/
    @Deprecated
    @RequestMapping(value = "/saveAddress", method = RequestMethod.POST)
    public ResultCode saveAddress() {
        JSONObject json = getJsonFromRequest();
        String walletAddress = json.getString("walletAddress");
        if (StringUtils.isBlank(walletAddress)) {
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        ApiToken apiToken = getApiToken();
        User update = new User();
        //update.setWalletAddress(walletAddress);
        User condi = new User();
        condi.setUserCode(apiToken.getUserCode());
        userService.updateByCondition(update, condi);
        return new ResultCode(StatusCode.SUCCESS);

    }


    /**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 出售价格 钱包地址已去掉
     * @Date 14:12 2019/6/26
     * @Param [body]
     **/
    @RequestMapping(value = "/saveAddressAndPrice", method = RequestMethod.POST)
    @Deprecated
    public ResultCode saveAddressAndPrice(@Valid @RequestBody SaveAddressAndPriceRequestBody body,
                                          BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = getErrors(result);
            return new ResultCode(StatusCode.ERROR_LACK_PARAM, errors);
        }
        ApiToken apiToken = getApiToken();
        body.setUserCode(apiToken.getUserCode());
        userService.saveAddressAndPrice(body);
        return new ResultCode(StatusCode.SUCCESS);

    }


    /**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 账户中心获取我的城市（默认五条）
     * @Date 16:55 2019/6/24
     * @Param []
     **/
    @RequestMapping("/myCities")
    public ResultCode myCities() {
        ApiToken apiToken = getApiToken();
        if (apiToken == null) {
            return new ResultCode(StatusCode.ERROR_504);
        }
        User user = userService.selectByUserCode(apiToken.getUserCode());
        CityDetail cityDetail = new CityDetail();
        cityDetail.setUserCode(user.getUserCode());
        int total = cityDetailService.getCount(cityDetail);

        HashMap<String, Object> data = Maps.newHashMap();
        /*List<MyCitiesResponseBody> list = Lists.newArrayList();

        if (total == 0) {
            data.put("list", list);
            data.put("total", total);
            return new ResultCode(StatusCode.SUCCESS, data);
        }*/

        List<MyCitiesResponseBody> list = cityService.getMyCities(user.getUserCode(), 5);
        data.put("list", list);
        data.put("total", total);
        return new ResultCode(StatusCode.SUCCESS, data);
    }

    /**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 获取我的所有城市
     * @Date 16:56 2019/6/24
     * @Param []
     **/
    @RequestMapping("/getMyAllCities")
    public ResultCode getMyAllCities() {
        ApiToken apiToken = getApiToken();
        if (apiToken == null) {
            return new ResultCode(StatusCode.ERROR_504);
        }
        User user = userService.selectByUserCode(apiToken.getUserCode());
        List<MyCitiesResponseBody> list = cityService.getMyCities(user.getUserCode(), 5000);
        HashMap<String, Object> data = Maps.newHashMap();
        data.put("list", list);
        data.put("total", list == null ? 0 : list.size());
        return new ResultCode(StatusCode.SUCCESS, data);
    }


    /**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 我的交易记录
     * @Date 17:02 2019/6/24
     * @Param []
     **/
    @RequestMapping("/getMyOrderDetails")
    public ResultCode getMyOrderDetails() {
        JSONObject json = getJsonFromRequest();
        Integer pageNumber = json.getInteger("pageNumber");
        ApiToken apiToken = getApiToken();
        User user = userService.selectByUserCode(apiToken.getUserCode());
        Map<String, Object> data = orderDetailService.getMyOrderDetails(user.getUserCode(), pageNumber,
                getRequest().getParameterMap());
        /*HashMap<String, Object> data = Maps.newHashMap();
        data.put("list", list);
        data.put("total", list == null ? 0 : list.size());*/
        return new ResultCode(StatusCode.SUCCESS, data);
    }


    /**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 修改城市信息  只修改价格和暂不出售
     * @Date 17:36 2019/6/24
     * @Param []
     **/
    @RequestMapping("/modifyCityDetail")
    public ResultCode modifyCityDetail(@RequestBody ModifyCityDetailRequestBody modifyCityDetailRequestBody,
                                       BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = getErrors(result);
            return new ResultCode(StatusCode.ERROR_LACK_PARAM, errors);
        }
        if (modifyCityDetailRequestBody.getAmount() != null &&
                modifyCityDetailRequestBody.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return new ResultCode(StatusCode.ERROR_AMOUNT);
        }
        ApiToken apiToken = getApiToken();
        modifyCityDetailRequestBody.setUserCode(apiToken.getUserCode());
        cityDetailService.modifyCityDetail(modifyCityDetailRequestBody);
        return new ResultCode(StatusCode.SUCCESS);
    }


    @RequestMapping("/getMyCityDetail")
    public ResultCode getMyCityDetail() {
        JSONObject json = getJsonFromRequest();
        String cityCode = json.getString("cityCode");
        ApiToken apiToken = getApiToken();
        MyCityDetailResponseBody body = cityDetailService.getMyCityDetail(apiToken.getUserCode(), cityCode);
        return new ResultCode(StatusCode.SUCCESS, body);
    }


    /**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 设置广告留言和url  收费
     * @Date 13:39 2019/7/21
     * @Param [body, result]
     **/
    @RequestMapping("/modifyMesaageAndUrl")
    public ResultCode modifyMesaageAndUrl(@Valid @RequestBody ModifyUrlAndMessageRequestBody body,
                                          BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = getErrors(result);
            return new ResultCode(StatusCode.ERROR_LACK_PARAM, errors);
        }

        ApiToken apiToken = getApiToken();
        String userCode = apiToken.getUserCode();
        body.setUserCode(userCode);
        cityDetailService.modifyMesaageAndUrl(body);
        return new ResultCode(StatusCode.SUCCESS);
    }

    @RequestMapping("/getMyMessageOrders")
    public ResultCode getMyMessageOrders() {
        JSONObject json = getJsonFromRequest();
        Integer pageNumber = json.getInteger("pageNumber");
        ApiToken apiToken = getApiToken();
        User user = userService.selectByUserCode(apiToken.getUserCode());
        Map<String, Object> data = messageFeeService.getMyMessageFeeDetail(user.getUserCode(), pageNumber,
                getRequest().getParameterMap());

        return new ResultCode(StatusCode.SUCCESS, data);
    }

    @RequestMapping("/ownCity/userInfo")
    public ResultCode getOwnCityUserInfo() {
        JSONObject json = getJsonFromRequest();
        String cityCode = json.getString("cityCode");
        if (StringUtil.isBlank(cityCode)) {
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        JSONObject jsonResult = new JSONObject();
        CityDetail cityDetail = cityDetailService.selectByCityCode(cityCode);
        if (cityDetail == null) {
            Platform platFormInfo = getPlatFormInfo();
            jsonResult.put("userCode", platFormInfo.getWalletAddress());
            jsonResult.put("price", platFormInfo.getAmount());
            jsonResult.put("sellStatus", EnumCitySellStatus.SELL.getStatus());
            jsonResult.put("ads", null);
            jsonResult.put("adsUrl", null);
        } else {
            jsonResult.put("userCode", cityDetail.getUserCode());
            jsonResult.put("price", cityDetail.getAmount());
            jsonResult.put("sellStatus", cityDetail.getCitySellStatus());
            jsonResult.put("ads", cityDetail.getMessage());
            jsonResult.put("adsUrl", cityDetail.getUrl());
        }
        //获取聊天室名称
        ChatHouse chatHouse = chatHouseService.selectByCityCode(cityCode);
        jsonResult.put("houseName", chatHouse == null ? null : chatHouse.getHouseName());
        //获取城市名称
        City city = cityService.selectByCityCode(cityCode);
        jsonResult.put("cityName", city == null ? null : city.getCityName());
        return new ResultCode(StatusCode.SUCCESS, jsonResult);
    }


    @RequestMapping("/isCityOwner")
    public ResultCode IsCityOwner() {
        String userCode = getApiToken().getUserCode();
        JSONObject json = getJsonFromRequest();
        String cityCode = json.getString("cityCode");
        if (StringUtils.isBlank(cityCode)) {
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        CityDetail detail = cityDetailService.selectByCityCode(cityCode);
        if (detail == null || !userCode.equals(detail.getUserCode())) {
            return new ResultCode(StatusCode.SUCCESS, Boolean.FALSE);
        }
        return new ResultCode(StatusCode.SUCCESS, Boolean.TRUE);
    }

    //获取我上传的图片
    @RequestMapping("/getMyPicture")
    public ResultCode getMyPicture() {
        String userCode = getApiToken().getUserCode();
        JSONObject json = getJsonFromRequest();
        Integer pageSize = json.getInteger("pageSize");
        Integer pageNumber = json.getInteger("pageNumber");

        CityPicture condi = new CityPicture();
        condi.setUserCode(userCode);
        condi.setIsShow(Boolean.TRUE);
        condi.setPageSize(pageSize);
        condi.setPageNumber(pageNumber);

        condi.setOrderBy("id desc");

        Page<CityPicture> page = cityPictureService.getPage(condi, getRequest().getParameterMap());

        List<CityPictureDto> dtos = new ArrayList<>();
        int total = 0;
        if (page != null) {
            List<CityPicture> list = page.getResult();
            if (list != null && list.size() > 0) {

                for (CityPicture cityPicture : list) {
                    CityPictureDto dto = new CityPictureDto();
                    dto.setCityCode(cityPicture.getCityCode());
                    dto.setCityName(cityPicture.getCityName());
                    dto.setLikeNum(cityPicture.getLikeNumber());
                    dto.setPictureUserCode(cityPicture.getUserCode());
                    User user = userService.selectByUserCode(cityPicture.getUserCode());
                    dto.setPictureUserName(user == null ? "" : user.getUserName());
                    dto.setTxid(cityPicture.getTxid());

                    CityDetail detail = cityDetailService.selectByCityCode(cityPicture.getCityCode());
                    dto.setCityUserCode(detail == null ? Constant.PLAT_CODE : detail.getUserCode());
                    dto.setPictureCode(cityPicture.getPictureCode());
                    dto.setCreateTime(cityPicture.getCreateTime());

                    LikeLog likeLog = new LikeLog();
                    likeLog.setChatCode(cityPicture.getPictureCode());
                    likeLog.setUserCode(userCode);
                    likeLog.setIsValid(0);
                    System.out.println(MessageFormat.format("userCode={0},pictureCode={1}", userCode
                            , cityPicture.getPictureCode()));
                    logger.info(MessageFormat.format("userCode={0}", userCode));
                    logger.info(MessageFormat.format("pictureCode={0}", cityPicture.getPictureCode()));

                    LikeLog byCondition = likeLogService.getByCondition(likeLog);
                    if (byCondition != null) {
                        dto.setIsLike(1);
                    }
                    dtos.add(dto);
                }
                total = page.getTotalRow();
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("list", dtos);
        data.put("totalCount", total);


        return new ResultCode(StatusCode.SUCCESS, data);
    }


}