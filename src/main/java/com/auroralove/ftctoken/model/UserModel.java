package com.auroralove.ftctoken.model;

import java.sql.Date;

/**
 * 用户实体模型
 *
 * @author zyu
 * @date 2019/1/22
 */
public class UserModel {

    /**
     *  用户id
     */
    private Long id;

    /**
     * 用户手机号
     */
    private Integer phone;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户密码
     */
    private String passWord;

    /**
     * 管理级别
     */
    private Integer amdinStatus;

    /**
     * 邀请人id
     */
    private Long parentId;

    /**
     * 注册时间
     */
    private Date registTime;

    /**
     *  我的团队id
     */
    private Long teamId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}