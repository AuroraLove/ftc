package com.auroralove.ftctoken.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zyu
 * @date 2019/2/21
 */
@Data
public class TeamAmount {

    @Autowired
    private Double teamRechargeAmount;

    @Autowired
    private Double teamRewardAmount;

    public TeamAmount() {
        this.teamRechargeAmount = 0.0;
        this.teamRewardAmount = 0.0;
    }
}
