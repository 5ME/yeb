package com.ygq.server.controller;


import com.ygq.server.Result;
import com.ygq.server.pojo.Department;
import com.ygq.server.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Yin Guiqing
 * @since 2022-02-17
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @ApiOperation(value = "添加部门")
    @PostMapping("/")
    public Result addDepartment(@RequestBody Department department) {
        return departmentService.addDepartment(department);
    }

    @ApiOperation(value = "删除部门")
    @DeleteMapping("/{did}")
    public Result deleteDepartment(@PathVariable Integer did) {
        return departmentService.deleteDepartment(did);
    }
}
