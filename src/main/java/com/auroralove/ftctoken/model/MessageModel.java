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
public class MessageModel implements Comparable<MessageModel>{

    /**
     * 留言id
     */
    private Long id;

    /**
     * 留言记录id
     */
    private Long tid;

    /**
     * 用户id
     */
    private Long uid;
    /**
     * 留言信息
     */
    private String leavingMsg;

    /**
     * 留言凭证地址
     */
    private String leavingPictureUrl;

    /**
     * 回复留言
     */
    private String replayMsg;
    /**
     * 留言时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date leavingDate;
    /**
     * 回复时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date replayDate;

    /**
     * 回复标识，0未回复，1回复
     */
	private Integer replayFlag;

	private String phone;

    @Override
    public int compareTo(MessageModel o) {
        return o.getLeavingDate().compareTo(this.leavingDate);
    }
}
