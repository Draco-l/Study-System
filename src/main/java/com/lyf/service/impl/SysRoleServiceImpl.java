package com.lyf.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lyf.entity.SysMenu;
import com.lyf.entity.SysPermission;
import com.lyf.entity.SysRole;
import com.lyf.mapper.SysMenuMapper;
import com.lyf.mapper.SysPermissionMapper;
import com.lyf.mapper.SysRoleMapper;
import com.lyf.service.SysMenuService;
import com.lyf.service.SysRoleService;
import com.lyf.utils.PageResult;
import com.lyf.utils.QueryInfo;
import com.lyf.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysPermissionMapper permissionMapper;

    
    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("开始角色数据分页-->页码{},-->{}页数-->{}查询内容",queryInfo.getPageNumber(),queryInfo.getPageSize(),queryInfo.getQueryString());
        PageHelper.startPage(queryInfo.getPageNumber(),queryInfo.getPageSize());
        Page<SysRole> page = roleMapper.findPage(queryInfo.getQueryString());
        long total = page.getTotal();
        log.info("查询的总条数-->{}",total);
        List<SysRole> result = page.getResult();
        log.info("分页列表-->{}",result);
        result.forEach(item ->{
            //查询角色下菜单信息
            List<SysMenu> menus = menuMapper.findByRoleId(item.getId());
            menus.forEach(menu -> {
                List<SysMenu> children = menuMapper.findByRoleIdAndParentId(menu.getId(),item.getId());
                menu.setChildren(children);
            });
            item.setMenus(menus);
            //查询角色权限
            List<SysPermission> permissions =  permissionMapper.finByRoleId(item.getId());
            item.setPermissions(permissions);
        });
         return  PageResult.pageResult(total,result);
    }

    @Override
    public Result insert(SysRole role) {
        log.info("查询角色信息是否存在");
        SysRole role1 =  roleMapper.findByLabel(role.getLabel());
        if (null != role1) {
            return Result.fail("该角色已经存在");
        }
        roleMapper.insert(role);
        if(role.getPermissions().size()>0){
            log.info("再添加对应的权限数据");
            role.getPermissions().forEach(item -> roleMapper.insertPermissions(role.getId(),item.getId()));
        }
        if(role.getMenus().size()> 0){
            role.getMenus().forEach(item -> roleMapper.insertMenus(role.getId(),item.getId()));
        }
        return Result.success("添加角色数据成功");
    }

    @Override
    public Result delete(Long id) {
        log.info("查询角色信息下是否有菜单权限");
        List<SysMenu> menus = menuMapper.findByRoleId(id);
        List<SysMenu> childrens = new ArrayList<>();
        menus.forEach(item->{
            childrens.addAll(menuMapper.findByRoleIdAndParentId(item.getId(),id));
        });
        if(menus.size()>0 || childrens.size() > 0){
            return Result.fail("删除失败，该角色下有菜单信息，请先删除对应的菜单信息！");
        }
        if(permissionMapper.finByRoleId(id).size()>0){
            return Result.fail("删除失败，该角色下有权限信息，请先删除对应的权限信息！");
        }
        roleMapper.delete(id);
        return Result.success("删除角色数据成功");
    }

    @Transactional
    @Override
    public Result update(SysRole role) {
        roleMapper.update(role);
        if(role.getPermissions().size()>0){
            log.info("先删除对应的权限数据");
            roleMapper.deletePermissionById(role.getId());
            log.info("再添加对应的权限数据");
            role.getPermissions().forEach(item-> roleMapper.insertPermissions(role.getId(), item.getId()));
        }
        if(role.getMenus().size()>0){
            roleMapper.deleteMenuById(role.getId());
            role.getMenus().forEach(item -> roleMapper.insertMenus(role.getId(),item.getId()));
        }
        return Result.success("修改角色数据成功");
    }

}
