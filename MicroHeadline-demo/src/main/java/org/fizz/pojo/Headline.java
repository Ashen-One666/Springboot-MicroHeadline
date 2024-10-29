package org.fizz.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.fizz.utils.Result;

/**
 * @TableName news_headline
 */
//@TableName(value ="news_headline") // .yaml中全局配置了table-prefix: news_，会自动映射表名和类名，此处无需再写注解
@Data
public class Headline implements Serializable {

    @TableId // 主键最好加上注解，防止后续sql语句找不到
    private Integer hid;

    private String title;

    private String article;

    private Integer type;

    private Integer publisher;

    private Integer pageViews;

    private Date createTime;

    private Date updateTime;

    @Version
    private Integer version;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;

}