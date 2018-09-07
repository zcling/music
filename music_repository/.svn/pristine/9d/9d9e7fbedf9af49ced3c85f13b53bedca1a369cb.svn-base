package com.qmx.mapper;

import com.qmx.base.IBaseMapper;
import com.qmx.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends IBaseMapper<User> {
    /**
     * @param username 用户名
     * @return 放回根据用户名查询到的用户
     */
    User selectByUsername(@Param("username") String username);

//    /**
//     *查询用户名是否存在，排除重复添加相同的用户
//     * @param params 用户名
//     * @return 放回根据用户名查询的结果
//     */
//    User findUserByUsername(@Param("cm") Map<String,Object> params);
}