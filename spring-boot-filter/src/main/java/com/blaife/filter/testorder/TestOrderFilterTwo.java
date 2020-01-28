package com.blaife.filter.testorder;

import javax.servlet.*;
import java.io.IOException;

public class TestOrderFilterTwo implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("testOrder Two running");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("测试Order过滤器Two,客户端-->servlet");
        filterChain.doFilter(servletRequest, servletResponse); // 执行下一步
        System.out.println("测试Order过滤器Two,servlet-->客户端");
    }

    @Override
    public void destroy() {

    }
}
