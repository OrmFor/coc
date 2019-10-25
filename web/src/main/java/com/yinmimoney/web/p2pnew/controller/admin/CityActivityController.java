package com.yinmimoney.web.p2pnew.controller.admin;

import cc.s2m.util.Page;
import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.enums.EnumCityActivityStatus;
import com.yinmimoney.web.p2pnew.pojo.City;
import com.yinmimoney.web.p2pnew.pojo.CityActivity;
import com.yinmimoney.web.p2pnew.service.ICity;
import com.yinmimoney.web.p2pnew.service.ICityActivity;
import com.yinmimoney.web.p2pnew.util.DateUtil;
import com.yinmimoney.web.p2pnew.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by dyf on 2019/7/31 18:03
 */
@RestController("admin_cityActivityController")
@RequestMapping("/admin/cityActivity")
public class CityActivityController extends BaseController {
    @Autowired
    private ICityActivity cityActivityService;

    @Autowired
    private ICity cityService;

    @RequestMapping("/addOrEdit")
    public ResultCode addOrEdit(){
        JSONObject json = getJsonFromRequest();
        Integer id = json.getInteger("id");
        String name = json.getString("name");
        String cityCode = json.getString("cityCode");
        String cityName = json.getString("cityName");
        String url = json.getString("url");
        String locationPic = json.getString("locationPic");
        String infoPic = json.getString("infoPic");
        String startTime = json.getString("startTime");
        String endTime = json.getString("endTime");
        if(StringUtil.isAnyBlank(name,cityCode,cityName,locationPic,infoPic,startTime,endTime)){
            return new ResultCode(StatusCode.ERROR_PARAM);
        }
        if(endTime.compareTo(startTime)<0){
            return new ResultCode("100","起始时间不应大于结束时间!");
        }
        //判断该城市是否存在
        City cityCond = new City();
        cityCond.setCityCode(cityCode);
        cityCond.setCityName(cityName);
        City city = cityService.getByCondition(cityCond);
        if(city==null){
            return new ResultCode("100","不存在该城市或城市名与城市编码不一致");
        }
        //添加
        if(id==null){
            //判断该城市是否已在办活动
            CityActivity cond = new CityActivity();
            cond.setIsDelete(0);
            cond.setCityCode(cityCode);
            cond.and(Expressions.ge("start_time",startTime));
            cond.and(Expressions.le("end_time",endTime));
            CityActivity byCondition = cityActivityService.getByCondition(cond);
            if(byCondition!=null){
                return new ResultCode("100","该城市已在该时间段发布活动");
            }
            CityActivity cityActivity = new CityActivity();
            cityActivity.setName(name);
            cityActivity.setCityCode(cityCode);
            cityActivity.setCityName(cityName);
            cityActivity.setUrl(url);
            cityActivity.setStartTime(DateUtil.dateStr(startTime,"yyyy-MM-dd"));
            cityActivity.setEndTime(DateUtil.dateStr(endTime,"yyyy-MM-dd"));
            JSONObject pictures = new JSONObject();
            pictures.put("locationPic",locationPic);
            pictures.put("infoPic",infoPic);
            cityActivity.setPictures(pictures.toJSONString());
            cityActivityService.insertSelective(cityActivity);
        }else{
            //编辑
            CityActivity cond = new CityActivity();
            cond.setId(id);
            cond.setCityCode(cityCode);
            cond.setIsDelete(0);
            CityActivity cityActivity = cityActivityService.getByCondition(cond);
            if(cityActivity!=null){
                cityActivity.setName(name);
                cityActivity.setCityCode(cityCode);
                cityActivity.setCityName(cityName);
                cityActivity.setUrl(url);
                cityActivity.setStartTime(DateUtil.dateStr(startTime,"yyyy-MM-dd"));
                cityActivity.setEndTime(DateUtil.dateStr(endTime,"yyyy-MM-dd"));
                JSONObject pictures = new JSONObject();
                pictures.put("locationPic",locationPic);
                pictures.put("infoPic",infoPic);
                cityActivity.setPictures(pictures.toJSONString());
                cityActivityService.updateByPrimaryKey(cityActivity);
            }
        }
        return new ResultCode(StatusCode.SUCCESS);
    }

    @RequestMapping("/delete")
    public ResultCode delete(){
        JSONObject json = getJsonFromRequest();
        Integer id = json.getInteger("id");
        CityActivity cityActivity = cityActivityService.selectByPrimaryKey(id);
        if(cityActivity!=null){
            cityActivity.setIsDelete(1);
            cityActivityService.updateByPrimaryKey(cityActivity);
        }
        return new ResultCode(StatusCode.SUCCESS);
    }

    @RequestMapping("/list")
    public ResultCode list(){
        JSONObject json = getJsonFromRequest();
        CityActivity cond = new CityActivity();
        cond.setIsDelete(0);
        String name = json.getString("name");
        String cityName = json.getString("cityName");
        String startTime = json.getString("startTime");
        String endTime = json.getString("endTime");
        if(StringUtil.isNotBlank(name)){
         cond.setName(name);
        }
        if(StringUtil.isNotBlank(cityName)){
          cond.setCityName(cityName);
        }
        if(StringUtil.isNotBlank(startTime)){
           cond.and(Expressions.ge("start_time",startTime));
        }
        if(StringUtil.isNotBlank(endTime)){
           cond.and(Expressions.le("end_time",endTime));
        }
        Integer pageNumber = json.getInteger("pageNumber");
        if(pageNumber==null){
            pageNumber=1;
        }
        Integer pageSize=10;
        cond.setPageNumber(pageNumber);
        cond.setPageSize(pageSize);
        JSONObject result = new JSONObject();
        Page<CityActivity> pageBean = cityActivityService.getPage(cond, null);
        pageBean.getResult().forEach(x->{
            Date now = DateUtil.getNow();
            now=DateUtil.getDayStart(now);
            if(x.getEndTime().compareTo(now)<0){
                x.setActivityStatus(EnumCityActivityStatus.HAS_END.getName());
            }else if(x.getStartTime().compareTo(now)<=0&&x.getEndTime().compareTo(now)>=0){
                x.setActivityStatus(EnumCityActivityStatus.OPENING.getName());
            }else if(x.getStartTime().compareTo(now)>0){
                x.setActivityStatus(EnumCityActivityStatus.NOT_OPEN.getName());
            }
            x.setStartTimeString(DateUtil.dateStr(x.getStartTime(),"yyyy-MM-dd"));
            x.setEndTimeString(DateUtil.dateStr(x.getEndTime(),"yyyy-MM-dd"));
        });
        result.put("list",pageBean.getResult());
        result.put("pageNumber",pageBean.getCurPage());
        result.put("totalPage",pageBean.getTotalPage());
        result.put("totalCount",pageBean.getTotalRow());
        return new ResultCode(StatusCode.SUCCESS,result);
    }
}
