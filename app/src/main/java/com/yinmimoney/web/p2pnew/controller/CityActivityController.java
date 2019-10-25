package com.yinmimoney.web.p2pnew.controller;

import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.pojo.City;
import com.yinmimoney.web.p2pnew.pojo.CityActivity;
import com.yinmimoney.web.p2pnew.service.ICity;
import com.yinmimoney.web.p2pnew.service.ICityActivity;
import com.yinmimoney.web.p2pnew.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dyf on 2019/7/31 19:08
 */
@RestController("api_cityActivityController")
@RequestMapping("/api/cityActivity")
public class CityActivityController extends BaseController {
    @Autowired
    private ICityActivity cityActivityService;
    @Autowired
    private ICity cityService;
    @RequestMapping("/list")
    public ResultCode getOpeningActivity(){
        CityActivity cond = new CityActivity();
        cond.setIsDelete(0);
        cond.and(Expressions.le("start_time", DateUtil.getNow()));
        cond.and(Expressions.ge("end_time",DateUtil.getDayStart(DateUtil.getNow())));
        List<CityActivity> list = cityActivityService.getList(cond);
        City cityCond = new City();
        list.forEach(x->{
            cityCond.setCityCode(x.getCityCode());
            City city = cityService.getByCondition(cityCond);
            if(city!=null){
                x.setLat(city.getLat());
                x.setLon(city.getLon());
            }
        });
        return new ResultCode(StatusCode.SUCCESS,list);
    }
}
