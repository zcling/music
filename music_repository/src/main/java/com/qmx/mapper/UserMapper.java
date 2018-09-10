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

}