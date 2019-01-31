package com.auroralove.ftctoken.entity;

import com.auroralove.ftctoken.model.OrderModel;
import com.auroralove.ftctoken.model.UserPayModel;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author zyu
 * @date 2019/1/27
 */
@Data
public class OrderEntity{

    /**
     * 订单id
     */
    private Long oid;

    /**
     * 售单id
     */
    private Long deal_sell_id;

    /**
     * 买单id
     */
    private Long deal_buy_id;

    /**
     * 出售人id
     */
    private Long seller_id;

    /**
     * 交易数量
     */
    private Double quantity;

    /**
     * 交易单价
     */
    private Double univalent;

    /**
     * 购买人id
     */
    private Long buyer_id;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 出售人手机号
     */
    private String seller_phone;

    /**
     * 购买人手机号
     */
    private String buyer_phone;

    /**
     * 支付方式
     */
    private Integer pay_way;

    /**
     * 订单金额
     */
    private Double deal_amount;

    /**
     * 确认支付日期
     */
    private Timestamp affirm_date;

    /**
     * 支付日期
     */
    private Timestamp pay_date;

    /**
     * 下单日期
     */
    private Timestamp order_date;

    /**
     * 完成订单日期
     */
    private Timestamp finish_date;

    /**
     * 买方实名
     */
    private String buyer_name;

    /**
     * 卖方实名
     */
    private String seller_name;

    /**
     * 出售人实名
     */
    private String sellerName;

    /**
     * 买方人实名
     */
    private String buyerName;

    /**
     * 支付信息
     */
    private UserPayModel payInfo;

    public OrderEntity() {

    }

    public OrderEntity(OrderModel orderModel) {
        this.oid = orderModel.getOid();
        this.buyer_id = orderModel.getBuyer_id();
        this.buyer_name = orderModel.getBuyer_name();
        this.seller_id = orderModel.getSeller_id();
        this.seller_name = orderModel.getSeller_name();
        this.buyer_phone = orderModel.getBuyer_phone();
        this.seller_phone = orderModel.getSeller_phone();
        this.deal_buy_id = orderModel.getDeal_buy_id();
        this.deal_sell_id = orderModel.getDeal_sell_id();
        this.quantity = orderModel.getQuantity();
        this.univalent = orderModel.getUnivalent();
        this.deal_amount =orderModel.getDeal_amount();
        this.affirm_date = orderModel.getAffirm_date();
        this.pay_way = orderModel.getPay_way();
        this.order_date = orderModel.getOrder_date();
        this.pay_date = orderModel.getPay_date();
        this.affirm_date = orderModel.getAffirm_date();
        this.finish_date = orderModel.getFinish_date();
    }

}
