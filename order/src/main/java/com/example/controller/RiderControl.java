package com.example.controller;

import com.example.mapper.RiderMapper;
import com.example.pojo.Order;
import com.example.pojo.Result;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("rider")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*")
public class RiderControl {

    @Autowired
    private RiderMapper rider;

    //查看所有的订单信息
    @GetMapping("/allorder")
    public List<Order> allorder(){
        return rider.queryallorder();
    }

    //筛选处还没被抢的单
    @GetMapping("/orderable")
    public  List<Order> orderable(){
        return rider.queryorderable();
    }

    //抢单
    @PostMapping("/rob")
    public Result rob(@RequestBody Order order){
        rider.rob(order.getNum(),(int)ThreadLocalUtil.get());
        return Result.success("抢单成功！");
    }

    //查看自己的订单信息
    @GetMapping("/myorder")
    public List<Order> myorder(){
        return rider.querymyorder((int) ThreadLocalUtil.get());
    }


}
