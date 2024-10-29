package org.fizz.pojo.vo;

import lombok.Data;

/**
 * vo: 接收参数类
 *      当前端传入的json请求体没有对应的实体类接值时，创建vo类接值
 */
@Data
public class PortalVo {
    private String keyWords;
    private int type = 0;
    private int pageNum = 1;
    private int pageSize = 10;
}
