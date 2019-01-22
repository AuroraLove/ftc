package com.auroralove.ftctoken.service;

import com.auroralove.ftctoken.entity.UserEntity;
import com.auroralove.ftctoken.mapper.UserMapper;
import com.auroralove.ftctoken.model.UserModel;
import com.auroralove.ftctoken.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public UserModel findByUserphone(Integer phone){
        return userMapper.findByUserphone(phone);
    }

    public UserModel findUserById(Long userId) {
        return userMapper.findUserById(userId);
    }

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

    public UserEntity getUserInfo(UserModel userModel) {
        UserEntity userEntity = new UserEntity(userModel);

        return userEntity;
    }
}
