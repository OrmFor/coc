package com.yinmimoney.web.p2pnew.controller;

import cc.s2m.util.Page;
import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.dto.CityPictureDto;
import com.yinmimoney.web.p2pnew.pojo.*;
import com.yinmimoney.web.p2pnew.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("pictureController")
@RequestMapping("/api/1.0")
public class PictureController extends BaseController {

    private static final Logger logger = LogManager.getLogger(PictureController.class);

    @Autowired
    private ICityPicture cityPictureService;
    @Autowired
    private ICityDetail cityDetailService;
    @Autowired
    private IUser userService;
    @Autowired
    private ILikeLog likeLogService;
    @Autowired
    private ICity cityService;

    @Autowired
    private ICommonOrderDetail commonOrderDetailService;


    @RequestMapping("/processCityPicture")
    public ResultCode processCityPicture() {
        JSONObject json = getJsonFromRequest();
        String userCode = getApiToken().getUserCode();
        commonOrderDetailService.processCityPicture(json, userCode);
        return new ResultCode(StatusCode.SUCCESS);
    }

    @RequestMapping("processLikePicture")
    public ResultCode processLikePicture() {
        JSONObject json = getJsonFromRequest();
        //登录的userCode
        String userCode = getApiToken().getUserCode();
        commonOrderDetailService.processLikePicture(json, userCode);

        return new ResultCode(StatusCode.SUCCESS);
    }


    @RequestMapping("/getCityPicture")
    public ResultCode getCityPicture() {
        JSONObject json = getJsonFromRequest();
        Integer pageSize = json.getInteger("pageSize");
        Integer pageNumber = json.getInteger("pageNumber");
        if (pageSize == null || pageSize < 0) {
            pageSize = 50;
        }

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 1;
        }

        String cityCode = json.getString("cityCode");
        CityPicture condi = new CityPicture();
        condi.setCityCode(cityCode);
        condi.setIsShow(Boolean.TRUE);
        condi.setPageNumber(pageNumber);
        condi.setPageSize(pageSize);
        condi.setIsShow(Boolean.TRUE);

        condi.setOrderBy("id desc");
        CityDetail detail = cityDetailService.selectByCityCode(cityCode);

