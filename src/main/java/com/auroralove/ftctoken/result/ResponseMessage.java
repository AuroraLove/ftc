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
	AUTHORIZED_ERROR(450, "账号或密码错误！"),

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
	INADEQUATE_PERMISSIONS(450,"您没有管理员权限，禁止访问!"),

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
	BASE_INCOMPLETE_INFORMATION(508," 您尚未绑定交易资料"),
	
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
	CONTENT_INFORMATION(513, "信息不完整"),

	/**
	 * 废弃失败
	 */
	ABANDON_FAIL(514, "废弃失败"),
	
	/**
	 * 上传失败
	 */
	UPLOAD_FAIL(515, "上传失败"),
	
	/**
	 * 有未通过的版本
	 */
	UNPASS_VERSION(516, "有未通过的版本"),

	/**
	 * 注册手机号已存在
	 */
	PHONE_EXITS(517,"注册手机号已存在"),

	/**
	 * 更新用户交易资料成功!
	 */
	USERDATA_UPDATE_SUCCESS(200,"更新用户交易资料成功"),

	/**
	 * 更新用户资料失败
	 */
	USERDATA_FAIL(519,"更新用户资料失败"),

	FAIL_CODE(520,"您的验证码已过期"),

	PAYPWD_FIAL(530,"交易密码错误"),

	UNFINISHED_ORDER_FIAL(530,"您有订单待完成"),

	SMS_UNUSED(521,"您的验证码已过期"),

	SMS_CODE_FAIL(523,"您的验证码输入有误"),

	RECHARGE_FAIL(524,"充值失败"),

	TRADEABLE_FAIL(524,"增加可用余额失败"),

	TRADEABLE_OK(200,"增加可用余额成功"),

	SINGLE_SAIL_FAIL(530,"每日仅可挂卖一次"),

	ADMIN_CANCLE_FAIL(541,"匹配订单状态已更新，请刷新后重试"),

	SYSTEM_TIME_FAIL(539," 交易时间 9：00-21：00"),

	UNRECHARGE_ERROR(539," 请先认购再交易"),

	NETWORK_ERROR(511," 网络异常，订单交易价格与系统不一致，请刷新后重试"),

	ACCOUNT_FROZEN_FAIL(530,"已被冻结七日警告"),

	ACCOUNT_FROZEN_PERMINANT_FAIL(530,"您的账户已被永久冻结"),

	BANLANCE_FAIL(530,"可交易额不足"),

	CANCLE_FAIL(530,"有撤销订单今日限制交易"),

	UNFINISHED_FAIL(530,"您有订单待完成"),

	LEAVING_FAIL(526,"留言失败"),

	UPDATE_FAIL(525,"更新失败"),

	PHONE_ERROR(525,"手机号不存在"),

	USERPASSWORD_FAIL(525,"修改密码失败"),

	REPEATE_ACTION_ORDER(528,"订单状态已更新，请刷新订单后重试"),

	REGIST_OK(200,"注册成功"),

	RECHARGE_OK(200,"充值成功"),

	MISS_TOKEN(888,"缺少token信息"),

	REPEATLOGIN_EXCEPTION(666,"您的账号已在其他地方登陆，请重新登陆"),

	SMS_FAIL(522,"发送短信失败！请重试");


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
