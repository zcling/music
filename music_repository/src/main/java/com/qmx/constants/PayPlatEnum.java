package com.qmx.constants;

import java.io.Serializable;

/**
 * Created by zcl on 2018/8/19.
 */
public enum PayPlatEnum {

    ALIPAY("支付宝支付"),
    WXPAY("微信支付");

    private String name;

    PayPlatEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
