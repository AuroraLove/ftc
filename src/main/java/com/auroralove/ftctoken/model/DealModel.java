package com.auroralove.ftctoken.model;

import com.auroralove.ftctoken.filter.Dfilter;
import com.auroralove.ftctoken.filter.Ufilter;
import lombok.Data;

import java.sql.Date;

/**
 * 交易记录模型
 *
 * @author zyu
 * @date 2019/1/24
 */
@Data
public class DealModel {

    /**
     * 交易记录id
     */
    private Long dealId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 单价
     */
    private Double univalent;

    /**
     * 数量
     */
    private Double quantity;

    /**
     * 金额
     */
    private Double amount;

    /**
     * 交易日期
     */
    private Date dealDate;

    /**
     * 交易状态
     */
    private Integer status;

    /**
     * 交易类型，0，买，1，卖，2，充值
     */
    private Integer type;

    public DealModel(Ufilter ufilter) {
        this.userId = ufilter.getId();
        this.amount = ufilter.getAmount();
    }

    public DealModel() {
    }

    public DealModel(Dfilter dfilter) {
        this.userId = dfilter.getId();
        this.type = dfilter.getTrctFlag();
        this.univalent = dfilter.getUnivalent();
        this.quantity = dfilter.getQuantity();
    }
}
