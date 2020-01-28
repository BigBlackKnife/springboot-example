package com.blaife.filter.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Order(3)
@WebFilter(filterName = "sessionFilter", urlPatterns = "/sessionTest/*", initParams = {
        @WebInitParam(name = "URL", value = "http://localhost:8080/blaife/sessionTest/main")})
public class SessionFilter implements Filter {

    //不需要登录就可以访问的路径(比如:注册登录等)
    String[] includeUrls = new String[]{"/blaife/sessionTest/login", "/blaife/sessionTest/loginHtml"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String url = filterConfig.getInitParameter("URL");
        System.out.println("我是sessionFilter的初始化方法！URL=" + url +  "，生活开始.........");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("我是sessionFilter过滤器的执行方法，客户端向Servlet发送的请求被我拦截到了");
        HttpServletRequest request = (HttpServletRequest)servletRequest; // request
        HttpServletResponse response = (HttpServletResponse)servletResponse; // response
        HttpSession session = request.getSession(false); // session
        // 当前执行方法名,在下方判断是否需要执行过滤方法
        boolean needFilter = isNeedFilter(request.getRequestURI());
        if (!needFilter) { // 不需要
            filterChain.doFilter(servletRequest, servletResponse);
        } else { // 需要
            if(session!=null && session.getAttribute("user") != null) { // 已登陆
                filterChain.doFilter(request, response);
            } else { // 未登录
                // "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
                // 此代码成立时，说明使用的时ajax提交，可能会需要其他方式，如提示用户登陆而不是重定向
                response.sendRedirect(request.getContextPath()+"/sessionTest/loginHtml");
            }
        }
        System.out.println("我是sessionFilter过滤器的执行方法，Servlet向客户端发送的响应被我拦截到了");
    }

    /**
     * 判断是否需要进行过滤
     * 更直接的办法是把不登录也可以访问的接口放在一起，直接屏蔽掉它
     * @param uri
     * @return
     */
    public boolean isNeedFilter(String uri) {
        for (String includeUrl : includeUrls) {
            if (includeUrl.equals(uri)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void destroy() {
        System.out.println("我是sessionFilter过滤器的被销毁时调用的方法！，活不下去了................" );
    }
}
