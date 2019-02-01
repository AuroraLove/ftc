package com.auroralove.ftctoken.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 奖励模型
 * @author zyu
 * @date 2019/1/25
 */
@Data
public class RecordModel {

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
    private Timestamp rewardDate;
}
