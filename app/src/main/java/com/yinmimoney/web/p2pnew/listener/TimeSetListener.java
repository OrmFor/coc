package com.yinmimoney.web.p2pnew.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.TimeZone;

public class TimeSetListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.setProperty("user.timezone", "UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
