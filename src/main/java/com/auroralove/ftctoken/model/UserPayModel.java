package com.auroralove.ftctoken.model;

import com.auroralove.ftctoken.filter.PayFilter;
import lombok.Data;

/**
 * 用户支付信息
 *
 * @author zyu
 * @date 2019/1/27
 */
@Data
public class UserPayModel {

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 用户支付信息记录id
     */
    private Long pid;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 银行卡号id
     */
    private String card_id;

    /**
     * 银行名
     */
    private String bank_name;

    /**
     * 支付宝账户
     */
    private String ali_pay;

    /**
     * 支付宝二维码存储地址
     */
    private String ali_url;

    /**
     * 微信账户
     */
    private String wechat_pay;

    /**
     * 微信二维码存储地址
     */
    private String wechat_url;

    /**
     * 银行地址
     */
    private String bank_address;

    public UserPayModel() {
    }

    public UserPayModel(PayFilter payFilter) {
        this.uid = payFilter.getId();
        this.ali_pay = payFilter.getAliPayAcct();
        this.wechat_pay = payFilter.getWechatPayAcct();
        this.name = payFilter.getUserName();
        this.phone = payFilter.getUserPhone();
        this.card_id = payFilter.getBankCard();
        this.bank_name = payFilter.getBankName();
    }
}
