package com.auroralove.ftctoken.mapper;

import com.auroralove.ftctoken.entity.AssetEntity;
import com.auroralove.ftctoken.model.DealModel;
import com.auroralove.ftctoken.entity.OrderEntity;
import com.auroralove.ftctoken.model.OrderModel;
import com.github.pagehelper.Page;

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
    int newOrder(OrderModel order);

    /**
     * 查询订单
     * @return
     */
    List<OrderEntity> getOrder(Long oid);

    /**
     * 获取奖励记录
     * @return
     */
    Page<AssetEntity> getRewardList(Long id);
}
