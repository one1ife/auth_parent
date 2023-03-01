package com.xyxy.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyxy.common.result.Result;
import com.xyxy.model.system.SysRole;
import com.xyxy.model.vo.AssginRoleVo;
import com.xyxy.model.vo.SysRoleQueryVo;
import com.xyxy.system.exception.XyxyException;
import com.xyxy.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "角色管理接口")
@RestController
//@Controller
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    //查询所有记录
    @ApiOperation("查询所有记录")
    @GetMapping("findAll")
    public Result findAllRole(){
        //TODO 模拟异常效果
        try{
            int i = 2 / 0;
        }catch (Exception e){
            //手动抛出异常
            throw new XyxyException(20001,"执行了自定义异常处理");
        }


        //调用Service
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }

    //http://localhost:8800/admin/system/sysRole/remove/1

    //逻辑删除
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("逻辑删除接口")
    @DeleteMapping("remove/{id}")
    public Result removeRole(@PathVariable Long id){
        //调用方法删除
        boolean isSuccess = sysRoleService.removeById(id);
        return isSuccess?Result.ok():Result.fail();

    }

    //3 条件分页查询
    // page当前页  limit每页记录数
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result findPageQueryRole(@PathVariable Long page,
                                    @PathVariable Long limit,
                                    SysRoleQueryVo sysRoleQueryVo) {
        //创建page对象
        Page<SysRole> pageParam = new Page<>(page,limit);
        //调用service方法
        IPage<SysRole> pageModel = sysRoleService.selectPage(pageParam,sysRoleQueryVo);
        //返回
        return Result.ok(pageModel);
    }

    //添加
    // @RequestBody 不能使用get提交方式
    // 传递json格式数据，把json格式数据分封装到对象里面
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @ApiOperation("添加角色")
    @PostMapping("save")
    public Result saveRole(@RequestBody SysRole sysRole){
        boolean save = sysRoleService.save(sysRole);
        return save?Result.ok():Result.fail();

    }

    //修改-根据id查询
    @ApiOperation("根据id查询")
    @PostMapping("findRoleById/{id}")
    public Result findRoleById(@PathVariable Long id){
        SysRole byId = sysRoleService.getById(id);
        return Result.ok(byId);
    }

    //修改-最终修改
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation("最终修改")
    @PostMapping("update")
    public Result updateRole(@RequestBody SysRole sysRole){
        boolean isSuccess = sysRoleService.updateById(sysRole);
        return isSuccess?Result.ok():Result.fail();
    }


    //批量删除
    // 多个id [1,2,3]
    //json 数组格式 --- java LIST集合
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("批量删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids){
        boolean isSuccess = sysRoleService.removeByIds(ids);
        return isSuccess?Result.ok():Result.fail();
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId) {
        Map<String, Object> roleMap = sysRoleService.getRolesByUserId(userId);
        return Result.ok(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        sysRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }
}
