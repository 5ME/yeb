package com.ygq.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ygq.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Yin Guiqing
 * @since 2022-02-17
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 根据用户id查询菜单列表
     *
     * @return 菜单列表
     */
    List<Menu> getMenusByAdminId();

    /**
     * 根据角色获取菜单列表
     *
     * @return 菜单列表
     */
    List<Menu> getMenusWithRole();

    /**
     * 查询所有菜单
     *
     * @return 所有菜单
     */
    List<Menu> getAllMenus();
}
