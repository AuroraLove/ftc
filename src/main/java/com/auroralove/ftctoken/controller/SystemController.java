package com.auroralove.ftctoken.controller;

import com.auroralove.ftctoken.annotation.UserLoginToken;
import com.auroralove.ftctoken.mapper.SystemMapper;
import com.auroralove.ftctoken.model.HelpModel;
import com.auroralove.ftctoken.result.ResponseMessage;
import com.auroralove.ftctoken.result.ResponseResult;
import com.auroralove.ftctoken.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台管理入口类
 *
 * @author zyu
 * @date 2019/2/21
 */
@RestController
public class SystemController {

    @Autowired
    private SystemService systemService;

//    @UserLoginToken
    @PostMapping("/updateHelp")
    public ResponseResult updateHelp(HelpModel helpModel) throws Exception{
        int result = systemService.updateHelp(helpModel);
        if (result > 0) {
            return new ResponseResult(ResponseMessage.OK);
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }

//    @UserLoginToken
    @PostMapping("/deleteHelp")
    public ResponseResult deleteHelp(HelpModel helpModel) throws Exception{
        int result = systemService.deleteHelp(helpModel);
        if (result > 0) {
            return new ResponseResult(ResponseMessage.OK);
        }
        return new ResponseResult(ResponseMessage.INTERNAL_SERVER_ERROR);
    }
}
