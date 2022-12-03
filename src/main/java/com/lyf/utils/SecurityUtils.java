package com.lyf.utils;

import com.lyf.entity.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用于获取当前登录用户的基本信息
 */
public class SecurityUtils {

    /**
     * 从Security主体信息中获取用户信息
     * @return
     */
    public static SysUser getUser(){
        SysUser user = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        将密码赋值为null，防止前端泄露
        user.setPassword(null);
       // user.setName(user.getUsername());
        return user;
    }

    /**
     * 在security中获取用户名
     * @return
     */
    public static String getUsername(){
        return getUser().getUsername();
    }


    public static Long gerUserId(){
        return getUser().getId();
    }

}
