package com.xyxy.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xyxy.model.system.SysRole;
import com.xyxy.system.mapper.SysRoleMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SysRoleMapperTest {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    //1.查询表所有记录
    @Test
    public void testSelectList() {
        System.out.println(("----- selectAll method test ------"));
        //UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper
        //所以不填写就是无任何条件
        List<SysRole> users = sysRoleMapper.selectList(null);
        for (SysRole sysRole : users) {
            System.out.println("sysRole = " + sysRole);
        }
    }

    //2.添加操作
    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("测试角色");
        sysRole.setRoleCode("testManager");
        sysRole.setDescription("测试角色");
        int rows = sysRoleMapper.insert(sysRole);
        System.out.println(rows);
    }

    //3.修改操作
    @Test
    public void update(){
        //根据id查询
        SysRole sysRole = sysRoleMapper.selectById(1);

        //设置修改值
        sysRole.setDescription("系统管理员zou");

        //调用方法实现xiugai
        sysRoleMapper.updateById(sysRole);
    }

    /**
     * application-dev.yml 加入配置
     * 此为默认值，如果你的默认值和默认的一样，则不需要该配置
     * mybatis-plus:
     *   global-config:
     *     db-config:
     *       logic-delete-value: 1
     *       logic-not-delete-value: 0
     */
    //根据id删除
    @Test
    public void testDeleteById(){
        int result = sysRoleMapper.deleteById(2L);
        System.out.println(result);
    }

    //批量删除
    @Test
    public void testDeleteBatchIds() {
        int result = sysRoleMapper.deleteBatchIds(Arrays.asList(1, 2));
        System.out.println(result);
    }

    //条件查询
    @Test
    public void testSelect(){
        //创建条件构造器对象
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name","用户管理员");
        List<SysRole> sysRoles = sysRoleMapper.selectList(wrapper);
        System.out.println(sysRoles);
    }
}
