package com.auroralove.ftctoken.entity;

import com.auroralove.ftctoken.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zyu
 * @date 2019-1-22  20:43
 */
public class UserEntity {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户手机号
     */
    private Integer phone;

    /**
     * 用户密码
     */
    private String passWord;

    /**
     * 用户验证token
     */
    private String token;

    /**
     * 我的团队id
     */
    private Long teamId;

    /**
     * 管理员基本，0，超级管理员，1，普通管理员，2，无管理级别
     */
    private Integer amdinStatus;

    /**
     * 用户上级邀请人id
     */
    private Long parentId;

    /**
     * App系统版本
     */
    private String version;

    /**
     * FTC锁仓额度
     */
    private Double FTCLockedAcct;

    /**
     * FTC总奖励额度
     */
    private Double FTCRewardAcct;

    /**
     * 可交易额度
     */
    private Double tradeableAcct;

    /**
     * 公共信息查询
     */
    private String publicInfo;

    /**
     * FTC价格
     */
    private Double FTCPrice;

    /**
     * FTC系统锁仓额度
     */
    private Double FTCSystemAcct;

    /**
     * EOS-FTC价格比
     */
    private Map<String, List<Map<String,Double>>> EOSPrice;

    public UserEntity() {

    }

    public UserEntity(UserModel userModel) {
        this.id = userModel.getId();
        this.phone = userModel.getPhone();
        this.amdinStatus = userModel.getAmdinStatus();
        this.teamId = userModel.getTeamId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Integer getAmdinStatus() {
        return amdinStatus;
    }

    public void setAmdinStatus(Integer amdinStatus) {
        this.amdinStatus = amdinStatus;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Double getFTCLockedAcct() {
        return FTCLockedAcct;
    }

    public void setFTCLockedAcct(Double FTCLockedAcct) {
        this.FTCLockedAcct = FTCLockedAcct;
    }

    public Double getFTCRewardAcct() {
        return FTCRewardAcct;
    }

    public void setFTCRewardAcct(Double FTCRewardAcct) {
        this.FTCRewardAcct = FTCRewardAcct;
    }

    public Double getTradeableAcct() {
        return tradeableAcct;
    }

    public void setTradeableAcct(Double tradeableAcct) {
        this.tradeableAcct = tradeableAcct;
    }

    public String getPublicInfo() {
        return publicInfo;
    }

    public void setPublicInfo(String publicInfo) {
        this.publicInfo = publicInfo;
    }

    public Double getFTCPrice() {
        return FTCPrice;
    }

    public void setFTCPrice(Double FTCPrice) {
        this.FTCPrice = FTCPrice;
    }

    public Double getFTCSystemAcct() {
        return FTCSystemAcct;
    }

    public void setFTCSystemAcct(Double FTCSystemAcct) {
        this.FTCSystemAcct = FTCSystemAcct;
    }

    public Map<String, List<Map<String, Double>>> getEOSPrice() {
        return EOSPrice;
    }

    public void setEOSPrice(Map<String, List<Map<String, Double>>> EOSPrice) {
        this.EOSPrice = EOSPrice;
    }
}