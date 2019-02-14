package com.auroralove.ftctoken.controller;

import com.auroralove.ftctoken.annotation.UserLoginToken;
import com.auroralove.ftctoken.entity.AccountEntity;
import com.auroralove.ftctoken.entity.MessageEntity;
import com.auroralove.ftctoken.entity.TeamEntity;
import com.auroralove.ftctoken.entity.UserEntity;
import com.auroralove.ftctoken.filter.MsgFilter;
import com.auroralove.ftctoken.filter.PayFilter;
import com.auroralove.ftctoken.filter.Ufilter;
import com.auroralove.ftctoken.model.MessageModel;
import com.auroralove.ftctoken.model.PublicInfoModel;
import com.auroralove.ftctoken.model.UserModel;
import com.auroralove.ftctoken.model.UserPayModel;
import com.auroralove.ftctoken.result.ResponseMessage;
import com.auroralove.ftctoken.result.ResponseResult;
import com.auroralove.ftctoken.service.TokenService;
import com.auroralove.ftctoken.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Value("${picture.url}")
    private String pictureUrl;

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
     * 取用户账户金额
     * @param ufilter
     * @return ResponseResult
     */
    @PostMapping("/home/userAccount")
    public ResponseResult userAccount(Ufilter ufilter){
        AccountEntity userResult = userService.userAccount(ufilter.getId());
        if(userResult != null){
            return new ResponseResult(ResponseMessage.OK,userResult);
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
     * 修改登陆密码
     * @param
     * @return
     */
    @PostMapping("/home/changeLoginPwd")
    public ResponseResult changeLoginPwd(Ufilter ufilter){
    	if (ufilter.getPhone() != null && ufilter.getPassWord() != null){
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
    	if (ufilter.getId() != null && ufilter.getPayPwd() != null){
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
    	if (ufilter.getId() != null && ufilter.getType() != null&& ufilter.getMessage() != null){
    		int res=0;
    		if(ufilter.getPicture()!=null){
    			try {
	    			String fileName=ufilter.getPicture().getOriginalFilename();
	    			File file=new File(pictureUrl);
	    			if (!file.exists()) {
	    				file.mkdirs();
	    			}
	    			 // 转存文件到指定的路径
					ufilter.getPicture().transferTo(new File(pictureUrl + fileName));
	    			//type=11，类型为上传留言
	    			if(ufilter.getType()==11){
	    				res=userService.uploadMsg(ufilter, fileName);
	    			}
				} catch (IllegalStateException e) {
					return new ResponseResult(ResponseMessage.FAIL,false);
				} catch (IOException e) {
					return new ResponseResult(ResponseMessage.FAIL,false);
				}
    		}else{
    			//直接存数据库
    			if(ufilter.getType()==11){
    				res=userService.uploadMsg(ufilter, null);
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
     * 回复留言
     * @param
     * @return
     */
    @PostMapping("/home/relayMsg")
    public ResponseResult relayMsg(MsgFilter msgFilter){
        if (msgFilter.getMessage() != null && msgFilter.getMessage() != null){
            int res=0;
                //直接存数据库
                if(msgFilter.getMType()==12){
                    res=userService.replayMsg(msgFilter);
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
    	    List<MessageEntity> messageEntities = new ArrayList<>();
            List<MessageModel> messageInfos=userService.messageInfo(ufilter.getId());
            for (MessageModel messageModel : messageInfos) {
                MessageEntity messageEntity=new MessageEntity(messageModel);
                messageEntities.add(messageEntity);
            }
    		return new ResponseResult(ResponseMessage.OK,messageEntities);
    	}
    	 return new ResponseResult(ResponseMessage.FAIL,false);
    }

    /**
     * 用户交易资料
     * @param
     * @return
     */
    @PostMapping("/home/userData")
    public ResponseResult userData(PayFilter payFilter,HttpServletRequest request) throws Exception{
        if (payFilter.getId() != null){
            UserPayModel payInfo = userService.getPayInfo(payFilter.getId());
            if (payInfo != null){
                //更新用户资料
                UserPayModel userPayModel = userService.updatePayInfo(payFilter,request);
                int n = 0;
                //更新交易密码
                if (payFilter.getPayPwd() != null){
                    n = userService.updatePayPwd(payFilter);
                }
                if (userPayModel != null && n > 0){
                    return new ResponseResult(ResponseMessage.USERDATA_UPDATE_SUCCESS,userPayModel);
                }
                return new ResponseResult(ResponseMessage.USERDATA_FAIL,"更新用户交易资料失败!");
            }
            UserPayModel userPayModel = userService.saveUserData(payFilter,request);
            if (userPayModel != null){
                return new ResponseResult(ResponseMessage.OK,userPayModel);
            }
        }
        return new ResponseResult(ResponseMessage.FAIL,"系统出错");
    }

    /**
     * 获取团队详情
     * @param
     * @return
     */
    @PostMapping("/home/teamInfo")
    public ResponseResult teamInfo(Ufilter ufilter) throws Exception{
        if (ufilter.getId() != null) {
            TeamEntity team = userService.getTeam(ufilter, -1,1L);
            Long total = userService.getTotal(team,0L);
            team.setTotal(total);
            return new ResponseResult(ResponseMessage.OK,team);
        }
        return new ResponseResult(ResponseMessage.ABANDON_FAIL,"系统出错");
    }

    /**
     * 取公告信息
     * @param
     * @return
     */
    @PostMapping("/home/getPublicMsg")
    public ResponseResult getPublicMsg(){
        List<PublicInfoModel> publicInfoModel = userService.getPublicMsg();
        return new ResponseResult(ResponseMessage.OK,publicInfoModel);
    }

    /**
     * 新增公告
     * @param
     * @return
     */
    @PostMapping("/home/newPublicMsg")
    public ResponseResult newPublicMsg(String information){
        int result = userService.newPublicMsg(information);
        if (result > 0){
            return new ResponseResult(ResponseMessage.OK,result);
        }
        return new ResponseResult(ResponseMessage.FAIL,"新增公告失败");
    }

    /**
     * 删除公告
     * @param
     * @return
     */
    @PostMapping("/home/deletePublicMsg")
    public ResponseResult deletePublicMsg(Long pid){
        int result = userService.deletePublicMsg(pid);
        if (result > 0){
            return new ResponseResult(ResponseMessage.OK,result);
        }
        return new ResponseResult(ResponseMessage.FAIL,"删除公告失败");
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
