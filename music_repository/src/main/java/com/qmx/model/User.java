package com.qmx.model;

import com.qmx.base.BaseModel;


public class User extends BaseModel {
    private static final long serialVersionUID = -8736616045315083846L;

    private String username;

    private String password;

    /**
     * 是否启用
     */
    private Integer enable;

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


    @Override
    public String toString() {
        return "User{" +
                "id=" + super.getId() +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enable=" + enable +
                '}';
    }
}