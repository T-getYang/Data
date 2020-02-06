package com.ty.xcx.demo.mapper;

import com.ty.xcx.demo.model.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("SELECT * FROM `order` where opid = (#{openid})")
    List<Order> getOrdersByUnionId(String openid);

    @Insert("INSERT INTO `order`(opid)VALUES (#{openid})")
    void addOrder(String opid);

    @Delete("DELETE FROM `order` WHERE id=#{id}")
    void deleteOrder(Integer id);

    @Update("UPDATE `order` SET state=#{state} WHERE id=#{id}")
     void updateOrder(Integer id, Integer state);

    @Select("SELECT `state`\n" +
            "\tFROM `order` WHERE id = #{id} AND `state` = #{state}")
    Boolean queryStatus(Integer state, Integer id);

}

