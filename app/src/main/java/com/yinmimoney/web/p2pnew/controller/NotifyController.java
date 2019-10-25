package com.yinmimoney.web.p2pnew.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.enums.EnumTradeType;
import com.yinmimoney.web.p2pnew.pojo.City;
import com.yinmimoney.web.p2pnew.pojo.CityAdsPrice;
import com.yinmimoney.web.p2pnew.pojo.Platform;
import com.yinmimoney.web.p2pnew.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api")
public class NotifyController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(NotifyController.class);

    @Autowired
    private IPlatform platformService;
    @Autowired
    private IOrder orderService;
    @Autowired
    private ICityDetail cityDetailService;
    @Autowired
    private ICommonOrderDetail commonOrderDetailService;

    private String webHookSecret = "";

    private String getWebHookSecret() {
        Platform plat = platformService.getByPlatCode(Constant.PLAT_CODE);
        webHookSecret = plat.getWebhookSecret();
        return webHookSecret;
    }


    /**
     * {"payment":{"amountUsd":"0.01000051892326656","spendAmountSatoshis":"5125","createdAt":"2019-06-27T09:08:36.481Z","feeAmountUsd":"0.00046775302970344007","changeAmountUsd":"0.13734781320226513","paymentOutputs":[{"amount":"0.01","amountUsd":"0.01","address":"1KehiL6KzWtpRaYR87oxoiCpasrRMrsRmB","type":"ADDRESS","userId":"6093","satoshis":"4896","createdAt":"2019-06-27T09:08:36.504Z","paymentId":"194636","currency":"USD","id":"435267","updatedAt":"2019-06-27T09:08:36.504Z"}],"normalizedTxid":"12d56d35b917be23a69a95ddca3d1959648fa8a17bf7d56b91a46e2b8df38788","buttonId":"order_201906271708292074","inputAmountSatoshis":"72367","currency":"USD","id":"194636","changeAmountSatoshis":"67242","updatedAt":"2019-06-27T09:08:36.639Z","amount":"0.01000051892326656","clientId":"12148","feeAmountSatoshis":"229","txid":"2055d071ae866e87dcdb42b15b3ae9f854c0c8ec57faa03d10e364d622cd2728","inputAmountUsd":"0.14781608515523514","userId":"6584","spendAmountUsd":"0.010468271952970001","satoshis":"4896","referrerUrl":"http://localhost:3000/","browserUserAgent":"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36","user":{"onboardingCompletedAt":"2019-06-25T08:49:24.279Z","bio":"I like Money Button.","receiveEmails":true,"activeWalletId":"6530","gravatarKey":"2145a44c38beca64b90e4a1e0b8ba969","createdAt":"2019-06-25T08:47:12.958Z","emailVerified":false,"defaultLanguage":"en","defaultCurrency":"USD","name":"tora","id":"6584","activeHandleId":"51323","email":"837952519@qq.com","defaultHandle":{"createdAt":"2019-06-25T08:47:13.005Z","localPart":"6584","domain":"moneybutton.com","onSale":false,"id":"51323","userId":"6584","updatedAt":"2019-06-25T08:47:13.005Z"},"updatedAt":"2019-06-25T08:49:27.387Z"},"status":"RECEIVED"},"secret":"a9cbe14d7bab407abb7411128328d9c8"}
     **/

    @RequestMapping("/notify")
    public void notifyFromMoneyButton() {

        JSONObject json = getJsonFromRequest();
        LOGGER.info("notify:::[json={}]", json);
        String secret = json.getString("secret");
        if (StringUtils.isBlank(webHookSecret)) {
            getWebHookSecret();
        }

        if (!secret.equals(webHookSecret)) {
            return;
        }

        JSONObject payment = json.getJSONObject("payment");
        String buttonData = payment.getString("buttonData");
        if (StringUtils.isNotBlank(buttonData)) {
            JSONObject buttonStr = JSON.parseObject(buttonData);
            String type = buttonStr.getString("type");
            if ("Ads".equals(type)) {
                commonOrderDetailService.processAds(payment);
            } else if ("Like".equals(type)) {
                type = EnumTradeType.LIKE.getName();
                commonOrderDetailService.processCallBackData(type, payment);
            } else if ("Msg".equals(type)) {
                type = EnumTradeType.SPEAK.getName();
                commonOrderDetailService.processCallBackData(type, payment);
            } else if ("ChangeName".equals(type)) {
                type = EnumTradeType.SET_HOUSE_NAME.getName();
                commonOrderDetailService.processCallBackData(type, payment);
            } else if ("ChatRoomAds".equals(type)) {
                type = EnumTradeType.CHATROOM_ADS.getName();
                commonOrderDetailService.processCallBackData(type, payment);
            } else if("CityPicture".equals(type)){
                type = EnumTradeType.CITY_PICTURE.getName();
                commonOrderDetailService.processCallBackData(type,payment);
            } else if("PictureLike".equals(type)){
                type = EnumTradeType.PICTURE_LIKE.getName();
                commonOrderDetailService.processCallBackData(type,payment);
            }else if("cityPictureOpen".equals(type)){
                type=EnumTradeType.CITY_PICTURE_OPEN.getName();
                commonOrderDetailService.processCallBackData(type,payment);
            }else if("CityAdsPrice".equals(type)){//城市竞价排名回调
                type = EnumTradeType.CITY_ADS_PRICE.getName();
                commonOrderDetailService.processCallBackData(type,payment);
            }else if("ModifyCityAdsPrice".equals(type)){//城市竞价排名回调
                type = EnumTradeType.CITY_ADS_PRICE.getName();
                commonOrderDetailService.processCallBackData(type,payment);
            }
        } else {
            orderService.processWebHook(payment);
        }
    }

    @RequestMapping("/oauth")
    public void notifyOauthFromMoneyButton(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }


    }

    @RequestMapping("/notify1")
    public void notify1() {

        JSONObject json = getJsonFromRequest();
        LOGGER.info("notify:::[json={}]", json);
        String secret = json.getString("secret");
        if (StringUtils.isBlank(webHookSecret)) {
            getWebHookSecret();
        }

        if (!secret.equals(webHookSecret)) {
            return;
        }

        JSONObject payment = json.getJSONObject("payment");
        String buttonData = payment.getString("buttonData");
        if (StringUtils.isNotBlank(buttonData)) {
            JSONObject buttonStr = JSON.parseObject(buttonData);
            String type = buttonStr.getString("type");
            if ("ChatRoomAds".equals(type)) {
                type = EnumTradeType.CHATROOM_ADS.getName();
                commonOrderDetailService.processCallBack(type, payment);
            }
        }
    }



    @Autowired
    private ICityAdsPrice cityAdsPriceService;
    @RequestMapping("/notify11")
    public void processAdsPrice() {
        JSONObject json = getJsonFromRequest();
        String userCode = json.getString("userCode");
        String cityCode = json.getString("cityCode");
        CityAdsPrice    bean = new CityAdsPrice();
        bean.setIsExpire(Boolean.valueOf(true));
        CityAdsPrice condi = new CityAdsPrice();
        condi.setUserCode(userCode);
        condi.setCityCode(cityCode);
        cityAdsPriceService.updateByCondition(bean,condi);
    }

    @Autowired
    private ICity cityService;

    @RequestMapping(value = "/getCity")
    public ResultCode getCity() {
        City bean = new City();
        bean.setIsShow(true);
        bean.setOrderBy("id asc");
        List<City> list = cityService.getList(bean);
        List<CityBean> lists = new ArrayList<>();
        for(City city : list){
            CityBean b = new CityBean();
            b.setCityCode(city.getCityCode());
            b.setCityName(city.getCityName());
            b.setLat(city.getLat());
            b.setLon(city.getLon());
            lists.add(b);
        }

        return new ResultCode(StatusCode.SUCCESS,lists);

    }


}

class CityBean{
    private String cityCode;
    private String cityName;
    private java.math.BigDecimal lat;
    private java.math.BigDecimal lon;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }
}
