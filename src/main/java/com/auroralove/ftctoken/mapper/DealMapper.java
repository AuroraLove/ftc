package com.auroralove.ftctoken.mapper;

import com.auroralove.ftctoken.entity.DealEntity;
import com.auroralove.ftctoken.entity.RecordEntity;
import com.auroralove.ftctoken.model.DealModel;
import com.auroralove.ftctoken.model.OrderModel;
import com.auroralove.ftctoken.model.RewardRecordModel;
import com.auroralove.ftctoken.model.TotalInfoModel;
import org.apache.ibatis.annotations.Param;

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
     * 获取充值列表
     * @return
     */
    List<DealEntity> getRecharegeDeals(@Param("phone")String phone);

    /**
     * 新增订单
     * @return
     */
    int newOrder(OrderModel order);

    /**
     * 查询订单
     * @return
     */
    OrderModel getOrder(@Param("oid")Long oid);

    /**
     * 获取交易记录
     * @return
     */
    List<DealEntity> getDealRecord(@Param("id")Long id);

    /**
     * 获取可交易资产记录
     * @return
     */
    List<DealEntity> getAssertDealRecord(@Param("id")Long id);
    /**
     * 修改订单状态
     * @return
     */
    int updateOrder(OrderModel orderModel);

    /**
     * 获取奖励记录
     * @return
     */
    List<RecordEntity> getRewardRecord(@Param("uid")Long id);

    /**
     * 可交易资产查询
     * @return
     */
    DealModel getTradeableAmount(@Param("uid") Long id);

    /**
     * 判断用户是否有未完成订单信息
     * @return
     */
    List<DealEntity> getDealStatus(@Param("id")Long id);

    /**
     * 判断是否只挂卖一次
     * @return
     */
    List<DealEntity> getSingleSell(@Param("id")Long id);

    /**
     * 修改交易订单状态
     * @return
     */
    int updateDealStatus(@Param("did") Long did,@Param("dealStatus") Integer dealStatus);

    /**
     * 获取未完成交易订单数
     * @return
     */
    Integer getUnfinishedDeal(@Param("uid")Long uid);

    /**
     * 获取当日是否有撤销订单交易记录
     * @return
     */
    List<DealEntity> getCancleAction(@Param("uid")Long uid);

    /**
     * 数据中心
     * @return
     */
    TotalInfoModel getTotalInfo();

//    /**
//     * 查询订单列表
//     * @return
//     */
//    List<OrderModel> getOrderList(@Param("status") Integer status,@Param("type") Integer type);

//    /**
//     * 查询订单列表总数
//     * @return
//     */
//    Integer getOrderCount(@Param("status") Integer status,@Param("type") Integer type);

    /**
     * 增加奖励记录
     * @return
     */
    int newRewardRecord(RewardRecordModel rewardRecordModel);

    /**
     * 订单交易记录列表
     * @return
     * @param dealType
     */
    List<DealEntity> dealRecordList(@Param("status") Integer status,@Param("type") Integer dealType);

    Integer getDealTotal(@Param("type")Integer dealType, @Param("status")Integer dealStatus);

    List<OrderModel> getTimeoutOrder();
}
