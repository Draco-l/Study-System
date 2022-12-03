package com.lyf.controller;

import com.lyf.entity.SysPermission;
import com.lyf.service.SysPermissionService;
import com.lyf.utils.QueryInfo;
import com.lyf.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 权限数据控制层
 */
@RestController
@Api(tags = "权限数据")
@RequestMapping("/Permission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService permissionService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo){
        return permissionService.findPage(queryInfo);
    }

    @ApiOperation(value = "添加权限")
    @PostMapping("/insert")
    public Result insert(@RequestBody SysPermission permission){
        return permissionService.insert(permission);
    }
    @ApiOperation(value = "修改权限")
    @PutMapping("/update")
    public Result update(@RequestBody SysPermission permission){
        return permissionService.update(permission);
    }
    @ApiOperation(value = "删除权限")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }


    @ApiOperation(value = "查询所有权限")
    @GetMapping("/findAll")
    public  Result findAll(){
        return permissionService.findAll();
    }

}
