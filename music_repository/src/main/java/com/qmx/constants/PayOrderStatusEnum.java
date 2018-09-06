package com.qmx.constants;

/**
 * Created by zcl on 2018/8/19.
 */
public enum PayOrderStatusEnum {

    INIT("等待支付"),
    SUCCESS("支付成功"),
    FINISH("业务处理完成"),
    FAIL("支付失败");

    private String name;

    PayOrderStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
