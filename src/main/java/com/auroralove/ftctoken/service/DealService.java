package com.auroralove.ftctoken.service;

import com.auroralove.ftctoken.dict.DealEnum;
import com.auroralove.ftctoken.entity.UserEntity;
import com.auroralove.ftctoken.filter.Dfilter;
import com.auroralove.ftctoken.filter.Ufilter;
import com.auroralove.ftctoken.mapper.DealMapper;
import com.auroralove.ftctoken.model.DealModel;
import com.auroralove.ftctoken.model.OrderModel;
import com.auroralove.ftctoken.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易大厅
 * @author zyu
 * @date 2019/1/24
 */
@Service
public class DealService {

     @Autowired
     private DealMapper dealMapper;

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
        dealModel.setDealId(idWorker.nextId());
        //默认状态匹配中
        dealModel.setStatus(DealEnum.MATCHING_STATUS.getValue());
        return dealMapper.newDealRecord(dealModel);
    }

    public void matchOrder(){
        //获取买单数
        List<DealModel> purchaseDeals =  dealMapper.getPurchaseDeals();
        //获取卖单数
        List<DealModel> sellDeals =  dealMapper.getSellDeals();
        List<OrderModel> orders = new ArrayList<>();
        int max = purchaseDeals.size() > sellDeals.size() ? purchaseDeals.size() : sellDeals.size();
        for (int i = 0;i < max;i++){

        }
    }

}
