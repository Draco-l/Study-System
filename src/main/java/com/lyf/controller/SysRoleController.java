package com.lyf.controller;

import com.lyf.entity.SysRole;
import com.lyf.service.SysRoleService;
import com.lyf.utils.QueryInfo;
import com.lyf.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "角色数据")
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;
    
    @ApiOperation(value = "分页查询")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo){
        return roleService.findPage(queryInfo);
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/insert")
    public Result insert(@RequestBody SysRole role){
        return roleService.insert(role);
    }
    
    @ApiOperation(value = "修改角色")
    @PutMapping("/update")
    public Result update(@RequestBody SysRole role){
        return roleService.update(role);
    }
    
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return roleService.delete(id);
    }
}
