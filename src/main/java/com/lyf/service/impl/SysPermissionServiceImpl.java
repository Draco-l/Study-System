package com.lyf.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lyf.entity.SysPermission;
import com.lyf.mapper.SysPermissionMapper;
import com.lyf.service.SysPermissionService;
import com.lyf.utils.PageResult;
import com.lyf.utils.QueryInfo;
import com.lyf.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
@Slf4j
public class SysPermissionServiceImpl  implements SysPermissionService {

    @Autowired
    private SysPermissionMapper permissionMapper;


    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("开始权限数据分页-->页码{},-->{}页数-->{}查询内容",queryInfo.getPageNumber(),queryInfo.getPageSize(),queryInfo.getQueryString());
        PageHelper.startPage(queryInfo.getPageNumber(),queryInfo.getPageSize());
        Page<SysPermission> page = permissionMapper.findPage(queryInfo.getQueryString());
        long total = page.getTotal();
        List<SysPermission> result = page.getResult();
        log.info("查询的总条数-->{}",total);
        log.info("分页列表-->{}",result);
        return  new PageResult(total,result);
    }

    @Override
    public Result insert(SysPermission permission) {
        permissionMapper.insert(permission);
        return Result.success("添加权限数据成功");
    }

    @Override
    public Result delete(Long id) {
        permissionMapper.delete(id);
        return Result.success("删除权限数据成功");
    }

    @Override
    public Result update(SysPermission permission) {
        permissionMapper.update(permission);
        return Result.success("修改权限数据成功");
    }

    @Override
    public Result findAll() {
        return Result.success("查询权限信息成功",permissionMapper.findAll());
    }
}
