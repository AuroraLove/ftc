package com.auroralove.ftctoken.service;

import com.auroralove.ftctoken.dict.DealEnum;
import com.auroralove.ftctoken.entity.UserEntity;
import com.auroralove.ftctoken.filter.Ufilter;
import com.auroralove.ftctoken.mapper.UserMapper;
import com.auroralove.ftctoken.model.DealModel;
import com.auroralove.ftctoken.model.UserModel;
import com.auroralove.ftctoken.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * @author zyu
 * @date 2019-1-22 20:52
 */
@Service("UserService")
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IdWorker idWorker;

    /**
     * 通过手机号查找用户
     * @param phone
     * @return
     */
    public UserModel findByUserphone(Integer phone){
        return userMapper.findByUserphone(phone);
    }

    /**
     * 通过用户id查找用户
     * @param userId
     * @return
     */
    public UserModel findUserById(Long userId) {
        return userMapper.findUserById(userId);
    }

    /**
     * 注册用户
     * @param userModel
     * @return
     */
    public Boolean registUser(UserModel userModel) {
        userModel.setId(idWorker.nextId());
        userModel.setTeamId(idWorker.nextId());
        int reslut = userMapper.newUser(userModel);
        //注册成功
        if(reslut > 0){
            return true;
        }
        return false;
    }

    /**
     * 返回主页用户信息
     * @param userModel
     * @return
     */
    public UserEntity getUserInfo(UserModel userModel) {
        UserEntity userEntity = new UserEntity(userModel);

        return userEntity;
    }

    /**
     *  为用户账户充值
     * @param ufilter
     * @return
     */
    public int recharge(Ufilter ufilter) {
        DealModel dealModel = new DealModel(ufilter);
        dealModel.setType(DealEnum.DEALTYPE_RECHARGE.getValue());
        dealModel.setDealId(idWorker.nextId());
        //增加用户充值记录
        int reslut = userMapper.recharge(dealModel);
        if (reslut > 0){
            //为用户增加充值记录标识
            reslut = userMapper.rechargeFlag(ufilter.getId(),DealEnum.RECHARGE_FLAG.getValue());
        }
        return reslut;
    }
}
