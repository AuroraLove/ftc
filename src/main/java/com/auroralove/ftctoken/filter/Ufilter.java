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
    
    /**
     * 支付密码
     */
    private String pay_pwd;

    private Double amount;

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
    

}
