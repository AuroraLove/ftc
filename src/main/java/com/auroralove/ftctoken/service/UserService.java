package com.auroralove.ftctoken.service;

import com.auroralove.ftctoken.dict.DealEnum;
import com.auroralove.ftctoken.entity.AccountEntity;
import com.auroralove.ftctoken.entity.TeamEntity;
import com.auroralove.ftctoken.entity.UserEntity;
import com.auroralove.ftctoken.filter.MsgFilter;
import com.auroralove.ftctoken.filter.PayFilter;
import com.auroralove.ftctoken.filter.Ufilter;
import com.auroralove.ftctoken.mapper.SystemMapper;
import com.auroralove.ftctoken.mapper.UserMapper;
import com.auroralove.ftctoken.model.*;
import com.auroralove.ftctoken.utils.IdWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    @Value("${picture.url}")
    private String pictureUrl;

    /**
     * 通过手机号查找用户
     * @param phone
     * @return
     */
    public UserModel findByUserphone(String phone){
        System.out.println(phone);
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
        //获取用户交易资料
        UserPayModel payModel = userMapper.getPayInfo(userModel.getId());
        UserEntity userEntity = new UserEntity(userModel,dealAccount,rewardAccount,payModel);
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
    	int reslut = userMapper.changeLoginPwd(ufilter.getPhone(),ufilter.getPassWord());
        return reslut;
    }
    
    /**
     *  修改支付密码
     * @param ufilter
     * @return
     */
    public int changePayPwd(Ufilter ufilter) {
    	int reslut = userMapper.changePayPwd(ufilter.getId(),ufilter.getPayPwd());
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
    public List<MessageModel> messageInfo(Long uid) {
        List<MessageModel> reslut = userMapper.messageInfo(uid);
        return reslut;
    }

    /**
     *  保存/修改用户交易资料
     * @param payFilter
     * @return
     */
    public UserPayModel saveUserData(PayFilter payFilter,HttpServletRequest request) throws Exception{
        UserPayModel payModel = new UserPayModel(payFilter);
        payModel.setPid(idWorker.nextId());
        if(payFilter.getAliPayCode() != null){
            payModel.setAli_url(savePicture(payFilter.getAliPayCode(),request));
        }
        if (payFilter.getWehatPayCode() != null){
            payModel.setWechat_url(savePicture(payFilter.getWehatPayCode(),request));
        }
        int result =userMapper.savePayInfo(payModel);
        if (result > 0){
            return payModel;
        }
        return null;
    }

    /**
     * 存储图片
     * @param picture
     * @param request
     * @return
     * @throws Exception
     */
    private String savePicture(MultipartFile picture, HttpServletRequest request) throws Exception{
        String fileName=picture.getOriginalFilename();
        // 设置存放图片文件的路径
        File file=new File(pictureUrl);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 转存文件到指定的路径
        picture.transferTo(new File(pictureUrl + fileName));
        return  fileName;
    }

    public UserPayModel getPayInfo(Long uid) {
        return userMapper.getPayInfo(uid);
    }

    /**
     *  取团队详情
     * @return
     */
    public TeamEntity getTeam(Ufilter ufilter, Integer level, Long count) {

        TeamEntity result = new TeamEntity();
        //设置根用户手机号
        result.setPhone(ufilter.getPhone());
        result.setUid(ufilter.getId());
        //获取子记录
        List<UserModel> userChilds = userMapper.getUserChilds(ufilter.getId());
        level ++;
        if (userChilds.size() > 0){
            List<TeamEntity> childs = new ArrayList<>();
            for (UserModel userModel:userChilds) {
                //设置子用户级别
                result.setLevel(level);
                //设置子用户级别人数
                result.setCount(count);
                Ufilter filter = new Ufilter(userModel.getId(),userModel.getPhone());
                //递归获取子用户
                TeamEntity userChild = getTeam(filter,level,Long.valueOf(userChilds.size()));
                childs.add(userChild);
            }
            result.setChilds(childs);
        }else {
            //设置子用户级别
            result.setLevel(level);
            //设置子用户级别人数
            result.setCount(count);
        }
        return result;
    }

    /**
     * 获取团队总人数
     * @param team
     * @param total
     * @return
     */
    public Long getTotal(TeamEntity team, Long total) {
        List<TeamEntity> childs = team.getChilds();
        if (childs != null){
            total += childs.size();
            if (childs.size()>0){
                for (TeamEntity teamEntity :childs){
                    total = getTotal(teamEntity,total);
                }
            }
        }
        return total;
    }

    /**
     * 更新用户资料
     * @param payInfo
     * @return
     */
    public UserPayModel updatePayInfo(PayFilter payFilter,HttpServletRequest request) throws Exception{
        UserPayModel payModel = new UserPayModel(payFilter);
        if(payFilter.getAliPayCode() != null){
            payModel.setAli_url(savePicture(payFilter.getAliPayCode(),request));
        }
        if (payFilter.getWehatPayCode() != null){
            payModel.setWechat_url(savePicture(payFilter.getWehatPayCode(),request));
        }
        int result =userMapper.updatePayInfo(payModel);
        if (result > 0){
            return payModel;
        }
        return null;
    }

    /**
     * 更新用户交易密码
     * @param payFilter
     * @return
     */
    public int updatePayPwd(PayFilter payFilter) {
        return userMapper.updatePayPwd(payFilter.getId(),payFilter.getPayPwd());
    }

    /**
     * 取公告信息
     * @return
     */
    public List<PublicInfoModel> getPublicMsg() {
        List<PublicInfoModel> publicInfoModels = userMapper.getPublicMsg();
        if (publicInfoModels == null){
            return new ArrayList<PublicInfoModel>();
        }
        return publicInfoModels;
    }

    /**
     * 新增公告信息
     * @return
     */
    public int newPublicMsg(String information) {
        return userMapper.newPublicMsg(information);
    }

    /**
     * 获取公告信息
     * @return
     */
    public int deletePublicMsg(Long pid) {
        return userMapper.deletePublicMsg(pid);
    }

    public AccountEntity userAccount(Long id) {

        //取交易金额
        AccountModel dealAccount = userMapper.getDealAccountInfo(id);
        if (dealAccount == null){
            dealAccount = new AccountModel();
        }
        //取奖励金额
        AccountModel rewardAccount = userMapper.getRewardAccount(id);
        if (rewardAccount == null){
            rewardAccount = new AccountModel();
        }

        //取锁仓金额
        AccountModel lockedAccount = userMapper.getRewardAccount(id);
        if (rewardAccount == null){
            rewardAccount = new AccountModel();
        }

    }
}
