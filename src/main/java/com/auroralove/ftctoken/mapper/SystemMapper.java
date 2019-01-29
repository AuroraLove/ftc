package com.auroralove.ftctoken.mapper;

import com.auroralove.ftctoken.model.SystemModel;

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
}
