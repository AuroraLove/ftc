package com.auroralove.ftctoken.entity;

import com.auroralove.ftctoken.model.AccountModel;
import com.auroralove.ftctoken.model.UserModel;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 实体业务模型，用于返回值
 * @author zyu
 * @date 2019-1-22  20:43
 */
@Data
public class UserEntity {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户验证token
     */
    private String token;

    /**
     * 我的团队id
     */
    private Long teamId;

    /**
     * 管理员基本，0，超级管理员，1，普通管理员，2，无管理级别
     */
    private Integer amdinStatus;

    /**
     * 用户上级邀请人id
     */
    private Long parentId;

    /**
     * FTC锁仓额度
     */
    private Double FTCLockedAcct;

    /**
     * FTC总奖励额度
     */
    private Double FTCRewardAcct;

    /**
     * 可交易额度
     */
    private Double tradeableAcct;

    public UserEntity() {

    }

    public UserEntity(UserModel userModel, AccountModel accountModel,AccountModel rewardModel) {
        this.id = userModel.getId() ;
        this.phone = userModel.getPhone();
        this.amdinStatus = userModel.getAmdinStatus();
        this.teamId = userModel.getTeamId();
        this.parentId = userModel.getParentId();
        this.FTCLockedAcct = accountModel.getFTCLockedAcct();
        this.tradeableAcct = accountModel.getTradeableAcct();
        this.FTCRewardAcct = rewardModel.getFTCRewardAcct();
    }

}
