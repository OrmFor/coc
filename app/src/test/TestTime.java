import com.yinmimoney.web.p2pnew.util.DateUtil;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TestTime {

    public static void main(String[] args) throws ParseException {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(format.format(new Date()));


        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Copenhagen"));

        //Europe/London
        //TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        System.out.println(System.currentTimeMillis());
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format1.format(new Date()));


        System.out.println(BigDecimal.valueOf(
                Long.parseLong(DateUtil.getTimesBetween(format1.parse("2019-08-22 15:45:04"),
                        format1.parse("2019-08-19 15:45:04")))
        ).divide(new BigDecimal(3600),0,BigDecimal.ROUND_UP)

        );

    }

}
