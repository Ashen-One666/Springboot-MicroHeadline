package org.fizz.service;

import org.fizz.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.fizz.utils.Result;

/**
* @author fez0618
* @description 针对表【news_user】的数据库操作Service
* @createDate 2024-10-28 15:19:23
*/
public interface UserService extends IService<User> {

    /**
     * 登录业务
     * @param user
     * @return
     */
    Result login(User user);

    /**
     * 根据token获取用户数据
     * @param token
     * @return
     */
    Result getUserInfo(String token);

    /**
     * 检查账号是否已经被注册
     * @param username
     * @return
     */
    Result checkUserName(String username);

    /**
     * 用户注册
     * @param user
     * @return
     */
    Result regist(User user);
}
