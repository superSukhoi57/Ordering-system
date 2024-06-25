package com.example.mapper;

import com.example.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RiderMapper {

    //所有订单信息
    List<Order> queryallorder();
    //可以抢的订单
    List<Order> queryorderable();
    //抢单
    void rob(@Param("num")int num,@Param("rider")int rider);
    //查看自己的订单信息
    List<Order> querymyorder(int account);
}
