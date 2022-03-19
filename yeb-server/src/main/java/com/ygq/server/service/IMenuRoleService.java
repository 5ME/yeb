package com.ygq.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ygq.server.pojo.MenuRole;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Yin Guiqing
 * @since 2022-02-17
 */
public interface IMenuRoleService extends IService<MenuRole> {

    /**
     * 更新角色菜单
     *
     * @param rid  角色id
     * @param mids 菜单id数组
     * @return {@code true} 表示修改成功，{@code false} 表示修改失败
     */
    boolean updateMenuRole(Integer rid, Integer[] mids);
}
