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
import com.auroralove.ftctoken.store.TaskQueueManager;
import com.auroralove.ftctoken.utils.CanlendarUtil;
import com.auroralove.ftctoken.utils.IdWorker;
import com.auroralove.ftctoken.utils.JsonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.javacrumbs.shedlock.core.SchedulerLock;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Logger;

/**
 * 交易大厅
 *
 * @author zyu
 * @date 2019/1/24
 */
@Service
@Component
public class DealService {

    private static final String FIVE_MIN = "PT5M";

    private static final String TWO_MIN = "PT2M";

    private static final String ONE_MIN = "PT1M";

    private TaskQueueManager taskQueueManager;

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
     *
     * @param dfilter
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo getDealRecord(Dfilter dfilter, Integer pageNum, Integer pageSize) {
        List<DealEntity> dealRecord = new ArrayList<>();
        PageHelper.startPage(pageNum, pageSize);
        if (dfilter.getAssertFlag().equals(0)) {
            //交易记录
            dealRecord = dealMapper.getDealRecord(dfilter.getId());
        } else {
            //可交易资产
            dealRecord = dealMapper.getAssertDealRecord(dfilter.getId());
        }
        PageInfo page = new PageInfo(dealRecord);
        return page;
    }

    public PageInfo dealRecordList(Dfilter dfilter, Integer pageNum, Integer pageSize) {
        List<DealEntity> dealRecords = new ArrayList<>();
        PageHelper.startPage(pageNum, pageSize);
        //根据订单状态获取交易列表
        dealRecords = dealMapper.dealRecordList(dfilter.getDealListStatus(), dfilter.getDealType());
        PageInfo pageInfo = new PageInfo(dealRecords);
        return pageInfo;
    }

    /**
     * 可交易资产查询
     *
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
     *
     * @param ufilter
     * @return
     */
    public PageInfo subReward(Ufilter ufilter, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<RecordEntity> dealEntities = dealMapper.getRewardRecord(ufilter.getId());
        PageInfo page = new PageInfo(dealEntities);
        return page;
    }

    /**
     * 交易,买单或卖单
     *
     * @param dfilter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int deal(Dfilter dfilter, AccountEntity accountEntity) {
        DealModel dealModel = new DealModel(dfilter);
        SystemModel systemInfo = systemMapper.getSystemInfo();
        if (dfilter.getDealType().equals(DealEnum.RECHARGE_FLAG.getValue())
                || dfilter.getDealType().equals(DealEnum.SELL_FLAG.getValue())) {
            if (!systemInfo.getCNY().equals(dealModel.getUnivalent())){
                //网络异常，订单交易价格和系统价格不一致
                return -13;
            }
            //验证用户交易密码
            //获取用户
            UserModel user = userMapper.findUserById(dfilter.getId());
            if (!dfilter.getPayPwd().equals(user.getPay_pwd())) {
                return -3;
            }
            //买卖交易判断用户资料是否完整
            UserPayModel payInfo = userMapper.getPayInfo(dfilter.getId());
            if (payInfo == null) {
                return -1;
            }
            dealModel.setUser_name(payInfo.getName());
            //判断账户是否进行充值认购
            if (user.getRegistFlag() == null || !user.getRegistFlag().equals(1)) {
                return -12;
            }
            //判断账户是否冻结，1冻结，0正常
            if (!user.getAccountStatus().equals(0)) {
                //1,冻结一周，判断是否超过解冻期
                if (user.getAccountStatus().equals(1)
                        && user.getFrozenDate().after(CanlendarUtil.getPastDate(7))) {
                    return -8;
                }
                //2,冻结一月，判断是否超过解冻期
                if (user.getAccountStatus().equals(2)
                        && user.getFrozenDate().after(CanlendarUtil.getPastDate(30))) {
                    return -8;
                }
                //3,永久冻结
                if (user.getAccountStatus().equals(3)) {
                    return -8;
                }
            }
            //判断是否在可交易时间内
            if (!CanlendarUtil.isEffectiveDate(startTime, endTime)) {
                return -11;
            }
            //判断用户是否有未完成订单
            List<DealEntity> dealModels = dealMapper.getDealStatus(dfilter.getId());
            if (dealModels.size() > 0) {
                return -4;
            }
            if (dfilter.getDealType().equals(DealEnum.SELL_FLAG.getValue())) {
                //判断是否当天只挂卖一次
                List<DealEntity> singleSells = dealMapper.getSingleSell(dfilter.getId());
                if (singleSells.size() > 0) {
                    return -5;
                }
                //判断可交易金额是否大于订单提交金额
                Double tradeableAcct = accountEntity.getTradeableAcct();
                if (tradeableAcct == null || tradeableAcct < dealModel.getDeal_amount()) {
                    return -6;
                }
            }
            //判断用户是否当日有撤销操作
            List<DealEntity> cancleModels = dealMapper.getCancleAction(dfilter.getId());
            if (cancleModels.size() > 0) {
                return -7;
            }
            //默认状态匹配中
            dealModel.setStatus(DealEnum.MATCHING_STATUS.getValue());
        }
        dealModel.setTid(idWorker.nextId());
        int result = dealMapper.newDealRecord(dealModel);
        return result;
    }

    /**
     * 订单详情
     *
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
     *
     * @param dfilter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateDealStatus(Dfilter dfilter) {
        if (dfilter.getDealStatus().equals(DealEnum.SYSTEM_CANLCE_STATUS.getValue())){
            DealModel dealRecordInfo = dealMapper.getDealRecordInfo(dfilter.getDid());
            if (dealRecordInfo.getStatus().equals(DealEnum.UNPAID.getValue())){
                //正在执行匹配任务，禁止撤销
                return -1;
            }
        }
        return dealMapper.updateDealStatus(dfilter.getDid(), dfilter.getDealStatus());
    }

    /**
     * 获取未完成交易数
     *
     * @param id
     * @return
     */
    public Integer getUnfinishedDeal(Long id) {
        Integer count = dealMapper.getUnfinishedDeal(id);
        return count == null ? 0 : count;
    }

