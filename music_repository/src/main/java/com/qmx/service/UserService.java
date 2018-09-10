package com.qmx.service;


import com.qmx.base.BaseService;
import com.qmx.exception.BusinessException;
import com.qmx.mapper.UserMapper;
import com.qmx.model.User;

import com.qmx.util.PasswordHelper;
import com.qmx.util.WebUtil;
import freemarker.template.utility.NumberUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "User")
public class UserService extends BaseService<User> {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 存储用户
     *
     * @param user 保存新的用户
     */
    public boolean saveUser(User user) {
        Assert.notNull(user.getPassword(), "密码不能为空");
        Assert.notNull(user.getUsername(), "用户名不能为空");
        //根据用户名查询用户，判断是否该用户名已经被占用
        Map param = new HashMap();
        param.put("username", user.getUsername());
        String sn = checkSn();
        User userResult = userMapper.selectByUsername(user.getUsername());
        if (userResult != null) {
            logger.error("{}:用户名已被占用", user.getUsername());
            return false;
        } else {
            //实例化密码加密工具
            PasswordHelper passwordHelper = new PasswordHelper();
            //加密密码
            passwordHelper.encryptPassword(user);
            user.setNumSn(sn);
            userService.save(user);
            return true;
        }
    }

    public String checkSn() {
        String str = RandomStringUtils.randomNumeric(6);
        Boolean flag = Boolean.TRUE;
        while (flag) {
            Map pram = new HashMap();
            pram.put("numSn", str);
            List list = this.queryList(pram);
            if (list == null || list.size() <= 0) {
                flag = Boolean.FALSE;
            }
        }
        return str;
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
