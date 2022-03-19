package com.ygq.server.controller;


import com.ygq.server.Result;
import com.ygq.server.pojo.Joblevel;
import com.ygq.server.service.IJoblevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
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
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {
    @Autowired
    private IJoblevelService joblevelService;

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/")
    public List<Joblevel> getAllJoblevel() {
        return joblevelService.list();
    }

    @ApiOperation(value = "添加职称信息")
    @PostMapping("/")
    public Result addJoblevel(@RequestBody Joblevel joblevel) {
        joblevel.setCreateDate(LocalDateTime.now());
        if (joblevelService.save(joblevel)) {
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    @ApiOperation(value = "更新职称信息")
    @PutMapping("/")
    public Result updateJoblevel(@RequestBody Joblevel joblevel) {
        if (joblevelService.updateById(joblevel)) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    @ApiOperation(value = "删除职称")
    @DeleteMapping("/{id}")
    public Result deleteJoblevel(@PathVariable Integer id) {
        if (joblevelService.removeById(id)) {
            return Result.success("删除成功");
        }
        return Result.error("删除成功");
    }

    @ApiOperation(value = "批量删除职称")
    @DeleteMapping("/")
    public Result deleteJoblevelByIds(@RequestBody Integer[] ids) {
        if (joblevelService.removeByIds(Arrays.asList(ids))) {
            return Result.success("批量删除成功");
        }
        return Result.error("批量删除失败");
    }
}
