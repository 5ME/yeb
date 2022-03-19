package com.ygq.server.controller;


import com.ygq.server.pojo.Menu;
import com.ygq.server.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/system/cfg")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @ApiOperation(value = "根据用户id查询菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenusByAdminId() {
        return menuService.getMenusByAdminId();
    }
}
