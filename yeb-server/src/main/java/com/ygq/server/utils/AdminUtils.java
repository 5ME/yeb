package com.ygq.server.utils;

import com.ygq.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * AdminUtils
 * 操作员工具类
 *
 * @author Yin Guiqing
 * @version 1.0
 * @date 2022-03-20 16:42
 */
public class AdminUtils {
    /**
     * 获取当前登录的操作员
     *
     * @return 当前登录的操作员对象
     */
    public static Admin getCurrentAdmin() {
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
