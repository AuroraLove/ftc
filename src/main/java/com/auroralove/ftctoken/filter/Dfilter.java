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
     * 交易标识，0为买，1为卖，2注册充值
     */
    private Integer trctFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getUnivalent() {
		return univalent;
	}

	public void setUnivalent(Double univalent) {
		this.univalent = univalent;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Integer getTrctFlag() {
		return trctFlag;
	}

	public void setTrctFlag(Integer trctFlag) {
		this.trctFlag = trctFlag;
	}

}
