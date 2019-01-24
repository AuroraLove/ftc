package com.auroralove.ftctoken.mapper;

import com.auroralove.ftctoken.filter.Ufilter;
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
    UserModel findByUserphone(@Param("phone") Integer phone);

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
     * @param uid
     * @param flag
     * @return Result<int>
     */
    int rechargeFlag(@Param("id") Long uid,@Param("flag")Integer flag);
}
