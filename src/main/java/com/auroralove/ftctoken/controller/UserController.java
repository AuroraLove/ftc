package com.auroralove.ftctoken.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.auroralove.ftctoken.annotation.UserLoginToken;
import com.auroralove.ftctoken.entity.MessageEntity;
import com.auroralove.ftctoken.entity.UserEntity;
import com.auroralove.ftctoken.filter.Ufilter;
import com.auroralove.ftctoken.model.MessageModel;
import com.auroralove.ftctoken.model.UserModel;
import com.auroralove.ftctoken.result.ResponseMessage;
import com.auroralove.ftctoken.result.ResponseResult;
import com.auroralove.ftctoken.service.TokenService;
import com.auroralove.ftctoken.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户中心REST访问接口
 * @author zyu
 * @date 2019-1-22  20:45
 */
@RestController
@RequestMapping("/v1/rest")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;


    /**
     * 登录
     * @param ufilter
     * @return ResponseResult
     */
    @PostMapping("/login")
    public ResponseResult login(Ufilter ufilter){
        UserModel userForBase = userService.findByUserphone(ufilter.getPhone());
        if(userForBase ==null){
            return new ResponseResult(ResponseMessage.DATA_NOT_FOUND,false);
        }else {
            if (!userForBase.getPassWord().equals(ufilter.getPassWord())){
                return new ResponseResult(ResponseMessage.AUTHORIZED_ERROR,false);
            }else {
                String token = tokenService.getToken(userForBase);
                UserEntity userResult = userService.getUserInfo(userForBase);
                userResult.setToken(token);
                return new ResponseResult(ResponseMessage.OK, userResult);
            }
        }
    }

    /**
     * 注册
     * @param userModel
     * @return ResponseResult
     */
    @PostMapping("/regist")
    public ResponseResult regist(UserModel userModel){
        if(userService.findByUserphone(userModel.getPhone()) != null){
            return new ResponseResult(ResponseMessage.PHONE_EXITS,false);
        }
        Boolean flag = userService.registUser(userModel);
        if(flag != null && flag){
            return new ResponseResult(ResponseMessage.OK,true);
        }
        return new ResponseResult(ResponseMessage.FAIL,false);
    }

    /**
     * 账户充值
     * @param ufilter
     * @return
     */
    @PostMapping("/home/recharge")
    public ResponseResult recharge(Ufilter ufilter){
        if (ufilter.getId() != null && ufilter.getAmount() != null){
            int result = userService.recharge(ufilter);
            if (result > 0){
                return new ResponseResult(ResponseMessage.OK,"充值成功");
            }
            return new ResponseResult(ResponseMessage.FAIL,"充值失败");
        }
        return new ResponseResult(ResponseMessage.FAIL,"系统出错");
    }

    /**
     * 查看我的团队
     * @param
     * @return
     */
    @PostMapping("/home/team")
    public ResponseResult team(Ufilter ufilter){
        return null;
    }

    /**
     * 修改登陆密码
     * @param
     * @return
     */
    @PostMapping("/home/changeLoginPwd")
    public ResponseResult changeLoginPwd(Ufilter ufilter){
    	if (ufilter.getId() != null && ufilter.getPassWord() != null){
    		int result = userService.changeLoginPwd(ufilter);
    		 if (result > 0){
                 return new ResponseResult(ResponseMessage.OK,true);
             }
             return new ResponseResult(ResponseMessage.FAIL,false);
    	}
    	 return new ResponseResult(ResponseMessage.FAIL,false);
    }

    /**
     * 修改支付密码
     * @param
     * @return
     */
    @PostMapping("/home/changePayPwd")
    public ResponseResult changePayPwd(Ufilter ufilter){
    	if (ufilter.getId() != null && ufilter.getPay_pwd() != null){
    		int result = userService.changePayPwd(ufilter);
    		 if (result > 0){
                return new ResponseResult(ResponseMessage.OK,true);
            }
            return new ResponseResult(ResponseMessage.FAIL,false);
    	}
    	 return new ResponseResult(ResponseMessage.FAIL,false);

    }

    /**
     * 上传留言
     * @param
     * @return
     */
    @PostMapping("/home/uploadMsg")
    public ResponseResult uploadMsg(Ufilter ufilter,HttpServletRequest request){
    	if (ufilter.getId() != null && ufilter.getmType() != null&& ufilter.getMessage() != null){
    		int res=0;
    		if(ufilter.getPicture()!=null){
    			try {
	    			String fileName=ufilter.getPicture().getOriginalFilename();
	    			//存凭证
	    			String url=null;
	    			// 项目在容器中实际发布运行的根路径
	    			String realPath=request.getSession().getServletContext().getRealPath("/");
	    			// 设置存放图片文件的路径
	    			url=realPath+"pictures/";
	    			File file=new File(url);
	    			if (!file.exists()) {
	    				file.mkdirs();
	    			}
	    			 // 转存文件到指定的路径
					ufilter.getPicture().transferTo(file);
	    			//type=11，类型为上传留言
	    			if(ufilter.getmType()==11){
	    				res=userService.uploadMsg(ufilter, url+"/"+fileName);
	    			}else{
	    				res=userService.replayMsg(ufilter, url+"/"+fileName);
	    			}
				} catch (IllegalStateException e) {
					return new ResponseResult(ResponseMessage.FAIL,false);
				} catch (IOException e) {
					return new ResponseResult(ResponseMessage.FAIL,false);
				}
    		}else{
    			//直接存数据库
    			if(ufilter.getmType()==11){
    				res=userService.uploadMsg(ufilter, null);
    			}else{
    				res=userService.replayMsg(ufilter, null);
    			}
    		}
    		 if (res > 0){
                 return new ResponseResult(ResponseMessage.OK,true);
             }
             return new ResponseResult(ResponseMessage.FAIL,false);

    	}
         return new ResponseResult(ResponseMessage.FAIL,false);

    }
    /**
     * 查询留言簿
     * @param
     * @return
     */
    @PostMapping("/home/messageInfo")
    public ResponseResult messageInfo(Ufilter ufilter){
    	if (ufilter.getId() != null){
    		MessageModel messageInfo=userService.messageInfo(ufilter.getId());
    		MessageEntity messageEntity=new MessageEntity(messageInfo);
    		return new ResponseResult(ResponseMessage.OK,messageEntity);
    	}
    	 return new ResponseResult(ResponseMessage.FAIL,false);

    }

    /**
     * 获取系统参数
     * @param
     * @return
     */
    @PostMapping("/system")
    public ResponseResult getSystem(){
        return new ResponseResult(ResponseMessage.OK,userService.getSystem());
    }


    /**
     *  @UserLoginToken 注解直接添加token验证，从header中获取，不需要的接口则无需增加
     */
    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
