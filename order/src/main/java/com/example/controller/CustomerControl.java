package com.example.controller;

import com.example.mapper.CustomerMapper;
import com.example.pojo.Menu;
import com.example.pojo.Order;
import com.example.pojo.Result;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*")
public class CustomerControl {
    @Autowired
    private CustomerMapper customer;

    //请求所有的菜单信息
    @GetMapping("getallmenu")
    public List<Menu> getallmenu(){
        int account= ThreadLocalUtil.get();
        return customer.queryAllMenu(account);
    }
    //请求自己的订单信息
    @GetMapping("myorder")
    public List<Order> myorder(){
        int account=ThreadLocalUtil.get();
        return customer.queryMyOrder(account);
    }
    //下单
    @PostMapping("pay")
    public Result pay(@RequestBody Menu menu){
        int account=ThreadLocalUtil.get();
        //用户先减去price
        customer.subprice(account,menu.getPrice());
        //商家的账户加上price
        customer.addprice(menu.getSeller(),menu.getPrice());
        //将订单添加到orders表
        customer.addorder(menu.getSeller(),menu.getName(),account);
        return Result.success("下单成功！");
    }
    //退订，这个操作前端可以先预处理，当顾客请求自己的的订单信息时，如果rider不为空就证明骑手已经出餐，
    // 这种情况下不能退（前端直接提示），不发送请求。发送的请求都是可以直接退的!
    @PostMapping("/withdraw")
    public Result withdraw(@RequestBody Order order){
        Order qbnum=customer.queryOrderBynum(order.getNum());
        //先找出这款菜的价格
        int price=customer.queryprice(qbnum.getSeller(),qbnum.getName());
        //商家先减去price
        customer.sellersub(qbnum.getSeller(),price);
        //顾客加上price
        customer.customeradd(qbnum.getCustomer(),price);
        //删除订单
        customer.withdraw(qbnum.getNum());
        return Result.success("退款成功！");
    }

    //确认收货
    @PostMapping("/confirm")
    public Result confirm(@RequestBody Order order){
        //确认收获并删除订单
        customer.confirm(order.getNum());
        return Result.success("谢谢惠顾😚");
    }

}
