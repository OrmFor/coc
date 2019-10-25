//package com.yinmimoney.web.p2pnew.task;
//
//import cc.s2m.web.utils.webUtils.StaticProp;
//import cc.s2m.web.utils.webUtils.vo.Expressions;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.distributed.lock.redis.RedisReentrantLock;
//import com.google.common.base.Strings;
//import com.yinmimoney.web.p2pnew.enums.EnumOrderStatus;
//import com.yinmimoney.web.p2pnew.pojo.Order;
//import com.yinmimoney.web.p2pnew.pojo.OrderDetail;
//import com.yinmimoney.web.p2pnew.service.IOrder;
//import com.yinmimoney.web.p2pnew.service.IOrderDetail;
//import com.yinmimoney.web.p2pnew.util.DateUtil;
//import com.yinmimoney.web.p2pnew.util.RedisStringManager;
//import org.apache.commons.codec.binary.Base64;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.stereotype.Component;
//import redis.clients.jedis.JedisPool;
//
//import java.io.UnsupportedEncodingException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.BrokenBarrierException;
//import java.util.concurrent.CyclicBarrier;
//import java.util.concurrent.TimeUnit;
//
//@Component
//@EnableScheduling
//public class OrderTask implements Runnable{
//
//    private static final Logger logger = LogManager.getLogger(OrderTask.class);
//
//    @Autowired
//    private IOrder orderService;
//
//    @Autowired
//    private IOrderDetail orderDetailService;
//
//    @Autowired
//    private JedisPool jedisPool;
//
//    @Autowired
//    protected RedisStringManager redisStringManager;
//
//    private static final String KEY = "1GzmCKEVAwBCCRNEta9sX7nUpe6pSHTfEY";
//
//    private static final String uriPrex = "https://daily.bitdb.network/q/1P6o45vqLdo6X8HRCZk8XuDsniURmXqiXo/";
//
// @Scheduled(cron = "* 5 * * * ?") // 秒 分 时 日 月 星期几
//    public void run() {
//        super.run();
//    }
//
//
//    public void run() {
//        doTask();
//    }
//
//    protected void doTask() {
//        try {
//            cyclicBarrier.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (BrokenBarrierException e) {
//            e.printStackTrace();
//        }
//        logger.info("============start==============");
//        Order condi = new Order();
//        condi.setStatus(EnumOrderStatus.PRE_LOCK.getStatus());
//        Date towMin = null;
//        if ("local".equals(StaticProp.sysConfig.get("environment"))) {
//            towMin = DateUtil.rollMinute(DateUtil.getNow(), -3);
//        } else {
//            towMin = DateUtil.rollMinute(DateUtil.convertToGMT(), -3);
//        }
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        logger.info("date={}", format.format(towMin));
//        condi.and(Expressions.lt("update_time", towMin));
//        List<Order> list = orderService.getList(condi);
//
//
//        for (Order order : list) {
//            String orderNo = order.getOrderNo();
//            RedisReentrantLock lock=new RedisReentrantLock(jedisPool,orderNo);
//            try {
//                if (lock.tryLock(5000L, TimeUnit.MILLISECONDS)) {
//                    //查询一下是否存在
//                    OrderDetail detail = new OrderDetail();
//                    detail.setOrderNo(orderNo);
//                    List<OrderDetail> listDetail = orderDetailService.getList(detail);
//                    if(listDetail != null && listDetail.size() > 0){
//                        continue;
//                    }
//
//                    //如果轮询超过5次，就关闭订单
//                    if (!validateRequestLimit(order.getOrderNo(), 10)) {
//                        //关闭订单
//                        Order bean = new Order();
//                        bean.setStatus(EnumOrderStatus.CLOSE.getStatus());
//                        Order orderCondi = new Order();
//                        orderCondi.setOrderNo(orderNo);
//                        //更改状态
//                        orderService.updateByCondition(bean,orderCondi);
//                        //解锁城市
//                        orderService.processUnLock(order.getCityCode());
//                        continue;
//
//                    }
//
//                    String encodedText = null;
//                    try {
//                        encodedText = getTextEncode(orderNo);
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                    HttpGet get = new HttpGet(uriPrex + encodedText);
//                    get.setHeader("key", KEY);
//                    String result = null;
//                    CloseableHttpClient client = HttpClients.createDefault();
//                    try {
//                        HttpResponse res = client.execute(get);
//                        if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                            HttpEntity entity = res.getEntity();
//                            result = EntityUtils.toString(entity, "UTF-8");
//                        }
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    } finally {
//                        //关闭连接 ,释放资源
//                        client.getConnectionManager().shutdown();
//                    }
//                    //  System.out.println(result);
//
//                    if (result != null) {
//                        JSONObject json = JSONObject.parseObject(result);
//                        JSONArray array = json.getJSONArray("t");
//                        JSONObject tt = (JSONObject) array.get(0);
//                        String[] split = tt.getString("op").split(" ");
//                        for (int j = 1; j < split.length; j++) {
//                            Order newOrder = orderService.selectByOrderNo(orderNo);
//                            Order bean = new Order();
//                            bean.setStatus(EnumOrderStatus.COMPLETED.getStatus());
//                            bean.setTxid(tt.getString("txid"));
//
//                            Order orderCondi = new Order();
//                            orderCondi.setOrderNo(orderNo);
//
//                            //更改状态
//                            orderService.updateByCondition(bean,orderCondi);
//
//                            //处理orderDetail
//                             orderService.processOrderDetail(newOrder);
//                            //处理cityDetail
//                            orderService.processCityDetail(order);
//
//                            orderService.processUnLock(order.getCityCode());
//
//                        }
//
//                    } else {
//                        String value = redisStringManager.find(orderNo);
//                        updateRequestLimit(orderNo, value, 60);
//                    }
//                }else{
//                    System.out.println("已锁定不执行");
//                    continue;
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }finally {
//                lock.unlock();
//            }
//        }
//    }
//
//
//    public boolean validateRequestLimit(String key, int num) {
//        String value = redisStringManager.find(key);// 目前已经发送次数
//        if (!Strings.isNullOrEmpty(value)) {
//            if (Integer.parseInt(value) > num) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//
//    public void updateRequestLimit(String key, String value, int minute) {
//        // 保存第一次发送验证码，并且置过期时间
//        if (Strings.isNullOrEmpty(value))
//            redisStringManager.addWithTimeout(key, "1", minute, TimeUnit.MINUTES);
//        else
//            // 每发送一次增加一次记录
//            redisStringManager.incr(key);
//    }
//
//    private String getTextEncode(String content) throws UnsupportedEncodingException {
//        String text = "{\n" +
//                "  \"v\": 3,\n" +
//                "  \"q\": {\n" +
//                "    \"find\": {\n" +
//                "      \"out.s1\": \"Cityonchain\",\n" +
//                "      \"out.s4\": {\n" +
//                "        \"$exists\": true\n" +
//                "      },\n" +
//                "       \"out.str\":{\n" +
//                "         \"$regex\":" + "\" {} \"," +
//                "         \"$options\":\"i\"\n" +
//                "       }\n" +
//                "    },\n" +
//                "    \"project\": {\n" +
//                "      \"out.s2\": 1,\n" +
//                "      \"out.s4\": 1,\n" +
//                "      \"out\":1,\n" +
//                "      \"timeago\": 1,\n" +
//                "      \"timestamp\": 1,\n" +
//                "      \"tx.h\": 1\n" +
//                "    },\n" +
//                "    \"limit\": 2000\n" +
//                "  },\n" +
//                "  \"r\": { \n" +
//                "    \"f\": \"[.[] | { txid: .tx.h, city: .out[0].s2, priceUSD: .out[0].s4[0:-3],op : .out[0].str, timestamp: (.timestamp / 1000 | floor | todate), timeago: .timeago }]\"\n" +
//                "  }\n" +
//                "}";
//        Base64 base64 = new Base64();
//        byte[] textByte = text.getBytes("UTF-8");
//        String encodedText = base64.encodeToString(textByte);
//        return encodedText;
//    }
//
//
//    static final  CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
//
//}
