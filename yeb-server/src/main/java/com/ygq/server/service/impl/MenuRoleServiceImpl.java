package com.ygq.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ygq.server.mapper.MenuRoleMapper;
import com.ygq.server.pojo.MenuRole;
import com.ygq.server.service.IMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Yin Guiqing
 * @since 2022-02-17
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {
    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Transactional
    @Override
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        // 更新角色菜单分两步进行：
        // 1. 先把当前角色拥有的菜单清空
        // 2. 再根据传入的菜单id数组在 menu_role 表中插入值
        // 因为分了两步，记得在此方法上标注 @Transactional 注解
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid", rid));
        if (mids == null || mids.length == 0) {
            return true;
        }
        Integer res = menuRoleMapper.insertRecords(rid, mids);
        return res == mids.length;
    }
}
