package com.auroralove.ftctoken.entity;

import com.auroralove.ftctoken.model.MessageModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 实体业务模型，用于返回值
 * @author zyu
 * @date 2019-1-22  20:43
 */
@Data
public class MessageEntity {

    /**
     * 留言信息
     */
    private String leavingMsg;
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

    private String leavingPictureUrl;

    public MessageEntity() {

    }


    public MessageEntity(MessageModel messageModel) {
        this.leavingMsg = messageModel.getLeavingMsg() == null ? "":messageModel.getLeavingMsg();
        this.leavingDate = messageModel.getLeavingDate();
        this.replayDate = messageModel.getReplayDate();
        this.replayMsg = messageModel.getReplayMsg() == null ? "":messageModel.getReplayMsg();
        this.leavingPictureUrl = messageModel.getLeavingPictureUrl();

    }



}
