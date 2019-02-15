package com.auroralove.ftctoken.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 短信验证信息模型
 * @author zyu
 * @date 2019/2/15
 */
@Data
public class VeritifyModel {

    /**
     * 验证码id
     */
    private Long vid;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 验证码
     */
    private Integer code;

    /**
     * 生成验证码时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date newDate;

    /**
     * 验证码失效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lossDate;
}
