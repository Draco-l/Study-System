package com.lyf.mapper;

import com.github.pagehelper.Page;
import com.lyf.entity.SysRole;
import org.apache.ibatis.annotations.Param;

/**
 * 角色信息增删改查
 */
public interface SysRoleMapper {

    /**
     * 添加角色信息
     * @param role 角色数据
     */
    void insert(SysRole role);

    /**
     * 修改角色数据
     * @param role
     */
    void update(SysRole role);

    /**
     * 删除角色
     * @param id
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<SysRole> findPage(String queryString);

    /**
     * 添加角色权限信息
     */
    void insertPermissions(@Param("roleId") Long roleId,@Param("permissionID") Long permissionId);


    /**
     * 添加角色菜单信息
     */
    void insertMenus(@Param("roleId") Long roleId,@Param("menuId") Long menuId);

    /**
     * 根据角色id删除对应的权限信息
     * @param roleId
     */
    void deletePermissionById(@Param("roleId") Long roleId);

    /**
     * 根据角色id删除对应的权限信息
     * @param roleId
     */
    void deleteMenuById(@Param("roleId") Long roleId);

    /**
     * 根据角色名称查询角色是否已经存在
     * @param label
     * @return
     */
    SysRole findByLabel(String label);
}
