package com.ygq.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ygq.server.pojo.Employee;
import com.ygq.server.pojo.ResultPage;

import java.time.LocalDate;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Yin Guiqing
 * @since 2022-02-17
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * 获取所有员工（分页）
     *
     * @param pageNo        页码
     * @param pageSize      每页条目数
     * @param employee      根据搜索条件封装的对象
     * @param joinDateScope 入职日期范围
     * @return 查询到的分页数据
     */
    ResultPage getEmployeeByPage(Integer pageNo, Integer pageSize,
                                 Employee employee, LocalDate[] joinDateScope);
}
