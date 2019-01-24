package com.auroralove.ftctoken.dict;

/**
 *  交易状态
 * @author BlueThink
 */
public enum DealEnum {


    /**
     * 充值
     */
    DEALTYPE_RECHARGE (2),

    /**
     * 充值标识
     */
    RECHARGE_FLAG(0)

    ;

    private Integer value;

    DealEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
