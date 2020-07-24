<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/bootstrap3/css/bootstrap.min.css"/>
    <link href="<%=request.getContextPath() %>/resources/img/logo.png" rel="Shortcut Icon">
    <title>选片看样系统</title>
    <style type="text/css">
        body{
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            min-width: 1200px;
            min-height: 660px;
        }
        .head{
            text-align: center;
        }
        .main{
            width: 100%;
            min-height: 460px;
            display: flex;
            justify-content: center;
            align-items: center;
            background-image: linear-gradient(#ee9ca7, #ffdde1);
        }
        .main_content{
            width: 600px;
            height: auto;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 40px 80px;
            background-color: rgba(250, 250, 250, 0.4);
        }
        .header {
            width: 100%;
            padding: 0 50px;
            box-sizing: border-box;
            height: 60px;
            display: flex;
            align-items: center;
            font-family: 'Myriad Pro', 'Helvetica Neue', Arial, Helvetica, sans-serif;
            color: #333;
            font-size: 14px;
            background-color: #fff;
        }
        .footer {
            width: 100%;
            height: 60px;
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
            font-family: 'Myriad Pro', 'Helvetica Neue', Arial, Helvetica, sans-serif;
            color: #333;
            font-size: 14px;
            background-color: #fff;
        }
        .login-form{
            width: 368px;
            margin: 0 auto;
        }
        .form-control{
            font-family: "Helvetica Neue For Number", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, sans-serif;
            -webkit-box-sizing: border-box;
            box-sizing: border-box;
            margin: 0;
            list-style: none;
            position: relative;
            display: inline-block;
            padding: 6px 11px 6px 30px;
            width: 100%;
            height: 40px;
            font-size: 14px;
            line-height: 1.5;
            color: rgba(0, 0, 0, 0.65);
            background-color: #fff;
            background-image: none;
            border: 1px solid #d9d9d9;
            border-radius: 4px;
            -webkit-transition: all .3s;
            -o-transition: all .3s;
            transition: all .3s;
        }
        .submitBtn{
            color: #fff;
            background-color: #eb6383;
            padding: 0 15px;
            font-size: 14px;
            border-radius: 4px;
            height: 40px;
            width: 100%;
            margin-top: 24px;
            border: 1px solid transparent;
            cursor: pointer;
        }
        .submitBtn:active{
            color: #fff;
            background-color: #DC6077;
            border-color: #DC6077;
        }
        ::-webkit-input-placeholder {/*Chrome/Safari*/
            color:#bfbfbf;
        }
        ::-moz-placeholder {/*Firefox*/
            color:#bfbfbf;
        }
        :-ms-input-placeholder {/*IE*/
            color:#bfbfbf;
        }
        .loginIcon{
            position: absolute;
            top: 12px;
            left: 5px;
            z-index: 10;
        }
        input:checked+label{
            background-color: #1890ff;
            border: 1px solid #1890ff;
        }
        input:checked+label::after {
            content: "";
            position: absolute;
            width: 9px;
            height: 5px;
            border: 2px solid #fff;
            top: 3px;
            left: 3px;
            border-top: none;
            border-right: none;
            transform: rotate(-45deg);
        }
        .alert-danger {
            color: #a94442;
            background-color: #f2dede;
            border-color: #ebccd1;
        }
        .alert {
            padding: 10px 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<div class="header">
    <img src="<%=request.getContextPath()%>/resources/img/icon-20.png" height="20" alt="logo">
    <span style="display: inline-block; margin-left: 10px;">选片看样系统</span>
</div>
<div class="main" id="main">
    <div class="main_content">
        <div class="head">
            <img src="<%=request.getContextPath()%>/resources/img/icon-80.png" height="80" alt="logo">
            <div style="margin-top: 10px; font-size: 20px; margin-bottom: 30px; color: #eb6383; font-weight: bold;">选片看样系统</div>
            <div style="clear: both"></div>
        </div>
        <form class="login-form" action="login" method="post" id="login">
            <%if (request.getParameter("error") != null) {%>
            <div class="col-sm-offset-0 col-sm-18">
                <div class="alert alert-danger" style=""role="alert">
                    <%=((Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION")).getMessage()%>
                </div>
            </div>
            <%}%>
            <div class="form-group">
                <div style="position: relative;margin-bottom: 30px">
                    <img src="<%=request.getContextPath()%>/resources/img/user.png" alt="" width="18px" class="loginIcon">
                    <input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名">
                </div>
            </div>
            <div class="form-group">
                <div style="position: relative">
                    <img src="<%=request.getContextPath()%>/resources/img/pwd.png" alt="" width="17px" class="loginIcon">
                    <input type="password" class="form-control" id="password" name="password" placeholder="密码">
                </div>
            </div>
            <div class="form-group">
                <div>
                    <button type="submit" class="submitBtn">登   录</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    var clientHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
    document.getElementById('main').style.height = clientHeight - 120 + 'px';
</script>
<div class="footer">
    重庆米弘科技有限公司 技术支持 © 2020
</div>
<script src="<%=request.getContextPath() %>/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/login/md5-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/login/login.js"></script>
</body>
</html>