package com.ygq.server.controller;

import com.ygq.server.Result;
import com.ygq.server.pojo.Admin;
import com.ygq.server.pojo.AdminLoginParam;
import com.ygq.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * LoginController
 *
 * @author Yin Guiqing
 * @version 1.0
 * @date 2022-02-25 14:45
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录返回token")
    @PostMapping("/login")
    public Result login(@RequestBody AdminLoginParam adminLoginParam,
                        HttpServletRequest request) {
        return adminService.login(adminLoginParam.getUsername(),
                adminLoginParam.getPassword(), adminLoginParam.getCode(), request);
    }

    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public Result logout() {
        return Result.success("退出成功！");
    }

    @ApiOperation("获取当前登录用户的信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal) {
        if (principal == null) {
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUsername(username);
        admin.setPassword(null);
        admin.setRoles(adminService.getRoles(admin.getId()));
        return admin;
    }
}
