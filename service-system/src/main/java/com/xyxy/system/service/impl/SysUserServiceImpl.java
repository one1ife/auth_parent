package com.xyxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyxy.model.system.SysUser;
import com.xyxy.model.vo.RouterVo;
import com.xyxy.model.vo.SysUserQueryVo;
import com.xyxy.system.mapper.SysUserMapper;
import com.xyxy.system.service.SysMenuService;
import com.xyxy.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xyxy
 * @since 2023-02-25
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo userQueryVo){

        return sysUserMapper.selectPage(pageParam, userQueryVo);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        SysUser sysUser = sysUserMapper.selectById(id);
        sysUser.setStatus(status);
        sysUserMapper.updateById(sysUser);
    }

    @Override
    public SysUser getByUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Map<String, Object> getUserInfo(String username) {
        //根据用户username查询用户基本信息
        SysUser sysUser = this.getByUsername(username);
        //根据用户id查询菜单的权限
        List<RouterVo> routerVoList = sysMenuService.getUserMenuList(sysUser.getId());

        //根据用户id查询按钮的权限
        List<String> permsList = sysMenuService.getUserButtonList(sysUser.getId());

        Map<String,Object> result = new HashMap<>();
        result.put("name",username);
        result.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        result.put("roles","[\"admin\"]");
        //菜单权限数据
        result.put("routers",routerVoList);


        //按钮权限数据
        result.put("buttons",permsList);
        return result;
    }

}
