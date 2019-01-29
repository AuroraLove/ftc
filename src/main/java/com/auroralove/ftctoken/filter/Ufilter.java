package com.auroralove.ftctoken.filter;


import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Text;

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
    private String pay_pwd;
    
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
     * 留言类型
     */
    private Integer mType;

    private Double amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
    
	public String getPay_pwd() {
		return pay_pwd;
	}

	public void setPay_pwd(String pay_pwd) {
		this.pay_pwd = pay_pwd;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MultipartFile getPicture() {
		return picture;
	}

	public void setPicture(MultipartFile picture) {
		this.picture = picture;
	}

	public Integer getmType() {
		return mType;
	}

	public void setmType(Integer mType) {
		this.mType = mType;
	}

	public Long getMesId() {
		return mesId;
	}

	public void setMesId(Long mesId) {
		this.mesId = mesId;
	}
    
	

}
