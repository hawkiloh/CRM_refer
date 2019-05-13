<html>
<head>
    <title>用户登录-客户管理系统</title>
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" type="text/css" href="/static/style/css/login.css?v=201612202107" />
    <script type="text/javascript" src="/static/js/jquery-1.11.1.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/static/js/login.js?v=20170115" charset="utf-8"></script>
    <script type="text/javascript" src="/static/scripts/boot.js"></script>
    <script type="text/javascript" src="/static/js/cookie_util.js"></script>
    <style type="text/css">
        .red{
            color: indianred;
        }

    </style>
</head>
<body onkeydown="enterlogin(event)" style="background: url(static/img/login-bg.jpg) no-repeat ;background-size: 100% 100%">
<div class="top_div"></div>
<div style="background: rgb(255, 255, 255);border-radius: 4px; margin: -100px auto auto; border: 1px solid rgb(231, 231, 231);border-image:none;width:400px;text-align: center;">
        <div style="width: 165px; height: 96px; position: absolute;">
            <div class="tou"></div>
            <div class="initial_left_hand" id="left_hand"></div>
            <div class="initial_right_hand" id="right_hand"></div>
        </div>
           <p style="padding-top: 20px; position: relative;font-size: 15px;color: rgb(26, 117, 151);font-weight: 600;">
               用户登陆
           </p>
        <p style="padding: 30px 0px 10px; position: relative;">
            <span class="u_logo"></span>
            <input class="ipt" type="text" name="username" placeholder="请输入登录名"/>
        </p>
        <p style="position: relative;">
            <span class="p_logo"></span>
            <input class="ipt" id="password" type="password" name="password" placeholder="请输入密码"/>
        </p>
        <P style="position: relative;text-align: left;margin-top: 5px;">
            <input class="rememberMe" type="checkbox" name="rememberMe" value="1" checked style="vertical-align:middle;margin-left:40px;height:20px;"/> 记住密码
        </P>
        <div style="height: 50px; line-height: 50px; margin-top: 10px;border-top-color: rgb(231, 231, 231); border-top-width: 1px; border-top-style: solid;">
            <P style="margin: 0px 35px 20px 45px;">
                <span style="float: left;">
                    <#--<a style="color: rgb(204, 204, 204);" href="javascript:;">忘记密码?</a>-->
                </span>
                <span style="float: right;">
                    <#--<a style="color: rgb(204, 204, 204); margin-right: 10px;" href="javascript:;">注册</a>-->
                    <a style="background: rgb(0, 142, 173);cursor: pointer; padding: 7px 10px; border-radius: 4px; border: 1px solid rgb(26, 117, 152); border-image: none; color: rgb(255, 255, 255); font-weight: bold;" onclick="submitForm()">登录</a>
                </span>
            </P>
        </div>
</div>
<div style="text-align:center;">
    <p>
        <a href="#" class="red">@CRM</a><span style="margin-left: 5px;color: #1b6d85">客户关系管理系统</span>
    </p>
</div>
</body>
<script style="text/javascript">
    //登陆
    function submitForm() {
        var username=$("[name='username']").val();
        var password=$("[name='password']").val();

        $.ajax({
            url: "/user/login",
            async: true,
            type: "post",
            data:{loginName:username,password:password},
            dataType: "json",
            success: function (response) {
                var user=response.data;
                if (response.state==0){
                    setCookie("loginName",username);
                    setCookie("userId",user.id);
                    window.location.href="/toIndex";
                }else{
                    mini.alert(response.message);
                }
               console.log(response);
            }
        });
    }

    //回车登录
    function enterlogin(event){
        if (event.keyCode == 13){
            event.returnValue=false;
            event.cancel = true;
            submitForm();
        }
    }
</script>
</html>
