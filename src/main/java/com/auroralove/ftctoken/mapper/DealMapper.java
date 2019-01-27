package com.auroralove.ftctoken.mapper;

import com.auroralove.ftctoken.model.DealModel;
import com.auroralove.ftctoken.model.OrderEntity;
import com.auroralove.ftctoken.model.OrderModel;

import java.util.List;

/**
 * @author zyu
 * @date 2019/1/25
 */
public interface DealMapper {

    /**
     * 新增交易记录
     * @param dealModel
     * @return
     */
    int newDealRecord(DealModel dealModel);

    /**
     * 获取正在匹配的买单交易
     * @return
     */
    List<DealModel> getPurchaseDeals();

    /**
     * 获取正在匹配的卖单交易
     * @return
     */
    List<DealModel> getSellDeals();

    /**
     * 新增订单
     * @return
     */
    int newOrders(List<OrderModel> orders);

    /**
     * 查询订单
     * @return
     */
    List<OrderEntity> getOrder(Long oid);
}
