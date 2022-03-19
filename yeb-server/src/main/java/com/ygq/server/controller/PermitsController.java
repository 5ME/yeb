package com.ygq.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ygq.server.Result;
import com.ygq.server.pojo.Menu;
import com.ygq.server.pojo.MenuRole;
import com.ygq.server.pojo.Role;
import com.ygq.server.service.IMenuRoleService;
import com.ygq.server.service.IMenuService;
import com.ygq.server.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PermitsController
 * 权限组
 *
 * @author Yin Guiqing
 * @version 1.0
 * @date 2022-03-19 18:51
 */
@RestController
@RequestMapping("/system/basic/permits")
public class PermitsController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IMenuRoleService menuRoleService;

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/")
    public List<Role> getAllRoles() {
        return roleService.list();
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/role")
    public Result addRole(@RequestBody Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        if (roleService.save(role)) {
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/role/{rid}")
    public Result deleteRole(@PathVariable Integer rid) {
        if (roleService.removeById(rid)) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/menus")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @ApiOperation(value = "根据角色id查询菜单id")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMidsByRid(@PathVariable Integer rid) {
        List<MenuRole> menuRoles =
                menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid", rid));
        return menuRoles.stream().map(MenuRole::getMid).collect(Collectors.toList());
    }

    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/")
    public Result updateMenuRole(Integer rid, Integer[] mids) {
        if (menuRoleService.updateMenuRole(rid, mids)) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }
}
