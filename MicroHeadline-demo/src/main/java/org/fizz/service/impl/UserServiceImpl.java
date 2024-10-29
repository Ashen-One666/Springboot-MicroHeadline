package org.fizz.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fizz.pojo.User;
import org.fizz.service.UserService;
import org.fizz.mapper.UserMapper;
import org.fizz.utils.JwtHelper;
import org.fizz.utils.MD5Util;
import org.fizz.utils.Result;
import org.fizz.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * service层方法要根据接口文档写！
 * 测试使用postman模拟前端
 * @author fez0618
 * @description 针对表【news_user】的数据库操作Service实现
 * @createDate 2024-10-28 15:19:23
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 登录业务
     *  1. 根据账号查询用户对象 - loginUser
     *  2. 如果用户对象为空，则查询失败，账号错误 501
     *  3. 对比密码，失败 返回 503
     *  4. 成功，则根据用户id生成token，将token装入result中返回
     * @param user
     * @return
     */
    @Override
    public Result login(User user) {
        // 根据账号查询数据库
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User loginUser = userMapper.selectOne(wrapper);
        // 账号不存在
        if (loginUser == null) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        // 对比密码
        if (!StringUtils.isEmpty(user.getUserPwd())
                && MD5Util.encrypt(user.getUserPwd()).equals(loginUser.getUserPwd())) {
            // 登录成功
            // 根据用户id生成token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
            // 将token封装到result返回
            Map data = new HashMap<>();
            data.put("token", token);
            return Result.ok(data);
        }
        /*
        String pwd1 = user.getUserPwd();
        System.out.println("加密前密码 = " + pwd1);
        String pwd2 = MD5Util.encrypt(pwd1);
        System.out.println("加密后密码 = " + pwd2);
        String pwd3 = loginUser.getUserPwd();
        System.out.println("数据库中的密码 = " + pwd3);
        if(pwd2.equals(pwd3)){System.out.println("成功");}
        else {System.out.println("失败");}
        */
        // 密码错误
        return Result.build(loginUser, ResultCodeEnum.PASSWORD_ERROR);
    }

    /**
     * 根据token获取用户信息
     *  1. 校验token是否在有效期 过期504
     *  2. 根据token解析用户id
     *  3. 根据用户id查询用户数据
     *  4. 去掉密码，封装result并返回 格式 "loginUser": {"uid": 1, ...（用户信息）}
     * 测试（postman模拟）：
     *  1. 先向后端发送登录请求，获取该次登录的token
     *  2. 再向后端发送获取用户数据请求，在请求头中加入这个token，测试能否解析
     * @param token
     * @return
     */
    @Override
    public Result getUserInfo(String token) {
        // 为true则过期
        boolean expiration = jwtHelper.isExpiration(token);
        if (expiration) {
            // 失效，当作未登录看待
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }

        int userId = jwtHelper.getUserId(token).intValue();
        User user = userMapper.selectById(userId);
        user.setUserPwd(null);

        Map<String, Object> data = new HashMap<>();
        data.put("loginUser", user);

        return Result.ok(data);
    }

    /**
     * 检查账号是否已经被注册
     *  1. 根据账号进行count查询
     *  2. 如果count = 0表示可用， > 0则不可用
     * @param username
     * @return
     */
    @Override
    public Result checkUserName(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        Long count = userMapper.selectCount(wrapper);
        if (count == 0) {
            return Result.ok(null);
        }
        return Result.build(null, ResultCodeEnum.USERNAME_USED);
    }

    /**
     * 用户注册
     *  1. 检查账号是否已经被注册
     *      （注意，在用户点击注册按钮时任然需要再次检查，因为可能在填写用户名和完成注册之间别人已经注册了该用户名）
     *  2. 密码加密处理
     *  3. 账号保存
     *  4. 返回结果
     * @param user
     * @return
     */
    @Override
    public Result regist(User user) {
        // 检查账号是否已经被注册
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        Long count = userMapper.selectCount(wrapper);
        if (count > 0) {
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }

        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        userMapper.insert(user);

        return Result.ok(null);
    }
}




