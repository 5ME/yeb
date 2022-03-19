package com.ygq.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygq.server.pojo.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Yin Guiqing
 * @since 2022-02-17
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据用户id查询菜单列表
     *
     * @param id 用户id
     * @return 菜单列表
     */
    List<Menu> getMenusByAdminId(Integer id);

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
