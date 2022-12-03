package com.lyf.config.security.service;

import com.lyf.entity.SysMenu;
import com.lyf.entity.SysUser;
import com.lyf.mapper.SysUserMapper;
import com.lyf.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserDetailsService接口，实现自定义登录逻辑
 * 重写loadUserByUsername方法
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public UserDetails loadUserByUsername(String username) {
        //1.在mapper中自定义登录，根据用户名获取用户信息
        SysUser user;
//        if(redisUtil.hasKey("userInfo_"+username)){
//            //缓存中存在用户信息，从redis中获取
//            user =(SysUser)redisUtil.getValue("userInfo_" + username);
        //刷新5分钟重置时间
                //redisUtil.expire("userInfo_" + username,5);
//        }else {
            user = userMapper.findByUsername(username);
            if (null == user) {
                throw new UsernameNotFoundException("用户名或密码错误！");
            }
//        是管理员
            if (user.isAdmin()) {
//            user.setMenus(userMapper.findMenus(null));
                user.setRoles(userMapper.findRoles(null));
                user.setPermissions(userMapper.findPermissions(null));
                //获取父级菜单
                List<SysMenu> menus = userMapper.findMenus(null);
                //获取子级菜单
                menus.forEach(item -> item.setChildren(userMapper.findChildrenMenus(item.getId(), null)));
                user.setMenus(menus);
            } else {
//            非管理员需要查询角色信息
                user.setRoles(userMapper.findRoles(user.getId()));
                user.setPermissions(userMapper.findPermissions(user.getId()));
                //获取父级菜单
                List<SysMenu> menus = userMapper.findMenus(user.getId());
                //获取子级菜单
                menus.forEach(item -> item.setChildren(userMapper.findChildrenMenus(item.getId(), user.getId())));
                user.setMenus(menus);
            }
//            redisUtil.setValueTime("userInfo_" + username, user,5);
//        }
        return user;
    }
}
