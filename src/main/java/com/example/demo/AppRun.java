package com.example.demo;

import com.example.demo.swagger.api.ApiOriginFilter;
import com.example.demo.swagger.LoginFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;


/**
 * @author liuwei
 * @version 1.0
 **/
@SpringBootApplication
@MapperScan("com.example.demo.mybatis.mapper")
public class AppRun {
    /**
     * 配置过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(apiOriginFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("apiOriginFilter");
        registration.setOrder(Integer.MAX_VALUE - 1);
        return registration;
    }

    /**
     * 配置过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean loginFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(loginFilter());
        registration.addUrlPatterns("/hplus/*");
        registration.addUrlPatterns("/api/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("loginFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }

    /**
     * 创建一个bean
     * @return
     */
    @Bean(name = "apiOriginFilter")
    public Filter apiOriginFilter() {
        return new ApiOriginFilter();
    }

    /**
     * 创建一个bean
     * @return
     */
    @Bean(name = "loginFilter")
    public Filter loginFilter() {
        return new LoginFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(AppRun.class, args);
        System.out.println("======================Spring Boot 启动成功========================");
    }

}
