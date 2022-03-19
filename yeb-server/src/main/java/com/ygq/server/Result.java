package com.ygq.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Result
 *
 * @author Yin Guiqing
 * @version 1.0
 * @date 2022-02-17 23:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private int code;
    private String message;
    private Object obj;

    public static Result success(String message) {
        return new Result(200, message, null);
    }

    public static Result success(String message, Object obj) {
        return new Result(200, message, obj);
    }

    public static Result error(String message) {
        return new Result(500, message, null);
    }

    public static Result error(String message, Object obj) {
        return new Result(500, message, obj);
    }
}
