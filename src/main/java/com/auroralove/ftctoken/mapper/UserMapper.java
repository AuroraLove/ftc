package com.auroralove.ftctoken.mapper;

import com.auroralove.ftctoken.model.*;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zyu
 * @date 2019-1-22  20:44
 */
public interface UserMapper {

    /**
     * 通过手机号查找用户
     *
     * @param phone phone
     * @return Result<UserModel>
     */
    UserModel findByUserphone(@Param("phone") String phone);

    /**
     * 通过手机号查找用户
     *
     * @param id id
     * @return Result<UserModel>
     */
    UserModel findUserById(@Param("id") Long id);

    /**
     * 新增用户
     *
     * @param userModel userModel
     * @return Result<int>
     */
    int newUser(UserModel userModel);

    /**
     * 账户充值
     *
     * @param dealModel
     * @return Result<int>
     */
    int recharge(DealModel dealModel);

    /**
     * 增加充值标识
     *
     * @param id
     * @param flag
     * @return Result<int>
     */
    int rechargeFlag(@Param("id") Long id,@Param("flag")Integer flag);
    
    /**
     * 修改登陆密码
     *
     * @param phone
     * @param password
     * @return Result<int>
     */
    int changeLoginPwd(@Param("phone") String phone,@Param("password")String password);
    /**
     * 修改支付密码
     *
     * @param id
     * @param payPwd
     * @return Result<int>
     */
    int changePayPwd(@Param("id") Long id,@Param("payPwd")String payPwd);

    /**
     * 获取支付信息
     * @param uid
     * @return
     */
    UserPayModel getPayInfo(@Param("uid") Long uid);

    /**
     * 返回用户账户信息详情
     * @param uid
     * @return
     */
    AccountModel getDealAccountInfo(Long uid);

    /**
     * 返回用户账户奖励金额信息详情
     * @param id
     * @return
     */
    AccountModel getRewardAccount(Long id);
    /**
     * 上传留言
     *
     * @param id
     * @param message
     * @return Result<int>
     */
    int uploadMsg(@Param("id")Long id,@Param("uid") Long uid,@Param("message")String message,@Param("url")String url);
    /**
     * 回复留言
     *
     * @param message
     * @return Result<int>
     */
    int replayMsg(@Param("mesId") Long mesId,@Param("message") String message);

    /**
     * 查询留言
     *
     * @param uid
     * @return Result<UserModel>
     */
    List<MessageModel> messageInfo(Long uid);

    /**
     * 存储用户支付信息
     *
     * @param payModel
     * @return Result<UserModel>
     */
    int savePayInfo(UserPayModel payModel);

    /**
     * 取团队信息
     *
     * @param uid
     * @return Result<UserModel>
     */
    List<UserModel> getUserChilds(@Param("uid") Long uid);

    /**
     * 更新用户资料
     *
     * @param payModel
     * @return Result<UserModel>
     */
    int updatePayInfo(UserPayModel payModel);

    /**
     * 更新用户交易密码
     * @param id
     * @param payPwd
     * @return
     */
    int updatePayPwd(@Param("id") Long id, @Param("payPwd") String payPwd);
}
