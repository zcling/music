package com.qmx.constants;

/**
 * Created by zcl on 2018/8/19.
 */
public enum  TradingTypeEnum {

    /** 系统支付 */
    SYS_RECHARGE("系统充值"),
    /** 会员充值 */
    //MEMBER_RECHARGE("会员充值"),
    /** 会员支付 */
    //MEMBER_PAYMENT("会员支付"),
    /** 预存款充值 */
    DEPOSIT_RECHARGE("预存款充值"),
    /** 订单支付 */
    ORDER_PAYMENT("订单支付"),
    /** 订单退款 */
    ORDER_REFUND("订单退款"),
    /** 订单返利 */
    ORDER_PROFIT("订单返利"),
    //adminProfit,
    /** 授信额度充值 */
    CREDIT_RECHARGE("授信充值"),
    //creditRecharge,
    /** 钱包充值 */
    WALLET_RECHARGE("钱包充值"),
    /** 提现 */
    WALLET_WITHDRAW("钱包提现"),
    /** 订单短信 */
    ORDER_SMS_CHARGE("订单短信"),
    /** 短信通知*/
    SMS_NOTIFY_CHARGE("短信通知"),
    /** 订单补发 */
    //ORDER_SMS_RESEND_CHARGE("订单补发"),
    /** 短信推广 */
    SMS_PROMOTION_CHARGE("短信推广"),
    /** 短信验证码 */
    SMS_VERIFY_CODE("短信验证码"),
    /** 系统租金扣费*/
    SYS_RENT_CHARGE("系统租金扣费"),
    /** 系统余额扣除*/
    SYS_BALANCE_DEDUCTION("系统余额扣除"),
    /** 资金冻结*/
    FUNDS_FREEZE("资金冻结"),
    /** 解除冻结资金*/
    DE_FUNDS_FREEZE("解除冻结资金"),
    /** 其他*/
    OTHER("其他");

    private String name;

    TradingTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
