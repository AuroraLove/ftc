package com.auroralove.ftctoken.result;

public enum ResponseMessage {

	/**
	 * 成功
	 */
	OK(200,"成功!"),

	/**
	 * 操作失败
	 */
	FAIL(500, "操作失败!"),

	/**
	 * 用户未提交Authorization Header信息进行身份认证
	 */
	MISSING_AUTHORIZATION_HEADER(450, "用户未提交Authorization Header信息进行身份认证!"),

	/**
	 * 用户身份认证失败，禁止访问
	 */
	AUTHORIZED_ERROR(450, "用户身份认证失败，禁止访问!"),

	/**
	 * 用户令牌无效，禁止访问
	 */
	TOKEN_INVALIDATION(450, "用户令牌无效，禁止访问!"),

	/**
	 * 当前账户被锁定，禁止访问
	 */
	LOCKED_ACCOUNT(450,"当前账户被锁定，禁止访问!"),

	/**
	 * 用户权限不足，禁止访问
	 */
	INADEQUATE_PERMISSIONS(450,"用户权限不足，禁止访问!"),

	/**
	 * 系统内部错误
	 */
	INTERNAL_SERVER_ERROR(500, "系统内部错误!"),

	/**
	 * 请求参数错误
	 */
	PARAMETER_ERROR(501, "请求参数错误!"),

	/**
	 * 保存失败
	 */
	SAVE_FAIL(502,"保存失败!"),

	/**
	 * 删除失败
	 */
	DELETE_FAIL(503,"删除失败"),

	/**
	 * 找不到数据
	 */
	DATA_NOT_FOUND(504, "找不到数据!"),

	/**
	 * 资源名称重复
	 */
	NAME_REPETITION(505, "资源名称重复!"),

	/**
	 * 英文名称重复
	 */
	E_NAME_REPETITION(506, "英文名称重复!"),

	/**
	 * 版本号重复
	 */
	VERSION_NAME_REPETITION(507, "版本号重复!"),

	/**
	 * 基本信息不完整
	 */
	BASE_INCOMPLETE_INFORMATION(508, "基本信息不完整!"),
	
	/**
	 * 版本信息不完整
	 */
	VERSION_INCOMPLETE_INFORMATION(509, "版本信息不完整!"),

	/**
	 * 文件夹名称重复
	 */
	FOLDER_NAME_REPETITION(510, "文件夹名称重复!"),
	
	/**
	 * 名称重复
	 */
	TITLE_NAME_REPETITION(511, "名称重复!"),

	/**
	 * 操作太频繁
	 */
	FREQUENT_FEEDBACK(512, "操作太频繁，请五分钟后再提交!"),

	/**
	 * 信息为空
	 */
	CONTENT_INFORMATION(513, "信息不完整!"),

	/**
	 * 废弃失败
	 */
	ABANDON_FAIL(514, "废弃失败!"),
	
	/**
	 * 上传失败
	 */
	UPLOAD_FAIL(515, "上传失败!"),
	
	/**
	 * 有未通过的版本
	 */
	UNPASS_VERSION(516, "有未通过的版本!"),

	/**
	 * 注册手机号已存在
	 */
	PHONE_EXITS(517,"注册手机号已存在"),

	/**
	 * 更新用户交易资料成功!
	 */
	USERDATA_UPDATE_SUCCESS(200,"更新用户交易资料成功!"),

	/**
	 * 更新用户资料失败
	 */
	USERDATA_FAIL(519,"更新用户资料失败");

	private final int status;
	
	private final String message;
	
	ResponseMessage(int status, String message){
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
	
}
