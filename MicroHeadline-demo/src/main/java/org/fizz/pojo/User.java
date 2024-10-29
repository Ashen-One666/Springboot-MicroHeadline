package org.fizz.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * @TableName news_user
 */
//@TableName(value ="news_user") // .yaml中全局配置了table-prefix: news_，会自动映射表名和类名，此处无需再写注解
@Data
public class User implements Serializable {

    @TableId
    private Integer uid;

    private String username;

    private String userPwd;

    private String nickName;

    @Version
    private Integer version;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}