package com.xyxy.system.service;

import com.xyxy.model.system.SysUser;
import com.xyxy.system.custom.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(username);
        if(sysUser == null){
            throw new UsernameNotFoundException("用户不存在");
        }

        if(sysUser.getStatus() == 0){
            throw new RuntimeException("用户被禁用了");
        }

        //根据用户id查询操作权限数据
        List<String> userPermsList = sysMenuService.getUserButtonList(sysUser.getId());
        //转换security要求的格式数据
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String perm : userPermsList) {
            authorities.add(new SimpleGrantedAuthority(perm.trim()));
        }
        return new CustomUser(sysUser, authorities);
    }
}
