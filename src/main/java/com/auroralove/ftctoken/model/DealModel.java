package com.auroralove.ftctoken.model;

import com.auroralove.ftctoken.filter.Dfilter;
import com.auroralove.ftctoken.filter.Ufilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

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
     * 订单id
     */
    private Long oid;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deal_date;

    /**
     * 交易状态，4未支付，5已支付未确认，6确认支付完成，8，订单冻结，9撤销
     */
    private Integer status;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户实名
     */
    private String user_name;

    /**
     * 交易类型，0，买，1，卖，2，充值
     */
    private Integer type;

    /**
     * 可交易资产
     */
    private Double tradeableAmount;

    public DealModel(Ufilter ufilter) {
        this.uid = ufilter.getId();
        this.deal_amount = ufilter.getAmount();
    }

    public DealModel() {
    }

    public DealModel(Dfilter dfilter) {
        this.uid = dfilter.getId();
        this.type = dfilter.getDealType();
        this.univalent = dfilter.getUnivalent();
        this.quantity = dfilter.getQuantity();
        this.deal_amount = dfilter.getAmount();
        this.phone = dfilter.getPhone();
        this.user_name = dfilter.getUserName();
    }

    public DealModel(OrderModel order) {
        this.uid = order.getBuyer_id();
        this.univalent = order.getUnivalent();
        this.quantity = order.getQuantity();
        this.phone = order.getBuyer_phone();
        this.user_name = order.getBuyer_name();
        this.phone = order.getBuyer_phone();
        this.type = 7;
        this.status = 6;
    }
}
