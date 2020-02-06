package com.ty.xcx.demo.contoller;

import com.ty.xcx.demo.mapper.OrderMapper;
import com.ty.xcx.demo.model.BizResponse;
import com.ty.xcx.demo.util.XmlHelper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.*;

import static com.ty.xcx.demo.contoller.UserContoller.tokenAndOpenID;


@RequestMapping("/pay")
@RestController
public class  PayContoller {
    @Autowired
    OrderMapper OrderMapper;
    //下单返回前端签名
    @GetMapping
    public BizResponse pay(@RequestParam("id") Integer id ,@RequestHeader("token") String token) throws IOException {
        if(token==null||token.trim().length()==0)return BizResponse.fail(2,"未登录");
        //统一下单
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        OkHttpClient client = new OkHttpClient();

        // 下单的信息
        Map<String, Object> params = new TreeMap<>();
        params.put("appid", "wx74eb8c9316b66f91");
        params.put("mch_id", "1508262781");
        params.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        params.put("body", "支付一分钱-测试");
        params.put("out_trade_no", id);
        params.put("total_fee", 1);
        params.put("spbill_create_ip", "163.125.16.94");
        params.put("notify_url", "https://wx.panqingshan.cn/notice/ty/pay/notice");
        params.put("trade_type", "JSAPI");
        params.put("openid", tokenAndOpenID.get(token));

        // 签名
        Set<String> strings = params.keySet();
        StringBuffer signParams = new StringBuffer();
        for (String string : strings) {
            signParams.append(string).append("=").append(params.get(string)).append("&");
        }
        signParams.append("key=").append("lakJYzxp5znq5Pz1JYDBGInzrUNVAeYj");
        String sign = DigestUtils.md5Hex(signParams.toString());
        params.put("sign", sign);

        // Map -> XML
        String xml = XmlHelper.toXml(params);
        okhttp3.RequestBody xmlBody = okhttp3.RequestBody.create(MediaType.parse("application/xml"), xml);
        // 构建HTTP请求
        Request request = new Request.Builder().url(url).post(xmlBody).build();
        // 微信服务器返回的内容
        Response response = client.newCall(request).execute();
        String string = response.body().string();

        System.out.println("string = " + string);
        Map<String, String> prePayMap = XmlHelper.of(string).toMap();
        // 返回签名

        Map<String, String> requestPayParams = new TreeMap<>();
        requestPayParams.put("appId", "wx74eb8c9316b66f91");
        requestPayParams.put("timeStamp", System.currentTimeMillis() + "");
        requestPayParams.put("nonceStr", UUID.randomUUID().toString().replace("-", ""));
        requestPayParams.put("package", "prepay_id="+prePayMap.get("prepay_id"));
        requestPayParams.put("signType", "MD5");


        Set<String> requestPayParamsSet = requestPayParams.keySet();
        StringBuffer requestPaySignPara = new StringBuffer();
        for (String requestPayKey : requestPayParamsSet) {
            requestPaySignPara.append(requestPayKey).append("=").append(requestPayParams.get(requestPayKey)).append("&");
        }
        requestPaySignPara.append("key=").append("lakJYzxp5znq5Pz1JYDBGInzrUNVAeYj");
        String requestPaySign = DigestUtils.md5Hex(requestPaySignPara.toString());
        requestPayParams.put("paySign", requestPaySign);

        return BizResponse.success(requestPayParams);
    }

    // https://wx.panqingshan.cn/notice/1802/pay/notice
    // -> http://192.168.8.222:8080/pay/notice
    @PostMapping("/notice")
    public String notice(@org.springframework.web.bind.annotation.RequestBody  String body){
        //System.out.println("body = " + body);
        XmlHelper xmlHelper = XmlHelper.of(body);
        Map<String, String> map = xmlHelper.toMap();
        String outTradeNo = map.get("out_trade_no");

        OrderMapper.updateOrder(Integer.valueOf(outTradeNo) ,1);

        return "<xml>\n" +
                "\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }

}
