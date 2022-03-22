package com.ygq.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ResultPage
 * 分页查询的公共返回对象
 *
 * @author Yin Guiqing
 * @version 1.0
 * @date 2022-03-20 20:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultPage {
    /**
     * 总条数
     */
    private Long total;
    /**
     * 数据
     */
    private List<?> data;
}
