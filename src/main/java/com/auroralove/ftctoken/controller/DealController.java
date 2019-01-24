package com.auroralove.ftctoken.controller;

import com.auroralove.ftctoken.entity.UserEntity;
import com.auroralove.ftctoken.filter.Dfilter;
import com.auroralove.ftctoken.filter.Ufilter;
import com.auroralove.ftctoken.result.ResponseMessage;
import com.auroralove.ftctoken.result.ResponseResult;
import com.auroralove.ftctoken.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 交易大厅
 *
 * @author zyu
 * @date 2019/1/24
 */
public class DealController {

    @Autowired
    private DealService dealService;

    /**
     * 分佣奖励
     * @param
     * @return
     */
    @PostMapping("/home/rewardRecord")
    public ResponseResult team(Ufilter ufilter){
        if (ufilter.getTeamId() != null){
            List<UserEntity> userEntities = dealService.subReward(ufilter);
            return new ResponseResult(ResponseMessage.OK,userEntities);
        }
        return new ResponseResult(ResponseMessage.FAIL,"系统出错");
    }

    /**
     * 买单交易
     * @param
     * @return
     */
    @PostMapping("/home/purchase")
    public ResponseResult purchase(Dfilter dfilter){
        if (ufilter.getTeamId() != null){
            List<UserEntity> userEntities = dealService.subReward(ufilter);
            return new ResponseResult(ResponseMessage.OK,userEntities);
        }
        return new ResponseResult(ResponseMessage.FAIL,"系统出错");
    }

}
