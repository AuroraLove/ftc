package com.auroralove.ftctoken.mapper;

import com.auroralove.ftctoken.entity.UserEntity;
import com.auroralove.ftctoken.model.AccountModel;
import com.auroralove.ftctoken.model.DealModel;
import com.auroralove.ftctoken.model.MessageModel;
import com.auroralove.ftctoken.model.UserModel;
import com.auroralove.ftctoken.model.UserPayModel;

import org.apache.ibatis.annotations.Param;
import org.w3c.dom.Text;

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
     * @param id
     * @param password
     * @return Result<int>
     */
    int changeLoginPwd(@Param("id") Long id,@Param("password")String password);
    /**
     * 修改支付密码
     *
     * @param id
     * @param pay_pwd
     * @return Result<int>
     */
    int changePayPwd(@Param("id") Long id,@Param("pay_pwd")String pay_pwd);

    /**
     * 获取支付信息
     * @param buyer_id
     * @return
     */
    List<UserPayModel> getPayInfo(Long buyer_id);

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
     * @param id
     * @param message
     * @return Result<int>
     */
    int replayMsg(@Param("mesId") Long mesId,@Param("message") String message);

    /**
     * 查询留言
     *
     * @param id
     * @param message
     * @return Result<UserModel>
     */
    MessageModel messageInfo(Long uid);

    /**
     * 存储用户支付信息
     *
     * @param payModel
     * @return Result<UserModel>
     */
    int savePayInfo(UserPayModel payModel);
}
