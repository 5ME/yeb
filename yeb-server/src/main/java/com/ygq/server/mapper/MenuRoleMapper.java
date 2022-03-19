package com.ygq.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygq.server.pojo.MenuRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Yin Guiqing
 * @since 2022-02-17
 */
@Repository
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    /**
     * 批量插入角色菜单
     *
     * @param rid  角色id
     * @param mids 菜单id数组
     * @return 插入的行数
     */
    Integer insertRecords(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
