package com.ty.xcx.demo.contoller;

import com.ty.xcx.demo.model.BizResponse;
import com.ty.xcx.demo.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ty.xcx.demo.model.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

import static com.ty.xcx.demo.contoller.UserContoller.tokenAndOpenID;

@RequestMapping("/orderxx")
@RestController
public class OrderContoller {
    @Autowired
    OrderMapper OrderMapper;

    @GetMapping
    public BizResponse getOrder(@RequestHeader("token") String token, HttpServletRequest request, HttpServletResponse response) {
        String openid = tokenAndOpenID.get(token);

        List<Order> orders = OrderMapper.getOrdersByUnionId(openid);
        Collections.reverse(orders);
        return BizResponse.fail(0, orders);
    }
//    @GetMapping("/state")
//    public BizResponse getState(@RequestParam("id")Integer id){
//        OrderMapper.getState(id);
//
//        return BizResponse.fail(0,);
//    }

    @GetMapping("/state")
    public BizResponse queryStatus(@RequestParam Integer state,@RequestParam Integer id){
        System.out.println("status = " + state);
        Boolean bool = OrderMapper.queryStatus(state,id);
        System.out.println("bool = " + bool);
        return BizResponse.of(0,bool);
    }

    @PostMapping
    public BizResponse add(@RequestHeader("token") String token,HttpServletRequest request){
        String openid = tokenAndOpenID.get(token);
        OrderMapper.addOrder(openid);
        return BizResponse.of(0, null);
    }

    @DeleteMapping
    public BizResponse deleteOrder(@RequestParam("id")Integer id) {
        OrderMapper.deleteOrder(id);
        return BizResponse.of(0, null);
    }


}
