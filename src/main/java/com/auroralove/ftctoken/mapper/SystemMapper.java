package com.auroralove.ftctoken.mapper;

import com.auroralove.ftctoken.model.DataCenterModel;
import com.auroralove.ftctoken.model.HelpModel;
import com.auroralove.ftctoken.model.SystemModel;

import java.util.List;

/**
 * @author zyu
 * @date 2019/1/28
 */
public interface SystemMapper {

    /**
     * 获取系统参数信息
     * @return
     */
    SystemModel getSystemInfo();

    /**
     * 获取帮助中心信息
     * @return
     */
    List<HelpModel> getHelpInfo();
    /**
     * 更新系统参数信息
     * @return
     */
    int updateSystem(SystemModel systemModel);

    /**
     * 数据中心
     * @return
     */
    DataCenterModel getDataCenter();

    int newHelp(HelpModel helpModel);

    int newPicture(HelpModel helpModel);

    int updateHelp(HelpModel helpModel);

    int deleteHelp(HelpModel helpModel);

    int deletePicture(HelpModel helpModel);
}
