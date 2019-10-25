package com.yinmimoney.web.p2pnew.task;


import cc.s2m.web.utils.webUtils.StaticProp;
import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.yinmimoney.web.p2pnew.enums.EnumOrderStatus;
import com.yinmimoney.web.p2pnew.pojo.City;
import com.yinmimoney.web.p2pnew.pojo.Order;
import com.yinmimoney.web.p2pnew.service.ICity;
import com.yinmimoney.web.p2pnew.service.IOrder;
import com.yinmimoney.web.p2pnew.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class UnLockTask extends BaseTask {

    private static final Logger logger = LogManager.getLogger(UnLockTask.class);

    @Autowired
    private IOrder orderService;
    @Autowired
    private ICity cityService;

    @Scheduled(cron = "1 * * * * ?") // 秒 分 时 日 月 星期几
    public void run() {
        super.run();
    }

    @Override
    protected void doTask() {
        logger.info("=============start================");
        Order condi = new Order();
        Date towMin = null;
        if("local".equals(StaticProp.sysConfig.get("environment"))){
            towMin = DateUtil.rollMinute(DateUtil.getNow(), -3);

        }else {
             towMin = DateUtil.rollMinute(DateUtil.convertToGMT(), -3);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("date={}",format.format(towMin));
        condi.and(Expressions.lt("update_time", towMin));
        condi.setStatus(EnumOrderStatus.PRE_LOCK.getStatus());
        List<Order> list = orderService.getList(condi);

        if (list != null && list.size() > 0) {
            for (Order order : list) {
                /*logger.info("orderNo={}",order.getOrderNo());
                Order update = new Order();
                update.setStatus(EnumOrderStatus.CLOSE.getStatus());
                Order condiOrder = new Order();
                condiOrder.setOrderNo(order.getOrderNo());
                orderService.updateByCondition(update, condi);*/


                City bean = cityService.selectByCityCode(order.getCityCode());
                if (bean != null) {
                    logger.info("city={}",bean.getCityName());
                    City updateCity = new City();
                    updateCity.setIsLock(Boolean.FALSE);
                    City condition = new City();
                    condition.setCityCode(bean.getCityCode());
                    cityService.updateByCondition(updateCity,condition);

                }


            }
        }

    }
}
