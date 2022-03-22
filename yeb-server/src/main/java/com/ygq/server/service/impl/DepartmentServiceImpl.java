package com.ygq.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ygq.server.Result;
import com.ygq.server.mapper.DepartmentMapper;
import com.ygq.server.pojo.Department;
import com.ygq.server.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper,
        Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartments(-1);
    }

    @Override
    public Result addDepartment(Department department) {
        department.setEnabled(true);
        departmentMapper.addDepartment(department);
        // 受影响的行数
        if (department.getRes() == 1) {
            return Result.success("添加成功", department);
        }
        return Result.error("添加失败");
    }

    @Override
    public Result deleteDepartment(Integer did) {
        Department department = new Department();
        department.setId(did);
        departmentMapper.deleteDepartment(department);
        switch (department.getRes()) {
            case -2:
                return Result.error("删除失败，该部门下还有子部门");
            case -1:
                return Result.error("删除失败，该部门下还有员工");
            case 1:
                return Result.success("删除成功", department);
            default:
                return Result.error("删除失败");
        }
    }
}
