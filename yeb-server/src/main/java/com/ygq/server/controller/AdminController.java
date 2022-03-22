package com.ygq.server.controller;


import com.ygq.server.Result;
import com.ygq.server.pojo.Admin;
import com.ygq.server.pojo.Role;
import com.ygq.server.service.IAdminService;
import com.ygq.server.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Yin Guiqing
 * @since 2022-02-17
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private IRoleService roleService;

    @ApiOperation(value = "根据关键词查询操作员")
    @GetMapping("/")
    public List<Admin> getAllAdmins(String keywords) {
        return adminService.getAllAdmins(keywords);
    }

    @ApiOperation(value = "更新操作员")
    @PostMapping("/")
    public Result updateAdmin(@RequestBody Admin admin) {
        if (adminService.updateById(admin)) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    @ApiOperation(value = "删除操作员")
    @DeleteMapping("/{id}")
    public Result deleteAdmin(@PathVariable Integer id) {
        if (adminService.removeById(id)) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.list();
    }

    @ApiOperation(value = "更新操作员角色")
    @PutMapping("/role")
    public Result updateAdminRole(Integer adminId, Integer[] rids) {
        if (adminService.updateAdminRole(adminId, rids)) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }
}
