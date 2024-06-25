package com.example.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class Mvcinit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{Druid.class, Mybatis.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMvc.class};
    }

    @Override//设置拦截的东西：/表示拦截所有
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
