package com.qmx.constants;

import java.io.Serializable;

/**
 * 登录身份信息
 */
public class LoginInfo implements Serializable {
    private static final long serialVersionUID = 5798882004228239559L;

    /** 当前登录ID */
    private Long id;

    /** 用户名 */
    private String username;

    /** 角色 */
    private String role;

    /** 上级用户（所属人）ID */
    private Long memberId;

    /** 是否员工 */
    private Boolean isEmployee;

    /**
     * @param id
     *            ID
     * @param username
     *            用户名
     */
    public LoginInfo(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    /**
     * @param id
     *            ID
     * @param username
     *            用户名
     */
    public LoginInfo(Long id, String username, String role, Boolean isEmployee, Long memberId) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.isEmployee = isEmployee;
        this.memberId = memberId;
    }

    /**
     * 获取ID
     *
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id
     *            ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username
     *            用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(Boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String toString() {
        return username;
    }
}
