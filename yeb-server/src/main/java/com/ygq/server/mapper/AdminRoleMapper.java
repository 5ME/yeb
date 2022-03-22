package com.ygq.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygq.server.pojo.AdminRole;
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
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * 更新操作员角色
     *
     * @param adminId 操作员id
     * @param rids    角色id数组
     */
    Integer addAdminRole(@Param("adminId") Integer adminId,
                            @Param("rids") Integer[] rids);
}
