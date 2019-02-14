package com.auroralove.ftctoken.model;

import com.auroralove.ftctoken.dict.DealEnum;
import com.auroralove.ftctoken.filter.Dfilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 订单表模型
 * @author zyu
 * @date 2019/1/25
 */
@Data
public class OrderModel {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date affirm_date;

    /**
     * 支付日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date pay_date;

    /**
     * 下单日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date order_date;

    /**
     * 完成订单日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finish_date;

    /**
     * 冻结订单日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date freeze_date;

    /**
     * 撤销订单日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cancle_date;

    /**
     * 买方实名
     */
    private String buyer_name;

    /**
     * 卖方实名
     */
    private String seller_name;

    public OrderModel() {
    }

    public OrderModel(DealModel purchase, DealModel sell) {
        this.seller_id = sell.getUid() ;
        this.buyer_id = purchase.getUid();
        this.seller_phone = sell.getPhone();
        this.seller_name = sell.getUser_name();
        this.buyer_name = purchase.getUser_name();
        this.buyer_phone = purchase.getPhone();
        this.status = DealEnum.UNPAID.getValue();
        this.quantity = purchase.getQuantity();
        this.univalent = purchase.getUnivalent();
        this.deal_amount = purchase.getQuantity() * purchase.getUnivalent();
        this.deal_buy_id = purchase.getTid();
        this.deal_sell_id = sell.getTid();
    }

    public OrderModel(Dfilter dfilter) {
        this.oid = dfilter.getOid();
        this.pay_way = dfilter.getPayWay();
        this.status = dfilter.getOrderStatus();
    }
}
