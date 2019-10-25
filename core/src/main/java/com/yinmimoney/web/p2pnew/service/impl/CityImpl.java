package com.yinmimoney.web.p2pnew.service.impl;

import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;
import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.dao.CityMapper;
import com.yinmimoney.web.p2pnew.dto.CheapCity;
import com.yinmimoney.web.p2pnew.dto.CityNameAndCode;
import com.yinmimoney.web.p2pnew.dto.CityZoomDto;
import com.yinmimoney.web.p2pnew.dto.CityZoomDtoNew;
import com.yinmimoney.web.p2pnew.enums.EnumCityInitStatus;
import com.yinmimoney.web.p2pnew.enums.EnumCityIsShow;
import com.yinmimoney.web.p2pnew.enums.EnumCitySellStatus;
import com.yinmimoney.web.p2pnew.exception.BussinessException;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;
import com.yinmimoney.web.p2pnew.pojo.City;
import com.yinmimoney.web.p2pnew.pojo.CityDetail;
import com.yinmimoney.web.p2pnew.pojo.Platform;
import com.yinmimoney.web.p2pnew.responsebody.CityDetailResponseBody;
import com.yinmimoney.web.p2pnew.responsebody.MyCitiesResponseBody;
import com.yinmimoney.web.p2pnew.service.IApiToken;
import com.yinmimoney.web.p2pnew.service.ICity;
import com.yinmimoney.web.p2pnew.service.ICityDetail;
import com.yinmimoney.web.p2pnew.service.IPlatform;
import com.yinmimoney.web.p2pnew.util.LocationUtils;
import com.yinmimoney.web.p2pnew.util.RedisUtils;
import com.yinmimoney.web.p2pnew.util.SunCallable;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class CityImpl extends BaseServiceImpl<City, CityMapper, Integer> implements ICity {

    private static final Logger logger = LogManager.getLogger(CityImpl.class);
    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    protected CityMapper getDao() {
        return cityMapper;
    }

    @Autowired
    private ICityDetail cityDetailService;

    @Autowired
    private IPlatform platformService;

    @Autowired
    private IApiToken apiTokenService;

    @Override
    public List<CityNameAndCode> getCityName(String cityName) {
        Map<String, String> condi = new HashMap<String, String>();
        condi.put("cityName", cityName);
        return cityMapper.getCityName(condi);
    }

    @Override
    public List getCityInRange(Double lat, Double lon, int raidus, String token) {

        String userCode = "";

        if (StringUtils.isNotBlank(token) && !"null".equals(token)) {
            userCode = getUserCodeFromToken(token);
        }

        long t = System.currentTimeMillis();
        List<CityZoomDtoNew> list = cityMapper.getCityInRangeNew();
        long t1 = System.currentTimeMillis();

        logger.info("数据库查询耗时:{}ms",t1 - t);

        List<Future<List>> futureList = new ArrayList<Future<List>>();

        int size = list.size();
        int sunSum = 4;
        int listStart, listEnd;
        if (sunSum > size) {
            sunSum = size;
        }
        SunCallable sunCallable;
        List<CityZoomDtoNew> mergeList = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(sunSum);
        long l = System.currentTimeMillis();
        logger.info("多线程循环开始================>");
        try {

            for (int i = 0; i < sunSum; i++) {
                listStart = size / sunSum * i;
                listEnd = size / sunSum * (i + 1);
                if (i == sunSum - 1) {
                    listEnd = size;
                }
                List<CityZoomDtoNew> sunList = list.subList(listStart, listEnd);
                sunCallable = new SunCallable(i, sunList, userCode);
                futureList.add(executorService.submit(sunCallable));
            }

            for (Future<List> future : futureList) {
                if (null != future) {
                    List<CityZoomDtoNew> cityZoomDtoNews = null;
                    try {
                        cityZoomDtoNews = future.get();
                    } catch (InterruptedException e) {
                        logger.info(e);
                        logger.info(e.getMessage());
                    } catch (ExecutionException e) {
                        logger.info(e);
                        logger.info(e.getMessage());
                    }
                    mergeList.addAll(cityZoomDtoNews);
                }

            }
        }catch (Exception e){
            logger.info(e);
            logger.info(e.getMessage());
        }finally {
            executorService.shutdown();
        }
        logger.info("多线程循环结束================>");
        long l1 = System.currentTimeMillis();
        logger.info(MessageFormat.format("多线程循环耗时t2-t1={0}ms",l1-l));

        return mergeList;
    }


    @Override
    public List getCityInRangeNew(Double lat, Double lon, Integer raidus, String token) {
        String userCode = "";

        if (StringUtils.isNotBlank(token) && !"null".equals(token)) {
            userCode = getUserCodeFromToken(token);
        }

        //首先要计算范围
         Map<String, Double> around = LocationUtils.getAround(lat, lon, raidus);
        long t = System.currentTimeMillis();
        List<CityZoomDto> list = cityMapper.getCityInRange(around);
        long t1 = System.currentTimeMillis();

        logger.info("数据库查询耗时:{}ms",t1 - t);

        List<CityZoomDto> data = new ArrayList<CityZoomDto>();
        if (list != null) {
            Map<String,CityZoomDto> map = Maps.newHashMap();
            long l = System.currentTimeMillis();
            logger.info("循环开始================>");
            for (CityZoomDto dto : list) {
                //CityZoomDtoCompr bean = new CityZoomDtoCompr();
                if (EnumCityInitStatus.PLATFORM.getStatus() == dto.getInitStatus()) {

                    Platform platform = platformService.getByPlatCode(Constant.PLAT_CODE);
                    dto.setAmount(platform.getAmount());
                    dto.setCitySellStatus(EnumCitySellStatus.SELL.getStatus());
                    dto.setInitStatus(EnumCityInitStatus.PLATFORM.getStatus());
//                    bean.setAt(platform.getAmount());
//                    bean.setcSS(EnumCitySellStatus.SELL.getStatus());
//                    bean.setiS(EnumCityInitStatus.PLATFORM.getStatus());

                } else {

                    //dto.setAt(cityDetail.getAmount());
                    //dto.setCy(cityDetail.getCurrency());

                     CityDetail cityDetail = cityDetailService.selectByCityCode(dto.getCityCode());
                     dto.setAmount(cityDetail.getAmount());


                    dto.setInitStatus(StringUtils.isNotBlank(userCode) &&
                            userCode.equals(cityDetail.getUserCode())?
                            EnumCityInitStatus.USER_SELF.getStatus() :
                            EnumCityInitStatus.USER.getStatus());


                    dto.setCitySellStatus(cityDetail.getCitySellStatus());
                }


                data.add(dto);
                //  map.put(dto.getCityCode(),dto);
            }
            logger.info("循环结束================>");
            long l1 = System.currentTimeMillis();
            logger.info(MessageFormat.format("循环耗时t2-t1={0}ms",l1-l));

            //redisTemplate.opsForHash().putAll("zoomcity", map);
        }
        return list;
    }


/*    @Override
    public List getCityInRange1(Double lat, Double lon, int raidus, String token) {
//        if(redisTemplate.hasKey("zoomcity")){
//            List<Object> zoomcity = redisTemplate.opsForHash().values("zoomcity");
//            return zoomcity;
//        }
        String userCode = "";

        if (StringUtils.isNotBlank(token) && !"null".equals(token)) {
                userCode = getUserCodeFromToken(token);
        }

        //首先要计算范围
       // Map<String, Double> around = LocationUtils.getAround(lat, lon, raidus);
       // List<CityZoomDtoNew> list = cityMapper.getCityInRange(around);
        List<CityZoomDtoNew> list = cityMapper.getCityInRangeNew();
        List<CityZoomDtoNew> data = new ArrayList<CityZoomDtoNew>();
        if (list != null) {
            Map<String,CityZoomDto> map = Maps.newHashMap();
            for (CityZoomDtoNew dto : list) {
                //CityZoomDtoCompr bean = new CityZoomDtoCompr();
                if (EnumCityInitStatus.PLATFORM.getStatus() == dto.getiS()) {

                    Platform platform = platformService.getByPlatCode(Constant.PLAT_CODE);
                    dto.setAt(platform.getAmount());
                    dto.setcSS(EnumCitySellStatus.SELL.getStatus());
                    dto.setiS(EnumCityInitStatus.PLATFORM.getStatus());
//                    bean.setAt(platform.getAmount());
//                    bean.setcSS(EnumCitySellStatus.SELL.getStatus());
//                    bean.setiS(EnumCityInitStatus.PLATFORM.getStatus());

                } else {

                   //dto.setAt(cityDetail.getAmount());
                   //dto.setCy(cityDetail.getCurrency());

                   // CityDetail cityDetail = cityDetailService.selectByCityCode(dto.getcC());
                   // dto.setAt(cityDetail.getAmount());


                    dto.setiS(StringUtils.isNotBlank(userCode) &&
                            dto.getuC().equals(userCode) ?
                            EnumCityInitStatus.USER_SELF.getStatus() :
                            EnumCityInitStatus.USER.getStatus());


                   // dto.setcSS(cityDetail.getCitySellStatus());
                }


                data.add(dto);
              //  map.put(dto.getCityCode(),dto);
            }
            //redisTemplate.opsForHash().putAll("zoomcity", map);
        }
        return list;
    }*/



    @Override
    public City selectByCityCode(String cityCode) {
        City city = new City();
        city.setCityCode(cityCode);
        return getByCondition(city);
    }

    @Override
    public CityDetailResponseBody getCityDetail(String cityName, String token) {

        City condi = new City();
        condi.and(Expressions.like("city_name_all", "%" + cityName + "%"));
        condi.setIsShow(EnumCityIsShow.IS_SHOW.getStatus());
        City city = getByCondition(condi);
        CityDetailResponseBody body = null;
        if (city == null) {
            return body;
        }
        body = new CityDetailResponseBody();
        body.setLat(city.getLat());
        body.setLon(city.getLon());

        body.setCityCode(city.getCityCode());
        body.setCityName(city.getCityName());
        //平台出售
        if (EnumCityInitStatus.PLATFORM.getStatus() == city.getInitStatus()) {

            Platform platform = platformService.getByPlatCode(Constant.PLAT_CODE);
            body.setAmount(platform.getAmount());
            body.setCurrency(platform.getCurrency());
            body.setMessage(platform.getPaltMessage());
            body.setCitySellStatus(EnumCitySellStatus.SELL.getStatus());
            body.setInitStatus(EnumCityInitStatus.PLATFORM.getStatus());
            body.setMessageStatus(Boolean.TRUE);


        } else {
            CityDetail cityDetail = cityDetailService.selectByCityCode(city.getCityCode());
            String userCode = "";

            if (StringUtils.isNotBlank(token) && !"null".equals(token)) {
                userCode = getUserCodeFromToken(token);
            }

            body.setAmount(cityDetail.getAmount());
            body.setCurrency(cityDetail.getCurrency());

            body.setMessage(cityDetail.getMessage());
            body.setUrl(cityDetail.getUrl());


            body.setInitStatus(StringUtils.isNotBlank(userCode) &&
                    cityDetail.getUserCode().equals(userCode) ?
                    EnumCityInitStatus.USER_SELF.getStatus() :
                    EnumCityInitStatus.USER.getStatus());
            body.setCitySellStatus(cityDetail.getCitySellStatus());
            body.setMessageStatus(cityDetail.getMessageStatus());


        }
        return body;
    }

    @Override
    public List<MyCitiesResponseBody> getMyCities(String userCode, Integer pageSize) {
        CityDetail condi = new CityDetail();
        condi.setUserCode(userCode);
        condi.setOrderBy("create_time desc ");
        condi.setPageSize(pageSize);
        condi.setPageNumber(1);
        List<CityDetail> list = cityDetailService.getList(condi);
        List<MyCitiesResponseBody> bodyList = Lists.newArrayList();
        if (list != null && list.size() > 0) {
            for (CityDetail detail : list) {
                MyCitiesResponseBody body = new MyCitiesResponseBody();
                City city = selectByCityCode(detail.getCityCode());
                body.setCityCode(detail.getCityCode());
                body.setCityName(city.getCityName());
                body.setAmount(detail.getAmount());
                body.setCurrency(detail.getCurrency());
                body.setCitySellStatus(detail.getCitySellStatus());
                bodyList.add(body);
            }
        }
        return bodyList;
    }

    @Override
    public CityDetailResponseBody getCityDetailForCode(String cityCode, String token) {
        City condi = new City();
        condi.setCityCode(cityCode);
        condi.setIsShow(EnumCityIsShow.IS_SHOW.getStatus());
        City city = getByCondition(condi);
        CityDetailResponseBody body = null;
        if (city == null) {
            return body;
        }
        body = new CityDetailResponseBody();
        body.setLat(city.getLat());
        body.setLon(city.getLon());

        body.setCityCode(city.getCityCode());
        body.setCityName(city.getCityName());
        //平台出售
        if (EnumCityInitStatus.PLATFORM.getStatus() == city.getInitStatus()) {

            Platform platform = platformService.getByPlatCode(Constant.PLAT_CODE);
            body.setAmount(platform.getAmount());
            body.setCurrency(platform.getCurrency());
            body.setMessage(platform.getPaltMessage());
            body.setCitySellStatus(EnumCitySellStatus.SELL.getStatus());
            body.setInitStatus(EnumCityInitStatus.PLATFORM.getStatus());
            body.setMessageStatus(Boolean.TRUE);

        } else {
            CityDetail cityDetail = cityDetailService.selectByCityCode(city.getCityCode());
            String userCode = "";

            if (StringUtils.isNotBlank(token) && !"null".equals(token)) {
                userCode = getUserCodeFromToken(token);
            }

            body.setAmount(cityDetail.getAmount());
            body.setCurrency(cityDetail.getCurrency());

            body.setMessage(cityDetail.getMessage());
            body.setUrl(cityDetail.getUrl());


            body.setInitStatus(StringUtils.isNotBlank(userCode) &&
                    cityDetail.getUserCode().equals(userCode) ?
                    EnumCityInitStatus.USER_SELF.getStatus() :
                    EnumCityInitStatus.USER.getStatus());
            body.setCitySellStatus(cityDetail.getCitySellStatus());
            body.setMessageStatus(cityDetail.getMessageStatus());
        }
        return body;
    }

    @Override
    public List<CheapCity> getCheapCity(Integer beginIndex, Integer pageSize, String orderBy) {
        return cityMapper.getCheapCity(beginIndex, pageSize, orderBy);
    }


    private String getUserCodeFromToken(String token) {
        try {
            String jsonStr = new String(Base64.decodeBase64(token), "UTF-8");
            JSONObject jsonToken = JSONObject.parseObject(jsonStr);

            String deviceId = jsonToken.getString("deviceId");
            String t = jsonToken.getString("token");
            if (Strings.isNullOrEmpty(deviceId) || Strings.isNullOrEmpty(t)) {
                throw new BussinessException(StatusCode.ERROR_USER_TOKEN_EXPIRED);

            }

            ApiToken apiToken = apiTokenService.getByToken(t);
            return apiToken == null ? "" : apiToken.getUserCode();
        } catch (UnsupportedEncodingException e) {
            throw new BussinessException(StatusCode.ERROR_USER_TOKEN_EXPIRED);
        }

    }
}
