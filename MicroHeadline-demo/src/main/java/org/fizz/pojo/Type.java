package org.fizz.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * @TableName news_type
 */
//@TableName(value ="news_type") // .yaml中全局配置了table-prefix: news_，会自动映射表名和类名，此处无需再写注解
@Data
public class Type implements Serializable {

    @TableId
    private Integer tid;

    private String tname;

    @Version
    private Integer version;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}