package com.auroralove.ftctoken.entity;

import lombok.Data;

import java.util.List;

@Data
public class RewardRecordEntity {

    private String phone;

    private List<RewardRecordEntity> childs;

}
