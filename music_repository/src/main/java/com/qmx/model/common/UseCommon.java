package com.qmx.model.common;

import com.baomidou.mybatisplus.annotations.TableField;
import com.qmx.model.User;

import java.util.List;

/**
 * 当前主要用于向前端传递数据。
 * Created by zcl on 2018/9/6.
 */
public class UseCommon {
    private String total;
    private List<User> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<User> getRows() {
        return rows;
    }

    public void setRows(List<User> rows) {
        this.rows = rows;
    }
}
