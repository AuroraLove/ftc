package com.auroralove.ftctoken.mapper;

import com.auroralove.ftctoken.model.*;
import org.apache.ibatis.annotations.Param;

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

    int newPicture(@Param("pictureModels") List<PictureModel> pictureModels);

    int updateHelp(HelpModel helpModel);

    int deleteHelp(HelpModel helpModel);

    int deletePicture(HelpModel helpModel);

    List<SystemLevelModel> getSystemLevel();

    int updateSystemLevel(SystemLevelModel systemLevelModel);

    int updateHelpPicture(PictureModel pictureModel);

    Double getSystemAmount();

    int systemCancleDeal();
}
