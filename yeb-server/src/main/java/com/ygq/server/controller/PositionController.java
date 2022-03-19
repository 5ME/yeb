package com.ygq.server.controller;


import com.ygq.server.Result;
import com.ygq.server.pojo.Position;
import com.ygq.server.service.IPositionService;
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
@RequestMapping("/system/basic/position")
public class PositionController {
    @Autowired
    private IPositionService positionService;

    @ApiOperation(value = "获取所有职位信息")
    @GetMapping("/")
    public List<Position> getAllPositions() {
        return positionService.list();
    }

    @ApiOperation(value = "添加职位信息")
    @PostMapping("/")
    public Result addPosition(@RequestBody Position position) {
        position.setCreateDate(LocalDateTime.now());
        if (positionService.save(position)) {
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    @ApiOperation(value = "更新职位信息")
    @PutMapping("/")
    public Result updatePosition(@RequestBody Position position) {
        if (positionService.updateById(position)) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    public Result deletePosition(@PathVariable Integer id) {
        if (positionService.removeById(id)) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping("/")
    public Result deletePositionsByIds(@RequestBody Integer[] ids) {
        if (positionService.removeByIds(Arrays.asList(ids))) {
            return Result.success("批量删除成功");
        }
        return Result.error("批量删除失败");
    }
}
