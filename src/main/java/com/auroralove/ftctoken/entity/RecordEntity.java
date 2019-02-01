package com.auroralove.ftctoken.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 分佣记录业务模型
 * @author zyu
 * @date 2019/1/25
 */
@Data
public class RecordEntity {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 奖励金额
     */
    private Double amount;

    /**
     * 直推级别
     */
    private Integer level;

    /**
     * 奖励时间
     */
    private Timestamp rewardDate;



}
