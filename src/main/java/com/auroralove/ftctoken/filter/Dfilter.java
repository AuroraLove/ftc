package com.auroralove.ftctoken.filter;

import lombok.Data;
/**
 * 交易过滤器
 *
 * @author zyu
 * @date 2019/1/24
 */
@Data
public class Dfilter{

    /**
     * 用户id
     */
    private Long id;

    /**
     * 单价
     */
    private Double univalent;

    /**
     * 数量
     */
    private Double quantity;

    /**
     * 总价
     */
    private Double amount;

    /**
     * 交易标识，0为买，1为卖，2注册充值
     */
    private Integer dealType;

    /**
     * 订单id
     */
    private Long oid;

    /**
     * 交易单号id
     */
    private Long did;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户实名
     */
    private String userName;

    /**
     * 订单状态，4待支付，5已支付未确认收款，6确认收款，订单完成，8订单冻结，9撤销
     */
    private Integer orderStatus;

    /**
     * 订单状态，9撤销
     */
    private Integer dealStatus;

    /**
     * 交易方式，20银行卡，21支付宝，22微信支付
     */
    private Integer payWay;

    /**
     * 支付密码
     */
    private String payPwd;
}
