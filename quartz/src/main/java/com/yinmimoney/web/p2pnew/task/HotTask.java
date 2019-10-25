package com.yinmimoney.web.p2pnew.task;

import com.yinmimoney.web.p2pnew.dto.HotCityLasted;
import com.yinmimoney.web.p2pnew.pojo.CityHot;
import com.yinmimoney.web.p2pnew.service.ICityHot;
import com.yinmimoney.web.p2pnew.service.ICommonOrderDetail;
import com.yinmimoney.web.p2pnew.util.BeanCovertUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class HotTask extends BaseTask{
    private static final Logger logger = LogManager.getLogger(HotTask.class);

    @Autowired
    private ICommonOrderDetail commonOrderDetailService;
    @Autowired
    private ICityHot cityHotService;

    //                 0 0 10,14,16 * * ?
    @Scheduled(cron = "* 1,11,21,31,41,51 * * * ?") // 秒 分 时 日 月 星期几
    public void run() {
        super.run();
    }

    @Override
    protected void doTask() {
        logger.info("=============HotTask================");

        long l = System.currentTimeMillis();
        cityHotService.deleteAll();
        List<HotCityLasted> list = commonOrderDetailService.getCityHotLasted();

        long insert = System.currentTimeMillis();
        if(list != null) {
            for (HotCityLasted hot : list){
                CityHot bean =  BeanCovertUtil.beanCovert(hot,CityHot.class);
                cityHotService.insert(bean);
            }
        }

        long insert1 = System.currentTimeMillis();
        logger.info(insert1-insert);

        long l1 = System.currentTimeMillis();

       logger.info(l1-l);

    }
}
