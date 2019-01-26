package com.auroralove.ftctoken.mapper;

import com.auroralove.ftctoken.model.DealModel;
import com.auroralove.ftctoken.model.UserModel;
import org.apache.ibatis.annotations.Param;

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
     * @param password
     * @return Result<int>
     */
    int changePayPwd(@Param("id") Long id,@Param("pay_pwd")String pay_pwd);
    
}
