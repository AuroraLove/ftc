package com.auroralove.ftctoken.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date rewardDate;



}
