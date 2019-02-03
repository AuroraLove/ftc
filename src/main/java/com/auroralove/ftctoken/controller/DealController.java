package com.auroralove.ftctoken.controller;

import com.auroralove.ftctoken.entity.AssetEntity;
import com.auroralove.ftctoken.entity.RecordEntity;
import com.auroralove.ftctoken.filter.Dfilter;
import com.auroralove.ftctoken.filter.Ufilter;
import com.auroralove.ftctoken.entity.OrderEntity;
import com.auroralove.ftctoken.result.ResponseMessage;
import com.auroralove.ftctoken.result.ResponseResult;
import com.auroralove.ftctoken.service.DealService;
import com.auroralove.ftctoken.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     * 可交易资产查询
     * @param
     * @return
     */
    @PostMapping("/home/commutableAssets")
    public ResponseResult commutableAssets(Dfilter dfilter, @RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize){
        if (dfilter.getId() != null){
            PageInfo<AssetEntity> result = dealService.commutableAssets(dfilter,pageNum,pageSize);
            return new ResponseResult(ResponseMessage.OK,result);
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
            PageInfo<RecordEntity> dealEntities = dealService.subReward(ufilter,pageNum,pageSize);
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
            if (result > 0){
                return new ResponseResult(ResponseMessage.OK,"订单匹配中");
            }
        }
        return new ResponseResult(ResponseMessage.FAIL,"系统出错");
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
     * 订单详情查询
     * @param
     * @return
     */
    @PostMapping("/myDeal/orderInfo")
    public ResponseResult orderInfo(Dfilter dfilter){
        if (dfilter.getDid() != null){
            OrderEntity result = dealService.orderInfo(dfilter);
            return new ResponseResult(ResponseMessage.OK,result);
        }
        return new ResponseResult(ResponseMessage.FAIL,false);
    }

}
