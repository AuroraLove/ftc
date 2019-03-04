package com.auroralove.ftctoken.service;

import com.auroralove.ftctoken.mapper.UserMapper;
import com.auroralove.ftctoken.model.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zyu
 * @date 2019-1-22  21:04
 */
@Service("TokenService")
public class TokenService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public String getToken(UserModel userModel, String userDevice, Integer amdinFlag) {
        // 将 userEntity id 保存到 token 里面,以 password 作为 token 的密钥
        String token= JWT.create().withAudience(userModel.getId().toString(),userDevice,amdinFlag.toString())
                .sign(Algorithm.HMAC256(userModel.getPassWord()));
        //将用户设备保存的用户数据库中实现单一用户登录
        userModel.setUserDevice(userDevice);
        int i = userMapper.updateUserDevice(userModel);
        return token;
    }
}
