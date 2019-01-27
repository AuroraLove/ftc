package com.auroralove.ftctoken.model;



/**
 * 用户实体模型，与数据库字段相对应
 *
 * @author zyu
 * @date 2019/1/22
 */
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
    private String leaving_date;
    /**
     * 回复时间
     */
    private String replay_date;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getR_msg() {
		return r_msg;
	}
	public void setR_msg(String r_msg) {
		this.r_msg = r_msg;
	}
	public String getLeaving_date() {
		return leaving_date;
	}
	public void setLeaving_date(String leaving_date) {
		this.leaving_date = leaving_date;
	}
	public String getReplay_date() {
		return replay_date;
	}
	public void setReplay_date(String replay_date) {
		this.replay_date = replay_date;
	}
    
	
}
