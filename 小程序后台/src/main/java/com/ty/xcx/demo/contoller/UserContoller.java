package com.ty.xcx.demo.contoller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ty.xcx.demo.model.BizResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



@RestController
@RequestMapping("/dzm")
public class UserContoller {

    public static Map<String,String> tokenAndOpenID = new HashMap<>();
    //private Map<String,String>openIdAndSessionKey=new HashMap<>();

    @GetMapping("/auth")
    public BizResponse auth(String code) throws IOException {
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=wx74eb8c9316b66f91&secret=fdba93de4b77d6b615b4b7178d28d79b&js_code=%s&grant_type=authorization_code", code);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();//发送
        Response response = client.newCall(request).execute();//放回的数据
        String responseBody = response.body().string();
        String token= UUID.randomUUID().toString();//生成uuid然后转为token
        JSONObject json= JSON.parseObject(responseBody);
        String openid=json.getString("openid");
        tokenAndOpenID.put(token,openid);
        return BizResponse.success(token);//给前端

    }


//    @GetMapping("/add")
//    public BizResponse addD(@RequestParam("token")String token){
//
//        return BizResponse.success(token);//给前端
//
//    }

}