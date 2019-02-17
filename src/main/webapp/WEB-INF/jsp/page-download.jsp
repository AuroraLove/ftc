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
            <div style="width: 100%;padding-bottom:10px;border-bottom: 25px;display:inline"align="center">
                    <div style="width: 40%;float: left;margin-bottom: 5%;margin-left: 10%;">
                    <input type="image" style="width: 80%" src="images/anroid.png" onclick="doAnroid()" ></input>
                    </div>
                <div style="width:40%;float: right;margin-right:10%;">
                <input type="image" style="width: 80%" src="images/ios.png" onclick="doIOS()" ></input>
                </div>

            </div>

            <div class="login-logo">
                <a href="index.html">
                    <img class="align-content" src="images/download04.jpg" alt="">
                </a>
            </div>
        </div>
    </div>
</div>

</body>
<script>
    function doAnroid(){
        window.location.href = '/ftctoken/app-debug.apk';
    }
    function doIOS(){
        // window.location.href = '/download';
    }

</script>
</html>