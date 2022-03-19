package com.ygq.server.exception;

import com.ygq.server.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * GlobalException
 * 全局异常
 *
 * @author Yin Guiqing
 * @version 1.0
 * @date 2022-03-19 15:51
 */
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(SQLException.class)
    public Result sqlException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return Result.error("该数据有关联数据，操作失败");
        }
        // 如有需要还可以对异常进行细分
        return Result.error("数据库异常，操作失败");
    }
}
