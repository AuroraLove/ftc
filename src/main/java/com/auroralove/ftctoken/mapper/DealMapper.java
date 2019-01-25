package com.auroralove.ftctoken.mapper;

import com.auroralove.ftctoken.model.DealModel;

import java.util.List;

/**
 * @author zyu
 * @date 2019/1/25
 */
public interface DealMapper {

    /**
     * 新增交易记录
     * @param dealModel
     * @return
     */
    int newDealRecord(DealModel dealModel);

    List<DealModel> getPurchaseDeals();

    List<DealModel> getSellDeals();
}
