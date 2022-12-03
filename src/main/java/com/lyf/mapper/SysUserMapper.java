package com.lyf.mapper;

import com.github.pagehelper.Page;
import com.lyf.entity.SysMenu;
import com.lyf.entity.SysPermission;
import com.lyf.entity.SysRole;
import com.lyf.entity.SysUser;
import com.lyf.utils.QueryInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户相关操作
 */
public interface SysUserMapper {

    /**
     * 根据用户名获取用户对象
     * @param username
     * @return
     */
    SysUser findByUsername(String username);

    /**
     *根据用户id查询权限信息
     * @param userId
     * @return
     */
    List<SysRole> findRoles(@Param("userId") Long userId);

    /**
     * 根据用户id查询菜单信息
     * @param userId
     * @return
     */
    List<SysMenu> findMenus(@Param("userId") Long userId);

    /**
     * 根据用户id和父级id查询子菜单信息
     * @param id 父级id
     * @return
     */
    List<SysMenu> findChildrenMenus(@Param("id") Long id,@Param("userId") Long userId);

    /**
     * 根据用户id查询权限数据
     * @param userId
     * @return
     */
    List<SysPermission> findPermissions(@Param("userId") Long userId);

    /**
     * 分页查询用户信息
     * @param queryString 分页查询字符串
     * @return
     */
    Page<SysUser> findPage(String queryString);


    /**
     * 添加用户信息
     */
    void insert(SysUser user);

    /**
     * 更新用户信息
     */
    void update(SysUser user);

    /**
     * 删除用户信息
     */
    void delete(Long id);


    /**
     * 根据用户信息中的角色列表，添加用户的角色
     * @param userId
     * @param roleId
     */
    void insertUserRoles(@Param("userId") Long userId,@Param("roleId") Long roleId);

    /**
     *
     * @param userId
     */
    void deleteRolesByUserId(Long userId);


    /**
     * 根据用户id查询用户基本信息
     * @param id
     * @return
     */
    SysUser findById(Long id);

}
