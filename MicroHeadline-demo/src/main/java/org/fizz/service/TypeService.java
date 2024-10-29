package org.fizz.service;

import org.fizz.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import org.fizz.utils.Result;

/**
* @author fez0618
* @description 针对表【news_type】的数据库操作Service
* @createDate 2024-10-28 15:19:23
*/
public interface TypeService extends IService<Type> {
    /**
     * 查询所有类别数据
     * @return
     */
    Result findAllTypes();
}
