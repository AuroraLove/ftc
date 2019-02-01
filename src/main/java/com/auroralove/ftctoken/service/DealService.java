package com.auroralove.ftctoken.service;

import com.auroralove.ftctoken.dict.DealEnum;
import com.auroralove.ftctoken.entity.AssetEntity;
import com.auroralove.ftctoken.entity.RecordEntity;
import com.auroralove.ftctoken.filter.Dfilter;
import com.auroralove.ftctoken.filter.Ufilter;
import com.auroralove.ftctoken.mapper.DealMapper;
import com.auroralove.ftctoken.mapper.UserMapper;
import com.auroralove.ftctoken.model.DealModel;
import com.auroralove.ftctoken.entity.OrderEntity;
import com.auroralove.ftctoken.model.OrderModel;
import com.auroralove.ftctoken.model.UserPayModel;
import com.auroralove.ftctoken.utils.IdWorker;
import com.auroralove.ftctoken.utils.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易大厅
 * @author zyu
 * @date 2019/1/24
 */
@Service
@Component
public class DealService {

     @Autowired
     private DealMapper dealMapper;

     @Autowired
     private UserMapper userMapper;

     @Autowired
     private IdWorker idWorker;

    /**
     * 获取交易记录
     * @param dfilter
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<AssetEntity> commutableAssets(Dfilter dfilter, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        //取奖励金额
        Page<AssetEntity> rewards = dealMapper.getCommutableAssets(dfilter.getId());
        return new PageInfo<>(rewards);
    }

    /**
     * 分佣奖励查询
     * @param ufilter
     * @return
     */
    public PageInfo<RecordEntity> subReward(Ufilter ufilter,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<RecordEntity> dealEntities = dealMapper.getRewardRecord(ufilter.getId());
        return new PageInfo<>(dealEntities);
    }

    /**
     * 交易,买单或卖单
     * @param dfilter
     * @return
     */
    public int deal(Dfilter dfilter) {
        DealModel dealModel = new DealModel(dfilter);
        dealModel.setTid(idWorker.nextId());
        //默认状态匹配中
        dealModel.setStatus(DealEnum.MATCHING_STATUS.getValue());
        return dealMapper.newDealRecord(dealModel);
    }

    @Scheduled(cron = "${model.Btime.cron}")
    public void matchOrder(){
        //获取买单数
        List<DealModel> purchaseDeals =  dealMapper.getPurchaseDeals();
        //获取卖单数
        List<DealModel> sellDeals =  dealMapper.getSellDeals();
        List<OrderModel> orders = new ArrayList<>();
        int min = purchaseDeals.size() < sellDeals.size() ? purchaseDeals.size() : sellDeals.size();
        for (int i = 0;i < min;i++){
            //匹配生成订单
            if (purchaseDeals.get(i).getUid() != sellDeals.get(i).getUid()
                    && purchaseDeals.get(i).getDeal_amount() == sellDeals.get(i).getDeal_amount()){
                OrderModel orderModel = new OrderModel(purchaseDeals.get(i), sellDeals.get(i));
                orderModel.setOid(idWorker.nextId());
                int n = dealMapper.newOrder(orderModel);
                if (n > 0){
                    orders.add(orderModel);
                }

            }
        }
        System.out.println("===========匹配任务"+ System.currentTimeMillis()+"================");
    }

    /**
     * 订单详情
     * @param dfilter
     * @return
     */
    public OrderEntity orderInfo(Dfilter dfilter) {
        OrderModel orderModel = dealMapper.getOrder(dfilter.getDid());
        OrderEntity orderEntity = new OrderEntity(orderModel);
        //获取卖单用户收款信息
        UserPayModel payModel = userMapper.getPayInfo(orderModel.getDeal_sell_id());
        orderEntity.setPayInfo(payModel);
        return orderEntity;
    }

    public int updateOrder(Dfilter dfilter) {
        OrderModel orderModel = new OrderModel(dfilter);
        return dealMapper.updateOrder(orderModel);
    }
}
