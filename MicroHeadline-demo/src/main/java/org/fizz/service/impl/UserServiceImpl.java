package org.fizz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fizz.pojo.User;
import org.fizz.service.UserService;
import org.fizz.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author fez0618
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2024-10-28 15:19:23
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




