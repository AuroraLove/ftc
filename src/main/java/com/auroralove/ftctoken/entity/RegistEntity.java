package com.auroralove.ftctoken.entity;

import com.auroralove.ftctoken.model.TotalInfoModel;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author zyu
 * @date 2019/2/20
 */
@Data
public class RegistEntity extends PageInfo{

    private Integer rechargeTotal;

    private Integer regitTotal;

    private PageInfo dealList;

    public RegistEntity(TotalInfoModel totalInfoModel, PageInfo page) {
        this.rechargeTotal = totalInfoModel.getRechargeTotal();
        this.regitTotal = totalInfoModel.getRegitTotal();
        this.dealList = page;
    }
}
