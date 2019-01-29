package com.auroralove.ftctoken.entity;

import com.auroralove.ftctoken.model.OrderModel;
import com.auroralove.ftctoken.model.UserPayModel;
import lombok.Data;

import java.util.List;

/**
 * @author zyu
 * @date 2019/1/27
 */
@Data
public class OrderEntity extends OrderModel {

    /**
     * 出售人实名
     */
    private String sellerName;

    /**
     * 买方人实名
     */
    private String buyerName;

    /**
     * 支付类型
     */
    private List<UserPayModel> payInfo;
}
