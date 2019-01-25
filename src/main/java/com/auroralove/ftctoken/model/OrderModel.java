package com.auroralove.ftctoken.model;

import lombok.Data;

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
     * 出售人id
     */
    private Long sellerId;

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


}
