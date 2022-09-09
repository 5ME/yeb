package com.ygq.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.ygq.server.Result;
import com.ygq.server.pojo.*;
import com.ygq.server.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
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
@RequestMapping("/employee/basic")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private INationService nationService;
    @Autowired
    private IPoliticsStatusService politicsStatusService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IJoblevelService joblevelService;
    @Autowired
    private IPositionService positionService;

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

    @ApiOperation(value = "获取所有民族")
    @GetMapping("/nation")
    private List<Nation> getAllNation() {
        return nationService.list();
    }

    @ApiOperation(value = "获取所有政治面貌")
    @GetMapping("/politicsStatus")
    public List<PoliticsStatus> getAllPoliticsStatus() {
        return politicsStatusService.list();
    }

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/department")
    public List<Department> getAllDepartment() {
        return departmentService.getAllDepartments();
    }

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/joblevel")
    public List<Joblevel> getAllJoblevel() {
        return joblevelService.list();
    }

    @ApiOperation(value = "获取所有职位")
    @GetMapping("/position")
    public List<Position> getAllPosition() {
        return positionService.list();
    }

    @ApiOperation(value = "获取新增员工的工号")
    @GetMapping("/getWorkID")
    public Result getWorkID() {
        String workId = employeeService.getWorkID();
        return Result.success("工号", workId);
    }

    @ApiOperation(value = "添加员工")
    @PostMapping("/")
    public Result addEmployee(@RequestBody Employee employee) {
        if (employeeService.addEmployee(employee)) {
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    @ApiOperation(value = "更新员工信息")
    @PutMapping("/")
    public Result updateEmployee(@RequestBody Employee employee) {
        if (employeeService.updateById(employee)) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    @ApiOperation(value = "删除员工")
    @DeleteMapping("/{id}")
    public Result deleteEmployee(@PathVariable Integer id) {
        if (employeeService.removeById(id)) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    @ApiOperation(value = "导出员工数据")
    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response) {
        // 先查询员工
        List<Employee> employeeList = employeeService.getEmployee(null);
        // 使用 Easy POI
        ExportParams exportParams = new ExportParams("员工表", "员工表", ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Employee.class,
                employeeList);
        // 将文件以流的形式写出去，try-with-resources 写法
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            // 设置响应头
            // 流数据，文件下载
            response.setHeader("content-type", "application/octet-stream");
            // URLEncoder.encode 防止乱码
            response.setHeader("content-disposition",
                    "attachment;filename=" + URLEncoder.encode("员工表.xlsx",
                            StandardCharsets.UTF_8));
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "导入员工数据")
    @PostMapping("/import")
    public Result importEmployee(MultipartFile file) {
        // 把待会儿需要设置的外键查出来
        List<Nation> nationList = nationService.list();
        List<PoliticsStatus> politicsList = politicsStatusService.list();
        List<Department> departmentList = departmentService.list();
        List<Joblevel> joblevelList = joblevelService.list();
        List<Position> positionList = positionService.list();
        // Easy POI 导入参数
        ImportParams importParams = new ImportParams();
        // 表格标题行数
        importParams.setTitleRows(1);
        try {
            // 导入文件流
            List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream()
                    , Employee.class, importParams);
            // 设置各种外键id
            list.forEach(employee -> {
                for (Nation nation : nationList) {
                    if (nation.getName().equals(employee.getNation().getName())) {
                        employee.setNationId(nation.getId());
                        break;
                    }
                }
                for (PoliticsStatus politics : politicsList) {
                    if (politics.getName().equals(employee.getPoliticsStatus().getName())) {
                        employee.setPoliticId(politics.getId());
                        break;
                    }
                }
                for (Department dep : departmentList) {
                    if (dep.getName().equals(employee.getDepartment().getName())) {
                        employee.setDepartmentId(dep.getId());
                        break;
                    }
                }
                for (Joblevel joblevel : joblevelList) {
                    if (joblevel.getName().equals(employee.getJoblevel().getName())) {
                        employee.setJobLevelId(joblevel.getId());
                        break;
                    }
                }
                for (Position position : positionList) {
                    if (position.getName().equals(employee.getPosition().getName())) {
                        employee.setPosId(position.getId());
                        break;
                    }
                }
            });
            // 插入到数据库
            if (employeeService.saveBatch(list)) {
                return Result.success("导入成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("导入失败");
    }
}
