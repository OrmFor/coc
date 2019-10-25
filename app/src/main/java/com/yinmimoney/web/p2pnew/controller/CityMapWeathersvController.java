package com.yinmimoney.web.p2pnew.controller;

import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.pojo.CityMapWeathersv;
import com.yinmimoney.web.p2pnew.service.ICityMapWeathersv;
import com.yinmimoney.web.p2pnew.util.HttpUtil;
import com.yinmimoney.web.p2pnew.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dyf on 2019/8/27 13:46
 */
@RestController(value = "app_cityMapWeathersvController")
@RequestMapping("/app")
public class CityMapWeathersvController extends BaseController{
    private final static String WEATHER_DATA_URL="https://weathersv.app/api/channel/";
    private final static String WEATHER_SKIP_URL="https://weathersv.app/find";

    @Autowired
    private ICityMapWeathersv cityMapWeathersvService;

    @RequestMapping("/weathersv/data")
    public ResultCode getData(){
        JSONObject json = getJsonFromRequest();
        String cityCode = json.getString("cityCode");
        if(StringUtil.isBlank(cityCode)){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        CityMapWeathersv cityMapWeathersv = cityMapWeathersvService.selectByCityCode(cityCode);
        if(cityMapWeathersv==null){
            return new ResultCode(StatusCode.ERROR_PARAM);
        }
        String requestDataUrl=WEATHER_DATA_URL+cityMapWeathersv.getWsId();
        String get = HttpUtil.executeHttpRequest(requestDataUrl, "GET", null);
        JSONObject result = new JSONObject();
        result.put("isOpen",Boolean.FALSE);
        result.put("skipUrl",WEATHER_SKIP_URL+"?lat="+cityMapWeathersv.getLat()+"&lon="+cityMapWeathersv.getLon());
        if(get!=null){
            JSONObject jsonObject = JSONObject.parseObject(get);
            JSONObject current = jsonObject.getJSONObject("current");
            if(current!=null){
                result.put("isOpen",Boolean.TRUE);
                result.put("current",current);
            }
        }
        return new ResultCode(StatusCode.SUCCESS,result);
    }
}
