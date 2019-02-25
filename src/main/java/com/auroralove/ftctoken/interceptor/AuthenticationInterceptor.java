package com.auroralove.ftctoken.interceptor;

import com.auroralove.ftctoken.annotation.UserLoginToken;
import com.auroralove.ftctoken.exception.MissingTokenException;
import com.auroralove.ftctoken.exception.RepeatLoginException;
import com.auroralove.ftctoken.model.UserModel;
import com.auroralove.ftctoken.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auroralove.ftctoken.annotation.PassToken;
import com.auroralove.ftctoken.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;


/**
 * @author zyu
 * @date 2019-1-22  20:41
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new MissingTokenException("缺少token！");
                }
                // 获取 token 中的 userEntity id
                Long userId;
                String userDevice;
                try {
                    userId = Long.valueOf(JWT.decode(token).getAudience().get(0));
                    //获取用户设备信息
                    userDevice = JWT.decode(token).getAudience().get(1);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                UserModel user = userService.findUserById(userId);
                if (user == null) {
                    throw new RuntimeException("用户不存在！");
                }
                //踢出用户
                if(user.getUserDevice() == null){
                    user.setUserDevice("");
                }
                if (!user.getUserDevice().equals(userDevice)){
                    throw new RepeatLoginException("账号重复登录！");
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassWord())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("401");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
