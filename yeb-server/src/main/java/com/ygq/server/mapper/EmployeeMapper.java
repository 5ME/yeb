package com.ygq.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ygq.server.pojo.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 获取所有员工（分页）
     *
     * @param page          MyBatis-Plus分页模型
     * @param employee      根据搜索条件封装的对象
     * @param joinDateScope 入职日期范围
     * @return MyBatis-Plus 分页对象
     */
    IPage<Employee> getEmployeeByPage(Page<Employee> page,
                                      @Param("employee") Employee employee,
                                      @Param("joinDateScope") LocalDate[] joinDateScope);

    /**
     * 查询员工
     *
     * @param id 员工id（为null则查询所有）
     * @return 查询到的员工列表
     */
    List<Employee> getEmployee(Integer id);
}
