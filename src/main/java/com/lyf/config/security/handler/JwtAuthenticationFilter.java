package com.lyf.config.security.handler;


import com.lyf.config.security.service.UserDetailServiceImpl;
import com.lyf.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token认证
 * 在接口访问前进行过滤
 */

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenUtils TokenUtils;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Autowired
    protected HttpServletRequest request;


    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //1.获取token
        String authHeader = request.getHeader("Authorization");
        //2.判断token是否存在
        if(null != authHeader && authHeader.startsWith(tokenHead)){
            //拿到token主体
            String authToken = authHeader.substring(tokenHead.length());

//            System.out.println("--------"+TokenUtil.getUserNameByToken(authToken));
//            System.out.println("============="+TokenUtil.getUserNameByToken(authToken));
            //根据token获取用户名
            String username = TokenUtils.getUserNameByToken(authToken);
            //3.token存在，但是没有登录信息
            if(null != username && null == SecurityContextHolder.getContext().getAuthentication()){
                //没有登录信息,直接登录
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //判断token是否有效
                if(TokenUtils.validateToken(authToken,userDetails)) {
                    //刷新security中的用户信息
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

        }

        chain.doFilter(request, response);
    }
}
