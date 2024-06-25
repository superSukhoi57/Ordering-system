package com.example.controller;

import com.example.mapper.UserMapper;
import com.example.pojo.Result;
import com.example.pojo.User;
import com.example.utils.JwtUtil;
import com.example.utils.MD5Util;
import com.example.utils.ThreadLocalUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/user")
//被放行了，所以这里要单独加
@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*")
public class UserControl {

    @Autowired
    private UserMapper mapper;

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return mapper.findAll().toString();
    }

    @GetMapping("register")
    @ResponseBody
    public Result register(@RequestParam(value="username") String username,
                           @RequestParam(value="password") String password,
                           @RequestParam(value="category") int category){
        //先检查用户名是否存在
        User user=mapper.queryByUsername(username);
        if(user!=null)
            return Result.error("用户名已存在!");
        int account=new Random().nextInt(1000000000);
        //对密码加密
        password= MD5Util.string2MD5(password);
        mapper.insert(account,password,username,category);
        return Result.success(account);

    }


    @PostMapping("login")
    @ResponseBody
    public Result login(@RequestParam(value="account") int account,
                        @RequestParam(value="password") String password,
                        HttpServletResponse response){
        User user=mapper.queryByAccount(account);
        //被放行了，所以这里要单独加,而且要在所有return前面加，否则行不通
        response.setHeader("Access-Control-Allow-Credentials","true");
        if(user==null)
            return Result.error("账号或密码错误");
        if(MD5Util.string2MD5(password).equals(user.getPassword())) {
            Map<String,Object> map=new HashMap<>();
            //将用户的账号加入token的负载!
            map.put("account",user.getAccount());
            String token=JwtUtil.genToken(map);
            //创建一个cookie
            //Cookie cookie=new Cookie("token",token);
            //cookie.setMaxAge(60*60*6);//是以秒为单位的！
            //TODO：由于这里没有SameSite=None方法所以要动手写，还是同源策略
            response.setHeader("Set-Cookie", "token=" + token + "; Max-Age=" + 60*60*6 + ";Path=/;Secure; SameSite=None;Domain=localhost");
            //添加cookie到响应
            //response.addCookie(cookie);

            return Result.success("登录成功");
        }

        return Result.error("账号或密码错误");
    }

    //验证cookie是否可用
    @GetMapping("getCookie")
    @ResponseBody
    public String getCookie(HttpServletRequest request) {
        // 获取所有的cookie
        Cookie[] cookies = request.getCookies();
        String token=null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 找到名为"myCookie"的cookie
                if (cookie.getName().equals("token")) {
                    //获取cooki里面的token数据
                     token=cookie.getValue();
                    Map<String,Object> claims= JwtUtil.parseToken(token);
                    //从token里获取account
                    /*将一个Integer对象转换为String，但是Java不允许这样的转换。
                    String account=(String)claims.get("account");
                    return account;*/
                    Integer account = (Integer)claims.get("account");
                    //将Integer转化为String
                    String accountString = String.valueOf(account);
                    return accountString;
                    //return "Cookie Value: " + cookie.getValue();
                }

            }
        }
        return "No cookie found";
    }

    //测试是否可以在线程局部变量得到jwt携带的数据
    @GetMapping("getTL")
    @ResponseBody
    public String getTL(){
        return ThreadLocalUtil.get().toString();
    }

    //更改密码
    @PostMapping("alterPwd")
    @ResponseBody
    public Result alterPwd(@RequestParam(value="newPwd") String newPwd){
        int account=ThreadLocalUtil.get();
        //将密码加秘密后存储
        mapper.alterPwd(account,MD5Util.string2MD5(newPwd));
        //TODO：记得在这里加入Redis来实现令牌过期。
        return Result.success("密码更改成功！");
    }


    //更改其他信息
    @PostMapping("alterOther")
    @ResponseBody
    public Result alterOther(@RequestParam(value="Nun")String Nun,@RequestParam(value="Npic")String Npic){
        int account=ThreadLocalUtil.get();
        mapper.alterOther(account,Nun,Npic);
        return Result.success("修改成功");
    }

    //获取我的信息
    @GetMapping("/mymessage")
    @ResponseBody
    public List<User> mymessage(){
        return mapper.querymessage((int)ThreadLocalUtil.get());
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping("redis")
    @ResponseBody
    public String redis(@RequestParam("key")String key){
        return stringRedisTemplate.opsForValue().get(key);
    }
}
