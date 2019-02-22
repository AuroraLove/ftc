package com.auroralove.ftctoken.entity;

import com.auroralove.ftctoken.model.UserModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TeamEntity {

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 当前直销级别
     */
    private Integer level;

    /**
     * 当前直销用户数
     */
    private Long count;

    /**
     * 记录总人数
     */
    private Long total ;

    /**
     * 子用户
     */
    private List<TeamEntity> childs;

    /**
     * 团队用户id
     */
    private List<Long> ids ;

    public TeamEntity(UserModel userModel) {
        this.phone = userModel.getPhone();
        this.uid = userModel.getId();
    }

    public TeamEntity(){
        this.ids = new ArrayList<>();
    }

}
