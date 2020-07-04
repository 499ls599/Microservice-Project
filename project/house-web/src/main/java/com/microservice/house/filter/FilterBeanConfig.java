package com.microservice.house.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FilterBeanConfig {

    /**
     * @ Description   :
     * 1:构造Filter
     * 2：配置拦截urlPattern
     * 3：利用FilterRegistrationBean进行包装
     *
     * @ Author        :  1910959369@qq.com
     * @ CreateDate    :  2020/7/3 18:38
     */

    //加上@Bean注解，SpringBoot会将返回对象识别成一个SpirngBean
    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new LogFilter());
        List<String> urlList = new ArrayList<>();
        urlList.add("*");
        filterRegistrationBean.setUrlPatterns(urlList);
        return filterRegistrationBean;
    }

}
