package com.auroralove.ftctoken.model;

import lombok.Data;

/**
 * @author zyu
 * @date 2019/1/28
 */
@Data
public class SystemModel {

    /**
     * 系统参数EOS:FTC,EOS数值
     */
    private Double EOS;

    /**
     * 系统参数EOS:FTC,FTC数值
     */
    private Double FTC;

    /**
     * FTC系统设定锁仓额度
     */
    private Double FTCLocked;

    /**
     * FTC当日价格
     */
    private Double FTCPrice;

    /**
     * FTC买卖单交易单价
     */
    private Double univalent;

    /**
     * FTC买卖单交易数量
     */
    private Double quantity;

    /**
     * 系统控制用户邀请级别
     */
    private Integer userLevel;

    /**
     * 版本信息
     */
    private String version;

    /**
     * 系统公共信息
     */
    private String publicInfo;
}
