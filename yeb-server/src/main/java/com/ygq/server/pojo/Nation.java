package com.ygq.server.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yin Guiqing
 * @since 2022-02-17
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
// 使用 lombok 注解重写 equals() 和 hashCode() 方法
@EqualsAndHashCode(callSuper = false, of = "name")
@TableName("t_nation")
@ApiModel(value="Nation对象", description="民族实体类")
public class Nation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "民族")
    @Excel(name = "民族")
    @NonNull
    private String name;


}
