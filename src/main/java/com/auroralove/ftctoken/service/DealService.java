package com.auroralove.ftctoken.service;

import com.auroralove.ftctoken.dict.DealEnum;
import com.auroralove.ftctoken.entity.AccountEntity;
import com.auroralove.ftctoken.entity.DealEntity;
import com.auroralove.ftctoken.entity.OrderEntity;
import com.auroralove.ftctoken.entity.RecordEntity;
import com.auroralove.ftctoken.filter.Dfilter;
import com.auroralove.ftctoken.filter.Ufilter;
import com.auroralove.ftctoken.mapper.DealMapper;
import com.auroralove.ftctoken.mapper.SystemMapper;
import com.auroralove.ftctoken.mapper.UserMapper;
import com.auroralove.ftctoken.model.*;
import com.auroralove.ftctoken.platform.JPushInstance;
import com.auroralove.ftctoken.utils.IdWorker;
import com.auroralove.ftctoken.utils.JsonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
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
     private SystemMapper systemMapper;

     @Autowired
     private IdWorker idWorker;

    /**
     * 获取交易记录
     * @param dfilter
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo getDealRecord(Dfilter dfilter, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        //交易记录
        List<DealEntity> dealRecord = dealMapper.getDealRecord(dfilter.getId());
        PageInfo page = new PageInfo(dealRecord);
        return page;
    }

    /**
     * 可交易资产查询
     * @param dfilter
     * @return
     */
    public DealModel tradeableAmount(Dfilter dfilter) {
        //可交易资产查询
        DealModel tradeableInfo = dealMapper.getTradeableAmount(dfilter.getId());
        return tradeableInfo;
    }
    /**
     * 分佣奖励查询
     * @param ufilter
     * @return
     */
    public PageInfo subReward(Ufilter ufilter,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<RecordEntity> dealEntities = dealMapper.getRewardRecord(ufilter.getId());
        PageInfo page = new PageInfo(dealEntities);
        return page;
    }

    /**
     * 交易,买单或卖单
     * @param dfilter
     * @return
     */
    public int deal(Dfilter dfilter, AccountEntity accountEntity) {
        DealModel dealModel = new DealModel(dfilter);
        if (dfilter.getDealType() == DealEnum.RECHARGE_FLAG.getValue()
                || dfilter.getDealType() == DealEnum.SELL_FLAG.getValue()){
            if (dfilter.getDealType() == DealEnum.SELL_FLAG.getValue()){
                //判断是否当天只挂卖一次
                List<DealEntity> dealModels = dealMapper.getSingleSell(dfilter.getId());
                if (dealModels.size() > 0){
                    return -5;
                }
                //判断可交易金额是否大于订单提交金额
                Double tradeableAcct = accountEntity.getTradeableAcct();
                if (tradeableAcct == null || tradeableAcct < dfilter.getAmount()){
                    return -6;
                }

            }
            //判断用户是否当日有撤销操作
            List<DealEntity> cancleModels = dealMapper.getCancleAction(dfilter.getId());
            for (DealEntity cancleModel:cancleModels) {
                //判断是否为订单撤销操作
                if (cancleModel.getOid() != null){
                   return -7;
                }
            }
            //判断用户是否有未完成订单
            List<DealEntity> dealModels = dealMapper.getDealStatus(dfilter.getId());
            if (dealModels.size()>0){
                return -4;
            }
            //买卖交易判断用户资料是否完整
            UserPayModel payInfo = userMapper.getPayInfo(dfilter.getId());
            if (payInfo == null){
                return -1;
            }
            //验证用户支付密码
            UserModel user = userMapper.findUserById(dfilter.getId());
            if (!dfilter.getPayPwd().equals(user.getPay_pwd())){
                return -3;
            }
            dealModel.setUser_name(payInfo.getName());
            //默认状态匹配中
            dealModel.setStatus(DealEnum.MATCHING_STATUS.getValue());
        }
        dealModel.setTid(idWorker.nextId());
        int result = dealMapper.newDealRecord(dealModel);
        //充值
        if (result > 0 && DealEnum.DEALTYPE_RECHARGE.getValue().equals(dfilter.getDealType())){
            return -2;
        }
        return result;
    }

    /**
     * 订单详情
     * @param dfilter
     * @return
     */
    public OrderEntity orderInfo(Dfilter dfilter) {
        OrderModel orderModel = dealMapper.getOrder(dfilter.getOid());
        OrderEntity orderEntity = new OrderEntity(orderModel);
        //获取卖单用户收款信息
        UserPayModel payModel = userMapper.getPayInfo(orderModel.getSeller_id());
        orderEntity.setPayInfo(payModel);
        return orderEntity;
    }

    /**
     * 更新交易订单状态
     * @param dfilter
     * @return
     */
    public int updateDealStatus(Dfilter dfilter) {
        return dealMapper.updateDealStatus(dfilter.getDid(),dfilter.getDealStatus());
    }

    /**
     * 获取未完成交易数
     * @param id
     * @return
     */
    public Integer getUnfinishedDeal(Long id) {
        Integer count = dealMapper.getUnfinishedDeal(id);
        return count == null?0 : count;
    }

    /**
     * 更新订单状态
     * @param dfilter
     * @return
     */
    public int updateOrder(Dfilter dfilter) {
        OrderModel orderModel = new OrderModel(dfilter);
        int result =  dealMapper.updateOrder(orderModel);
        //完成订单释放金额
        if (result >0 && dfilter.getOrderStatus().equals(6)){
            //取充值金额
            AccountModel rechargeAccount = userMapper.getRegistAmount(dfilter.getId());
            if (rechargeAccount == null){
                rechargeAccount = new AccountModel();
                rechargeAccount.setRechargeAcct(0.0);
            }
            //取释放金额
            AccountModel realeaseAcct = userMapper.getReleaseAmount(dfilter.getId());
            if (realeaseAcct == null){
                realeaseAcct = new AccountModel();
                realeaseAcct.setReleaseAmount(0.0);
            }
            //增加释放记录
            Double lockedAcct = rechargeAccount.getRechargeAcct() - realeaseAcct.getReleaseAmount();;
            if (lockedAcct > 100.0){
                OrderModel order = dealMapper.getOrder(dfilter.getOid());
                DealModel dealModel = new DealModel(order);
                dealModel.setTid(idWorker.nextId());
                dealModel.setDeal_amount(100.0);
                result = dealMapper.newDealRecord(dealModel);
            }
        }
        return result;
    }


    @Scheduled(cron = "${model.Btime.cron}")
    public void matchOrder(){
        //获取买单数
        List<DealModel> purchaseDeals =  dealMapper.getPurchaseDeals();
        //获取卖单数
        List<DealModel> sellDeals =  dealMapper.getSellDeals();
        List<OrderModel> orders = new ArrayList<>();

        //遍历订单进行匹配
        Iterator purchaseIterator = purchaseDeals.iterator();
        //遍历卖方
        Iterator sellIterator = sellDeals.iterator();
        while (purchaseIterator.hasNext()){
            DealModel purchaseDeal = (DealModel) purchaseIterator.next();
            while (sellIterator.hasNext()){
                DealModel sellDeal = (DealModel) sellIterator.next();
                //卖方交易金额
                Double sellAmount = purchaseDeal.getQuantity() * purchaseDeal.getUnivalent();
                //买房交易金额
                Double buyAmount = sellDeal.getQuantity() * sellDeal.getUnivalent();
                if (!purchaseDeal.equals(sellDeal.getUid())
                        && sellAmount.equals(buyAmount)){
                    OrderModel orderModel = new OrderModel(purchaseDeal, sellDeal);
                    orderModel.setOid(idWorker.nextId());
                    int n = dealMapper.newOrder(orderModel);
                    if (n > 0){
                        //移除以匹配订单
                        sellIterator.remove();
                        orders.add(orderModel);
                        //跳出当前循环
                        break;
                    }
                }
            }
        }
//        int min = purchaseDeals.size() < sellDeals.size() ? purchaseDeals.size() : sellDeals.size();
//        for (int i = 0;i < min;i++){
//            //匹配生成订单
//            if (!purchaseDeals.get(i).getUid().equals(sellDeals.get(i).getUid())
//                    && purchaseDeals.get(i).getDeal_amount().equals(sellDeals.get(i).getDeal_amount())){
//                OrderModel orderModel = new OrderModel(purchaseDeals.get(i), sellDeals.get(i));
//                orderModel.setOid(idWorker.nextId());
//                int n = dealMapper.newOrder(orderModel);
//                if (n > 0){
//                    orders.add(orderModel);
//                }
//            }
//        }
        if (orders.size() > 0){
            for (OrderModel orderModel:orders) {
                //调用极光推送消息
                JPushInstance.SendPush(JsonUtils.objectToJson(orderModel),orderModel.getBuyer_id()
                ,orderModel.getSeller_id());
            }
        }
        System.out.println("===========匹配任务"+ System.currentTimeMillis()+"================");
    }

}
