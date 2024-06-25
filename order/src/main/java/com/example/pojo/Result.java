package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO：这是一个返回的类，会转化为Json，是一个全是静态方法的类！

@NoArgsConstructor//lombok的无参构造方法
@AllArgsConstructor//Lombok的全参数构造方法
@Data//TODO：控制台No acceptable representation就是将类自动转换成Json字符串，写@Data注解或者像@EnableWebMvc配置json转换器
public class Result<T> {//TODO：泛型：我们声明什么他就是什么。
    private Integer code;//业务状态码-成功1-失败
    private String message;//提示信息
    private T data;//响应数据

    //块速返回操作成功响应结果(带响应数据)
    /*

第一个 <E>：这是在声明这个方法使用泛型，E 是一个类型占位符，表示这个方法可以接受任何类型的参数。
第二个 Result<E>：这表示这个方法将返回一个 Result 对象，这个对象的 data 成员变量的类型是 E。
第三个 (E data)：这表示这个方法接受一个类型为 E 的参数 data
     */
    public static <E> Result<E> success(E data) {
        return new Result<>(0,"操作成功",data);
    }

    //快速返回操作成功响应结果
    public static Result success() {
        return new Result(0,"操作成功", null);
    }

    public static Result error(String message) {
        return new Result(1,message, null);
    }
}
