package com.auroralove.ftctoken.entity;

import com.auroralove.ftctoken.model.MessageModel;
import com.auroralove.ftctoken.model.UserModel;

import java.util.List;
import java.util.Map;

/**
 * 实体业务模型，用于返回值
 * @author zyu
 * @date 2019-1-22  20:43
 */
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
    private String leavingDate;
    /**
     * 回复时间
     */
    private String replayDate;
    
	public String getLeavingMsg() {
		return leavingMsg;
	}

	public void setLeavingMsg(String leavingMsg) {
		this.leavingMsg = leavingMsg;
	}

	public String getReplayMsg() {
		return replayMsg;
	}

	public void setReplayMsg(String replayMsg) {
		this.replayMsg = replayMsg;
	}

	public String getLeavingDate() {
		return leavingDate;
	}

	public void setLeavingDate(String leavingDate) {
		this.leavingDate = leavingDate;
	}

	public String getReplayDate() {
		return replayDate;
	}

	public void setReplayDate(String replayDate) {
		this.replayDate = replayDate;
	}

    public MessageEntity() {

    }

    public MessageEntity(MessageModel messageModel) {
        this.leavingMsg = messageModel.getMsg();
        this.leavingDate = messageModel.getLeaving_date();
        this.replayDate = messageModel.getReplay_date();
        this.replayMsg = messageModel.getR_msg();
    }


    
}
