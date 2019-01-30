package com.auroralove.ftctoken.filter;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户支付过滤器
 *
 * @author zyu
 * @date 2019/1/29
 */
@Data
public class PayFilter {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 银行卡号
     */
    private String bankCard;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 支付宝账号
     */
    private String aliPayAcct;

    /**
     * 支付宝二维码
     */
    private MultipartFile aliPayCode;

    /**
     * 微信支付账号
     */
    private String wechatPayAcct;

    /**
     * 微信支付二维码
     */
    private MultipartFile wehatPayCode;

    /**
     * 用户实名
     */
    private String userName;

    /**
     * 用户手机号
     */
    private String userPhone;

    /**
     * 银行地址
     */
    private String bankAddress;
}
