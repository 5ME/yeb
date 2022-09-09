package com.ygq.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ygq.server.pojo.Employee;
import com.ygq.server.pojo.ResultPage;

import java.time.LocalDate;
import java.util.List;

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

    /**
     * 获取工号，就是最大工号加1
     *
     * @return 工号
     */
    String getWorkID();

    /**
     * 新增员工
     *
     * @param employee 封装的员工对象
     * @return {@code true} 表示添加成功，{@code false} 表示添加失败
     */
    boolean addEmployee(Employee employee);

    /**
     * 查询员工
     *
     * @param id 员工id（为null则查询所有）
     * @return 查询到的员工列表
     */
    List<Employee> getEmployee(Integer id);
}
