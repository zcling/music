package com.qmx.controller.pay;

import com.qmx.model.pay.SysPayOrder;
import com.qmx.service.pay.WxNativePayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zcl on 2018/8/19.
 */
@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private WxNativePayService wxNativePayService;

    @RequestMapping(value = "/data",method = RequestMethod.GET)
    public void getPayResult() {
        SysPayOrder sysPayOrder = new SysPayOrder();
        sysPayOrder.setId(1234L);
        wxNativePayService.doWxPayReq(sysPayOrder, "123456", "");
    }
}
