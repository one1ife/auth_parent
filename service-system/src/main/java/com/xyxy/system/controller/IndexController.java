package com.xyxy.system.controller;

import com.xyxy.common.result.Result;
import com.xyxy.common.result.ResultCodeEnum;
import com.xyxy.common.utils.JwtHelper;
import com.xyxy.common.utils.MD5;
import com.xyxy.model.system.SysUser;
import com.xyxy.model.vo.LoginVo;
import com.xyxy.system.exception.XyxyException;
import com.xyxy.system.service.SysUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 后台登录登出
 * </p>
 */

@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
        //根据用户名称查询数据库
        SysUser sysUser = sysUserService.getByUsername(loginVo.getUsername());

        //如果查询为空
        if(null == sysUser) {
            throw new XyxyException(20001,"用户不存在");
        }
        //判断密码是否一致
        if(!MD5.encrypt(loginVo.getPassword()).equals(sysUser.getPassword())) {
            throw new XyxyException(20002,"密码不正确");
        }

        //判断用户是否可用
        if(sysUser.getStatus().intValue() == 0) {
            throw new XyxyException(20003,"用户不可用");
        }


        //根据useid和username生成token字符串，通过map返回
        Map<String, Object> map = new HashMap<>();
        map.put("token", JwtHelper.createToken(sysUser.getId(), sysUser.getUsername()));
        return Result.ok(map);
    }
    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/info")
    public Result info(HttpServletRequest request) {
        //获取请求头token字符串
        String token = request.getHeader("token");

        //从token字符串获取用户名称
        String username = JwtHelper.getUsername(token);

        //根据用户名称获取用户信息（基本信息 和 菜单权限 和 按钮权限数据）
        Map<String, Object> map = sysUserService.getUserInfo(username);
        return Result.ok(map);
    }
    /**
     * 退出
     * @return
     */
    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }
}
