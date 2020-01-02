package com.blaife.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器demo
 * Filter是Servlet技术中最实用的技术，Web开发人员通过Filter技术，对web服务器管理的所有web资源：例如Jsp, Servlet, 静态图片文件或静态 html 文件等进行拦截，从而实现一些特殊的功能
 * 例如：URL级别的权限访问控制、过滤敏感词汇、压缩响应信息
 *
 * 它主要用于对用户请求进行预处理，也可以对HttpServletResponse进行后处理。
 * 使用Filter的完整流程：Filter对用户请求进行预处理，接着将请求交给Servlet进行处理并生成响应，最后Filter再对服务器响应进行后处理。
 */
public class MyFilter implements Filter {
    /**
     * 初始化执行
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 触发执行方法
     * @param srequest
     * @param sresponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) srequest; // 获取request
        System.out.println("this is MyFilter,url :"+request.getRequestURI()); // 输出请求方法头
        filterChain.doFilter(srequest, sresponse); // 执行下一步
    }

    /**
     * 销毁时执行
     */
    @Override
    public void destroy() {

    }
}
