package com.auroralove.ftctoken.model;

import lombok.Data;

/**
 * 用户支付信息
 *
 * @author zyu
 * @date 2019/1/27
 */
@Data
public class UserPayModel {

    private Long uid;

    private Long pid;

    private String name;

    private String card_id;

    private String bank_name;

    private String ali_pay;

    private String ali_url;

    private String wechat_pay;

    private String wechat_url;

    private String bank_address;

}
