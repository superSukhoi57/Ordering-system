package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
/*
@RunWith(SpringRunner.class)是JUnit的一个注解，用于指定测试运行器。SpringRunner是Spring测试模块中的一个运行器，
它提供了Spring测试上下文的支持。  在JUnit中，运行器（Runner）决定了如何以及何时运行测试。SpringRunner是SpringJUnit4ClassRunner的别名
，它会创建Spring应用上下文（ApplicationContext）并管理Spring Bean的生命周期。
当您在测试类上使用@RunWith(SpringRunner.class)注解时，这意味着您希望在运行测试时使用Spring的测试支持。
Spring会在运行测试前创建并初始化Spring应用上下文，然后在这个上下文中查找和创建需要的Spring Bean。
 */
@SpringBootTest
@ContextConfiguration(locations = {"classpath:Spring.xml"})
public class RedisTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setValue(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public String getValue(String key) {
        String str=stringRedisTemplate.opsForValue().get(key);
        System.out.println(str);
        return str;
    }

    @Test
    public void mytest(){
        stringRedisTemplate.opsForValue().set("ikun","25");
        String str=stringRedisTemplate.opsForValue().get("ikun");
        System.out.println("从redis获取ikun："+str);

    }
}
