package com.ygq.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ygq.server.Result;
import com.ygq.server.pojo.Admin;
import com.ygq.server.pojo.Menu;
import com.ygq.server.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Yin Guiqing
 * @since 2022-02-17
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 登录之后返回token
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param request  HttpServletRequest
     * @return 统一返回结果对象
     */
    Result login(String username, String password, String code,
                 HttpServletRequest request);

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 查询到的用户
     */
    Admin getAdminByUsername(String username);

    /**
     * 根据用户id查询其角色列表
     *
     * @param adminId 用户id
     * @return 角色列表
     */
    List<Role> getRoles(Integer adminId);
}
