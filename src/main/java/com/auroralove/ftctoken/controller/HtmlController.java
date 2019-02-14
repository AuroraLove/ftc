package com.auroralove.ftctoken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 静态页面REST访问接口
 * @author zyu
 * @date 2019-1-22  20:45
 */
@Controller
public class HtmlController {

    @GetMapping("/register")
    public String register(Long uid, HttpServletRequest request){
        request.setAttribute("uid",uid);
        return "page-register.jsp";
    }
    @GetMapping("/chart")
    public String chart(){
       // request.setAttribute("uid",uid);
        return "charts-chartjs.jsp";
    }
    @GetMapping("/chart1")
    public String chart1(){
        return "voucher_center.jsp";
    }

}
