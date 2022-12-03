package com.lyf.service;

//import com.lyf.entity.SysRole;
import com.lyf.entity.SysRole;
import com.lyf.entity.SysUser;
import com.lyf.utils.QueryInfo;
import com.lyf.utils.Result;
import com.lyf.vo.LoginVo;
//import com.lyf.vo.LoginVo;


/**
 * 用户操作逻辑接口
 */
public interface SysUserService {

//    /**
//     * 获取所有用户信息
//     *
//     */
//    Result findAll();

    /**
     * 登录接口
     * @param loginVo 登录参数： 账号、密码
     * @return 返回一个token，用token获取资源
     */
    Result login(LoginVo loginVo);


    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    SysUser findByUsername(String username);

    /**
     * 分页查询
     * @param queryInfo
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    Result insert(SysUser user);

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    Result update(SysUser user);

    /**
     * 添加用户信息
     * @param id
     * @return
     */
    Result delete(Long id);





}
