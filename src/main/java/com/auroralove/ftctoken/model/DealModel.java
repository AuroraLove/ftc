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
    private Long tid;

    /**
     * 用户id
     */
    private Long uid;

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
    private Double deal_amount;

    /**
     * 交易日期
     */
    private Date deal_date;

    /**
     * 交易状态
     */
    private Integer status;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 交易类型，0，买，1，卖，2，充值
     */
    private Integer type;

    public DealModel(Ufilter ufilter) {
        this.uid = ufilter.getId();
        this.deal_amount = ufilter.getAmount();
    }

    public DealModel() {
    }

    public DealModel(Dfilter dfilter) {
        this.uid = dfilter.getId();
        this.type = dfilter.getTrctFlag();
        this.univalent = dfilter.getUnivalent();
        this.quantity = dfilter.getQuantity();
    }
}
