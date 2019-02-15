package com.auroralove.ftctoken.model;

import lombok.Data;

/**
 * 用户账户模型
 *
 * @author zyu
 * @date 2019/1/28
 */
@Data
public class AccountModel {

    /**
     * 可交易资产
     */
    private Double tradeableAcct;

    /**
     * FTC锁仓额度
     */
    private Double FTCLockedAcct;

    /**
     * FTC总奖励额
     */
    private Double FTCRewardAcct;

    /**
     * 释放金额
     */
    private Double releaseAmount;

    /**
     * 买单金额
     */
    private Double buyAcct;

    /**
     * 卖单总金额
     */
    private Double sellAcct;

    /**
     * 注册总金额
     */
    private Double rechargeAcct;

}
