package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.Param;


import java.util.List;


public interface UserMapper {
    List<User> findAll();

    User queryByUsername(String username);

    //向数据库插入数据,用来注册用户
    void insert(@Param("account") int account,@Param("password")String password,
                @Param("username")String username,@Param("category") int category);
    User queryByAccount(int account);

    void alterPwd(@Param("account") int account, @Param("newPwd") String newPwd);

    void alterOther(@Param("account") int account,@Param("nun") String nun, @Param("npic") String npic);
    //获取个人信息
    List<User> querymessage(int account);
}
