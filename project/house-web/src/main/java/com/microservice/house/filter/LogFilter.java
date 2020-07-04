package com.microservice.house.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class LogFilter implements Filter {

    //添加日志
    private Logger logger = LoggerFactory.getLogger(LogFilter.class);

    //启动执行
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    //拦截执行
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("request-coming");
        filterChain.doFilter(servletRequest,servletResponse);
    }
    //销毁执行
    @Override
    public void destroy() {

    }
}
