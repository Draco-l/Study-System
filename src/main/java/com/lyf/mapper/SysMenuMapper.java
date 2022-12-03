package com.lyf.mapper;

import com.github.pagehelper.Page;
import com.lyf.entity.SysMenu;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * 菜单数据的增删改查
 */

public interface SysMenuMapper {

    /**
     * 添加菜单信息
     * @param menu 菜单数据
     */
    void insert(SysMenu menu);

    /**
     * 修改菜单数据
     * @param menu
     */
    void update(SysMenu menu);

    /**
     * 删除菜单
     * @param id
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<SysMenu> findPage(String queryString);

    /**
     * 查询所有父级菜单
     * @return
     */
    List<SysMenu> findParent();

    /**
     * 根据角色id查询菜单信息
     * @param id
     * @return
     */
    List<SysMenu> findByRoleId(Long id);

    /**
     * 根据角色id和父级id查询所有子级菜单
     * @param parentId
     * @param roleId
     * @return
     */
    List<SysMenu> findByRoleIdAndParentId(@Param("parentId") Long parentId, @Param("roleId") Long roleId);
}
