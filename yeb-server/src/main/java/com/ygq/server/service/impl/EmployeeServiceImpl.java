package com.ygq.server.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ygq.server.mapper.EmployeeMapper;
import com.ygq.server.pojo.Employee;
import com.ygq.server.pojo.ResultPage;
import com.ygq.server.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Yin Guiqing
 * @since 2022-02-17
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public ResultPage getEmployeeByPage(Integer pageNo, Integer pageSize,
                                        Employee employee,
                                        LocalDate[] joinDateScope) {
        // 开启分页
        Page<Employee> page = new Page<>(pageNo, pageSize);
        // MyBatis-Plus查询到的结果
        IPage<Employee> employeeByPage =
                employeeMapper.getEmployeeByPage(page, employee, joinDateScope);
        // 封装到自定义的分页结果中
        ResultPage resultPage = new ResultPage();
        resultPage.setTotal(employeeByPage.getTotal());
        resultPage.setData(employeeByPage.getRecords());
        return resultPage;
    }
}
