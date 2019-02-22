package com.auroralove.ftctoken.service;

import com.auroralove.ftctoken.mapper.SystemMapper;
import com.auroralove.ftctoken.model.DataCenterModel;
import com.auroralove.ftctoken.model.HelpModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统参数服务
 *
 * @author zyu
 * @date 2019/1/24
 */
@Service
public class SystemService {

    @Autowired
    private SystemMapper systemMapper;

    /**
     * 获取用户数据中心
     * @return
     */
    public DataCenterModel getDataCenter() {
        DataCenterModel dataCenterModel = new DataCenterModel();
        dataCenterModel = systemMapper.getDataCenter();
        return dataCenterModel;
    }

    /**
     * 更新帮助中心
     * @return
     */
    public int updateHelp(HelpModel helpModel) {
        int i = systemMapper.updateHelp(helpModel);
        return i;
    }

    public int deleteHelp(HelpModel helpModel) {
        int i = systemMapper.deleteHelp(helpModel);
        //删除帮助中心图片
        int n = systemMapper.deletePicture(helpModel);
        return i;
    }
}
