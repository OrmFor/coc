import com.yinmimoney.web.p2pnew.util.LmAppUtil;
import com.yinmimoney.web.p2pnew.util.LmSignatureAlgorithm;
import com.yinmimoney.web.p2pnew.util.LmSignatureUtils;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

public class TestEncode {

    public static void main(String[] args) throws Exception {

/*        StringBuffer sb = new StringBuffer();
        FileInputStream fis = new FileInputStream("C:\\Users\\Thinkpad\\Desktop\\Unleash the dragon.jpg");
        BufferedInputStream bis = new BufferedInputStream(fis);                                               ByteArrayOutputStream bos=
                new java.io.ByteArrayOutputStream();
        byte[] buff=new byte[1024];
        int len=0;
        while((len=fis.read(buff))!=-1){
            bos.write(buff,0,len);
        }
        //得到图片的字节数组
        byte[] result=bos.toByteArray();

        PrivateKey privateKey = LmSignatureUtils.getRsaPkcs8PrivateKey(Base64
                .decodeBase64(LmAppUtil.getLmPrivateKey()));
        byte[] sign = LmSignatureUtils.sign(LmSignatureAlgorithm.SHA1WithRSA,
                privateKey, result);
        System.out.println(sign.length);*/

        System.out.println(Boolean.TRUE);
    }
}
