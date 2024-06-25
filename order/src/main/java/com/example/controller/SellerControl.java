package com.example.controller;

import com.example.mapper.SellerMapper;
import com.example.pojo.Menu;
import com.example.pojo.Middle;
import com.example.pojo.Order;
import com.example.pojo.Result;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seller")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*")
public class SellerControl {

    @Autowired
    private SellerMapper seller;


    //————————————————————————————————添加菜名————————————————————————
    //FIXME：@RequestParam注解不能获取put,get,请求请求体的x-www-form-urlencode数据！！但可以获取post的url和body的数据
    @PutMapping("provide")
    public Result provide(@RequestParam(value="name") String name,@RequestParam(value="price") int price){
        //存入是以Integer存进去的，所以要用Integer接值，Integer可以自动拆箱，所以可以用int来接值。
        int account=ThreadLocalUtil.get();
        Menu menu = seller.findByaccount(account,name);
        if(menu!=null)
            return Result.error("菜名重复！");
        seller.add(account,name,price);
        return Result.success("添加成功");

    }

    @DeleteMapping("delete")
    public  Result delete(@RequestParam(value = "name") String name){
        Integer account=ThreadLocalUtil.get();
        //——————先进行退款操作：
        //1.查找改菜的该菜订单的数量和价格
        //FIXME：int不能接收返回值null但Integer可以！！
        Integer orderCount=seller.orderCount(account,name);
        if(orderCount==null) {
            //下架商品
            seller.delete(account,name);
            return Result.success("删除成功！");
        }
        int price = seller.queryPrice(account,name);

        //2.商家账户先扣钱
        seller.subSum(account,price,orderCount);
        /*
        // 还可以创建一个新的Map来存储更新操作的参数
            Map<String, Object> updateParameters = new HashMap<>();
            updateParameters.put("price", price);
            updateParameters.put("orderCount", orderCount);
            updateParameters.put("account", parameters.get("account"));

            // 执行更新操作
            sellerMapper.subSum(updateParameters);
         */
        //3.找出预定该菜的顾客的account，和该订单的数量(顾客账号，顾客订单数量)
        List<Middle> UCMap=seller.queryUC(account,name);
        //4.给每个顾客退钱
        for (Middle middle : UCMap) {
            int theAccount = middle.getCustomer();
            int theNum = middle.getNum();
            seller.refund(theAccount, theNum, price);
        }

        //下架商品
        seller.delete(account,name);
        //将订单从orders表上删除
        seller.delFromOrders(account,name);

        return Result.success("删除成功！已经把钱退回给客户！");
    }

    //查看自己的菜单
    @GetMapping("mymenu")
    public List<Menu> mymenu(){
        int account=ThreadLocalUtil.get();
        List<Menu> menuList=seller.queryMenu(account);
        return menuList;
    }
    //查看订单
    @GetMapping("myorder")
    public List<Order> myorder(){
        int account=ThreadLocalUtil.get();
        List<Order> orderList=seller.queryOrder(account);
        return  orderList;
    }

}