        Page<CityPicture> page = cityPictureService.getPage(condi, getRequest().getParameterMap());
        List<CityPictureDto> dtos = new ArrayList<>();
        int total = 0;
        ApiToken token = getApiToken();
        if (page != null) {
            List<CityPicture> list = page.getResult();
            if (list != null && list.size() > 0) {
                for (CityPicture cityPicture : list) {
                    CityPictureDto dto = new CityPictureDto();
                    dto.setCityCode(cityCode);
                    dto.setCityName(cityPicture.getCityName());
                    dto.setLikeNum(cityPicture.getLikeNumber());
                    dto.setPictureUserCode(cityPicture.getUserCode());
                    User user = userService.selectByUserCode(cityPicture.getUserCode());
                    dto.setPictureUserName(user == null ? "" : user.getUserName());
                    dto.setTxid(cityPicture.getTxid());
                    dto.setCityUserCode(detail == null ? Constant.PLAT_CODE : detail.getUserCode());
                    dto.setPictureCode(cityPicture.getPictureCode());
                    dto.setCreateTime(cityPicture.getCreateTime());

                    if (token != null) {
                        String userCode = token.getUserCode();
                        LikeLog likeLog = new LikeLog();
                        likeLog.setChatCode(cityPicture.getPictureCode());
                        likeLog.setUserCode(userCode);
                        likeLog.setIsValid(0);
                        LikeLog byCondition = likeLogService.getByCondition(likeLog);
                        if (byCondition != null) {
                            dto.setIsLike(1);
                        }
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


    @RequestMapping("/getHotPicture")
    public ResultCode getHotPicture() {
        JSONObject json = getJsonFromRequest();
        Integer pageSize = json.getInteger("pageSize");
        Integer pageNumber = json.getInteger("pageNumber");
        ApiToken token = getApiToken();
        if (pageSize == null || pageSize < 0) {
            pageSize = 50;
        }

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 1;
        }

        String cityCode = json.getString("cityCode");
        if(StringUtils.isBlank(cityCode)){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }

        int count = cityPictureService.getHotPictureCount(cityCode);

        List<CityPictureDto> list = cityPictureService.getHotPicture(cityCode,pageSize,pageNumber);
        list.forEach(x ->{
            CityDetail cityDetail = cityDetailService.selectByCityCode(cityCode);
            x.setCityUserCode(cityDetail.getUserCode());
            User user = userService.selectByUserCode(x.getPictureUserCode());
            x.setPictureUserName(user.getUserName());
            if (token != null) {
                String userCode = token.getUserCode();
                LikeLog likeLog = new LikeLog();
                likeLog.setChatCode(x.getPictureCode());
                likeLog.setUserCode(userCode);

                likeLog.setIsValid(0);
                LikeLog byCondition = likeLogService.getByCondition(likeLog);
                if (byCondition != null) {
                    x.setIsLike(1);
                }
            }


        });
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("totalCount", count);
        return new ResultCode(StatusCode.SUCCESS,data);
    }


    @RequestMapping("/getHotPictureAll")
    public ResultCode getHotPictureAll() {
        JSONObject json = getJsonFromRequest();
        Integer pageSize = json.getInteger("pageSize");
        Integer pageNumber = json.getInteger("pageNumber");

        ApiToken token = getApiToken();
        if (pageSize == null || pageSize < 0) {
            pageSize = 50;
        }

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 1;
        }


        int count = cityPictureService.getHotPictureAllCount();

        List<CityPictureDto> list = cityPictureService.getHotPictureAll(pageSize,pageNumber);
        list.forEach(x ->{
            CityDetail cityDetail = cityDetailService.selectByCityCode(x.getCityCode());
            x.setCityUserCode(cityDetail.getUserCode());
            User user = userService.selectByUserCode(x.getPictureUserCode());
            x.setPictureUserName(user.getUserName());
            if (token != null) {
                String userCode = token.getUserCode();
                LikeLog likeLog = new LikeLog();
                likeLog.setChatCode(x.getPictureCode());
                likeLog.setUserCode(userCode);
                likeLog.setIsValid(0);
                LikeLog byCondition = likeLogService.getByCondition(likeLog);
                if (byCondition != null) {
                    x.setIsLike(1);
                }
            }


        });
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("totalCount", count);
        return new ResultCode(StatusCode.SUCCESS,data);
    }


    @RequestMapping("/getCityPictureAll")
    public ResultCode getCityPictureAll() {
        JSONObject json = getJsonFromRequest();
        Integer pageSize = json.getInteger("pageSize");
        Integer pageNumber = json.getInteger("pageNumber");
        if (pageSize == null || pageSize < 0) {
            pageSize = 50;
        }

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 1;
        }

        CityPicture condi = new CityPicture();
        condi.setPageNumber(pageNumber);
        condi.setPageSize(pageSize);
        condi.setIsShow(Boolean.TRUE);

        condi.setOrderBy("id desc");

        Page<CityPicture> page = cityPictureService.getPage(condi, getRequest().getParameterMap());
        List<CityPictureDto> dtos = new ArrayList<>();
        int total = 0;
        ApiToken token = getApiToken();
        if (page != null) {
            List<CityPicture> list = page.getResult();
            if (list != null && list.size() > 0) {
                for (CityPicture cityPicture : list) {
                    CityDetail detail = cityDetailService.selectByCityCode(cityPicture.getCityCode());

                    CityPictureDto dto = new CityPictureDto();
                    dto.setCityCode(cityPicture.getCityCode());
                    dto.setCityName(cityPicture.getCityName());
                    dto.setLikeNum(cityPicture.getLikeNumber());
                    dto.setPictureUserCode(cityPicture.getUserCode());
                    User user = userService.selectByUserCode(cityPicture.getUserCode());
                    dto.setPictureUserName(user == null ? "" : user.getUserName());
                    dto.setTxid(cityPicture.getTxid());
                    dto.setCityUserCode(detail == null ? Constant.PLAT_CODE : detail.getUserCode());
                    dto.setPictureCode(cityPicture.getPictureCode());

                    if (token != null) {
                        String userCode = token.getUserCode();
                        LikeLog likeLog = new LikeLog();
                        likeLog.setChatCode(cityPicture.getPictureCode());
                        likeLog.setUserCode(userCode);
                        likeLog.setIsValid(0);
                        LikeLog byCondition = likeLogService.getByCondition(likeLog);
                        if (byCondition != null) {
                            dto.setIsLike(1);
                        }
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
