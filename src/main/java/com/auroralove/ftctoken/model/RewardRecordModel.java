package com.auroralove.ftctoken.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 奖励模型
 * @author zyu
 * @date 2019/1/25
 */
@Data
public class RewardRecordModel {

    /**
     * 记录id
     */
    private Long rid;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 买单用户id
     */
    private Long childId;

    /**
     * 买单用户手机号
     */
    private String childPhone;

    /**
     * 奖励人用户手机号
     */
    private String parentPhone;

    /**
     * 直销级别
     */
    private Integer level;

    /**
     * 奖励金额
     */
    private Double amount;

    /**
     * 奖励日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date rewardDate;

    /**
     * 记录状态，默认6已完成
     */
    private Integer status;

    public RewardRecordModel() {
    }
    public RewardRecordModel(UserModel user,  SystemLevelModel systemLevelModel) {
        this.uid = user.getParentId();
        this.childId = user.getId();
        this.childPhone = user.getPhone();
        this.level = systemLevelModel.getLevel();
        this.amount = systemLevelModel.getAmount();
        this.status = 6;
    }
}
