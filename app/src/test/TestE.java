import cc.s2m.web.utils.webUtils.util.AccountDigestUtils;
import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.exception.BussinessException;
import org.apache.commons.codec.binary.Base64;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TestE {
    public static void main(String[] args){
/*
        String s = AccountDigestUtils.getMd5Pwd("user00002", "wwy123456789");
        System.out.println(s);
        System.out.println(1 << 30);


        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date currDate = cal.getTime();
       System.out.println(currDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
         currDate = cal2.getTime();
        System.out.println(currDate);


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        calendar.add(Calendar.MILLISECOND, -(dstOffset+zoneOffset));
        System.out.println(calendar.getTime());
*/
        String ss = "\"byConditi\"";
        System.out.println(ss);
        ss=ss.replace("\"","\\\'");
        System.out.println(ss);


        JSONObject json = new JSONObject();
        json.put("deviceId", "20030107");
        json.put("token", "90152238-ed1d-4d7d-b07d-5dab35adb26f");
        String signToken = null;
        try {
            signToken = new String(Base64.encodeBase64(json.toJSONString().getBytes()), "UTF-8");
        } catch (Exception e) {

            throw new BussinessException(StatusCode.ERROR);
        }

        System.out.println(signToken);

    }
}
