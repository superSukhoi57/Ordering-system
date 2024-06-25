package com.example;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.springframework.web.bind.annotation.GetMapping;


public class CookieTest {


   /* @Test
    public String setCookie(HttpServletResponse response) {
        // 创建一个cookie
        Cookie cookie = new Cookie("myCookie", "cookieValue");
        // 设置cookie的有效期为600秒
        cookie.setMaxAge(600);
        // 将cookie添加到响应中
        response.addCookie(cookie);
        return "Cookie has been set";
    }*/
   @Test
   public void setCookie() {
       // 创建一个cookie
       Cookie cookie = new Cookie("token", "cookieValue");
       // 设置cookie的有效期为6小时
       cookie.setMaxAge(60*60*6);
       // 将cookie添加到响应中
       System.out.println(cookie);
       // 输出cookie的各个值
       System.out.println("Name: " + cookie.getName());
       System.out.println("Value: " + cookie.getValue());
       System.out.println("Max Age: " + cookie.getMaxAge());
       System.out.println("Domain: " + cookie.getDomain());
       System.out.println("Path: " + cookie.getPath());
   }
    @GetMapping("/getCookie")
    public String getCookie(HttpServletRequest request) {
        // 获取所有的cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 找到名为"myCookie"的cookie
                if (cookie.getName().equals("myCookie")) {
                    return "Cookie Value: " + cookie.getValue();
                }
            }
        }
        return "No cookie found";
    }
}