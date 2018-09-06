package com.qmx.service.pay;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.qmx.exception.BusinessException;
import com.qmx.model.pay.SysPayOrder;
import com.qmx.util.AmountUtil;
import com.qmx.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zcl on 2018/8/19.
 */
@Component
public class WxNativePayService {
    private final Logger _log = LoggerFactory.getLogger(WxNativePayService.class);
    @Autowired
    private PayService payOrderService;
    @Value("${server.error.path:${error.path:/error}}")
    private String notifyUrl;

    public WxNativePayService() {
    }

    public Map<String, Object> doWxPayReq(SysPayOrder payOrder, String productId, String mchConfig) {
        String logPrefix = "【微信支付扫描支付下单】";
        String tradeType = "NATIVE";
        Assert.notNull(payOrder, "payOrder不能为空");
       // Assert.hasText(mchConfig, "mchConfig不能为空");
        String payOrderId = payOrder.getId() + "";

        try {
            //WxPayConfig wxPayConfig = WxPayUtil.getWxPayConfig(mchConfig, tradeType, this.notifyUrl);
            WxPayConfig wxPayConfig = new WxPayConfig();
            wxPayConfig.setMchId("1226538202");
            wxPayConfig.setAppId("wx874ad5cfa9935ffb");
            wxPayConfig.setKeyPath("http://test.qmx028.com/usr/local/file/upload/certificate/63bbfd50-6870-4b5b-9950-6399822e1eaa.p12");
            wxPayConfig.setMchKey("Y2pMiXVx2fc9k06elfdOOY2ONVc4IXWa");
            wxPayConfig.setNotifyUrl(notifyUrl);
            wxPayConfig.setTradeType(tradeType);
            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(wxPayConfig);
            WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = this.buildUnifiedOrderRequest(payOrder, productId, wxPayConfig);
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
            this._log.info("{} >>> 下单成功", logPrefix);
            Map<String, Object> map = new HashMap();
            map.put("payOrderId", payOrderId);
            map.put("prepayId", wxPayUnifiedOrderResult.getPrepayId());
            // SysPayOrder result = this.payOrderService.updateChannelOrderId(payOrderId, wxPayUnifiedOrderResult.getPrepayId());
            this._log.info("更新第三方支付订单号:payOrderId={},prepayId={},result={}", new Object[]{payOrderId, wxPayUnifiedOrderResult.getPrepayId()});
            map.put("codeUrl", wxPayUnifiedOrderResult.getCodeURL());
            return map;
        } catch (WxPayException var13) {
            this._log.error(var13.getMessage());
            this._log.info("{}下单返回失败:{}", logPrefix, JSONUtil.toJson(var13));
            this._log.info("err_code:{}", var13.getErrCode());
            this._log.info("err_code_des:{}", var13.getErrCodeDes());
            //this.payOrderService.updateFail(payOrderId, var13.getErrCode(), var13.getErrCodeDes(), var13.getXmlString());
            throw new BusinessException(var13.getMessage());
        } catch (Exception var14) {
            this._log.error(var14.getMessage());
            throw new BusinessException(var14.getMessage());
        }
    }

    private WxPayUnifiedOrderRequest buildUnifiedOrderRequest(SysPayOrder payOrder, String productId, WxPayConfig wxPayConfig) {
        String tradeType = wxPayConfig.getTradeType();
        String payOrderId = payOrder.getId() + "";
        String amount = AmountUtil.convertDollar2Cent(new BigDecimal(20).toString());
        Integer totalFee = Integer.valueOf(Integer.parseInt(amount));
        String deviceInfo = "phone";
        String body ="ces";
        String detail = null;
        String attach = "+" + "PcShop";
        String feeType = "CNY";
        String spBillCreateIP = "39.107.232.45";
        String timeStart = null;
        String timeExpire = null;
        String goodsTag = null;
        String notifyUrl = "ces";
        String limitPay = null;
        String openId = null;
        String sceneInfo = null;
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setDeviceInfo(deviceInfo);
        request.setBody(body);
        request.setDetail((String) detail);
        request.setAttach(attach);
        request.setOutTradeNo(payOrderId);
        request.setFeeType(feeType);
        request.setTotalFee(totalFee);
        request.setSpbillCreateIp(spBillCreateIP);
        request.setTimeStart((String) timeStart);
        request.setTimeExpire((String) timeExpire);
        request.setGoodsTag((String) goodsTag);
        request.setNotifyURL(notifyUrl);
        request.setTradeType(tradeType);
        request.setProductId(productId);
        request.setLimitPay((String) limitPay);
        request.setOpenid((String) openId);
        request.setSceneInfo((String) sceneInfo);
        return request;
    }
}
