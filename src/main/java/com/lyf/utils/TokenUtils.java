package com.lyf.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * token工具类，用于生成token
 * 用户登录拿到token然后访问系统资源
 */
@Component
public class TokenUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;


    /**
     * 传入用户登录信息，生成token
     * @param details
     * @return
     */
    public String generateToken(UserDetails details){
        Map<String,Object> claims = new HashMap<>(2);
        claims.put("username",details.getUsername());
        claims.put("created",new Date());
        return this.generateJwt(claims);
    }

    /**
     * 根据荷载信息生成token
     * @param claims
     * @return
     */
    private String generateJwt(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(new Date(System.currentTimeMillis() +expiration * 1000))
                .compact();
    }

    /**
     * 根据token获取荷载信息
     * @param token
     * @return
     */
    public Claims getTokenBody(String token){
        try {
            return  Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 根据token得到用户名
     * @param token
     * @return
     */
    public String getUsernameByToken(String token){
        return getTokenBody(token).getSubject();
    }

    /**
     * 根据token在荷载里面取到用户名
     */
    public String getUserNameByToken(String token) {
        return (String) this.getTokenBody(token).get("username");
    }


    /**
     * 根据token判断当前时间内，token是否过期
     * @param token
     * @return
     */
    public boolean isExpiration(String token){
        return this.getTokenBody(token).getExpiration().before(new Date());
    }

    /**
     * 刷新token令牌
     * @param token
     * @return
     */
    public String refreshToken(String token){
        Claims claims = this.getTokenBody(token);
        claims.setExpiration(new Date());
        return this.generateJwt(claims);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameByToken(token);
        return username.equals(userDetails.getUsername()) && !isExpiration(token);
    }

}
