package com.auroralove.ftctoken.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 可交易资产实体模型
 *
 * @author zyu
 * @date 2019/1/29
 */
@Data
public class AssetEntity {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 买卖状态
     */
    private Integer tradingStatus;

    /**
     * 交易日期
     */
    private Timestamp tadingDate;

    /**
     * 交易金额
     */
    private Double tradingAmount;


}
