package com.example.mapper;

import com.example.pojo.Menu;
import com.example.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerMapper {
    List<Menu> queryAllMenu(int account);

    List<Order> queryMyOrder(int account);
    //用户下单减去price
    void subprice(@Param("account") int account,@Param("price") int price);
    //商家账户加上price
    void addprice(@Param("account") int seller,@Param("price") int peice);
    //将订单添加到orders表
    void addorder(@Param("seller") int seller, @Param("name") String name, @Param("account") int account);
    //找出要退订的菜的价格
    int queryprice(@Param("seller") int seller, @Param("name") String name);
    //商家先减去price
    void sellersub(@Param("seller") int seller, @Param("price") int price);
    //顾客加上price
    void customeradd(@Param("customer") int customer,@Param("price") int price);
    //删除订单,num唯一，所以可以直接使用它
    void withdraw(@Param("num")int num);
    //确认收获，此操作回将订单信息从orders表删除
    void confirm(@Param("num") int num);
    //通过订单号查找订单。
    Order queryOrderBynum(@Param("num") int num);

}
