package com.auroralove.ftctoken.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 用户实体模型，与数据库字段相对应
 *
 * @author zyu
 * @date 2019/1/22
 */
@Data
public class MessageModel {

    /**
     * 留言信息
     */
    private String msg;
    /**
     * 回复留言
     */
    private String r_msg;
    /**
     * 留言时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date leaving_date;
    /**
     * 回复时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date replay_date;

    /**
     * 回复标识，0未回复，1回复
     */
	private Integer replay_flag;

	private String phone;

}
