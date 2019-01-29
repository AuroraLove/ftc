package com.auroralove.ftctoken.service;

import com.auroralove.ftctoken.dict.DealEnum;
import com.auroralove.ftctoken.entity.UserEntity;
import com.auroralove.ftctoken.filter.Dfilter;
import com.auroralove.ftctoken.filter.Ufilter;
import com.auroralove.ftctoken.mapper.DealMapper;
import com.auroralove.ftctoken.mapper.UserMapper;
import com.auroralove.ftctoken.model.DealModel;
import com.auroralove.ftctoken.entity.OrderEntity;
import com.auroralove.ftctoken.model.OrderModel;
import com.auroralove.ftctoken.model.UserPayModel;
import com.auroralove.ftctoken.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
     * 分佣奖励查询
     * @param ufilter
     * @return
     */
    public List<UserEntity> subReward(Ufilter ufilter) {
        return null;
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
            OrderModel orderModel = new OrderModel(purchaseDeals.get(i), sellDeals.get(i));
            orderModel.setOid(idWorker.nextId());
            orders.add(orderModel);
        }
        if (orders.size() > 0){
            int n = dealMapper.newOrders(orders);
        }
        System.out.println("===========匹配任务"+ System.currentTimeMillis()+"================");
    }

    /**
     * 订单详情
     * @param dfilter
     * @return
     */
    public OrderEntity orderInfo(Dfilter dfilter) {
        List<OrderEntity> orderEntities = dealMapper.getOrder(dfilter.getDid());
        OrderEntity orderEntity = (OrderEntity) Collections.singletonList(orderEntities);
        //获取买单用户支付信息
        List<UserPayModel> payModel = userMapper.getPayInfo(orderEntity.getBuyer_id());
        orderEntity.setPayInfo(payModel);
        return orderEntity;
    }
}
