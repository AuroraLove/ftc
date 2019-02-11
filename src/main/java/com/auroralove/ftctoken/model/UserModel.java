package com.auroralove.ftctoken.model;

import lombok.Data;

import java.util.Date;

/**
 * 用户实体模型，与数据库字段相对应
 *
 * @author zyu
 * @date 2019/1/22
 */
@Data
public class UserModel {

    /**
     *  用户id
     */
    private Long id;

    /**
     * 用户手机号
     */
    private String phone;

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

    /**
     * 用户是否已充值
     */
    private Integer flag;
    
    /**
     * 支付密码
     */
    private String pay_pwd;

}
