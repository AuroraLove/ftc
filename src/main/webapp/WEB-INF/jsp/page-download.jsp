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
    <style type="text/css">
        *{margin:0; padding:0;}
        img{max-width: 100%; height: auto;}
        .test{height: 600px; max-width: 600px; font-size: 40px;}
    </style>
</head>

<script src="assets/js/vendor/jquery-2.1.4.min.js"></script>
<script src="assets/js/popper.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/jquery.matchHeight.min.js"></script>
<script src="assets/js/main.js"></script>

<body class="bg-white">
<div class="sufee-login d-flex align-content-center flex-wrap">

    <div align="center" style="width: 100%">
        <a href="index.jsp">
            <img class="align-content" src="images/download02.jpg" alt="">
        </a>
    </div>
    <div class="container">

        <div class="login-content">
            <div style="width: 100%;padding-bottom:10px;border-bottom: 25px;display:inline"align="center">
                    <div style="width: 40%;float: left;margin-bottom: 5%;margin-left: 10%;">
                    <input type="image" style="width: 90%" src="images/anroid.png" onclick="doAnroid()" ></input>
                    </div>
                <div style="width:40%;float: right;margin-right:10%;">
                <input type="image" style="width: 90%" src="images/ios.png" onclick="doIOS()" ></input>
                </div>

            </div>

            <div class="login-logo">
                <a href="index.jsp">
                    <img class="align-content" src="images/download04.jpg" alt="">
                </a>
            </div>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">
    function is_weixin() {
        var ua = navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i) == "micromessenger") {
            return true;
        } else {
            return false;
        }
    }
    var isWeixin = is_weixin();
    var winHeight = typeof window.innerHeight != 'undefined' ? window.innerHeight : document.documentElement.clientHeight;
    function loadHtml(){
        var div = document.createElement('div');
        div.id = 'weixin-tip';
        div.innerHTML = '<p><img src="live_weixin.png" alt="微信打开"/></p>';
        document.body.appendChild(div);
    }

    function loadStyleText(cssText) {
        var style = document.createElement('style');
        style.rel = 'stylesheet';
        style.type = 'text/css';
        try {
            style.appendChild(document.createTextNode(cssText));
        } catch (e) {
            style.styleSheet.cssText = cssText; //ie9以下
        }
        var head=document.getElementsByTagName("head")[0]; //head标签之间加上style样式
        head.appendChild(style);
    }
    var cssText = "#weixin-tip{position: fixed; left:0; top:0; background: rgba(0,0,0,0.8); filter:alpha(opacity=80); width: 100%; height:100%; z-index: 100;} #weixin-tip p{text-align: center; margin-top: 10%; padding:0 5%;}";

</script>
<script>
    function doAnroid(){
        if(isWeixin){
            loadHtml();
            loadStyleText(cssText);
        }else {
            window.location.href = '/ftctoken/ftctoken.apk';

        }
    }
    function doIOS(){
        // window.location.href = "itms-services://?action=download-manifest&url=https://ssl.v07.cn/jyJZ.plist";
            // window.location.href = '/ftctoken/ftctoken.ipa';
        if(isWeixin){
            loadHtml();
            loadStyleText(cssText);
        }else {
            window.location.href = 'itms-services://?action=download-manifest&url=https://www.futurecoin01.com/ftctoken/ipa.plist';
        }

    }

</script>
</html>
