package com.qmx.model.pay;

import com.baomidou.mybatisplus.annotations.TableField;
import com.qmx.base.BaseModel;
import com.qmx.constants.PayOrderStatusEnum;
import com.qmx.constants.PayPlatEnum;
import com.qmx.constants.TradingTypeEnum;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zcl on 2018/8/19.
 */
public class SysPayOrder extends BaseModel {

    /**
     * 商户ID
     */
    @TableField("mch_id")
    private Long mchId;
    /**
     * 商户订单号（请求方订单id）
     */
    @TableField("mch_order_id")
    private String mchOrderId;
    /**
     * 条码支付的条码
     */
    @TableField("auth_code")
    private String authCode;
    /**
     * 支付渠道id
     */
    @TableField("channel_id")
    private Long channelId;
    /**
     * 渠道标识ID：weixin,alipay
     */
    @TableField("channel_no")
    private String channelNo;
    /**
     * 支付渠道名称
     */
    @TableField("channel_name")
    private String channelName;
    /**
     * 支付金额(单位:元)
     */
    private BigDecimal amount;

    /**
     * 已退款金额(单位:元)
     */
    @TableField("refund_amount")
    private BigDecimal refundAmount;
    /**
     * 三位货币代码,人民币:cny
     */
    private String currency;
    /**
     * 支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成
     */
    @TableField("pay_status")
    private PayOrderStatusEnum payStatus;
    /**
     * 客户端IP
     */
    @TableField("client_ip")
    private String clientIp;
    /**
     * 设备
     */
    private String device;
    /**
     * 商品标题
     */
    private String subject;
    /**
     * 商品描述信息
     */
    private String body;

    /**
     * 产品id（微信扫码支付必填）
     */
    @TableField("product_id")
    private String productId;
    /**
     * 微信js支付必填
     */
    @TableField("open_id")
    private String openId;
    /**
     * 微信MWEB支付必填
     */
    @TableField("scene_info")
    private String sceneInfo;
    /**
     * 特定渠道发起时额外参数
     */
    private String extra;
    /**
     * 渠道订单号：支付宝，微信订单号
     */
    @TableField("channel_order_id")
    private String channelOrderId;
    /**
     * 渠道支付错误码
     */
    @TableField("err_code")
    private String errCode;
    /**
     * 渠道支付错误描述
     */
    @TableField("err_msg")
    private String errMsg;
    /**
     * 扩展参数1
     */
    private String param1;
    /**
     * 扩展参数2
     */
    private String param2;
    /**
     * 通知地址
     */
    @TableField("notify_url")
    private String notifyUrl;
    @TableField(exist = false)
    private String returnUrl;
    /**
     * 通知次数
     */
    @TableField("notify_count")
    private Integer notifyCount;
    /**
     * 最后一次通知时间
     */
    @TableField("last_notify_time")
    private Date lastNotifyTime;
    /**
     * 订单失效时间
     */
    @TableField("expire_time")
    private Date expireTime;
    /**
     * 订单支付成功时间
     */
    @TableField("pay_succ_time")
    private Date paySuccTime;
    /**
     * 交易场景
     */
    @TableField("trading_scene")
    private String tradingScene;
    /**
     * 交易类型
     */
    @TableField("trading_type")
    private TradingTypeEnum tradingType;
    /**
     * 支付平台
     */
    @TableField("pay_plat")
    private PayPlatEnum payPlat;
    /**
     * 所属人id
     */
    @TableField("member_id")
    private Long memberId;
    /**
     * 用户id（充值使用）
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 供应商id
     */
    @TableField("supplier_id")
    private Long supplierId;
    /**
     * 集团供应商id
     */
    @TableField("group_supplier_id")
    private Long groupSupplierId;


    public Long getMchId() {
        return mchId;
    }

    public void setMchId(Long mchId) {
        this.mchId = mchId;
    }

    public String getMchOrderId() {
        return mchOrderId;
    }

    public void setMchOrderId(String mchOrderId) {
        this.mchOrderId = mchOrderId;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public PayOrderStatusEnum getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PayOrderStatusEnum payStatus) {
        this.payStatus = payStatus;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSceneInfo() {
        return sceneInfo;
    }

    public void setSceneInfo(String sceneInfo) {
        this.sceneInfo = sceneInfo;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getChannelOrderId() {
        return channelOrderId;
    }

    public void setChannelOrderId(String channelOrderId) {
        this.channelOrderId = channelOrderId;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public Integer getNotifyCount() {
        return notifyCount;
    }

    public void setNotifyCount(Integer notifyCount) {
        this.notifyCount = notifyCount;
    }

    public Date getLastNotifyTime() {
        return lastNotifyTime;
    }

    public void setLastNotifyTime(Date lastNotifyTime) {
        this.lastNotifyTime = lastNotifyTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getPaySuccTime() {
        return paySuccTime;
    }

    public void setPaySuccTime(Date paySuccTime) {
        this.paySuccTime = paySuccTime;
    }

    public String getTradingScene() {
        return tradingScene;
    }

    public void setTradingScene(String tradingScene) {
        this.tradingScene = tradingScene;
    }

    public TradingTypeEnum getTradingType() {
        return tradingType;
    }

    public void setTradingType(TradingTypeEnum tradingType) {
        this.tradingType = tradingType;
    }

    public PayPlatEnum getPayPlat() {
        return payPlat;
    }

    public void setPayPlat(PayPlatEnum payPlat) {
        this.payPlat = payPlat;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getGroupSupplierId() {
        return groupSupplierId;
    }

    public void setGroupSupplierId(Long groupSupplierId) {
        this.groupSupplierId = groupSupplierId;
    }

}
