package com.auroralove.ftctoken.entity;

import com.github.pagehelper.PageInfo;
import lombok.Data;

/**
 * 订单列表
 * @author zyu
 * @date 2019/2/20
 */
@Data
public class OrderListEntity extends PageInfo {

    /**
     * 当前状态列表订单数
     */
    private Integer totalOrders;

    /**
     *  订单状态，4待支付，5已支付未确认收款，6确认收款，订单完成，8订单冻结，9撤销
     */
    private Integer status;

    private PageInfo orders;

    public OrderListEntity(PageInfo page, Integer totalOrders, Integer orderStatus) {
      this.orders = page;
      this.totalOrders = totalOrders;
      this.status = orderStatus;
    }
}
