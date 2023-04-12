package com.itheima.reggie.filter;


import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        //1.获取请求的URL
        String requestURI = request.getRequestURI();

        //定义直接放行的url
        String[] urls=new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        //2.判断本次请求是否需要处理
        Boolean check = check(requestURI, urls);

        //3.如果不需要直接放行
        if (check){
            log.info("本次请求为{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }
        //4.判断是否已经登录，如果已经登录，直接放行
        if (request.getSession().getAttribute("employee")!=null){
            log.info("本次请求的id为{}",request.getSession().getAttribute("employee"));
            Long employee = (Long) request.getSession().getAttribute("employee");
            //线程携带id
            BaseContext.setCurrentId(employee);
            filterChain.doFilter(request,response);
            return;
        }

        //4.1判断是否已经登录，如果已经登录，直接放行
        if (request.getSession().getAttribute("user")!=null){
            log.info("本次请求的id为{}",request.getSession().getAttribute("user"));
            Long user = (Long) request.getSession().getAttribute("user");
            //把user赋值给线程携带
            BaseContext.setCurrentId(user);
            filterChain.doFilter(request,response);
            return;
        }
        log.info("用户未登录");
        //5.如果未登录，返回未登录结果
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    /**
     * 路径匹配器
     * @param requestURL
     * @param urls
     * @return
     */
    public Boolean check(String requestURL,String[] urls){
        for (String url:urls){
            boolean match = PATH_MATCHER.match(url, requestURL);
            if (match){
                return true;
            }

        }
        return false;
    }
}
