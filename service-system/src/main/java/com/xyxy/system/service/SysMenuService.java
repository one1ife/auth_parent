package com.xyxy.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyxy.model.system.SysMenu;
import com.xyxy.model.vo.AssginMenuVo;
import com.xyxy.model.vo.RouterVo;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author xyxy
 * @since 2023-02-26
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 菜单树形数据
     * @return
     */
    List<SysMenu> findNodes();

    /**
     * 根据角色获取授权权限数据
     * @return
     */
    List<SysMenu> findSysMenuByRoleId(Long roleId);

    /**
     * 保存角色权限
     * @param  assginMenuVo
     */
    void doAssign(AssginMenuVo assginMenuVo);

    List<RouterVo> getUserMenuList(String id);

    List<String> getUserButtonList(String id);
}
