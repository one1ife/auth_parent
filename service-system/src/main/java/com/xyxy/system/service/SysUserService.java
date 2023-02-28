package com.xyxy.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyxy.model.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyxy.model.vo.SysUserQueryVo;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xyxy
 * @since 2023-02-25
 */
public interface SysUserService extends IService<SysUser> {

    IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo adminQueryVo);

    void updateStatus(Long id, Integer status);

    SysUser getByUsername(String username);

    Map<String, Object> getUserInfo(String username);
}
