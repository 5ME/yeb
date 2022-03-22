package com.ygq.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygq.server.pojo.Department;
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
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 获取所有部门
     *
     * @return 所有部门的list
     */
    List<Department> getAllDepartments(@Param("parentId") Integer parentId);

    /**
     * 添加部门
     *
     * @param department 要添加的部门
     */
    void addDepartment(Department department);

    /**
     * 删除部门
     *
     * @param department 要删除的部门
     */
    void deleteDepartment(Department department);
}
