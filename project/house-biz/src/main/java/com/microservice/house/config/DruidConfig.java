package com.microservice.house.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.druid")
    //在SpringBoot容器启动的时候也要把dataSource也启动，容器销毁时关闭数据源释放连接
    @Bean(initMethod = "init",destroyMethod = "close")
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
        return  dataSource;
    }

    //可以打印慢日志的方法
    //Druid数据源监控及慢sql记录
    @Bean
    public StatFilter statFilter(){
        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(1);//根据时间才使用
        statFilter.setLogSlowSql(true);//是否打印出慢日志
        statFilter.setMergeSql(true);//是否要将日志合并起来
        return statFilter;
    }

    //TODO   健康之心开启会导致找不到-httpservlet
    //添加监控，可以分析Mysql的执行时间，执行时间的分布
   /* @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        return servletRegistrationBean;
    }*/
}
