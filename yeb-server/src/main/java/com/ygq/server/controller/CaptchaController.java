package com.ygq.server.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * CaptchaController
 *
 * @author Yin Guiqing
 * @version 1.0
 * @date 2022-02-28 20:12
 */
@RestController
public class CaptchaController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @ApiOperation("验证码")
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        // 定义 response 输出类型为 image/jpeg 类型
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.addHeader("Cache-Control", "post-check=0,pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        // ****************生成验证码 Begin****************
        // 获取验证码文本内容
        String text = defaultKaptcha.createText();
        System.out.println("验证码内容：" + text);
        // 将验证码放入session
        request.getSession().setAttribute("captcha", text);
        // 创建验证码图片
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(image, "jpeg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // ****************生成验证码 End****************
        // 查看验证码： http://localhost:8081/captcha
    }
}
