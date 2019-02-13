package com.auroralove.ftctoken.entity;

import lombok.Data;

@Data
public class AccountEntity {

    /**
     * 可交易额度
     */
    private Double tradeableAcct;

    /**
     * FTC锁仓额度
     */
    private Double FTCLockedAcct;

    /**
     * FTC总奖励额度
     */
    private Double FTCRewardAcct;
}
