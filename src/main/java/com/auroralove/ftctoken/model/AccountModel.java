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
}
