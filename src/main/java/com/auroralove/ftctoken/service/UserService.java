package com.auroralove.ftctoken.service;

import com.auroralove.ftctoken.dict.DealEnum;
import com.auroralove.ftctoken.entity.AccountEntity;
import com.auroralove.ftctoken.entity.TeamEntity;
import com.auroralove.ftctoken.entity.UserEntity;
import com.auroralove.ftctoken.filter.Dfilter;
import com.auroralove.ftctoken.filter.MsgFilter;
import com.auroralove.ftctoken.filter.PayFilter;
import com.auroralove.ftctoken.filter.Ufilter;
import com.auroralove.ftctoken.mapper.DealMapper;
import com.auroralove.ftctoken.mapper.SystemMapper;
import com.auroralove.ftctoken.mapper.UserMapper;
import com.auroralove.ftctoken.model.*;
import com.auroralove.ftctoken.utils.IdWorker;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * @author zyu
 * @date 2019-1-22 20:52
 */
@Service("UserService")
@Component
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DealMapper dealMapper;

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
    public Integer registUser(UserModel userModel) {
        userModel.setId(idWorker.nextId());
        userModel.setTeamId(idWorker.nextId());
        int reslut = userMapper.newUser(userModel);
        //注册成功
        if(reslut > 0){
            return reslut;
        }
        return reslut;
    }

    /**
     * 返回主页用户信息
     * @param userModel
     * @return
     */
    public UserEntity getUserInfo(UserModel userModel) {
        AccountEntity accountEntity = userAccount(userModel.getId());
        //获取用户交易资料
        UserPayModel payModel = userMapper.getPayInfo(userModel.getId());
        if (payModel == null){
            payModel = new UserPayModel();
        }
        payModel.setPayPwd(userModel.getPay_pwd());
        UserEntity userEntity = new UserEntity(userModel, accountEntity, payModel);
        return userEntity;
    }

    /**
     *  为用户账户充值
     * @param dfilter
     * @return
     */
    @Transactional
    public int recharge(Dfilter dfilter) throws Exception {
        DealModel dealModel = new DealModel(dfilter);
        //获取用户交易资料
        UserPayModel payModel = userMapper.getPayInfo(dfilter.getId());
        dealModel.setPhone(payModel.getPhone());
        dealModel.setUser_name(payModel.getName());
        dealModel.setType(DealEnum.DEALTYPE_RECHARGE.getValue());
        dealModel.setTid(idWorker.nextId());
        //增加用户充值记录
        int reslut = userMapper.recharge(dealModel);
        if (reslut > 0){
            //为用户增加充值记录标识,1为已注册充值
            int n = userMapper.rechargeFlag(dfilter.getId(),1);
            //更新用户团队人数
            List<SystemLevelModel> systemLevelModels = systemMapper.getSystemLevel();
            //记录对应子id
            Long childId = dfilter.getId();
            for (SystemLevelModel systemLevelModel:systemLevelModels) {
                //取邀请人id
                UserModel user = userMapper.findUserById(childId);
                if (user == null || user.getParentId() == null){
                    break;
                }
                //增加父对象团队人数
                reslut = userMapper.updateTeamTotal(user.getParentId());
                if (reslut == 0){
                    throw new Exception("更新团队人数失败");
                }
                childId = user.getParentId();
            }
        }
        return reslut;
    }
    /**
     *  修改登陆密码
     * @param ufilter
     * @return
     */
    public int changeLoginPwd(Ufilter ufilter) {
        //短信验证码是否正确
        int n = veritifyCode(ufilter.getPhone(),ufilter.getCode());
        if (n != 0){
            return -1;
        }
    	int reslut = userMapper.changeLoginPwd(ufilter.getPhone(),ufilter.getPassWord());
        return reslut;
    }
    
    /**
     *  修改支付密码
     * @param ufilter
     * @return
     */
    public int changePayPwd(Ufilter ufilter) {
        //短信验证码是否正确
        int n = veritifyCode(ufilter.getPhone(),ufilter.getCode());
        if (n != 0){
            return -1;
        }
    	int reslut = userMapper.changePayPwd(ufilter.getId(),ufilter.getPayPwd());
        return reslut;
    }

    /**
     *  获取系统信息
     * @return
     */
    public SystemModel getSystem() {
        //取公告信息
        List<PublicInfoModel> publicInfoModel = userMapper.getPublicMsg();
        if(publicInfoModel == null){
            publicInfoModel = new ArrayList<PublicInfoModel>();
        }
        SystemModel reslut = systemMapper.getSystemInfo();
        reslut.setPublicInfo(publicInfoModel);
        return reslut;
    }

    /**
     *  上传留言
     * @param ufilter
     * @return
     */
    public int uploadMsg(Ufilter ufilter, String url) {
        int reslut;
        if (ufilter.getTid() == null) {
            //首次上传留言记录日期
            ufilter.setTid(idWorker.nextId());
            reslut = userMapper.newUploadMsg(idWorker.nextId(), ufilter.getTid(), ufilter.getPhone(), ufilter.getId(), ufilter.getMessage(), url);
        }else {
            //获取首次上传日期
            Date newMessageDate = userMapper.getNewMessageDate(ufilter.getTid());
            reslut = userMapper.uploadMsg(idWorker.nextId(), ufilter.getTid(), ufilter.getPhone(), ufilter.getId(), ufilter.getMessage(), url,newMessageDate);
        }
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
        //设置团队用户列表id
        result.getIds().add(ufilter.getId());
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
                //设置团队用户列表id
//                result.getIds().add(userModel.getId());
                Ufilter filter = new Ufilter(userModel.getId(),userModel.getPhone());
                if(level < 6){
                    //递归获取子用户
                    TeamEntity userChild = getTeam(filter,level,Long.valueOf(userChilds.size()));
                    //设置团队用户列表id
                    List<Long> ids = userChild.getIds();
                    result.getIds().addAll(ids);
                    childs.add(userChild);
                }
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
                for (TeamEntity child :childs){
                    total = getTotal(child,total);
                }
            }
        }
        return total;
    }

    /**
     * 更新用户资料
     * @param payFilter
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
    @Transactional
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

    /**
     * 用户账户余额查询
     * @param uid
     * @return
     */
    public AccountEntity userAccount(Long uid) {
        //取可买单总金额
        AccountModel buyAccountInfo = userMapper.getBuyAmount(uid);
        if (buyAccountInfo == null){
            buyAccountInfo = new AccountModel();
            buyAccountInfo.setBuyAcct(0.0);
        }
        //取可卖单总金额
        AccountModel sellAccountInfo = userMapper.getSellAmount(uid);
        if (sellAccountInfo == null){
            sellAccountInfo = new AccountModel();
            sellAccountInfo.setSellAcct(0.0);
        }
        //取奖励金额
        AccountModel rewardAccount = userMapper.getRewardAccount(uid);
        if (rewardAccount == null){
            rewardAccount = new AccountModel();
            rewardAccount.setFTCRewardAcct(0.0);
        }
        //取充值金额
        AccountModel rechargeAmount = userMapper.getRechargeAmount(uid);
        if (rechargeAmount == null){
            rechargeAmount = new AccountModel();
            rechargeAmount.setRechargeAcct(0.0);
        }
        //取释放金额
        AccountModel realeaseAccount = userMapper.getReleaseAmount(uid);
        if (realeaseAccount == null){
            realeaseAccount = new AccountModel();
            realeaseAccount.setReleaseAmount(0.0);
        }
        //取系统账户余额
        AccountModel systemAccount = userMapper.getSystemAmount(uid);
        if (systemAccount == null){
            systemAccount = new AccountModel();
            systemAccount.setSystemAcct(0.0);
        }
        AccountEntity accountEntity = new AccountEntity(buyAccountInfo,sellAccountInfo,rewardAccount,rechargeAmount,realeaseAccount,systemAccount);
        return  accountEntity;
    }

    /**
     * 短信验证码入库
     * @param phone
     * @param code
     * @return
     */
    public int newVeritifyCode(String phone, Integer code) {
        int flag = 0;
        VeritifyModel veritifyModel = userMapper.getVeritifyInfo(phone);
        if (veritifyModel != null){
            flag = userMapper.updateVeritifyInfo(phone, code);
        }else {
            flag = userMapper.newVeritifyInfo(phone, code);
        }
        return flag;
    }

    /**
     * 验证短信码是否正确
     * @param phone
     * @param code
     * @return
     */
    public int veritifyCode(String phone, Integer code) {
        int flag = 0;
        VeritifyModel veritifyModel = userMapper.getVeritifyInfo(phone);
        //验证过期时间
        if (veritifyModel.getLossDate().before(new Date())){
            return -1;
        }
        //验证码值是否一致
        if (!veritifyModel.getCode().equals(code)){
            return -2;
        }
        return flag;
    }

    /**
     * 修改用户资料密码
     * @param ufilter
     * @return
     */
    public int updateUserInfo(Ufilter ufilter) {
        UserModel userModel = new UserModel(ufilter);
        int i = userMapper.updateUserInfo(userModel);
        return i;
    }

    /**
     * 帮助中心
     * @return
     */
    public List<HelpModel> getHelp() {
        List<HelpModel> helpModels = systemMapper.getHelpInfo();
        if (helpModels == null){
            return new ArrayList<>();
        }
        return helpModels;
    }

    /**
     * 更新系统参数
     * @return
     */
    public int updateSystem(SystemModel systemModel,HttpServletRequest request) throws Exception {
        if (systemModel.getSystemPicture()!= null){
            String pictureName = savePicture(systemModel.getSystemPicture(),request);
            systemModel.setGatheringCode(pictureName);
        }
        int i = systemMapper.updateSystem(systemModel);
        return i;
    }

    /**
     * 冻结用户账户
     * @return
     */
    public int frozenUser(Ufilter ufilter) {
       int i = userMapper.frozenUser(ufilter.getId(),ufilter.getAccountStatus());
       return i;
    }

    /**
     * 获取用户留言列表
     * @param ufilter
     * @return
     */
    public PageInfo messageListInfo(Ufilter ufilter) {
        if (ufilter.getPageNum() == null){
            ufilter.setPageNum(1);
        }
        if (ufilter.getPageSize() == null){
            ufilter.setPageSize(10);
        }
        PageHelper.startPage(ufilter.getPageNum(),ufilter.getPageSize());
        List<MessageModel> messageModels = userMapper.getMessages();
        PageInfo page = new PageInfo(messageModels);
        return  page;
    }

    @Transactional
    public int newHelp(HelpModel helpModel,HttpServletRequest request) throws Exception {
        Long pid = idWorker.nextId();
        helpModel.setPid(pid);
        int i = systemMapper.newHelp(helpModel);
        List<PictureModel> pictureModels = new ArrayList<>();
        if (helpModel.getPictureUrl()!=null){
            for (String str:helpModel.getPictureUrl()){
                PictureModel pictureModel = new PictureModel(str,pid);
                pictureModels.add(pictureModel);
            }
            i = systemMapper.newPicture(pictureModels);
        }
        return i;
    }

    /**
     * 获取团队总奖励金额
     * @param ids
     * @return
     */
    public TeamAmount getTeamAmount(List<Long> ids) {
        TeamAmount teamAmount = userMapper.getTeamAmount(ids);
        return teamAmount;
    }

    /**
     * 根据团队用户数获取用户列表
     * @param ids
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo getUsers(Integer pageNum, Integer pageSize) {
        List<TeamEntity> teamEntities = new ArrayList<>();
        PageHelper.startPage(pageNum,pageSize);
        List<UserModel> userModels = userMapper.getUsers();
        PageInfo pageInfo = new PageInfo(userModels);
        List<UserModel> list = pageInfo.getList();
        //取用户团队详情
        for (UserModel userModel : list) {
            Ufilter ufilter = new Ufilter(userModel);
            TeamEntity team = getTeam(ufilter, -1, 1L);
            //获取用户团队充值,奖励总数
            TeamAmount teamAmount = new TeamAmount();
            teamAmount = getTeamAmount(team.getIds());
            if (teamAmount == null) {
                teamAmount = new TeamAmount();
            }
            team.setTotal(Long.valueOf(team.getIds().size()-1));
            team.setTeamRechargeAmount(teamAmount.getTeamRechargeAmount());
            team.setTeamRewardAmount(teamAmount.getTeamRewardAmount());
            teamEntities.add(team);
        }
        pageInfo.setList(teamEntities);
        return pageInfo;
    }

    /**
     * 增加账户可用金额
     * @param dfilter
     * @return
     */
    public int addTradableAmount(Dfilter dfilter) {
        int resutl = userMapper.addTradableAmount(dfilter.getId(),dfilter.getAmount());
        return resutl;
    }

    /**
     * 更新用户团队总人数
     * @param dfilter
     * @return
     */
    @Transactional
    @Scheduled(cron = "${team.Btime.cron}")
    public void updateTeamInfo(){
        List<UserModel> userModels = userMapper.getUsers();
        //定时更新团队总人数
        if (userModels.size() == 0){
            return;
        }
        for (UserModel userModel : userModels) {
            Ufilter ufilter = new Ufilter(userModel);
            TeamEntity team = getTeam(ufilter, -1, 1L);
            //获取用户总数
            int size = team.getIds().size() - 1;
            UserModel user = new UserModel(ufilter.getId(),size);
            userMapper.updateUserInfo(user);
        }
    }

    /**
     * 统计各级人数
     * @param team
     * @param teamLevelInfo
     * @return
     */
    public HashMap<Integer, Integer> getTeamLevelInfo(TeamEntity team, HashMap<Integer, Integer> teamLevelInfo) {
        List<TeamEntity> childs = team.getChilds();
        if (childs != null){
            if (!teamLevelInfo.containsKey(team.getLevel())){
                teamLevelInfo.put(team.getLevel(),1);
            }
            if (childs.size()>0){
                for (TeamEntity child :childs){
                    if (teamLevelInfo.containsKey(child.getLevel())){
                        teamLevelInfo.put(child.getLevel(),teamLevelInfo.get(child.getLevel()) + 1);
                    }else {
                        teamLevelInfo.put(child.getLevel(),1);
                    }
                    teamLevelInfo = getTeamLevelInfo(child,teamLevelInfo);
                }
            }
        }
        return teamLevelInfo;
    }

}
