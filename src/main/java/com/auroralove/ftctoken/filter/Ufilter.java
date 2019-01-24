package com.auroralove.ftctoken.filter;

import lombok.Data;

/**
 * 用户访问过滤器
 *
 * @author zyu
 * @date 2019/1/22
 */
@Data
public class Ufilter {

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
     * 用户访问token
     */
    private String token;

    /**
     * 手机验证码
     */
    private Integer code;

    /**
     *  我的团队id
     */
    private Long teamId;

    private Double amount;

}
