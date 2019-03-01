package com.auroralove.ftctoken.controller;

import com.auroralove.ftctoken.annotation.UserLoginToken;
import com.auroralove.ftctoken.entity.AccountEntity;
import com.auroralove.ftctoken.entity.OrderEntity;
import com.auroralove.ftctoken.entity.OrderListEntity;
import com.auroralove.ftctoken.filter.Dfilter;
import com.auroralove.ftctoken.filter.Ufilter;
import com.auroralove.ftctoken.result.DealResult;
import com.auroralove.ftctoken.result.ResponseMessage;
import com.auroralove.ftctoken.result.ResponseResult;
import com.auroralove.ftctoken.service.DealService;
import com.auroralove.ftctoken.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交易大厅
 *
 * @author zyu
 * @date 2019/1/24
 */
@RestController
@RequestMapping("/v1/rest")
public class DealController {

    @Autowired
    private DealService dealService;

    @Autowired
    private UserService userService;

    /**
     * 交易记录查询
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/home/dealRecord")
    public ResponseResult dealRecord(Dfilter dfilter, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        if (dfilter.getId() != null) {
            PageInfo result = dealService.getDealRecord(dfilter, pageNum, pageSize);
            //设置未完成订单数
            DealResult responseResult = new DealResult(ResponseMessage.OK, result);
            responseResult.setUnfinished(dealService.getUnfinishedDeal(dfilter.getId()));
            return responseResult;
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 交易记录列表查询
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/home/dealRecordList")
    public ResponseResult dealRecordList(Dfilter dfilter, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo result = dealService.dealRecordList(dfilter, pageNum, pageSize);
        //设置未完成订单数
        DealResult responseResult = new DealResult(ResponseMessage.OK, result);
        responseResult.setTotalCount(dealService.getDealTotal(dfilter));
        return new ResponseResult(ResponseMessage.OK, responseResult);
    }

    /**
     * 分佣奖励
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/home/rewardRecord")
    public ResponseResult rewardRecord(Ufilter ufilter, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        if (ufilter.getId() != null) {
            PageInfo dealEntities = dealService.subReward(ufilter, pageNum, pageSize);
            return new ResponseResult(ResponseMessage.OK, dealEntities);
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 交易
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/transaction/deal")
    public ResponseResult deal(Dfilter dfilter) {
        if (dfilter.getId() != null) {
            AccountEntity accountEntity = userService.userAccount(dfilter.getId());
            int result = dealService.deal(dfilter, accountEntity);
            if (result == -5) {
                return new ResponseResult(ResponseMessage.SYSTEM_TIME_FAIL);
            }
            if (result == -5) {
                return new ResponseResult(ResponseMessage.SINGLE_SAIL_FAIL);
            }
            if (result == -8) {
                return new ResponseResult(ResponseMessage.ACCOUNT_FROZEN_FAIL);
            }
            if (result == -6) {
                return new ResponseResult(ResponseMessage.BANLANCE_FAIL);
            }
            if (result == -7) {
                return new ResponseResult(ResponseMessage.CANCLE_FAIL);
            }
            if (result == -4) {
                return new ResponseResult(ResponseMessage.UNFINISHED_ORDER_FIAL);
            }
            if (result == -1) {
                return new ResponseResult(ResponseMessage.BASE_INCOMPLETE_INFORMATION);
            }
            if (result == -3) {
                return new ResponseResult(ResponseMessage.PAYPWD_FIAL);
            }
            if (result == -2) {
                return new ResponseResult(ResponseMessage.OK, "充值成功!");
            }
            if (result == -9) {
                return new ResponseResult(ResponseMessage.RECHARGE_FAIL);
            }
            if (result > 0) {
                return new ResponseResult(ResponseMessage.OK, "订单匹配中!");
            }
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 订单状态修改
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/myDeal/orderStatus")
    public ResponseResult orderStatus(Dfilter dfilter) {
        if (dfilter.getOid() != null) {
            int result = dealService.updateOrder(dfilter);
            if (result > 0) {
                return new ResponseResult(ResponseMessage.OK, true);
            }
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 交易订单状态修改
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/myDeal/updateDealStatus")
    public ResponseResult updateDealStatus(Dfilter dfilter) {
        if (dfilter.getDid() != null) {
            int result = dealService.updateDealStatus(dfilter);
            if (result > 0) {
                return new ResponseResult(ResponseMessage.OK, true);
            }
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 订单详情查询
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/myDeal/orderInfo")
    public ResponseResult orderInfo(Dfilter dfilter) {
        if (dfilter.getOid() != null) {
            OrderEntity result = dealService.orderInfo(dfilter);
            return new ResponseResult(ResponseMessage.OK, result);
        }
//        else {
//            OrderListEntity orderListEntity = dealService.getOrderList(dfilter);
//            return new ResponseResult(ResponseMessage.OK,orderListEntity);
//        }
        return new ResponseResult(ResponseMessage.FAIL);
    }

}
