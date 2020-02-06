package com.ty.xcx.demo.contoller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ty.xcx.demo.mapper.OrderMapper;
import com.ty.xcx.demo.model.BizResponse;
import com.ty.xcx.demo.util.CertHttpUtil;
import com.ty.xcx.demo.util.MD5;
import com.ty.xcx.demo.util.WXPayUtil;
import com.ty.xcx.demo.util.XmlHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.*;

@RequestMapping("/refund")
@RestController
public class RefundContoller {
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";
    /**
     * 生成key
     */
    private static SecretKeySpec key = new SecretKeySpec(MD5.MD5Encode("lakJYzxp5znq5Pz1JYDBGInzrUNVAeYj").toLowerCase().getBytes(), ALGORITHM);

    @Autowired
    OrderMapper OrderMapper;

    @GetMapping
    public BizResponse refund(@RequestParam("id") Integer id , @RequestHeader("token") String token) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {

        //生成随机字符串
        String nonce_str = UUID.randomUUID().toString().replace("-", "");
        //将需要签名的参数拼成String
        String sign = "appid=" + "wx74eb8c9316b66f91" + "&mch_id=" + "1508262781" + "&nonce_str=" + nonce_str + "&notify_url=" + "https://wx.panqingshan.cn/notice/ty/refund/notive" + "&out_refund_no=" + nonce_str + "&out_trade_no=" + id + "&refund_account=" + "REFUND_SOURCE_RECHARGE_FUNDS" + "&refund_fee=" +1 + "&total_fee=" + 1;

        String mysign = WXPayUtil.sign(sign, "lakJYzxp5znq5Pz1JYDBGInzrUNVAeYj", "utf-8").toUpperCase();
        StringBuffer  paramBuffer = new StringBuffer();
        paramBuffer.append("<xml>");
        paramBuffer.append("<appid>"+"wx74eb8c9316b66f91"+"</appid>");
        paramBuffer.append("<mch_id>"+"1508262781"+"</mch_id>");
        paramBuffer.append("<nonce_str>"+nonce_str+"</nonce_str>");
        //写入回调地址
        paramBuffer.append("<notify_url>"+"https://wx.panqingshan.cn/notice/ty/refund/notive"+"</notify_url>");
        paramBuffer.append("<out_refund_no>"+nonce_str+"</out_refund_no>");
        paramBuffer.append("<out_trade_no>"+id+"</out_trade_no>");
        paramBuffer.append("<refund_account>"+"REFUND_SOURCE_RECHARGE_FUNDS"+"</refund_account>");
        paramBuffer.append("<refund_fee>"+1+"</refund_fee>");
        paramBuffer.append("<total_fee>"+1+"</total_fee>");
        paramBuffer.append("<sign>"+mysign+"</sign>");
        paramBuffer.append("</xml>");

        //在CertHttpUtil文件中initCert需要设置证书路径和证书密码（证书密码默认商户号）
        String result = CertHttpUtil.postData("https://api.mch.weixin.qq.com/secapi/pay/refund",paramBuffer.toString());
        System.out.println("rest="+result);
        return BizResponse.of(200,"退款成功");
    }
    //退款回调
    @PostMapping("/notive")
    public String refundCallback(@org.springframework.web.bind.annotation.RequestBody String body) throws Exception {
        XmlHelper xmlHelper = XmlHelper.of(body);
        Map<String, String> map = xmlHelper.toMap();
        String reqInfo = map.get("req_info");
        String data = decryptData(reqInfo);

        Map<String, String> root = XmlHelper.of(data).toMap();

        String outTradeNo = root.get("out_trade_no");
        System.out.println("pay：" + outTradeNo);
        OrderMapper.updateOrder(Integer.valueOf(outTradeNo),2);

        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }

    //签名
    public String sign(Map<String, Object> params){
        Set<String> packageSet = params.keySet();
        StringBuffer sign = new StringBuffer();
        for (String param : packageSet) {
            if(param == null || param.trim().length() == 0) continue;
            if(sign.length() > 0){
                sign.append("&");
            }
            sign.append(param).append("=").append(params.get(param));
        }
        sign.append("&key=").append("lakJYzxp5znq5Pz1JYDBGInzrUNVAeYj");
        return DigestUtils.md5Hex(sign.toString());
    }

    public String generateSign(Map<String, ?> paras) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 字符拼接
        StringBuffer sign = new StringBuffer();
        paras.forEach((k, v) -> sign.append(k).append("=").append(v).append("&"));
        sign.append("key=").append("lakJYzxp5znq5Pz1JYDBGInzrUNVAeYj");
        // MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(sign.toString().getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    public static String toXml(Map<String, Object> params) {
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            // 略过空值
            if (StringUtils.isEmpty(value)){
                continue;
            }
            xml.append("<").append(key).append(">");
            xml.append(entry.getValue());
            xml.append("</").append(key).append(">");
        }
        xml.append("</xml>");
        return xml.toString();
    }

    //解码方法
    public static String decryptData(String base64Data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(decode(base64Data)));
    }

    public static byte[] decode(String encodedText) {
        final Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(encodedText);
    }



}
