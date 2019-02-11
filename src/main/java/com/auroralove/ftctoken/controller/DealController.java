package com.auroralove.ftctoken.controller;

import com.auroralove.ftctoken.entity.OrderEntity;
import com.auroralove.ftctoken.filter.Dfilter;
import com.auroralove.ftctoken.filter.Ufilter;
import com.auroralove.ftctoken.result.DealResult;
import com.auroralove.ftctoken.result.ResponseMessage;
import com.auroralove.ftctoken.result.ResponseResult;
import com.auroralove.ftctoken.service.DealService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 交易记录查询
     * @param
     * @return
     */
    @PostMapping("/home/dealRecord")
    public ResponseResult dealRecord(Dfilter dfilter, @RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize){
        if (dfilter.getId() != null){
            PageInfo result = dealService.getDealRecord(dfilter,pageNum,pageSize);
            //设置未完成订单数
            DealResult responseResult = new DealResult(ResponseMessage.OK, result);
            responseResult.setUnfinished(dealService.getUnfinishedDeal(dfilter.getId()));
            return responseResult;
        }
        return new ResponseResult(ResponseMessage.FAIL,"系统出错");
    }

    /**
     * 分佣奖励
     * @param
     * @return
     */
    @PostMapping("/home/rewardRecord")
    public ResponseResult rewardRecord(Ufilter ufilter,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize){
        if (ufilter.getId() != null){
            PageInfo dealEntities = dealService.subReward(ufilter,pageNum,pageSize);
            return new ResponseResult(ResponseMessage.OK,dealEntities);
        }
        return new ResponseResult(ResponseMessage.FAIL,"系统出错");
    }

    /**
     * 交易
     * @param
     * @return
     */
    @PostMapping("/transaction/deal")
    public ResponseResult deal(Dfilter dfilter){
        if (dfilter.getId() != null){
            int result = dealService.deal(dfilter);
            if (result == -5){
                return new ResponseResult(ResponseMessage.FAIL,"每天仅可挂卖一次!");
            }
            if (result == -6){
                return new ResponseResult(ResponseMessage.FAIL,"您没有足够可交易的FTC!");
            }
            if (result == -4){
                return new ResponseResult(ResponseMessage.FAIL,"您还有未完成的订单!");
            }
            if (result == -1){
                return new ResponseResult(ResponseMessage.FAIL,"您的交易资料不完整!");
            }
            if (result == -3){
                return new ResponseResult(ResponseMessage.FAIL,"支付密码错误!");
            }
            if (result == -2){
                return new ResponseResult(ResponseMessage.OK,"充值成功!");
            }
            if (result > 0){
                return new ResponseResult(ResponseMessage.OK,"订单匹配中!");
            }
        }
        return new ResponseResult(ResponseMessage.FAIL,"系统出错!");
    }

    /**
     * 订单状态修改
     * @param
     * @return
     */
    @PostMapping("/myDeal/orderStatus")
    public ResponseResult orderStatus(Dfilter dfilter){
        if (dfilter.getOid() != null){
            int result = dealService.updateOrder(dfilter);
            if (result > 0){
                return new ResponseResult(ResponseMessage.OK,true);
            }
        }
        return new ResponseResult(ResponseMessage.FAIL,false);
    }

    /**
     * 交易订单状态修改
     * @param
     * @return
     */
    @PostMapping("/myDeal/updateDealStatus")
    public ResponseResult updateDealStatus(Dfilter dfilter){
        if (dfilter.getDid() != null && dfilter.getDealStatus() != null){
            int result = dealService.updateDealStatus(dfilter);
            if (result > 0){
                return new ResponseResult(ResponseMessage.OK,true);
            }
        }
        return new ResponseResult(ResponseMessage.FAIL,false);
    }


    /**
     * 订单详情查询
     * @param
     * @return
     */
    @PostMapping("/myDeal/orderInfo")
    public ResponseResult orderInfo(Dfilter dfilter){
        if (dfilter.getOid() != null){
            OrderEntity result = dealService.orderInfo(dfilter);
            return new ResponseResult(ResponseMessage.OK,result);
        }
        return new ResponseResult(ResponseMessage.FAIL,false);
    }

}
