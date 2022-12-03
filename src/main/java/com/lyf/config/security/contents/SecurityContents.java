package com.lyf.config.security.contents;

/**
 * 白名单
 */
public class SecurityContents {
    public static final  String[] WHITE_LIST = {
            //后端登录接口
            "/user/login",

            //swagger相关
            "/favicon.ico",
            "swagger/**",
            "swagger-ui.html",
            "/swagger-ui.html",
            "/doc.html",
            "/webjars/**",
            "/swagger-resource/**",
            "/v2/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html/*",
            "/swagger-resources",
            "/swagger-resources/configuration/ui"

    };
}
