package com.ygq.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ygq.server.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * RestAuthorizationEntryPoint
 * <p>
 * 当未登录或者token失效时访问接口时，自定义的返回结果
 *
 * @author Yin Guiqing
 * @version 1.0
 * @date 2022-02-28 15:34
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException,
            ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        Result result = Result.error("尚未登录，请先登录！");
        result.setCode(401);
        printWriter.write(new ObjectMapper().writeValueAsString(result));
        printWriter.flush();
        printWriter.close();
    }
}
