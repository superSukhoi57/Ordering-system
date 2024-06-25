package com.example.fliter;


import com.example.utils.JwtUtil;
import com.example.utils.ThreadLocalUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class Interceptor implements HandlerInterceptor {
    //preHandle前置拦截，在执行controller前执行。
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //TODO：处理预请求的代码，要是没写这段代码，那么携带请求头跨域请求会得不到响应
        //携带请求头的请求会先发送一个预请求，服务器收到后要返回一些东西证明服务器是可连接的，没有返回那么请求将不能正常发出。
        if (request.getMethod().equals("OPTIONS")) {
            response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");//*表示放行所有的源，http://127.0.0.1:5500
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setStatus(HttpServletResponse.SC_OK);
            return false;
        }

        //这是fecthAPI加了include后的

        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");//*表示放行所有的源，http://127.0.0.1:5500
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // 获取所有的cookie
        Cookie[] cookies = request.getCookies();
        String token=null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 找到名为"myCookie"的cookie
                if (cookie.getName().equals("token")) {
                    //获取cooki里面的token数据
                    token=cookie.getValue();
                    break;
                }

            }
        }

        //token验证
        try {//使用try：验证成功就不会抛出异常，没成功就有异常，用有异常来做分支！

            //根据token在redis获取信息,如果结果不为空就验证通过.
            Map<String,Object> claims= JwtUtil.parseToken(token);
            Integer account = (Integer)claims.get("account");
            //TODO:拦截器返回true就是放行！false就是不放行
            //得到数据后可以把业务数据存储到ThreadLocal中！调用我们自己写的工具类，这里存了账号！
            ThreadLocalUtil.set(account);

            return true;
        } catch (Exception e) {

            //设置状态码:
            response.setStatus(401);
            return false;
        }

    }
}
