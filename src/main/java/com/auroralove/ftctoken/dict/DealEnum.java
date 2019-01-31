package com.auroralove.ftctoken.dict;

/**
 *  交易状态
 * @author BlueThink
 */
public enum DealEnum {


    /**
     * 交易类型TYPE,充值
     */
    DEALTYPE_RECHARGE (2),

    /**
     * 交易类型TYPE,买
     */
    RECHARGE_FLAG(0),

    /**
     * 交易类型TYPE,卖
     */
    SELL_FLAG(1),

    /**
     * 交易类型TYPE,释放
     */
    RELEASE_FLAG(7),

    /**
     * 交易类型TYPE,撤销
     */
    CANCLE_FLAG(9),

    /**
     * 交易状态STATUS，匹配中
     */
    MATCHING_STATUS(3),

    /**
     * 交易状态STATUS，待支付
     */
    UNPAID(4),

    /**
     * 交易状态STATUS,以支付未收款
     */
    OBLIGATION(5),

    /**
     * 交易状态STATUS,确认收款，支付完成
     */
    FINISHING(6),

    /**
     * 交易状态STATUS,订单冻结
     */
    FROZEN(8),

    /**
     * 银行卡支付
     */
    BANK_CARD(20),

    /**
     * 支付宝支付
     */
    ALI_PAY(21),

    /**
     * 微信支付
     */
    WECHAT_PAY(22)
    ;

    private Integer value;

    DealEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
