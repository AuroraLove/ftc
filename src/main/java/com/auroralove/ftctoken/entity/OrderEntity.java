package com.auroralove.ftctoken.entity;

import com.auroralove.ftctoken.model.OrderModel;
import com.auroralove.ftctoken.model.UserPayModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

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
    private Long dealSellId;

    /**
     * 买单id
     */
    private Long dealBuyId;

    /**
     * 出售人id
     */
    private Long sellerId;

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
    private Long buyerId;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 出售人手机号
     */
    private String sellerPhone;

    /**
     * 购买人手机号
     */
    private String buyerPhone;

    /**
     * 支付方式
     */
    private Integer payWay;

    /**
     * 订单金额
     */
    private Double dealAmount;

    /**
     * 确认支付日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date affirmDate;

    /**
     * 支付日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payDate;

    /**
     * 下单日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderDate;

    /**
     * 完成订单日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishDate;

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
        this.buyerId = orderModel.getBuyer_id();
        this.buyerName = orderModel.getBuyer_name();
        this.sellerId = orderModel.getSeller_id();
        this.sellerName = orderModel.getSeller_name();
        this.buyerPhone = orderModel.getBuyer_phone();
        this.sellerPhone = orderModel.getSeller_phone();
        this.dealBuyId = orderModel.getDeal_buy_id();
        this.dealSellId = orderModel.getDeal_sell_id();
        this.quantity = orderModel.getQuantity();
        this.univalent = orderModel.getUnivalent();
        this.dealAmount =orderModel.getDeal_amount();
        this.affirmDate = orderModel.getAffirm_date();
        this.payWay = orderModel.getPay_way();
        this.orderDate = orderModel.getOrder_date();
        this.payDate = orderModel.getPay_date();
        this.affirmDate = orderModel.getAffirm_date();
        this.finishDate = orderModel.getFinish_date();
        this.status = orderModel.getStatus();
    }

}
