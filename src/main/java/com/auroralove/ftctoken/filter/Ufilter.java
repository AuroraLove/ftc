package com.auroralove.ftctoken.filter;


import com.auroralove.ftctoken.model.UserModel;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户访问过滤器
 *
 * @author zyu
 * @date 2019/1/22
 */
@Data
public class Ufilter {

    /**
     *  用户id
     */
    private Long id;

    /**
     *  留言簿类别id
     */
    private Long tid;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户密码
     */
    private String passWord;

    /**
     * 用户访问token
     */
    private String token;

    /**
     * 手机验证码
     */
    private Integer code;

    /**
     *  我的团队id
     */
    private Long teamId;

    /**
     *  充值用户标识。0，未充值，1充值
     */
    private Integer rechargeFlag;

    /**
     *  邀请人id
     */
    private Long parentId;

    /**
     * 支付密码
     */
    private String payPwd;
    
    /**
     * 留言id
     */
    private Long mesId; 
    /**
     * 留言信息
     */
    private String message;

    /**
     * 留言凭证
     */
    private MultipartFile picture;
    
    /**
     * 留言类型,11留言，12回复留言
     */
    private Integer type;

    /**
     * 总价
     */
    private Double amount;

    /**
     * 图片地址
     */
    private String pictureUrl;

    /**
     * 用户管理状态
     */
    private Integer amdinstatus;

    /**
     * 管理员登录
     */
    private Integer amdinFlag;

    /**
     * 冻结标识，0,账户正常，1，账户一周,2,冻结一月,3,冻结永久
     */
    private Integer accountStatus;

    /**
     * 用户设备信息
     */
    private String userDevice;

    private Integer pageSize;

    private Integer pageNum;

    public Ufilter() {
    }

    public Ufilter(Long childId, String childPhone) {
    	this.id = childId;
    	this.phone = childPhone;
    }

    public Ufilter(UserModel userModel) {
        this.phone = userModel.getPhone();
        this.id = userModel.getId();
    }
}
