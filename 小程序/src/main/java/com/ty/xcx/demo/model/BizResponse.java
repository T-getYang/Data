package com.ty.xcx.demo.model;


import lombok.Data;
import org.bouncycastle.asn1.bc.ObjectData;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 */
@Data
public class BizResponse {

    private Integer code;

    private String msg;

    private Object data;

    private final static Map<Integer,String> CODE_DESC = new HashMap<>();
    {
        CODE_DESC.put(0, "成功");
        CODE_DESC.put(1, "服务器错误");
        CODE_DESC.put(2,"登录过期");
    }

    public static BizResponse of(Integer code,Object data) {
        BizResponse bizResponse = new BizResponse();
        bizResponse.code = code;
        bizResponse.msg = CODE_DESC.get(code);
        bizResponse.data = data;
        return bizResponse;
    }

    public static BizResponse success(Object data){
        return of(0,data);
    }

    public static BizResponse success(){
        return of(0,null);
    }

    public static BizResponse fail(Integer code,Object data){
        return of(code,data);
    }
}
