package com.yinmimoney.web.p2pnew.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.service.IUser;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class OauthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LogManager.getLogger(TokenInterceptor.class);

    @Autowired
    private IUser userService;

    private List<String> canNoLoginUrls;// 可以不需要登录的链接，但是登录情况下又需要过token，保存用户信息

    public List<String> getCanNoLoginUrls() {
        return canNoLoginUrls;
    }

    public void setCanNoLoginUrls(List<String> canNoLoginUrls) {
        this.canNoLoginUrls = canNoLoginUrls;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (!Strings.isNullOrEmpty(uri) && uri.endsWith("/")) {
            uri = uri.substring(0, uri.length() - 1);
        }
        logger.info("uri={}", uri);
        if (canNoLoginUrls != null && canNoLoginUrls.contains(uri)) {
            return true;
        }
        HttpSession session = request.getSession(false);
        if(session == null){
            logger.info("session={}",session);
            writeJson(response, new ResultCode(StatusCode.ERROR_504));
            return false;
        }
        String sessionId = session.getId();
         String auth=(String) session.getAttribute("auth");
        logger.info("auth={},sessionId={}",auth,sessionId);
        if (StringUtils.isBlank(auth) || "undefined".equals(auth)) {
            writeJson(response, new ResultCode(StatusCode.ERROR_504));
            return false;
        }

        return true;
    }


    private void writeJson(HttpServletResponse response, ResultCode resultCode) {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(JSONObject.toJSONString(resultCode));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
}