package com.auroralove.ftctoken.service;

import com.auroralove.ftctoken.dict.DealEnum;
import com.auroralove.ftctoken.entity.UserEntity;
import com.auroralove.ftctoken.filter.MsgFilter;
import com.auroralove.ftctoken.filter.PayFilter;
import com.auroralove.ftctoken.filter.Ufilter;
import com.auroralove.ftctoken.mapper.SystemMapper;
import com.auroralove.ftctoken.mapper.UserMapper;
import com.auroralove.ftctoken.model.*;
import com.auroralove.ftctoken.utils.IdWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @author zyu
 * @date 2019-1-22 20:52
 */
@Service("UserService")
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private IdWorker idWorker;

    /**
     * 通过手机号查找用户
     * @param phone
     * @return
     */
    public UserModel findByUserphone(String phone){
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
        //取交易金额
        AccountModel dealAccount = userMapper.getDealAccountInfo(userModel.getId());
        if (dealAccount == null){
            dealAccount = new AccountModel();
        }
        //取奖励金额
        AccountModel rewardAccount = userMapper.getRewardAccount(userModel.getId());
        if (rewardAccount == null){
            rewardAccount = new AccountModel();
        }
        UserEntity userEntity = new UserEntity(userModel,dealAccount,rewardAccount);
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
        dealModel.setTid(idWorker.nextId());
        //增加用户充值记录
        int reslut = userMapper.recharge(dealModel);
        if (reslut > 0){
            //为用户增加充值记录标识
            reslut = userMapper.rechargeFlag(ufilter.getId(),DealEnum.RECHARGE_FLAG.getValue());
        }
        return reslut;
    }
    /**
     *  修改登陆密码
     * @param ufilter
     * @return
     */
    public int changeLoginPwd(Ufilter ufilter) {
    	int reslut = userMapper.changeLoginPwd(ufilter.getId(),ufilter.getPassWord());
        return reslut;
    }
    
    /**
     *  修改支付密码
     * @param ufilter
     * @return
     */
    public int changePayPwd(Ufilter ufilter) {
    	int reslut = userMapper.changePayPwd(ufilter.getId(),ufilter.getPay_pwd());
        return reslut;
    }

    /**
     *  获取系统信息
     * @return
     */
    public SystemModel getSystem() {
        SystemModel reslut = systemMapper.getSystemInfo();
        return reslut;
    }

    /**
     *  上传留言
     * @param ufilter
     * @return
     */
    public int uploadMsg(Ufilter ufilter,String url) {
    	int reslut = userMapper.uploadMsg(idWorker.nextId(),ufilter.getId(),ufilter.getMessage(),url);
        return reslut;
    }

    /**
     *  回复留言
     * @param msgFilter
     * @return
     */
    public int replayMsg(MsgFilter msgFilter) {
    	int reslut = userMapper.replayMsg(msgFilter.getMid(),msgFilter.getMessage());
        return reslut;
    }

    /**
     *  查留言簿
     * @param uid
     * @return
     */
    public MessageModel messageInfo(Long uid) {
    	MessageModel reslut = userMapper.messageInfo(uid);
        return reslut;
    }

    /**
     *  保存/修改用户交易资料
     * @param payFilter
     * @return
     */
    public int saveUserData(PayFilter payFilter,HttpServletRequest request) throws Exception{
        UserPayModel payModel = new UserPayModel(payFilter);
        payModel.setPid(idWorker.nextId());
        if(payFilter.getAliPayCode() != null){
            payModel.setAli_url(savePicture(payFilter.getAliPayCode(),request));
        }
        if (payFilter.getWehatPayCode() != null){
            payModel.setWechat_url(savePicture(payFilter.getWehatPayCode(),request));
        }
        int result =userMapper.savePayInfo(payModel);
        return result;
    }

    private String savePicture(MultipartFile picture, HttpServletRequest request) throws Exception{
        String fileName=picture.getOriginalFilename();
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
        picture.transferTo(new File(url + fileName));
        return url + fileName;
    }
}
