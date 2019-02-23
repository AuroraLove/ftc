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
    AccountModel getTradableAccountInfo(@Param("uid")Long uid);

    /**
     * 获取用户买单总金额
     * @param uid
     * @return
     */
    AccountModel getBuyAmount(@Param("uid")Long uid);
    /**
     * 获取用户卖单金额
     * @param uid
     * @return
     */
    AccountModel getSellAmount(@Param("uid")Long uid);
    /**
     * 获取用户注册金额
     * @param uid
     * @return
     */
    AccountModel getRegistAmount(@Param("uid")Long uid);

    /**
     * 获取用户系统金额
     * @param uid
     * @return
     */
    AccountModel getSystemAmount(@Param("uid")Long uid);
    /**
     * 获取用户释放金额
     * @param uid
     * @return
     */
    AccountModel getReleaseAmount(@Param("uid")Long uid);

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
     * @param phone
     * @param message
     * @return Result<int>
     */
    int uploadMsg(@Param("id") Long id, @Param("phone")String phone, @Param("uid") Long uid, @Param("message") String message, @Param("url") String url);
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

    /**
     * 获取公告信息
     * @return
     */
    List<PublicInfoModel> getPublicMsg();

    /**
     * 新增公告信息
     * @param id
     * @param payPwd
     * @return
     */
    int newPublicMsg(String information);

    /**
     * 获取公告信息
     * @param id
     * @param payPwd
     * @return
     */
    int deletePublicMsg(Long pid);

    /**
     * 获取短信验证码信息
     * @param id
     * @param payPwd
     * @return
     */
    VeritifyModel getVeritifyInfo(String phone);

    /**
     * 更新验证码
     * @param id
     * @param payPwd
     * @return
     */
    int updateVeritifyInfo(@Param("phone")String phone,@Param("code") Integer code);

    /**
     * 新增验证码
     * @param id
     * @param payPwd
     * @return
     */
    int newVeritifyInfo(@Param("phone")String phone,@Param("code") Integer code);

    /**
     * 更新用户资料
     * @param id
     * @param payPwd
     * @return
     */
    int updateUserInfo(UserModel userModel);

    /**
     * 冻结账户
     * @param id
     * @param payPwd
     * @return
     */
    int frozenUser(@Param("id") Long id,@Param("accountStatus") Integer accountStatus);

    /**
     * 获取留言列表
     * @param id
     * @param payPwd
     * @return
     */
    List<MessageModel> getMessages();

    /**
     * 获取团队充值总金额
     * @param id
     * @param payPwd
     * @return
     */
    TeamAmount getTeamAmount(@Param("ids")List<Long> ids);

    /**
     * 更新用户团队人数
     * @param parentId
     * @return
     */
    int updateTeamTotal(@Param("id")Long id);

    /**
     * 取用户列表总数
     * @param parentId
     * @return
     */
    List<UserModel> getUsers();
}
