package com.lyf.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lyf.config.security.service.UserDetailServiceImpl;
import com.lyf.entity.SysRole;
import com.lyf.entity.SysUser;
import com.lyf.mapper.SysUserMapper;
import com.lyf.service.SysUserService;
import com.lyf.utils.PageResult;
import com.lyf.utils.QueryInfo;
import com.lyf.utils.Result;
import com.lyf.utils.TokenUtils;
import com.lyf.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 登录接口
     * @param loginVo 登录参数： 账号、密码
     * @return
     */
    @Override
    public Result login(LoginVo loginVo) {
        log.info("1.开始登录");
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginVo.getUsername());
        log.info("2.判断用户是否存在，密码是否正确");
        if(null == userDetails || !passwordEncoder.matches(loginVo.getPassword(),userDetails.getPassword())){
            return Result.fail("账号或密码错误，请重新输入！");
        }
        if(!userDetails.isEnabled()){
            return Result.fail("该账号已禁用，请联系管理员！");
        }
        log.info("登录成功，在security对象中存入登录者信息");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("根据登录信息获取token");

//       借助jwt来生成token
        String token = tokenUtils.generateToken(userDetails);
        Map<String, String> map = new HashMap<>(2);
        map.put("tokenHead",tokenHead);
        map.put("token",token);
        return  Result.success("登录成功",map);
    }


    @Override
    public SysUser findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("分页查询 --> 页码--{},页数大小-->{}",queryInfo.getPageNumber(),queryInfo.getPageSize());
        PageHelper.startPage(queryInfo.getPageNumber(),queryInfo.getPageSize());
        Page<SysUser> page = userMapper.findPage(queryInfo.getQueryString());
        long total = page.getTotal();
        List<SysUser> result = page.getResult();
        result.forEach(item -> item.setRoles(userMapper.findRoles(item.getId())));
        return PageResult.pageResult(total,result);
    }

    @Transactional
    @Override
    public Result insert(SysUser user) {
        log.info("根据用户名查询用户信息");
        SysUser sysUser = userMapper.findByUsername(user.getUsername());
        if(null != sysUser){
            return Result.fail("用户名已经存在！");
        }
        log.info("给密码加密");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("1.添加用户信息");
        userMapper.insert(user);
        log.info("2.添加角色信息");
        List<SysRole> roles = user.getRoles();
        if(roles.size()>0){
            roles.forEach(item -> userMapper.insertUserRoles(user.getId(),item.getId()));
        }
        log.info("3.用户角色添加成功有{}个",roles.size());
        return Result.success("用户添加成功！");
    }

    @Transactional
    @Override
    public Result update(SysUser user) {
        log.info("1.将用户角色信息删除");
        userMapper.deleteRolesByUserId(user.getId());
        log.info("2.添加用户角色信息");
        List<SysRole> roles = user.getRoles();
        if(roles.size()>0){
            roles.forEach(item -> userMapper.insertUserRoles(user.getId(),item.getId()));
        }
        log.info("给密码加密");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("3.修改用户信息");
        userMapper.update(user);
        return Result.success("用户信息修改成功！");
    }

    @Override
    public Result delete(Long id) {
        SysUser user =  userMapper.findById(id);
        if(null == user){
           return Result.fail("用户不存在");
        }
        userMapper.deleteRolesByUserId(id);
        userMapper.delete(id);
        return Result.success("用户信息删除成功！");
    }

}