    /**
     * 更新订单状态
     *
     * @param dfilter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateOrder(Dfilter dfilter) {
        OrderModel orderModel = new OrderModel(dfilter);
        OrderModel order = dealMapper.getOrder(dfilter.getOid());
        if (order.getStatus().equals(dfilter.getOrderStatus())) {
            //不能执行两次相同操作
            return -1;
        }
        int result = dealMapper.updateOrder(orderModel);
        //更新匹配订单订单状态
        if (!dfilter.getOrderStatus().equals(9)) {
            result = flushOrderDealStatus(dfilter.getOrderStatus(), dfilter.getOid());
        }
        //执行单向撤销
        if (dfilter.getOrderStatus().equals(9)) {
            if (dfilter.getAdminFlag() != null) {
                //后台执行双向撤销
                result = flushOrderDealStatus(dfilter.getOrderStatus(), dfilter.getOid());
            } else {
                Dfilter buyFilter = new Dfilter();
                buyFilter.setDid(order.getDeal_buy_id());
                //卖单继续匹配
                Dfilter sellFilter = new Dfilter();
                sellFilter.setDid(order.getDeal_sell_id());
                //买单单向撤销
                if (dfilter.getDealType().equals(0)) {
                    buyFilter.setDealStatus(9);
                    sellFilter.setDealStatus(3);
                } else {
                    //卖单单向撤销
                    buyFilter.setDealStatus(3);
                    sellFilter.setDealStatus(9);
                }
                result = updateDealStatus(sellFilter);
                result = updateDealStatus(buyFilter);
            }
        }
        //完成订单释放金额
        if (result > 0 && dfilter.getOrderStatus().equals(6)) {
            //取充值金额
            AccountModel rechargeAccount = userMapper.getRechargeAmount(order.getBuyer_id());
            if (rechargeAccount == null) {
                rechargeAccount = new AccountModel();
                rechargeAccount.setRechargeAcct(0.0);
            }
            //取释放金额
            AccountModel realeaseAcct = userMapper.getReleaseAmount(order.getBuyer_id());
            if (realeaseAcct == null) {
                realeaseAcct = new AccountModel();
                realeaseAcct.setReleaseAmount(0.0);
            }
            //增加释放记录
            Double lockedAcct = rechargeAccount.getRechargeAcct() - realeaseAcct.getReleaseAmount();
            if (lockedAcct > 100.0) {
                DealModel dealModel = new DealModel(order);
                dealModel.setTid(idWorker.nextId());
                dealModel.setDeal_amount(100.0);
                result = dealMapper.newDealRecord(dealModel);
            }
            //增加奖励记录
            List<SystemLevelModel> systemLevelModels = systemMapper.getSystemLevel();
            //记录对应子id
            Long childId = order.getBuyer_id();
            UserModel rootUser = userMapper.findUserById(childId);
            for (SystemLevelModel systemLevelModel : systemLevelModels) {
                //取买单用户父id
                UserModel user = userMapper.findUserById(childId);
                if (user.getParentId() == null) {
                    break;
                }
                //增加父对象相应奖励记录
                UserModel parent = userMapper.findUserById(user.getParentId());
                RewardRecordModel rewardRecordModel = new RewardRecordModel(rootUser, systemLevelModel, parent);
                rewardRecordModel.setRid(idWorker.nextId());
                result = dealMapper.newRewardRecord(rewardRecordModel);
                childId = user.getParentId();
            }
        }
        return result;
    }

    /**
     * 随订单状态更新匹配订单状态
     *
     * @param orderStatus
     * @param oid
     * @return
     */
    private int flushOrderDealStatus(Integer orderStatus, Long oid) {
        int n = dealMapper.flushOrderDealStatus(orderStatus, oid);
        return n;
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
     *
     * @param ufilter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public PageInfo getRecharegeDeals(Ufilter ufilter) {
        if (ufilter.getPageNum() == null) {
            ufilter.setPageNum(1);
        }
        if (ufilter.getPageSize() == null) {
            ufilter.setPageSize(10);
        }
        //设置用户注册总参数
        TotalInfoModel totalInfoModel = dealMapper.getTotalInfo();
        PageHelper.startPage(ufilter.getPageNum(), ufilter.getPageSize());
        List<DealEntity> dealModels = dealMapper.getRecharegeDeals(ufilter.getPhone());
        PageInfo page = new PageInfo(dealModels);
        return page;
    }

    /**
     * 获取用户注册参数
     *
     * @param ufilter
     * @return
     */
    public TotalInfoModel statistacRecharge() {
        //设置用户注册总参数
        TotalInfoModel totalInfoModel = dealMapper.getTotalInfo();
        return totalInfoModel;
    }

    /**
     * 取匹配订单
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public synchronized Map<String, List<DealModel>> getMatchingList() throws Exception {
        Map<String,List<DealModel>> dealList = new HashMap<>(16);
        // 获取买单数
        List<DealModel> purchaseDeals = dealMapper.getPurchaseDeals();
        // 获取卖单数
        List<DealModel> sellDeals = dealMapper.getSellDeals();
//        for (DealModel purchaseDeal : purchaseDeals) {
//            dealMapper.updateDealStatus(purchaseDeal.getTid(), DealEnum.MATCHING_DEAL_STATUS.getValue());
//        }
//        for (DealModel sellDeal : sellDeals) {
//            dealMapper.updateDealStatus(sellDeal.getTid(), DealEnum.MATCHING_DEAL_STATUS.getValue());
//        }
        dealList.put("sellDeals",sellDeals);
        dealList.put("purchaseDeals",purchaseDeals);
        return dealList;
    }

    @Scheduled(cron = "${model.Btime.cron}")
    @SchedulerLock(name = "matchOrder", lockAtLeastForString = "PT15S", lockAtMostForString = "PT1M")
    @Transactional(rollbackFor = Exception.class)
    public void matchOrder() throws Exception {
        System.out.println("================数据库拉取匹配订单开始==================");
        // 获取买单数
//        List<DealModel> purchaseDeals = dealMapper.getPurchaseDeals();
//        // 获取卖单数
//        List<DealModel> sellDeals = dealMapper.getSellDeals();

//        for (DealModel purchaseDeal : purchaseDeals) {
//            int n = dealMapper.updateDealStatus(purchaseDeal.getTid(), DealEnum.MATCHING_DEAL_STATUS.getValue());
//            if (n > 0) {
//                SysCache.blockP.add(purchaseDeal);
//            }
//        }
//        for (DealModel sellDeal : sellDeals) {
//            int n = dealMapper.updateDealStatus(sellDeal.getTid(), DealEnum.MATCHING_DEAL_STATUS.getValue());
//            if (n > 0) {
//                SysCache.blockS.add(sellDeal);
//            }
//        }

//        //获取买单数
//        List<DealModel> purchaseDeals =  dealMapper.getPurchaseDeals();
//        //获取卖单数
//        List<DealModel> sellDeals =  dealMapper.getSellDeals();

//        for (DealModel purchaseDeal : purchaseDeals) {
//            int n = dealMapper.updateDealStatus(purchaseDeal.getTid(), DealEnum.MATCHING_DEAL_STATUS.getValue());
//            if (n == 0) {
//                throw new Exception();
//            }
//        }
//        for (DealModel sellDeal : sellDeals) {
//            int n = dealMapper.updateDealStatus(sellDeal.getTid(), DealEnum.MATCHING_DEAL_STATUS.getValue());
//            if (n == 0) {
//                throw new Exception();
//            }
//        }

        Map<String, List<DealModel>> matchingList = getMatchingList();
        //获取买单数
        List<DealModel> purchaseDeals = matchingList.get("purchaseDeals");
        //获取卖单数
        List<DealModel> sellDeals = matchingList.get("sellDeals");
        List<OrderModel> orders = new ArrayList<>();
//        记录已匹配订单
        HashMap<Long, OrderModel> matchedOrders = new HashMap<>(16);

        for (DealModel purchaseDeal : purchaseDeals) {
            for (DealModel sellDeal : sellDeals) {
                //卖方交易金额
                Double sellAmount = purchaseDeal.getQuantity() * purchaseDeal.getUnivalent();
                //买方交易金额
                Double buyAmount = sellDeal.getQuantity() * sellDeal.getUnivalent();
                //以买方匹配订单号作为key,判断是否为已匹配订单，是否交易金额相等，是否用户id相等
                if (!matchedOrders.containsKey(sellDeal.getTid())
                        && !(purchaseDeal.getUid()).equals(sellDeal.getUid())
                        && sellAmount.equals(buyAmount)
                        && !matchedOrders.containsKey(purchaseDeal.getTid())) {
                    LoggerFactory.getLogger(DealService.class).info("purchaseDeal.getUid()"+purchaseDeal.getUid()+"======="+"sellDeal.getUid()"+sellDeal.getUid());
                    OrderModel orderModel = new OrderModel(purchaseDeal, sellDeal);
                    orderModel.setOid(idWorker.nextId());
                    int n = dealMapper.newOrder(orderModel);
                    if (n == 0) {
                        throw new Exception();
                    }
                    orders.add(orderModel);
                    //更新买单卖单状态，并新增oid在匹配订单中
                    n = updateOrderDealStatus(purchaseDeal.getTid(), sellDeal.getTid(), orderModel.getOid());
                    if (n == 0) {
                        throw new Exception();
                    }
                    //将已匹配订单放入HashMap
                    matchedOrders.put(sellDeal.getTid(), orderModel);
                    matchedOrders.put(purchaseDeal.getTid(), orderModel);
                }
            }
        }
        if (orders.size() > 0) {
            for (OrderModel orderModel : orders) {
                //调用极光推送消息
                JPushInstance.SendPush(JsonUtils.objectToJson(orderModel), orderModel.getBuyer_id()
                        , orderModel.getSeller_id());
            }
        }
//        //遍历订单进行匹配
//        Iterator purchaseIterator = purchaseDeals.iterator();
//        //遍历卖方
//        Iterator sellIterator = sellDeals.iterator();
//        while (purchaseIterator.hasNext()){
//            DealModel purchaseDeal = (DealModel) purchaseIterator.next();
//            while (sellIterator.hasNext()){
//                DealModel sellDeal = (DealModel) sellIterator.next();
//                //卖方交易金额
//                Double sellAmount = purchaseDeal.getQuantity() * purchaseDeal.getUnivalent();
//                //买房交易金额
//                Double buyAmount = sellDeal.getQuantity() * sellDeal.getUnivalent();
//                if (!(purchaseDeal.getUid()).equals(sellDeal.getUid())
//                        && sellAmount.equals(buyAmount)){
//                    OrderModel orderModel = new OrderModel(purchaseDeal, sellDeal);
//                    orderModel.setOid(idWorker.nextId());
//                    int n = dealMapper.newOrder(orderModel);
//                    if (n > 0){
//                        orders.add(orderModel);
//                        //更新买单卖单状态，并新增oid在匹配订单中
//                        n = updateOrderDealStatus(purchaseDeal.getTid(),sellDeal.getTid(),orderModel.getOid());
//                        //移除以匹配订单
//                        purchaseIterator.remove();
//                        sellIterator.remove();
//                        break;
//                    }
//                }
//            }
//        }
        System.out.println("================数据库拉取匹配订单结束==================");
    }

    /**
     * 新增订单时，更新匹配订单状态和赋值oid
     *
     * @param purchaseDealTid
     * @param sellDealTid
     * @param oid
     * @return
     */
    protected int updateOrderDealStatus(Long purchaseDealTid, Long sellDealTid, Long oid) {
        int n = dealMapper.updateOrderDealStatus(purchaseDealTid, sellDealTid, oid);
        return n;
    }

    /**
     * 取订单总数
     *
     * @param dfilter
     * @return
     */
    public Integer getDealTotal(Dfilter dfilter) {
        Integer total = dealMapper.getDealTotal(dfilter.getDealType(), dfilter.getDealStatus());
        return total;
    }

    /**
     * 检测超时订单
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "${order.Btime.cron}")
    @SchedulerLock(name = "orderAutomatic", lockAtLeastForString = "PT10S", lockAtMostForString = FIVE_MIN)
    public void orderAutomatic() throws Exception {
        System.out.println("================检测超时订单开始==================");
        List<OrderModel> orderModels = dealMapper.getTimeoutOrder();
        if (orderModels.size() > 0) {
            for (OrderModel orderModel : orderModels) {
                Long oid = orderModel.getOid();
                //删除异常订单
                List<DealModel> errorOrder = dealMapper.getDealOrderInfo(oid);
                if (errorOrder.size() == 0) {
                    int n = dealMapper.deleteErrorOrder(oid);
                    if (n == 0) {
                        throw new Exception();
                    }
                } else {
                    if (orderModel.getStatus().equals(4)) {
                        //设置系统撤销
                        int n = dealMapper.updateUnpayTimeoutOrder(oid);
                        if (n == 0) {
                            throw new Exception();
                        }
                    }
                    if (orderModel.getStatus().equals(5)) {
                        //冻结订单,设置订单超时冻结
                        int n = dealMapper.updateTimeoutFrozenOrder(oid);
                        if (n == 0) {
                            throw new Exception();
                        }
                    }
                }
            }
        }
        System.out.println("================检测超时订单结束==================");
    }


    public void bulkOrder() {
        for (int i = 0; i < 500; i++) {
            DealModel p = new DealModel();
            p.setTid(idWorker.nextId());
            p.setType(0);
            p.setStatus(3);
            p.setUid(1112L);
            p.setUnivalent(1.0);
            p.setQuantity(500.0);
            p.setDeal_amount(500.0);
            p.setPhone("17538139836");
            p.setUser_name("xiaowang");
            int n = dealMapper.newDealRecord(p);
            System.out.println("=====" + i + "========" + n);
        }
        for (int i = 500; i < 1000; i++) {
            DealModel s = new DealModel();
            s.setTid(idWorker.nextId());
            s.setType(1);
            s.setUid(111L);
            s.setStatus(3);
            s.setUnivalent(1.0);
            s.setQuantity(500.0);
            s.setDeal_amount(500.0);
            s.setPhone("17538139836");
            s.setUser_name("li");
            int n = dealMapper.newDealRecord(s);
            System.out.println("=====" + i + "========" + n);

        }
    }

    /**
     * 初始化匹配中订单
     *
     * @return
     */
    public int initDealMatchingOrder() {
        return dealMapper.initDealMatchingOrder();
    }

    public DealModel getDealRecordInfo(Dfilter dfilter) {
        DealModel dealModel = dealMapper.getDealRecordInfo(dfilter.getDid());
        return dealModel;
    }
}
