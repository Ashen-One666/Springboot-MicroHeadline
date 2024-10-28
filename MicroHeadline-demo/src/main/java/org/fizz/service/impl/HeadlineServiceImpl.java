package org.fizz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fizz.pojo.Headline;
import org.fizz.service.HeadlineService;
import org.fizz.mapper.HeadlineMapper;
import org.springframework.stereotype.Service;

/**
* @author fez0618
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2024-10-28 15:19:23
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

}




