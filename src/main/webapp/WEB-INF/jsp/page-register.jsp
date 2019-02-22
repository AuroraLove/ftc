<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang=""> <!--<![endif]-->

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>邀请注册</title>
    <meta name="description" content="欢迎来到FtcToken，邀请您的加入！">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="assets/css/normalize.css">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/themify-icons.css">
    <link rel="stylesheet" href="assets/css/pe-icon-7-filled.css">
    <link rel="stylesheet" href="assets/css/flag-icon.min.css"><link rel="stylesheet" href="assets/css/cs-skin-elastic.css">
    <link rel="stylesheet" href="assets/css/style.css">

    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>

    <!-- <script type="text/javascript" src="https://cdn.jsdelivr.net/html5shiv/3.7.3/html5shiv.min.js"></script> -->
</head>

<script src="assets/js/vendor/jquery-2.1.4.min.js"></script>
<script src="assets/js/popper.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/jquery.matchHeight.min.js"></script>
<script src="assets/js/main.js"></script>

<body class="bg-dark">

<div class="sufee-login d-flex align-content-center flex-wrap">
    <div class="container">
        <div class="login-content">
            <div class="login-logo">
                <a href="index.jsp">
                    <img class="align-content" src="logo.png" alt="">
                </a>
            </div>
            <div class="login-form">
              <%--  <form action="/v1/rest/regist">--%>

                    <div class="form-group">
                        <label>注册手机号</label>
                        <input type="phone" id="phone" class="form-control" >
                    </div>
                    <div class="form-group" style="display: none">
                        <label>邀请人</label>
                        <input type="parentid" id="parentId" value="${uid}" class="form-control" disabled="disabled">
                    </div>
                    <div class="form-group">
                        <label>密码</label>
                        <input type="password" id="passWord" class="form-control" >
                    </div>
                  <div class="form-group">
                      <label>确认密码</label>
                      <input type="password" id="rePassWord" class="form-control" >
                  </div>
                  <div class="form-group">
                      <label>验证码</label>
                      <input type="phone" id="code" class="form-control" >
                      <div class="input-group-addon"style="margin-top: 5%"><a style="width: 100%;height: 100%" href="javascript:xiaoyueLogin();" id="verbtn" >获取验证码</a></div>
                  </div>
                    <input type="submit" onclick="doSubmit()" class="btn btn-primary btn-flat m-b-30 m-t-30"></input>
                    <div class="social-login-content">
                    </div>
                    <div class="register-link m-t-15 text-center">
                        <p>FtcToken邀请注册</p>
                    </div>
            </div>
        </div>
    </div>
</div>

</body>
<script>
    var InterValObj; //timer变量，控制时间
    var count = 60; //间隔函数，1秒执行
    var curCount; //当前剩余秒数
    jQuery(function () {
        jQuery("#verbtn").click(function () {})
        jQuery("#lkchage").click(function () {
            var mobile=jQuery("#phone").val();
            var ver = jQuery("#reg_verify").val();
            if(ver==""){
                alert("验证码不能为空");
                return;
            }
            jQuery.post("/ucenter/member/foundpassword",{mobile:mobile,reg_verify:ver},function (data) {
                console.log(data);
                alert(data.msg);
                location.href="/ucenter/member/login.jsp";
            })
        })


    })
    function xiaoyueLogin() {
        var mobile=jQuery("#phone").val();
        var reg = /^1[34578]\d{9}$/;
        if(!mobile){
            alert('手机号不能为空');
            return false;
        }
        if (!reg.test(mobile)) {
            alert('手机号码格式不正确');
            return false;
        }
        curCount = count;
        jQuery("#verbtn").removeAttr("href");
        jQuery("#verbtn").html("验证码(" + curCount + ")");
        InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
        jQuery.ajax({
            url:"v1/rest/sendMsg",
            type:"post",
            data:{
                "phone":jQuery("#phone").val()
            },
            success:function(data){
                if(data.status==200){
                    alert("短信发送成功！")
                }else{
                    alert("短信发送失败，请重试！");
                }
            }
        });
    }
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj); //停止计时器
            jQuery("#verbtn").attr("href", "javascript:xiaoyueLogin();");
            jQuery("#verbtn").html("重新发送验证码");
        }
        else {
            curCount--;
            jQuery("#verbtn").html("验证码(" + curCount + ")");
        }
    }

</script>
<script>
        function doSubmit(){
        var str = jQuery("#passWord").val();
        var str1 = jQuery("#rePassWord").val();
        var phone = jQuery("#phone").val();
        if(!(/^1[34578]\d{9}$/.test(phone))) {
            alert("您输入的手机号码有误，请重填!");
            return false;
        }
         if (str == null || str.length <6 || str.length >12) {
            alert("您输入的密码长度应为6~12位!");
            return false;
        }
         if (str != str1){
             alert("您输入的两次密码不一致!");
             return flase;
         }
        var reg = new RegExp(/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/);
        if (!reg.test(str)) {
            alert("您的密码应包含数字加字母 不能包含特殊符号！");
            return false;
        }

        jQuery.ajax({
            url:"/ftctoken/v1/rest/regist",
            type:"post",
            data:{
                "phone":jQuery("#phone").val(),
                "parentId":jQuery("#parentId").val(),
                "passWord":jQuery("#passWord").val(),
                "code":jQuery("#code").val()
            },
            success:function(data){
                if(data.status==200){
                    alert("注册成功！")
                    window.location.href = '/ftctoken/download';
                }else{
                    alert(data.message);
                }
            }
        });
    }
</script>
</html>
