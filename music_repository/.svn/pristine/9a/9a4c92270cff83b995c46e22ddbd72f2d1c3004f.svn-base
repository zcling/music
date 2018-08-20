package com.qmx.service;


import com.qmx.base.BaseService;
import com.qmx.mapper.UserMapper;
import com.qmx.model.User;

import com.qmx.util.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
@CacheConfig(cacheNames = "User")
public class UserService extends BaseService<User> {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     *存储用户
     * @param user 保存新的用户
     */
    public boolean saveUser(User user){
        Assert.notNull(user.getPassword(),"密码不能为空");
        Assert.notNull(user.getUsername(),"用户名不能为空");
        //根据用户名查询用户，判断是否该用户名已经被占用
        User userResult=userMapper.selectByUsername(user.getUsername());
        if (StringUtils.isEmpty(userResult)){
            return false;
        }else {
            //实例化密码加密工具
            PasswordHelper passwordHelper=new PasswordHelper();
            //加密密码
            passwordHelper.encryptPassword(user);
            userService.save(user);
            return true;
        }
    }

    /**
     *更新用户
     * @param user 更新的用户
     */
//    public void saveUpdate(User user , String userId){
//        Assert.notNull(user.getPassword(),"密码不能为空");
//        Assert.notNull(user.getUsername(),"用户名不能为空");
//        //根据用户名查询用户，判断是否该用户名已经被占用
//        user.setId(Long.valueOf(userId));
//        PasswordHelper passwordHelper=new PasswordHelper();
//        passwordHelper.encryptPassword(user);
//        userService.update(user);
//    }

    /**
     *通过用户id来查询用户
     * @param userId 用户id
     * @return 返回用户实体
     */
//    public User findByUserId(String userId){
//        Assert.notNull(userId,"用户Id不能为空");
//        return userService.find(Long.valueOf(userId));
//    }
}
