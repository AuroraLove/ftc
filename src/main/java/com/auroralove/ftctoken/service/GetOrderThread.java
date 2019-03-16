package com.auroralove.ftctoken.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.auroralove.ftctoken.dict.DealEnum;
import com.auroralove.ftctoken.filter.Dfilter;
import com.auroralove.ftctoken.model.OrderModel;
import com.auroralove.ftctoken.platform.JPushInstance;
import com.auroralove.ftctoken.utils.IdWorker;
import com.auroralove.ftctoken.utils.JsonUtils;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;

import com.auroralove.ftctoken.mapper.DealMapper;
import com.auroralove.ftctoken.model.DealModel;
import com.auroralove.ftctoken.utils.SysCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 查找订单线程
 *
 * @author
 */
@Service
public class GetOrderThread {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private DealService dealService;

    @Autowired
    private DealMapper dealMapper;


//    @Scheduled(cron = "${matching.Btime.cron}")
//    @SchedulerLock(name = "orderAutomatic", lockAtLeastForString = "PT5S", lockAtMostForString = "PT30S")
    public void matchingOrder() {
        System.out.println("===========开始匹配任务" + new Date() + "================");
        //获取买卖方链表第一个订单
        DealModel purchaseDeal = SysCache.blockP.poll();
        DealModel sellDeal = SysCache.blockS.poll();
        try {
            if (purchaseDeal == null) {
                if (sellDeal != null) {
                    SysCache.blockS.offer(sellDeal);
                }
                Thread.sleep(1000);
                return;
            }
            if (sellDeal == null) {
                if (purchaseDeal != null) {
                    SysCache.blockP.offer(purchaseDeal);
                }
                Thread.sleep(1000);
                return;
            }
            //判断订单状态是否已经撤销
            DealModel purchaseModel = dealService.getDealRecordInfo(new Dfilter(purchaseDeal.getTid()));
            if (!purchaseModel.getStatus().equals(DealEnum.MATCHING_STATUS.getValue())
                    && !purchaseModel.getStatus().equals(DealEnum.MATCHING_DEAL_STATUS.getValue())){
                //买单撤销，清除缓存，放还卖单
                SysCache.blockS.offer(sellDeal);
                return;
            }
            DealModel sellDealModel = dealService.getDealRecordInfo(new Dfilter(sellDeal.getTid()));
            if (!sellDealModel.getStatus().equals(DealEnum.MATCHING_STATUS.getValue())
                    &&!sellDealModel.getStatus().equals(DealEnum.MATCHING_DEAL_STATUS.getValue())){
                SysCache.blockP.offer(purchaseDeal);
                return;
            }

            //若买卖为同一个用户则调整队列顺序
            while (true) {
                if (!(purchaseDeal.getUid()).equals(sellDeal.getUid())) {
                    break;
                }
                SysCache.blockS.offer(sellDeal);
                sellDeal = SysCache.blockS.poll();
                if (sellDeal == null) {
                    throw new Exception();
                }
                Thread.sleep(2000);
            }

            //卖方交易金额
            Double sellAmount = purchaseDeal.getQuantity() * purchaseDeal.getUnivalent();
            //买方交易金额
            Double buyAmount = sellDeal.getQuantity() * sellDeal.getUnivalent();

            List<OrderModel> orders = new ArrayList<>();

            if (sellAmount.equals(buyAmount)) {
                OrderModel orderModel = new OrderModel(purchaseDeal, sellDeal);
                orderModel.setOid(idWorker.nextId());
                int n = dealMapper.newOrder(orderModel);
                //回滚事物，放还队列
                if (n == 0) {
                    throw new Exception();
                }
                orders.add(orderModel);
                //更新买单卖单状态，并新增oid在匹配订单中
                n = dealService.updateOrderDealStatus(purchaseDeal.getTid(), sellDeal.getTid(), orderModel.getOid());
                if (n == 0) {
                    throw new Exception();
                }
            } else {
                SysCache.blockP.offer(purchaseDeal);
                SysCache.blockS.offer(sellDeal);
            }
            if (orders.size() > 0) {
                for (OrderModel orderModel : orders) {
                    //调用极光推送消息
                    JPushInstance.SendPush(JsonUtils.objectToJson(orderModel), orderModel.getBuyer_id()
                            , orderModel.getSeller_id());
                }
            }

            Thread.sleep(1000);
            System.out.println("===========结束匹配任务" + new Date() + "================");
        } catch (Exception e) {
            //回滚事物，将匹配订单放还队列
            e.printStackTrace();
            if (purchaseDeal != null) {
                SysCache.blockP.offer(purchaseDeal);
            }
            if (sellDeal != null) {
                SysCache.blockS.offer(sellDeal);
            }
        }
    }

}
