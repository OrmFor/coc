package base;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.util.HexUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
* @Author wwy
* @Description //wwy
* @Date 18:30 2019/8/2
* @Param
* @return
**/
public class Test {

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = (new SSLContextBuilder()).loadTrustMaterial((KeyStore) null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0").setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).setConnectionRequestTimeout(10000).build()).build();
        } catch (KeyManagementException var2) {

        } catch (NoSuchAlgorithmException var3) {

        } catch (KeyStoreException var4) {

        }

        return null;
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        CloseableHttpClient client = Test.createSSLClientDefault();
        String text = "{\n" +
                "  \"v\": 3,\n" +
                "  \"q\": {\n" +
                "    \"find\": {\n" +
                "      \"out.s1\": \"Cityonchain\",\n" +
                "      \"out.s4\": {\n" +
                "        \"$exists\": true\n" +
                "      },\n" +
                "       \"out.str\":{\n" +
                "         \"$regex\":" + "\"" + HexUtil.str2HexStr("order_2019072508301919256") + "\"," +
                "         \"$options\":\"i\"\n" +
                "       }\n" +
                "    },\n" +
                "    \"project\": {\n" +
                "      \"out.s2\": 1,\n" +
                "      \"out.s4\": 1,\n" +
                "      \"out\":1,\n" +
                "      \"timeago\": 1,\n" +
                "      \"timestamp\": 1,\n" +
                "      \"tx.h\": 1\n" +
                "    },\n" +
                "    \"limit\": 2000\n" +
                "  },\n" +
                "  \"r\": { \n" +
                "    \"f\": \"[.[] | { txid: .tx.h, city: .out[0].s2, priceUSD: .out[0].s4[0:-3],op : .out[0].str, timestamp: (.timestamp / 1000 | floor | todate), timeago: .timeago }]\"\n" +
                "  }\n" +
                "}";

        final Base64 base64 = new Base64();
        final byte[] textByte = text.getBytes("UTF-8");
        final String encodedText = base64.encodeToString(textByte);
        HttpGet get = new HttpGet("https://daily.bitdb.network/q/1P6o45vqLdo6X8HRCZk8XuDsniURmXqiXo/" +
                encodedText);
        get.setHeader("key", "1GzmCKEVAwBCCRNEta9sX7nUpe6pSHTfEY");
        String result = null;
        try {
            HttpResponse res = client.execute(get);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
        }
        System.out.println(result);
        JSONObject json = JSONObject.parseObject(result);
        JSONArray array = json.getJSONArray("t");
        for (int i = 0; i < array.size(); i++) {
            JSONObject tt = (JSONObject) array.get(i);
            String[] split = tt.getString("op").split(" ");
            for (int j = 1; j < split.length; j++) {
                System.out.println(split[j]);
                System.out.println(HexUtil.toStringHex2(split[j]));

            }
        }
    }

}


class bean {
    private String txid;
    private String city;
    private JSONObject priceUSD;
    private String timestamp;
    private String op;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public JSONObject getPriceUSD() {
        return priceUSD;
    }

    public void setPriceUSD(JSONObject priceUSD) {
        this.priceUSD = priceUSD;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}