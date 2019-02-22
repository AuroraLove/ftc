package com.auroralove.ftctoken.entity;

import com.auroralove.ftctoken.model.AccountModel;
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

    /**
     * 释放金额
     */
    private Double realeaseAcct;

    /**
     * 充值金额
     */
    private Double recharegeAcct;

    /**
     * 买单金额
     */
    private Double buyAcct;

    /**
     * 卖单总金额
     */
    private Double sellAcct;

    public AccountEntity() {
    }

    public AccountEntity(AccountModel buyAccountInfo, AccountModel sellAccountInfo, AccountModel rewardAccount,
                         AccountModel rechargeAccount, AccountModel realeaseAcct,AccountModel systemAccount) {
        this.recharegeAcct = rechargeAccount.getRechargeAcct();
        this.FTCLockedAcct = rechargeAccount.getRechargeAcct() - realeaseAcct.getReleaseAmount();
        this.FTCRewardAcct = rewardAccount.getFTCRewardAcct();
        this.tradeableAcct = rewardAccount.getFTCRewardAcct() + systemAccount.getSystemAcct() + buyAccountInfo.getBuyAcct() + realeaseAcct.getReleaseAmount() - sellAccountInfo.getSellAcct();
        this.realeaseAcct = realeaseAcct.getReleaseAmount();
        this.buyAcct = buyAccountInfo.getBuyAcct();
        this.sellAcct = sellAccountInfo.getSellAcct();
    }
}
