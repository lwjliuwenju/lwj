<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>登录页面</title>
    <link rel="stylesheet" type="text/css" href="Js/themes/login.css" />
   <link rel="stylesheet" type="text/css" href="Js/themes/reset.css" />
     <script type="text/javascript" src='../../Js/json2.js'></script>
    <script type="text/javascript" src="../../Js/jquery.min.js"></script>
     <script type="text/javascript"  >
		     $(function() {
		    var host = window.location.host;
			var windowPath = "http://"+host + "/";
		    var login =  {
		        init: function() {
		            this.userLogin();
		            this.reset();
		        },
		        reset:function(){
		        	$('.reset').click(function(e){
		        	e.preventDefault();
		        	$('.input').val('');
		        	});
		        }
		        ,
		        userLogin: function() {
		            $('.login').click(function(e) {
		                e.preventDefault();
		                var username = $('.number > input').val();
		                var password = $('.password > input').val();
		                if (username == "" || password == "") { //判断两个均不为空（其他判断规则在其输入时已经判断） 
		                    alert("用户名密码均不能为空！")
		                    return false;
		                } else {
		                    $.ajax({
		                        url: windowPath+"login_login.action",
		                        data: {
		                            username: username,
		                            password: password
		                        },
		                        type: 'post',
		                        success: function(res) {
		                         
		                            var a = JSON.parse(res);
		                            
		                            var code = JSON.parse(res).code_;
		                            if (code == '0') {
		                            	var _menus = a.data.menus;
			                            var deps = a.data.deps;
			                            var userid = a.data.user.id
		                                sessionStorage.setItem('leftMenu', JSON.stringify(_menus));
		                                sessionStorage.setItem('username', username);
		                                sessionStorage.setItem("userid", userid);
		                                if(deps.length == 0){
		                                	var obj = {};
		                                	obj.id = 0;
		                                	deps.push(obj);
		                                }
		                                sessionStorage.setItem('deps', JSON.stringify(deps));
		                                location.href = 'index.jsp';
		                            }else{
		                             var a = JSON.parse(res);
		                            alert(a.message_);
		                            }
		                        }
		                    })
		                }
		            });
		            $(document).keyup(function(event) {
		                if (event.keyCode == 13) {
		                    $(".login").trigger("click");
		                }
		            });
		        }
		    }
		    login.init();
		});
    </script>
</head>
<body>
    <div class="container" >
    <img alt="" src="images/bg.gif" style="width:100%;">
        <div class="content clear">
                <div class="left-logo">
                    <img src="images/timg.jpg" alt="">
                </div>
                
            <div class="userinfo">
            <h3 class="title-desc">医院后勤综合管理系统</h3>
                <div class="number">
                    <label class="userinfo-desc">用户名:</label>
                    <input type="text" placeholder="请输入你的用户名" class="input">
                </div>
                <div class="password">
                    <label class="userinfo-desc">密&nbsp;&nbsp;&nbsp;码:</label>
                    <input type="password" placeholder="请输入密码" class="input">
                </div>
                <div class="submit">
                    <div class="login">
                        <a href="">登录</a>
                    </div>
                    <div class="reset">
                        <a href="">重置</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="copyright clear">
        <img src="images/logo.png" width="15" height="15" style="display:inline; margin-bottom:-2px;">
            Copyright © 2016 Shijin Limited All Rights Reserved 郑州时进科技有限公司版权所有
        </div>
    </div>
</body>
</html>
