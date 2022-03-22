package com.ygq.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygq.server.pojo.Admin;
import org.apache.ibatis.annotations.Param;
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
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 根据关键词查询所有操作员
     *
     * @param id       当前登录的操作员id
     * @param keywords 关键词
     * @return 查询到的操作员列表
     */
    List<Admin> getAllAdmins(@Param("id") Integer id, @Param("keywords") String keywords);
}
