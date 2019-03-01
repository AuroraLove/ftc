package com.auroralove.ftctoken.service;

import com.auroralove.ftctoken.dict.DealEnum;
import com.auroralove.ftctoken.entity.*;
import com.auroralove.ftctoken.filter.Dfilter;
import com.auroralove.ftctoken.filter.Ufilter;
import com.auroralove.ftctoken.mapper.DealMapper;
import com.auroralove.ftctoken.mapper.SystemMapper;
import com.auroralove.ftctoken.mapper.UserMapper;
import com.auroralove.ftctoken.model.*;
import com.auroralove.ftctoken.platform.JPushInstance;
import com.auroralove.ftctoken.utils.CanlendarUtil;
import com.auroralove.ftctoken.utils.IdWorker;
import com.auroralove.ftctoken.utils.JsonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Value("${system.startTime}")
    private String startTime;

    @Value("${system.endTime}")
    private String endTime;

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
        List<DealEntity> dealRecord = new ArrayList<>();
        PageHelper.startPage(pageNum,pageSize);
        if (dfilter.getAssertFlag().equals(0)){
            //交易记录
            dealRecord = dealMapper.getDealRecord(dfilter.getId());
        }else {
            //可交易资产
            dealRecord = dealMapper.getAssertDealRecord(dfilter.getId());
        }
        PageInfo page = new PageInfo(dealRecord);
        return page;
    }

    public PageInfo dealRecordList(Dfilter dfilter, Integer pageNum, Integer pageSize) {
        List<DealEntity> dealRecords = new ArrayList<>();
        PageHelper.startPage(pageNum,pageSize);
        //根据订单状态获取交易列表
        dealRecords = dealMapper.dealRecordList(dfilter.getDealStatus(),dfilter.getDealType());
        PageInfo pageInfo = new PageInfo(dealRecords);
        return pageInfo;
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
    @Transactional
    public int deal(Dfilter dfilter, AccountEntity accountEntity) {
        DealModel dealModel = new DealModel(dfilter);
        if (dfilter.getDealType() == DealEnum.RECHARGE_FLAG.getValue()
                || dfilter.getDealType() == DealEnum.SELL_FLAG.getValue()){
            if (!CanlendarUtil.isEffectiveDate(startTime,endTime)){
                return -11;
            }
            //买卖交易判断用户资料是否完整
            UserPayModel payInfo = userMapper.getPayInfo(dfilter.getId());
            if (payInfo == null){
                return -1;
            }
            dealModel.setUser_name(payInfo.getName());
            //验证用户支付密码
            UserModel user = userMapper.findUserById(dfilter.getId());
            //判断账户是否冻结，1冻结，0正常
            if (!user.getAccountStatus().equals(0)){
                //1,冻结一周，判断是否超过解冻期
                if (user.getAccountStatus().equals(1)
                        &&user.getFrozenDate().after(CanlendarUtil.getPastDate(7))){
                    return -8;
                }
                //2,冻结一月，判断是否超过解冻期
                if (user.getAccountStatus().equals(2)
                        &&user.getFrozenDate().after(CanlendarUtil.getPastDate(30))){
                    return -8;
                }
                //3,永久冻结
                if (user.getAccountStatus().equals(3)){
                    return -8;
                }
            }
            if (!dfilter.getPayPwd().equals(user.getPay_pwd())){
                return -3;
            }
            if (dfilter.getDealType() == DealEnum.SELL_FLAG.getValue()){
                //判断是否当天只挂卖一次
                List<DealEntity> dealModels = dealMapper.getSingleSell(dfilter.getId());
//                if (dealModels.size() > 0){
//                    return -5;
//                }
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
//                if (cancleModel.getOid() != null){
//                   return -7;
//                }
            }
            //判断用户是否有未完成订单
            List<DealEntity> dealModels = dealMapper.getDealStatus(dfilter.getId());
//            if (dealModels.size()>0){
//                return -4;
//            }
            //默认状态匹配中
            dealModel.setStatus(DealEnum.MATCHING_STATUS.getValue());
        }
        dealModel.setTid(idWorker.nextId());
        int result = dealMapper.newDealRecord(dealModel);
        //充值
        if (result > 0 && DealEnum.DEALTYPE_RECHARGE.getValue().equals(dfilter.getDealType())){
            UserModel userModel = new UserModel();
            userModel.setId(dfilter.getId());
            userModel.setRegistFlag(1);
            result = userMapper.updateUserInfo(userModel);
            if (result > 0){
                return -2;
            }
            return -9;
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
    @Transactional
    public int updateDealStatus(Dfilter dfilter) {
        if (dfilter.getDealStatus().equals(9)){
            //如果交易撤销，则将记录推出状态11，页面不可见状态
            return dealMapper.updateDealStatus(dfilter.getDid(),11);
        }
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
    @Transactional
    public int updateOrder(Dfilter dfilter) {
        OrderModel orderModel = new OrderModel(dfilter);
        int result =  dealMapper.updateOrder(orderModel);
        OrderModel order = dealMapper.getOrder(dfilter.getOid());
        //完成订单释放金额
        if (result >0 && dfilter.getOrderStatus().equals(6)){
            //取充值金额
            AccountModel rechargeAccount = userMapper.getRechargeAmount(order.getBuyer_id());
            if (rechargeAccount == null){
                rechargeAccount = new AccountModel();
                rechargeAccount.setRechargeAcct(0.0);
            }
            //取释放金额
            AccountModel realeaseAcct = userMapper.getReleaseAmount(order.getBuyer_id());
            if (realeaseAcct == null){
                realeaseAcct = new AccountModel();
                realeaseAcct.setReleaseAmount(0.0);
            }
            //增加释放记录
            Double lockedAcct = rechargeAccount.getRechargeAcct() - realeaseAcct.getReleaseAmount();
            if (lockedAcct > 100.0){
                DealModel dealModel = new DealModel(order);
                dealModel.setTid(idWorker.nextId());
                dealModel.setDeal_amount(100.0);
                result = dealMapper.newDealRecord(dealModel);
            }
            //增加奖励记录
            List<SystemLevelModel> systemLevelModels = systemMapper.getSystemLevel();
            //记录对应子id
            Long childId = order.getBuyer_id();
            for (SystemLevelModel systemLevelModel:systemLevelModels) {
                //取买单用户父id
                UserModel user = userMapper.findUserById(childId);
                if (user.getParentId() == null){
                    break;
                }
                //增加父对象相应奖励记录
                UserModel parent = userMapper.findUserById(user.getParentId());
                RewardRecordModel rewardRecordModel = new RewardRecordModel(user,systemLevelModel,parent);
                rewardRecordModel.setRid(idWorker.nextId());
                result = dealMapper.newRewardRecord(rewardRecordModel);
                childId = user.getParentId();
            }
        }
        return result;
    }

//    /**
//     * 获取订单列表
//     * @param dfilter
//     * @return
//     */
//    public OrderListEntity getOrderList(Dfilter dfilter) {
//        if (dfilter.getPageNum() == null){
//            dfilter.setPageNum(1);
//        }
//        if (dfilter.getPageSize() == null){
//            dfilter.setPageSize(10);
//        }
//        PageHelper.startPage(dfilter.getPageNum(),dfilter.getPageSize());
//        List<OrderModel> orderModels = dealMapper.getOrderList(dfilter.getOrderStatus());
//        PageInfo page = new PageInfo(orderModels);
//        Integer total = dealMapper.getOrderCount(dfilter.getDealStatus(),dfilter.getDealType());
//        OrderListEntity orderListEntity = new OrderListEntity(page,total,dfilter.getOrderStatus());
//        return orderListEntity;
//    }

    /**
     * 获取用户注册列表
     * @return
     * @param ufilter
     */
    @Transactional
    public PageInfo getRecharegeDeals(Ufilter ufilter){
        if (ufilter.getPageNum() == null){
            ufilter.setPageNum(1);
        }
        if (ufilter.getPageSize() == null){
            ufilter.setPageSize(10);
        }
        //设置用户注册总参数
        TotalInfoModel totalInfoModel = dealMapper.getTotalInfo();
        PageHelper.startPage(ufilter.getPageNum(),ufilter.getPageSize());
        List<DealEntity> dealModels = dealMapper.getRecharegeDeals(ufilter.getPhone());
        PageInfo page = new PageInfo(dealModels);
        return page;
    }

    /**
     * 获取用户注册参数
     * @return
     * @param ufilter
     */
    public TotalInfoModel statistacRecharge(){
        //设置用户注册总参数
        TotalInfoModel totalInfoModel = dealMapper.getTotalInfo();
        return totalInfoModel;
    }

    @Transactional
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
                if (!purchaseDeal.getUid().equals(sellDeal.getUid())
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

    /**
     * 取订单总数
     * @param dfilter
     * @return
     */
    public Integer getDealTotal(Dfilter dfilter) {
        Integer total = dealMapper.getDealTotal(dfilter.getDealType(),dfilter.getDealStatus());
        return total;
    }

    /**
     * 检测超时订单
     * @return
     */
    @Transactional
    @Scheduled(cron = "${order.Btime.cron}")
    public void orderAutomatic(){
        List<OrderModel> orderModels = dealMapper.getTimeoutOrder();
        if (orderModels.size() > 0){
            for (OrderModel orderModel:orderModels){
                Long oid = orderModel.getOid();
                if (orderModel.getStatus().equals(4)){
                    //设置系统撤销
                    Dfilter dfilter = new Dfilter(oid,10);
                    updateOrder(dfilter);
                }
                if (orderModel.getStatus().equals(5)){
                    //完成订单
                    Dfilter dfilter = new Dfilter(oid,6);
                    updateOrder(dfilter);
                }
            }
        }
    }
}
