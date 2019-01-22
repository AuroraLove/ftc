package com.auroralove.ftctoken.service;

import com.auroralove.ftctoken.model.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

/**
 * @author zyu
 * @date 2019-1-22  21:04
 */
@Service("TokenService")
public class TokenService {
    public String getToken(UserModel userModel) {
        // 将 userEntity id 保存到 token 里面,以 password 作为 token 的密钥
        String token= JWT.create().withAudience(userModel.getId().toString())
                .sign(Algorithm.HMAC256(userModel.getPassWord()));
        return token;
    }
}
