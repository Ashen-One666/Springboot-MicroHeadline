package org.fizz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fizz.pojo.Type;
import org.fizz.service.TypeService;
import org.fizz.mapper.TypeMapper;
import org.springframework.stereotype.Service;

/**
* @author fez0618
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2024-10-28 15:19:23
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

}




