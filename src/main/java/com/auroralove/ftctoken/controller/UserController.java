package com.auroralove.ftctoken.controller;

import com.auroralove.ftctoken.annotation.UserLoginToken;
import com.auroralove.ftctoken.entity.*;
import com.auroralove.ftctoken.filter.*;
import com.auroralove.ftctoken.model.*;
import com.auroralove.ftctoken.result.ResponseMessage;
import com.auroralove.ftctoken.result.ResponseResult;
import com.auroralove.ftctoken.service.DealService;
import com.auroralove.ftctoken.service.SystemService;
import com.auroralove.ftctoken.service.TokenService;
import com.auroralove.ftctoken.service.UserService;
import com.auroralove.ftctoken.utils.SendSMSUitl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


/**
 * 用户中心REST访问接口
 *
 * @author zyu
 * @date 2019-1-22  20:45
 */
@RestController
@RequestMapping("/v1/rest")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DealService dealService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private TokenService tokenService;

    @Value("${picture.url}")
    private String pictureUrl;

    /**
     * 登录
     *
     * @param ufilter
     * @return ResponseResult
     */
    @PostMapping("/login")
    public ResponseResult login(Ufilter ufilter) {
        UserModel userForBase = userService.findByUserphone(ufilter.getPhone());
        if (userForBase == null) {
            return new ResponseResult(ResponseMessage.PHONE_ERROR);
        }
        //判断账户是否冻结，1冻结，0正常
        if (!userForBase.getAccountStatus().equals(0)){
            //3,永久冻结
            if (userForBase.getAccountStatus().equals(3)){
                return new ResponseResult(ResponseMessage.ACCOUNT_FROZEN_PERMINANT_FAIL);
            }
        }
        if (!userForBase.getPassWord().equals(ufilter.getPassWord())) {
            return new ResponseResult(ResponseMessage.AUTHORIZED_ERROR);
        }

        if (ufilter.getAmdinFlag() != null && userForBase.getAmdinStatus().equals(0)) {
            return new ResponseResult(ResponseMessage.INADEQUATE_PERMISSIONS);
        }
        String token = tokenService.getToken(userForBase, ufilter.getUserDevice());
        UserEntity userResult = userService.getUserInfo(userForBase);
        userResult.setToken(token);
        return new ResponseResult(ResponseMessage.OK, userResult);
    }

    /**
     * 搜索用户
     *
     * @param ufilter
     * @return ResponseResult
     */
    @PostMapping("/findUser")
    public ResponseResult findUser(Ufilter ufilter) {
        if (ufilter.getPhone() != null) {
            UserModel userModel = userService.findByUserphone(ufilter.getPhone());
            UserEntity userResult = userService.getUserInfo(userModel);
            return new ResponseResult(ResponseMessage.OK, userResult);
        }
        return new ResponseResult(ResponseMessage.FAIL);
    }

    /**
     * 充值列表
     *
     * @param ufilter
     * @return ResponseResult
     */
    @UserLoginToken
    @PostMapping("/findRecharegeDeals")
    public ResponseResult findRecharegeDeals(Ufilter ufilter) {
        PageInfo recharegeDeals = dealService.getRecharegeDeals(ufilter);
        return new ResponseResult(ResponseMessage.OK, recharegeDeals);
    }

    /**
     * 统计用户充值
     *
     * @param ufilter
     * @return ResponseResult
     */
    @PostMapping("/statistacRecharge")
    public ResponseResult statistacRecharge() {
        TotalInfoModel totalInfoModel = dealService.statistacRecharge();
        return new ResponseResult(ResponseMessage.OK, totalInfoModel);
    }

    //    /**
//     * 搜索交易订单
//     *
//     * @param ufilter
//     * @return ResponseResult
//     */
 //    @PostMapping("/findDeal")
