package com.example.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
//不能和druid连接池放一起是因为这里要注入来连接池,如果放一起配会导致,注入的bean是空的!
//在这里要创建一个SqlSessionFactoryBean和MappScannerConfiguration的bean方法给IOC
@Configuration(proxyBeanMethods=false)//没后面的会报：iguration bean definition 'mybatisConfig' since its singleton instance has been created too early.
public class Mybatis {
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource)
    {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //其他的在mybatis-config.xml文件配置的语句：
        return sqlSessionFactoryBean;
    }
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.example.mapper");
        return mapperScannerConfigurer;
    }
}

/*
SqlSessionFactoryBean 是 MyBatis-Spring 集成库中的一个类。它用于创建 MyBatis 的 SqlSessionFactory，这是 MyBatis 的核心对象，
用于创建 SqlSession。  SqlSession 是 MyBatis 的主要接口，用于执行 SQL 命令、获取映射器（Mapper）或者管理事务。在 MyBatis 中，每个线程都
应该有自己的 SqlSession 实例，因为它们不是线程安全的
 */