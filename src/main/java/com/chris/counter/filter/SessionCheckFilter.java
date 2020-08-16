package com.chris.counter.filter;

import com.chris.counter.service.AccountService;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class SessionCheckFilter implements Filter {

    @Autowired
    private AccountService accountService;

    private Set<String> whiteRootPaths = Sets.newHashSet(
            "login", "msgsocket", "test"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin","*");
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String url = request.getRequestURI();
        String[] split = url.split("/");
        if(split.length < 2){
            request.getRequestDispatcher("/login/loginfail")
            .forward(servletRequest, servletResponse);
        }else{
            if(!whiteRootPaths.contains(split[1])){
                if(accountService.accountInCache(
                       request.getParameter("token")
                )){
                    filterChain.doFilter(servletRequest, servletResponse);
                }else{
                    request.getRequestDispatcher("/login/loginfail")
                            .forward(servletRequest, servletResponse);
                }

            }else{
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
