import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;

@Slf4j
public class authTest {

    /**
     * 生成签名
     */
    public String generateSignature(String accessKey, String secretKey, String params, String nonce, String timeStamp){
        HashMap<String, String> signMap = new HashMap<>();
        signMap.put("accessKey",accessKey);
        signMap.put("params",params);
        signMap.put("nonce",nonce);
        signMap.put("timeStamp",timeStamp);
        return new String(SecureUtil.hmacSha1(secretKey).digest(signMap.toString()));
    }

    /**
     * 校验签名
     */
    public boolean checkSignature(String requestSign, String accessKey, String secretKey, String params, String nonce, String timeStamp){
        HashMap<String, String> signMap = new HashMap<>();
        signMap.put("accessKey",accessKey);
        signMap.put("params",params);
        signMap.put("nonce",nonce);
        signMap.put("timeStamp",timeStamp);
        byte[] digested = SecureUtil.hmacSha1(secretKey).digest(signMap.toString());
        String sign = new String(digested);
        return sign.equals(requestSign);
    }

    @Test
    public void genarateSignatureTest(){
        // params
        HashMap<String, Object> params = new HashMap<>();
        params.put("name","zhangsan");
        String paramsStr = JSONUtil.toJsonStr(params);
        log.info("params: {}",paramsStr);
        // timestamp
        Long time = DateUtil.date().getTime();
        String timestamp = Long.toString(time);
        log.info("timestamp:{}", timestamp);
        // nonce
        String nonce = IdUtil.fastUUID();
        log.info("nonce: {}",nonce);
        // accessKey
        String accessKey = "1f481f72b6c843e5a368e43dafdd666a";
        //secretKey
        String secretKey = "f0b9745c45b04c5cbcf189f01d805c98";

        String sign = generateSignature(accessKey, secretKey, paramsStr, nonce, timestamp);
        log.info("sign:{}",sign);
    }

}
