package com.auroralove.ftctoken.model;

import lombok.Data;

import java.util.List;

/**
 * @author zyu
 * @date 2019/1/27
 */
@Data
public class OrderEntity extends OrderModel{

    /**
     * 支付类型
     */
    List<UserPayModel> payInfo;
}
