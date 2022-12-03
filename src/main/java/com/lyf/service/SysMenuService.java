package com.lyf.service;

import com.lyf.entity.SysMenu;
import com.lyf.entity.SysPermission;
import com.lyf.utils.QueryInfo;
import com.lyf.utils.Result;

public interface SysMenuService {

    /**
     * 分页查询
     * @param queryInfo 页码，页数大小，查询内容
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 添加菜单数据
     * @param menu
     * @return
     */
    Result insert(SysMenu menu);

    /**
     * 删除菜单数据
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 修改菜单数据
     * @param menu
     * @return
     */
    Result update(SysMenu menu);

    /**
     * 查询父级菜单
     */
    Result findParent();
}
