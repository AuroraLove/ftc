package com.auroralove.ftctoken.model;

import lombok.Data;

/**
 * @author zyu
 * @date 2019/2/22
 */
@Data
public class SystemLevelModel {

    /**
     * 记录id
     */
    private Long lid;

    /**
     * 系统奖励级别
     */
    private Integer level;

    /**
     * 系统奖励金额
     */
    private Double amount;
}
