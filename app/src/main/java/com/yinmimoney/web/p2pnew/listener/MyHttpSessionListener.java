package com.yinmimoney.web.p2pnew.listener;

import com.yinmimoney.web.p2pnew.controller.LoginController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MyHttpSessionListener implements HttpSessionListener {
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        String sessionid=se.getSession().getId();
        LOGGER.info("!创建的会话=("
                + sessionid+")");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String sessionid = se.getSession().getId();
        String oauth = (String)se.getSession().getAttribute("oauth");
        LOGGER.info("销毁会话={}，oauth={}", sessionid , oauth);

    }
}
