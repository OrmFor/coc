package com.yinmimoney.web.p2pnew.controller;

import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;
import com.yinmimoney.web.p2pnew.requestbody.CloseOrderRequestBody;
import com.yinmimoney.web.p2pnew.requestbody.PreOrderRequestBody;
import com.yinmimoney.web.p2pnew.responsebody.PreOrderResponseBody;
import com.yinmimoney.web.p2pnew.service.IOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;


@RestController
@RequestMapping("/api/1.0")
public class OrderController extends BaseController {

    @Autowired
    private IOrder orderService;

    @RequestMapping("/preOrder")
    public ResultCode processPreOrder(@Valid @RequestBody PreOrderRequestBody body, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = getErrors(result);
            return new ResultCode(StatusCode.ERROR_LACK_PARAM, errors);
        }
        ApiToken apiToken = getApiToken();
        PreOrderResponseBody preOrderResponseBody = orderService.processPreOrder(body,
                apiToken.getUserCode());
        return new ResultCode(StatusCode.SUCCESS,preOrderResponseBody);
    }


    @RequestMapping("/closeOrder")
    public ResultCode processCloseOrder(@Valid @RequestBody CloseOrderRequestBody body, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = getErrors(result);
            return new ResultCode(StatusCode.ERROR_LACK_PARAM, errors);
        }
        ApiToken apiToken = getApiToken();
        orderService.processCloseOrder(body,
                apiToken.getUserCode());
        return new ResultCode(StatusCode.SUCCESS);
    }

    @RequestMapping("/processPay")
    public ResultCode processPay(@RequestBody JSONObject body) {
        orderService.processWebHook(body);
        return new ResultCode(StatusCode.SUCCESS);
    }




}
