package com.ygq.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ygq.server.Result;
import com.ygq.server.mapper.EmployeeMapper;
import com.ygq.server.pojo.Employee;
import com.ygq.server.pojo.ResultPage;
import com.ygq.server.service.IEmployeeService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private RabbitTemplate rabbitTemplate;

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

    @Override
    public String getWorkID() {
        // 查询现在的最大的workID
        List<Map<String, Object>> maps =
                employeeMapper.selectMaps(
                        new QueryWrapper<Employee>().select("MAX(workID)"));
        // 最大的workID+1就是新增员工的workID
        return String.format("%08d",
                Integer.parseInt(maps.get(0).get("MAX(workID)").toString()) + 1);
    }

    @Override
    public boolean addEmployee(Employee employee) {
        // 合同起始日期
        LocalDate beginContract = employee.getBeginContract();
        // 合同结束日期
        LocalDate endContract = employee.getEndContract();
        // 计算合同天数
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        // 将合同期限转换成年，并保留两位小数
        DecimalFormat format = new DecimalFormat("#.00");
        employee.setContractTerm(Double.parseDouble(format.format(days / 365.0)));
        if (1 == employeeMapper.insert(employee)) {
            // 发送入职欢迎邮件
            Employee e = employeeMapper.getEmployee(employee.getId()).get(0);
            rabbitTemplate.convertAndSend("mail.welcome", e);
            return true;
        }
        return false;
    }

    @Override
    public List<Employee> getEmployee(Integer id) {
        return employeeMapper.getEmployee(id);
    }
}
