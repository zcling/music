package com.qmx.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.qmx.base.BaseModel;

@TableName("user")
public class User extends BaseModel {
    private static final long serialVersionUID = -8736616045315083846L;

    private String username;

    private String password;

    @TableField(" num_sn")
    private String numSn;

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumSn() {
        return numSn;
    }

    public void setNumSn(String numSn) {
        this.numSn = numSn;
    }
}