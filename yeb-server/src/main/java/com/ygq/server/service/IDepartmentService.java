package com.ygq.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ygq.server.Result;
import com.ygq.server.pojo.Department;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Yin Guiqing
 * @since 2022-02-17
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * 获取所有部门
     *
     * @return 所有部门的list
     */
    List<Department> getAllDepartments();

    /**
     * 添加部门
     *
     * @param department 要添加的部门
     * @return 执行添加操作后的结果
     */
    Result addDepartment(Department department);

    /**
     * 删除部门
     *
     * @param did 要删除部门的id
     * @return 执行删除操作后的结果
     */
    Result deleteDepartment(Integer did);
}
