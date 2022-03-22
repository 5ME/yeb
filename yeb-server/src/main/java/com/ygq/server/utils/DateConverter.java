package com.ygq.server.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * DateConverter
 * <p>
 * 日期转换器，将 String 转换为 LocalDate
 * <p>
 * 通过实现 {@link org.springframework.core.convert.converter.Converter} 接口实现
 *
 * @author Yin Guiqing
 * @version 1.0
 * @date 2022-03-22 19:37
 */
@Component
public class DateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        try {
            return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
