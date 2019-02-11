package com.auroralove.ftctoken.filter;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
    
    /**
     * 支付密码
     */
    private String payPwd;
    
    /**
     * 留言id
     */
    private Long mesId; 
    /**
     * 留言信息
     */
    private String message;	
    
    /**
     * 留言凭证
     */
    private MultipartFile picture;
    
    /**
     * 留言类型,11留言，12回复留言
     */
    private Integer type;

    /**
     * 总价
     */
    private Double amount;

    /**
     * 图片地址
     */
    private String pictureUrl;

    public Ufilter(Long childId, String childPhone) {
    	this.id = childId;
    	this.phone = childPhone;
    }
}
