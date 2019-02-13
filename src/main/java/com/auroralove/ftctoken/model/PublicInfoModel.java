package com.auroralove.ftctoken.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 公告信息
 * @author zyu
 * @date 2019/2/13
 */
@Data
public class PublicInfoModel {

    /**
     * 公告信息id
     */
    private Long pid;

    /**
     * 公告信息
     */
    private String information;

    /**
     * 公告时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;
}
