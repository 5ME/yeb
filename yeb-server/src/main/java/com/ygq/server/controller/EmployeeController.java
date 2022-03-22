package com.ygq.server.controller;


import com.ygq.server.pojo.Employee;
import com.ygq.server.pojo.ResultPage;
import com.ygq.server.service.IEmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Yin Guiqing
 * @since 2022-02-17
 */
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;

    /**
     * 获取所有员工（分页）
     *
     * @param pageNo        第几页
     * @param pageSize      每页条目数
     * @param employee      根据条件封装的对象
     * @param joinDateScope 入职日期范围（长度为2的数组）
     * @return 自定义的分页结果对象
     */
    @ApiOperation(value = "获取所有员工（分页）")
    @GetMapping("/")
    public ResultPage getEmployee(@RequestParam(defaultValue = "1") Integer pageNo,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  Employee employee, LocalDate[] joinDateScope) {
        return employeeService.getEmployeeByPage(pageNo, pageSize, employee,
                joinDateScope);
    }
}
