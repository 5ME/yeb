package com.ygq.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ygq.server.mapper.MenuMapper;
import com.ygq.server.pojo.Admin;
import com.ygq.server.pojo.Menu;
import com.ygq.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Yin Guiqing
 * @since 2022-02-17
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<Menu> getMenusByAdminId() {
        Integer adminId =
                ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        // 从redis获取数据
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        List<Menu> menus = (List<Menu>) valueOps.get("menu_" + adminId);
        // 如果为空，则去数据库查询
        if (CollectionUtils.isEmpty(menus)) {
            menus = menuMapper.getMenusByAdminId(adminId);
            // 将查询到的数据放入redis
            valueOps.set("menu_" + adminId, menus);
        }
        return menus;
    }

    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }
}
