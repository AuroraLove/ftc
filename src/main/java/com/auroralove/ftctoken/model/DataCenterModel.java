package com.auroralove.ftctoken.model;

import lombok.Data;

/**
 * 数据中心
 * @author zyu
 * @date 2019/2/20
 */
@Data
public class DataCenterModel {

    /**
     * 当日注册总金额
     */
    private Double dayRegistAmount;

    /**
     *当日释放总金额
     */
    private Double dayReleaseAmount;

    /**
     *冻结订单数
     */
    private Integer totalFrozenOrder;

    /**
     *待回复消息数
     */
    private Integer totalMessage;

    /**
     *总充值（FTC）
     */
    private Double totalRegistAmount;

    /**
     *未释放（FTC）
     */
    private Double totalUnReleaseAmount;

    /**
     *释放（FTC）
     */
    private Double totalReleaseAmount;

    /**
     *可交易（FTC）
     */
    private Double totalTrableAmount;

    /**
     *总奖励（FTC）
     */
    private Double totalReward;

    /**
     *系统总金额
     */
    private Double systemTotalAmount;

    public DataCenterModel(Double dayRegistAmount, Double dayReleaseAmount, Integer totalFrozenOrder, Integer totalMessage, Double totalRegistAmount, Double totalUnReleaseAmount, Double totalReleaseAmount, Double totalTrableAmount, Double totalReward, Double systemTotalAmount) {
        this.dayRegistAmount = dayRegistAmount;
        this.dayReleaseAmount = dayReleaseAmount;
        this.totalFrozenOrder = totalFrozenOrder;
        this.totalMessage = totalMessage;
        this.totalRegistAmount = totalRegistAmount;
        this.totalUnReleaseAmount = totalUnReleaseAmount;
        this.totalReleaseAmount = totalReleaseAmount;
        this.totalTrableAmount = totalTrableAmount;
        this.totalReward = totalReward;
        this.systemTotalAmount = systemTotalAmount;
    }

    public DataCenterModel() {
        this.dayRegistAmount = 0.0;
        this.dayReleaseAmount = 0.0;
        this.totalFrozenOrder = 0;
        this.totalMessage = 0;
        this.totalRegistAmount = 0.0;
        this.totalUnReleaseAmount = 0.0;
        this.totalReleaseAmount = 0.0;
        this.totalTrableAmount = 0.0;
        this.totalReward = 0.0;
        this.systemTotalAmount = 0.0;
    }
}
