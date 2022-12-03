package com.lyf.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域问题配置类
 */


@Configuration
public class webConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry//允许访问的路径
                .addMapping("/**")
                //配置请求来源
                .allowedOrigins("http://localhost:8888")
                //配置允许访问的方法
                .allowedMethods("GET","POST","DELETE","PUT")
                //是否允许携带参数
                .allowCredentials(true)
                //最大响应时间
                .maxAge(3600);
    }
}
