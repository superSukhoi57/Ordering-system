package com.example.mapper;

import com.example.pojo.Menu;
import com.example.pojo.Middle;
import com.example.pojo.Order;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SellerMapper {
    //查找是商家有的菜名。
    Menu findByaccount(@Param("account") int account, @Param("name") String name);
    //添加产品！
    void add(@Param("account") int account,@Param("name") String name, @Param("price") int price);
    //删除产品
    void delete(@Param("account") Integer account,@Param("name") String name);
    //在menus表里面查出商品的价格
    int queryPrice(@Param("account") Integer account, @Param("name") String name);
    //在orders表里面查出该菜的订单总数
    Integer orderCount(@Param("account") Integer account,@Param("name") String name);
    //先扣掉商家账号的钱
    void subSum(@Param("account") Integer account, @Param("price") int price,@Param("orderCount") int orderCount);
    //筛选处下单买该菜的顾客的account和订单的数量
    //@MapKey("customer")//TODO:指定mybatis查询的列名哪个作为key
    List<Middle> queryUC(@Param("account") Integer account, @Param("name") String name);
    //将钱退还给顾客
    void refund(@Param("account") int theAccount, @Param("num") int theNum,@Param("price") int price);
    //将订单从orders表删除
    void delFromOrders(@Param("account") Integer account,@Param("name") String name);
    //查找自己的菜单
    List<Menu> queryMenu(int account);
    //查看自己的订单
    List<Order> queryOrder(int account);
}
