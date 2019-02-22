package com.auroralove.ftctoken.filter;

import com.auroralove.ftctoken.model.PublicInfoModel;
import lombok.Data;

import java.util.List;

/**
 * 系统参数过滤器
 * @author zyu
 * @date 2019/2/20
 */
@Data
public class Sfilter {

    private Double eos;

    private Double ftc;

    private Double FTClocked;

    private Double ftcPrice;

    private Double univalent;

    private Double quantity;

    private Integer userLevel;

    private String version;

    private String comminssionScheme;

    private String gatheringCode;

    private String systemAccount;
}
