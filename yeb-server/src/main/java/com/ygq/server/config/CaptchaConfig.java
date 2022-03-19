package com.ygq.server.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * CaptchaConfig
 * <p>
 * 验证码配置类
 *
 * @author Yin Guiqing
 * @version 1.0
 * @date 2022-02-28 20:00
 */
@Configuration
public class CaptchaConfig {
    @Bean
    public DefaultKaptcha defaultKaptcha() {
        // 验证码生成器
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        // 配置
        Properties properties = new Properties();
        // 是否有边框
        properties.setProperty("kaptcha.border", "yes");
        // 设置边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // 边框粗细度，默认为1
        properties.setProperty("kaptcha.border.thickness", "1");
        // 验证码 session key
        properties.setProperty("kaptcha.session.key", "code");
        // 设置验证码文本字符颜色，默认黑色
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        // 设置字体样式
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        // 字符长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 字符间距
        properties.setProperty("kaptcha.textproducer.char.space", "4");
        // 验证码图片宽度，默认为 200
        properties.setProperty("kaptcha.image.width", "100");
        // 验证码图片高度，默认为40
        properties.setProperty("kaptcha.image.height", "40");
        // 设置干扰，这里去掉干扰了
        properties.setProperty("kaptcha.noise.impl",
                "com.google.code.kaptcha.impl.NoNoise");

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }
}
