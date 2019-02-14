package com.auroralove.ftctoken.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 交易订单实体模型
 *
 * @author zyu
 * @date 2019/1/29
 */
@Data
public class DealEntity {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 交易记录id
     */
    private Long did;

    /**
     * 订单id
     */
    private Long oid;

    /**
     * 买卖状态
     */
    private Integer tradingStatus;

    /**
     * 交易日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tadingDate;

    /**
     * 交易金额
     */
    private Double tradingAmount;

    /**
     * FTC买卖单交易单价
     */
    private Double univalent;

    /**
     * FTC买卖单交易数量
     */
    private Double quantity;

    /**
     * 交易类型
     */
    private Integer type;
}
