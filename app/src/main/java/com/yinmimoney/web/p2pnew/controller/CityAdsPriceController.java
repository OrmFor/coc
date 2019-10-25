package com.yinmimoney.web.p2pnew.controller;

import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.dto.CityAdsPriceDetailDto;
import com.yinmimoney.web.p2pnew.dto.CityAdsPriceDto;
import com.yinmimoney.web.p2pnew.dto.CityDto;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;
import com.yinmimoney.web.p2pnew.requestbody.CityAdsPriceRequestBody;
import com.yinmimoney.web.p2pnew.service.ICityAdsPrice;
import com.yinmimoney.web.p2pnew.service.ICityDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/1.0")
public class CityAdsPriceController extends BaseController {

    @Autowired
    private ICityAdsPrice cityAdsPriceService;
    @Autowired
    private ICityDetail cityDetailService;

    @RequestMapping("/processCityAdsPrice")
    public ResultCode processCityAdsPrice(@Valid @RequestBody CityAdsPriceRequestBody body,
                                          BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = getErrors(result);
            return new ResultCode(StatusCode.ERROR_LACK_PARAM, errors);
        }

        ApiToken apiToken = getApiToken();
        String userCode = apiToken.getUserCode();
        body.setUserCode(userCode);
        cityAdsPriceService.processCityAdsPrice(body);
        return new ResultCode(StatusCode.SUCCESS);
    }



    @RequestMapping("/modifyCityAdsPrice")
    public ResultCode modifyCityAdsPrice(@Valid @RequestBody CityAdsPriceRequestBody body,
                                          BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = getErrors(result);
            return new ResultCode(StatusCode.ERROR_LACK_PARAM, errors);
        }

        ApiToken apiToken = getApiToken();
        String userCode = apiToken.getUserCode();
        body.setUserCode(userCode);
        cityAdsPriceService.modifyCityAdsPrice(body);
        return new ResultCode(StatusCode.SUCCESS);
    }

    @RequestMapping("/getMyCityList")
    public ResultCode getMyCityList() {
      ApiToken apiToken = getApiToken();
       String userCode = apiToken.getUserCode();
       List<CityDto> list = cityDetailService.getMyCityList(userCode);
       return new ResultCode(StatusCode.SUCCESS,list);
    }


    @RequestMapping("/getCityAdsPriceList")
    public ResultCode getCityAdsPriceList(){
        JSONObject json = getJsonFromRequest();
        Integer pageNumber = json.getInteger("pageNumber");
        Integer pageSize = json.getInteger("pageSize");
        if(pageNumber == null || pageNumber < 0){
            pageNumber = 1;
        }
        if(pageSize == null || pageSize < 0 ){
            pageSize = 10;
        }

        Map<String, Object> data = cityAdsPriceService.getCityAdsPriceList(pageNumber,pageSize);

        return new ResultCode(StatusCode.SUCCESS,data);
    }


    @RequestMapping("/getByCityCodeAndUserCode")
    public ResultCode getByCityCodeAndUserCode(){
        JSONObject json = getJsonFromRequest();
        ApiToken apiToken = getApiToken();
        String userCode = apiToken.getUserCode();
        String cityCode = json.getString("cityCode");
        if(StringUtils.isBlank(cityCode) || StringUtils.isBlank(userCode)){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        CityAdsPriceDetailDto dto = cityAdsPriceService.getByCityCodeAndUserCode(cityCode, userCode);
        return new ResultCode(StatusCode.SUCCESS,dto);
    }



    @RequestMapping("/getMyCityAdsPrice")
    public ResultCode getMyCityAdsPrice(){
        ApiToken apiToken = getApiToken();
        String userCode = apiToken.getUserCode();
        /*Integer pageNumber = json.getInteger("pageNumber");
        Integer pageSize = json.getInteger("pageSize");
        if(pageNumber == null || pageNumber < 0){
            pageNumber = 1;
        }
        if(pageSize == null || pageSize < 0 ){
            pageSize = 10;
        }*/
        List<CityAdsPriceDto> dto = cityAdsPriceService.getMyCityAdsPriceList( userCode);
        return new ResultCode(StatusCode.SUCCESS,dto);
    }

}
