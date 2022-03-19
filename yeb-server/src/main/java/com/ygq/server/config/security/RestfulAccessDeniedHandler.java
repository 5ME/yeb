package com.ygq.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ygq.server.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * RestfulAccessDeniedHandler
 * <p>
 * 当接口访问没有权限时，自定义返回结果
 *
 * @author Yin Guiqing
 * @version 1.0
 * @date 2022-02-28 15:44
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        Result result = Result.error("权限不足，请联系管理员");
        result.setCode(403);
        printWriter.write(new ObjectMapper().writeValueAsString(result));
        printWriter.flush();
        printWriter.close();
    }
}