//    public ResponseResult findDeal(Ufilter ufilter) {
//        PageInfo recharegeDeals = dealService.getRecharegeDeals(ufilter);
//        return new ResponseResult(ResponseMessage.OK, recharegeDeals);
//    }


    /**
     * 找回密码
     *
     * @param ufilter
     * @return ResponseResult
     */
    @PostMapping("/forgetPassword")
    public ResponseResult forgetPassword(Ufilter ufilter) {
        UserModel userForBase = userService.findByUserphone(ufilter.getPhone());
        if (userForBase == null) {
            return new ResponseResult(ResponseMessage.AUTHORIZED_ERROR);
        } else {
            //验证手机验证码是否正确
            int i = userService.veritifyCode(ufilter.getPhone(), ufilter.getCode());
            if (i == -1) {
                return new ResponseResult(ResponseMessage.FAIL_CODE);
            }
            if (i == -2) {
                return new ResponseResult(ResponseMessage.SMS_CODE_FAIL);
            }
            ufilter.setId(userForBase.getId());
            int result = userService.updateUserInfo(ufilter);
            if (result > 0) {
                return new ResponseResult(ResponseMessage.OK);
            }
        }
        return new ResponseResult(ResponseMessage.USERPASSWORD_FAIL);
    }

    /**
     * 发送短信
     *
     * @param ufilter
     * @return ResponseResult
     */
    @PostMapping("/sendMsg")
    public ResponseResult sendMsg(Ufilter ufilter) {
        if (ufilter.getPhone() != null) {
            //生成随机验证码
            Integer code = new Random().nextInt(899999) + 100000;
            //发送短信
            boolean flag = SendSMSUitl.sendSMS(ufilter.getPhone(), code);
            //验证码入库
            if (flag) {
                int result = userService.newVeritifyCode(ufilter.getPhone(), code);
                if (result > 0) {
                    return new ResponseResult(ResponseMessage.OK, "短信已成功发送！");
                }
            }
            return new ResponseResult(ResponseMessage.SMS_FAIL);
        }
        return new ResponseResult(ResponseMessage.FAIL);
    }

    /**
     * 注册
     *
     * @param userModel
     * @return ResponseResult
     */
    @PostMapping("/regist")
    public ResponseResult regist(UserModel userModel) {
        if (userService.findByUserphone(userModel.getPhone()) != null) {
            return new ResponseResult(ResponseMessage.PHONE_EXITS);
        }
        int i = userService.veritifyCode(userModel.getPhone(), userModel.getCode());
        if (i == -1) {
            return new ResponseResult(ResponseMessage.FAIL_CODE);
        }
        if (i == -2) {
            return new ResponseResult(ResponseMessage.SMS_CODE_FAIL);
        }
        Integer flag = userService.registUser(userModel);
        if (flag != null && flag > 0) {
            return new ResponseResult(ResponseMessage.REGIST_OK);
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 取用户账户金额
     *
     * @param ufilter
     * @return ResponseResult
     */
    @UserLoginToken
    @PostMapping("/home/userAccount")
    public ResponseResult userAccount(Ufilter ufilter) {
        AccountEntity userResult = userService.userAccount(ufilter.getId());
        if (userResult != null) {
            return new ResponseResult(ResponseMessage.OK, userResult);
        }
        return new ResponseResult(ResponseMessage.FAIL, "服务器出错");
    }

    /**
     * 增加账户可交易金额
     *
     * @param dfilter
     * @return
     */
    @UserLoginToken
    @PostMapping("/addTradableAmount")
    public ResponseResult addTradableAmount(Dfilter dfilter) throws Exception {
        if (dfilter.getId() != null && dfilter.getAmount() != null) {
            int result = userService.addTradableAmount(dfilter);
            if (result > 0) {
                return new ResponseResult(ResponseMessage.TRADEABLE_OK);
            }
            return new ResponseResult(ResponseMessage.TRADEABLE_FAIL);
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 账户充值
     *
     * @param dfilter
     * @return
     */
//    @UserLoginToken
    @PostMapping("/home/recharge")
    public ResponseResult recharge(Dfilter dfilter) throws Exception {
        if (dfilter.getId() != null && dfilter.getAmount() != null) {
            int result = userService.recharge(dfilter);
            if (result > 0) {
                return new ResponseResult(ResponseMessage.RECHARGE_OK);
            }
            return new ResponseResult(ResponseMessage.RECHARGE_FAIL);
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 修改登陆密码
     *
     * @param
     * @return
     */
    @PostMapping("/home/changeLoginPwd")
    public ResponseResult changeLoginPwd(Ufilter ufilter) {
        if (ufilter.getPhone() != null && ufilter.getPassWord() != null) {
            int result = userService.changeLoginPwd(ufilter);
            if (result > 0) {
                return new ResponseResult(ResponseMessage.OK);
            }
            return new ResponseResult(ResponseMessage.UPDATE_FAIL);
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 修改支付密码
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/home/changePayPwd")
    public ResponseResult changePayPwd(Ufilter ufilter) {
        if (ufilter.getId() != null && ufilter.getPayPwd() != null) {
            UserModel userModel = userService.findUserById(ufilter.getId());
            ufilter.setPhone(userModel.getPhone());
            int result = userService.changePayPwd(ufilter);
            if (result == -1) {
                return new ResponseResult(ResponseMessage.SMS_CODE_FAIL);
            }
            if (result > 0) {
                return new ResponseResult(ResponseMessage.OK);
            }
            return new ResponseResult(ResponseMessage.UPDATE_FAIL);
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 上传留言
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/home/uploadMsg")
    public ResponseResult uploadMsg(Ufilter ufilter, HttpServletRequest request) {
        if (ufilter.getId() != null && ufilter.getPhone() != null && ufilter.getType() != null && ufilter.getMessage() != null) {
            int res = 0;
            if (ufilter.getPicture() != null) {
                try {
                    String fileName = ufilter.getPicture().getOriginalFilename();
                    File file = new File(pictureUrl);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    // 转存文件到指定的路径
                    ufilter.getPicture().transferTo(new File(pictureUrl + fileName));
                    //type=11，类型为上传留言
                    if (ufilter.getType() == 11) {
                        res = userService.uploadMsg(ufilter, fileName);
                    }
                } catch (IllegalStateException e) {
                    return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
                } catch (IOException e) {
                    return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
                }
            } else {
                //直接存数据库
                if (ufilter.getType() == 11) {
                    res = userService.uploadMsg(ufilter, "");
                }
            }
            if (res > 0) {
                return new ResponseResult(ResponseMessage.OK, true);
            }
            return new ResponseResult(ResponseMessage.LEAVING_FAIL);

        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);

    }

    /**
     * 回复留言
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/home/relayMsg")
    public ResponseResult relayMsg(MsgFilter msgFilter) {
        if (msgFilter.getMessage() != null) {
            int res = 0;
                res = userService.replayMsg(msgFilter);
            if (res > 0) {
                return new ResponseResult(ResponseMessage.OK, true);
            }
            return new ResponseResult(ResponseMessage.LEAVING_FAIL);
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 查询留言簿
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/home/messageInfo")
    public ResponseResult messageInfo(Ufilter ufilter) {
        if (ufilter.getId() != null) {
            List<MessageEntity> messageEntities = new ArrayList<>();
            List<MessageModel> messageInfos = userService.messageInfo(ufilter.getId());
            for (MessageModel messageModel : messageInfos) {
                MessageEntity messageEntity = new MessageEntity(messageModel);
                messageEntities.add(messageEntity);
            }
            return new ResponseResult(ResponseMessage.OK, messageEntities);
        } else {
            PageInfo messageInfos = userService.messageListInfo(ufilter);
            return new ResponseResult(ResponseMessage.OK, messageInfos);
        }
    }

    /**
     * 查询用户资料
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/home/queryUserData")
    public ResponseResult queryUserData(PayFilter payFilter) {
        if (payFilter.getId() != null) {
            UserPayModel payInfo = userService.getPayInfo(payFilter.getId());
            return new ResponseResult(ResponseMessage.OK, payInfo);
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 用户交易资料
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/home/userData")
    public ResponseResult userData(PayFilter payFilter, HttpServletRequest request) throws Exception {
        if (payFilter.getId() != null) {
            if(payFilter.getAdminFlag() == null){
                int i = userService.veritifyCode(payFilter.getUserPhone(), payFilter.getCode());
                if (i == -1) {
                    return new ResponseResult(ResponseMessage.FAIL_CODE);
                }
                if (i == -2) {
                    return new ResponseResult(ResponseMessage.SMS_CODE_FAIL);
                }
            }
            UserPayModel payInfo = userService.getPayInfo(payFilter.getId());
            if (payInfo != null) {
                int n = 0;
                //更新用户资料
                UserPayModel userPayModel = userService.updatePayInfo(payFilter, request);
                if (userPayModel != null){
                    n = 1;
                }
                //更新交易密码
                if (payFilter.getPayPwd() != null) {
                    n = userService.updatePayPwd(payFilter);
                }
                if (userPayModel != null && n > 0) {
                    return new ResponseResult(ResponseMessage.USERDATA_UPDATE_SUCCESS, userPayModel);
                }
                return new ResponseResult(ResponseMessage.USERDATA_FAIL);
            }
            UserPayModel userPayModel = userService.saveUserData(payFilter, request);
            if (userPayModel != null) {
                return new ResponseResult(ResponseMessage.OK, userPayModel);
            }
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 获取团队详情
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/home/teamInfo")
    public ResponseResult teamInfo(Ufilter ufilter) throws Exception {
        if (ufilter.getId() != null) {
            TeamEntity team = userService.getTeam(ufilter, -1, 0L);
            //正向递归，设置团队成员总数
            Long total = userService.getTotal(team, 1L);
            //获取团队各层级人数
            HashMap<Integer, Integer> teamLevelInfo = new HashMap<Integer,Integer>();
            teamLevelInfo = userService.getTeamLevelInfo(team, teamLevelInfo);
            //获取用户团队充值,奖励总数
            TeamAmount teamAmount = new TeamAmount();
            teamAmount = userService.getTeamAmount(team.getIds());
            if (teamAmount == null) {
                teamAmount = new TeamAmount();
            }
            team.setTotal(Long.valueOf(team.getIds().size() -1));
//            if (team.getTeamLevelInfo() == null){
//                team.setTeamLevelInfo(new ArrayList<>());
//            }
//            team.getTeamLevelInfo().add(teamLevelInfo);
            team.setTeamLevelInfo(teamLevelInfo);
            //更新用户团队成员
            team.setTeamRechargeAmount(teamAmount.getTeamRechargeAmount());
            team.setTeamRewardAmount(teamAmount.getTeamRewardAmount());
            return new ResponseResult(ResponseMessage.OK, team);
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 搜索用户团队详情
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/home/findTeamInfo")
    public ResponseResult findTeamInfo(Ufilter ufilter) throws Exception {
        UserModel userModel = userService.findByUserphone(ufilter.getPhone());
        ufilter.setId(userModel.getId());
        if (ufilter.getId() != null) {
            TeamEntity team = userService.getTeam(ufilter, -1, 1L);
            //正向递归，设置团队成员总数
//            Long total = userService.getTotal(team, 1L);
            //获取用户团队充值,奖励总数
            TeamAmount teamAmount = new TeamAmount();
            //去除本人id
            List teamIds = team.getIds();
            teamIds.remove(0);
            teamAmount = userService.getTeamAmount(team.getIds());
            if (teamAmount == null) {
                teamAmount = new TeamAmount();
            }
            team.setTotal(Long.valueOf(team.getIds().size()-1));
            //更新用户团队成员
            team.setTeamRechargeAmount(teamAmount.getTeamRechargeAmount());
            team.setTeamRewardAmount(teamAmount.getTeamRewardAmount());
            return new ResponseResult(ResponseMessage.OK, team);
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 获取团队列表详情
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/home/teamList")
    public ResponseResult teamList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) throws Exception {
        //取用户分页列表
        PageInfo teamEntities = userService.getUsers(pageNum, pageSize);
        return new ResponseResult(ResponseMessage.OK, teamEntities);
    }

    /**
     * 取公告信息
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/home/getPublicMsg")
    public ResponseResult getPublicMsg() {
        List<PublicInfoModel> publicInfoModel = userService.getPublicMsg();
        return new ResponseResult(ResponseMessage.OK, publicInfoModel);
    }

    /**
     * 新增公告
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/home/newPublicMsg")
    public ResponseResult newPublicMsg(String information) {
        int result = userService.newPublicMsg(information);
        if (result > 0) {
            return new ResponseResult(ResponseMessage.OK, result);
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 删除公告
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/home/deletePublicMsg")
    public ResponseResult deletePublicMsg(Long pid) {
        int result = userService.deletePublicMsg(pid);
        if (result > 0) {
            return new ResponseResult(ResponseMessage.OK, result);
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 帮助中心
     *
     * @param
     * @return
     */
    @PostMapping("/help")
    public ResponseResult help() {
        return new ResponseResult(ResponseMessage.OK, userService.getHelp());
    }

    /**
     * 新增公告
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/newHelp")
    public ResponseResult newHelp(HelpModel helpModel, HttpServletRequest request) throws Exception {
        int result = userService.newHelp(helpModel, request);
        if (result > 0) {
            return new ResponseResult(ResponseMessage.OK);
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

    /**
     * 更新系统参数
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/updateSystem")
    public ResponseResult updateSystem(SystemModel systemModel, HttpServletRequest request) throws Exception {
        int i = userService.updateSystem(systemModel, request);
        if (i > 0) {
            return new ResponseResult(ResponseMessage.OK);
        }
        return new ResponseResult(ResponseMessage.FAIL);
    }

    /**
     * 冻结账户
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/frozenUser")
    public ResponseResult frozenUser(Ufilter ufilter) {
        int i = userService.frozenUser(ufilter);
        if (i > 0) {
            return new ResponseResult(ResponseMessage.OK);
        }
        return new ResponseResult(ResponseMessage.FAIL);
    }

    /**
     * 数据中心查询
     *
     * @param
     * @return
     */
    @UserLoginToken
    @PostMapping("/dataCenter")
    public ResponseResult dataCenter() {
        DataCenterModel dataCenterEntity = systemService.getDataCenter();
        return new ResponseResult(ResponseMessage.OK, dataCenterEntity);
    }

    /**
     * 获取系统参数
     *
     * @param
     * @return
     */
    @PostMapping("/system")
    public ResponseResult getSystem() {
        return new ResponseResult(ResponseMessage.OK, userService.getSystem());
    }


    /**
     * @UserLoginToken 注解直接添加token验证，从header中获取，不需要的接口则无需增加
     */
    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage() {
        return "你已通过验证";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
