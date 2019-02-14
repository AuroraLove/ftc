<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang=""> <!--<![endif]-->

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>下载客户端</title>
    <meta name="description" content="FtcToken下载页面">
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

<body class="bg-white">

<div class="sufee-login d-flex align-content-center flex-wrap">

    <div align="center" style="width: 100%">
        <a href="index.html">
            <img class="align-content" src="images/download02.jpg" alt="">
        </a>
    </div>
    <div class="container">


        <div class="login-content">
            <div style="width: 100%;padding-bottom:10px;border-bottom: 10px">
                    <div style="width: auto;float: left">
                    <input type="image" src="images/anroid.png" onclick="doSubmit()" ></input>
                    </div>
                <div style="float: right;width: auto">
                <input type="image" src="images/ios.png" onclick="doSubmit()" ></input>
                </div>

            </div>

            <div class="login-logo">
                <a href="index.html">
                    <img class="align-content" src="images/download04.jpg" alt="">
                </a>
            </div>

    </div>
</div>

</body>
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
        if (reg.test(str)) {
            return true;
        } else {
            alert("您的密码应包含数字加字母 不能包含特殊符号！");
            return false;
        }

        jQuery.ajax({
            url:"/v1/rest/regist",
            type:"post",
            data:{
                "phone":jQuery("#phone").val(),
                "parentId":jQuery("#parentId").val(),
                "passWord":jQuery("#passWord").val()
            },
            success:function(data){
                if(data.msg==true){
                    alert("注册成功！");
                }else{
                    alert("注册失败");
                }
                // window.location.reload();//刷新当前页面
            }
        });
    }


</script>
</html>
