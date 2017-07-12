<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head id="Head1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>医院后勤管理服务平台</title>
    <link href="css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="Js/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="Js/themes/icon.css" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
      <style type="text/css" media="screen">
   li{
      list-style:none;
      }
        #h-header{ overflow:hidden; background:#00aae8 url(images/header_bg.jpg) no-repeat -223px top;height:98px;}
		.header_in{  width:1000px; height:100px;}
		.logo{ float:left; overflow:hidden; margin:16px 24px 0 -2px;}
		.h-nav{ float:left; width:640px; overflow:hidden; height:75px; margin:24px 0 0 0; font-size:14px;}
		.h-nav .top{ width:215px; height:24px; line-height:24px; float:right; margin-top:8px;}
		.h-nav .top li{ float:left;_display:inline; padding-left:20px; margin-right:20px;}
		.header_in .nav a{ color:#FFF;}
		.down{ width:640px; height:30px; line-height:30px; color:#000; float:left;margin-top:8px;margin-left:142px; }
		.down li{ float:left; height:30px;width:170px;}
		.down .date{ width:210px;}
		.down li span{ display:inline-block; width:2px; height:30px; background:url(../images/s1.jpg) no-repeat center;}
		.down li p{float:left;margin-left:6px;}
		.top li a{
		color:#fff;
		}
    #message {
        position: absolute;
        right: 0;
        bottom: 0px;
        width: 300px;
        height: 300px;
        display: none;
        border: 1px solid #4284d9;
    }
.clearfix:after{
  clear: both;
  display: block;
  height: 0px;
  content: "";
  overflow: hidden;
}
    * {
        box-sizing: border-box;
        margin:0;
        padding:0;
    }
    
    .messageTitle {
        background-color: #4284d9;
        height: 26px;
        padding: 2px 0px;
    }
    img { border:0px;}
    .clearfix {
        zoom:1;
    }
    
    
    #messageContent {
        padding: 0px 10px;
        height:100%;
        background-color: #fff;
    }
    #closeWebSocketMessage {
        float: right;
        font-size: 16px;
        cursor: pointer;
        line-height: 26px;
        color: red;
    }
    #west{
       background-color: blue;
    }
    #d{
    	display: hidden;
    }
    </style>
    <script type='text/javascript' src='/dwr/engine.js'></script>  
    <script type='text/javascript' src='/dwr/interface/DwrTest.js'></script> 
     <script type="text/javascript" src="Js/jquery.min.js"></script>
     <script type="text/javascript" src='Js/json2.js'></script>
  <script type="text/javascript" src="Js/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="Js/pinyin.js"></script>
    <script type="text/javascript" src='Js/outlook2.js'></script>
    <script type="text/javascript">
    var host = window.location.host;
	var windowPath = "http://"+host + "/";
    var _menus = JSON.parse(sessionStorage.getItem('leftMenu'));
    var userId = sessionStorage.getItem('userid');
   
    //设置登录窗口
    function openPwd() {
        $('#w').window({
            title: '修改密码',
            width: 300,
            modal: true,
            shadow: true,
            closed: true,
            height: 160,
            resizable: false
        });
    }
    //关闭登录窗口
    function closePwd() {
        $('#w').window('close');
    }



    //修改密码
    function serverLogin() {
        var $newpass = $('#txtNewPass');
        var $rePass = $('#txtRePass');

        if ($newpass.val() == '') {
            msgShow('系统提示', '请输入密码！', 'warning');
            return false;
        }
        if ($rePass.val() == '') {
            msgShow('系统提示', '请在一次输入密码！', 'warning');
            return false;
        }

        if ($newpass.val() != $rePass.val()) {
            msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
            return false;
        }

        $.post('/ajax/editpassword.ashx?newpass=' + $newpass.val(), function(msg) {
            msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
            $newpass.val('');
            $rePass.val('');
            close();
        })

    }

    $(function() {

        openPwd();

        $('#editpass').click(function() {
            $('#w').window('open');
        });

        $('#btnEp').click(function() {
            serverLogin();
        })

        $('#btnCancel').click(function() {
            closePwd();
        })

        $('#loginOut').click(function() {
            $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

                if (r) {
                	$.ajax({url:windowPath + "logout",success:function(){
                    	location.href = 'login.jsp';
                	}});
                }
            });
        });
       
    });
    $(function() {
    
    /*  信息*/
    var date = new Date().toLocaleDateString();
    $('#current-date').html(date);
    var a = new Array("日", "一", "二", "三", "四", "五", "六");  
	var week = new Date().getDay();  
	var str = "星期"+ a[week];  
	 $('#current-week').html(str);
	 /* 当前用户 */
	 var username = sessionStorage.getItem('username');
	  $('#current-user').html(username);
	 
})
    </script>
</head>

<body class="easyui-layout" style="overflow-y: hidden" scroll="no" onload="dwr.engine.setActiveReverseAjax(true);dwr.engine.setNotifyServerOnPageUnload(true);onPageLoad();">
    <noscript>
        <div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
            <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
        </div>
    </noscript>
    <div region="north" split="true" border="false" id="h-header" style="height:98px;">
	       <div class="header_in clearfix">
	        	<h1 class="logo"><a href="#"><img src="images/system_logo.jpg" /></a></h1>
	            <div class="h-nav clearfix">
	            	<ul class="top clearfix">
	                	<li style="background:url(images/sys_icon1.jpg) no-repeat left center;"><a href="#">网站首页</a></li>
	                	<li style="background:url(images/sys_icon2.jpg) no-repeat left center;"><a  href="javascript:" id="loginOut" >安全退出</a></li>
	                </ul>
	                <ul class="down clearfix">
	                	<li class="clearfix"><p>用户 :</p> <p id="current-user"></p></li>
	                	<li class="date clearfix" >
	                	<p>当前时间 :</p>
	                	<p id="current-date"></p>
	                <!-- 	<p id="current-week"></p> -->
	                	</li>
	                </ul>
	            </div>
	        </div>	
    </div>
    <!--头部结束  -->
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
            <center><strong>Copyright © 2016 Shijin Limited All Rights Reserved 郑州时进科技有限公司版权所有</strong></center>
    </div>
    <div region="west" hide="true"  split="true" title="导航菜单" style="width:180px;" id="west">
        <div id="nav" class="easyui-accordion" fit="true" border="false">
            <!--  导航内容 -->
        </div>
    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs" fit="true" border="false">
        </div>
    </div>
    <!--修改密码窗口-->
    <div id="w" class="easyui-dialog" title="修改密码" closed="true" collapsible="false" minimizable="false" maximizable="false" icon="icon-save" style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding=3>
                    <tr>
                        <td>新密码：</td>
                        <td>
                            <input id="txtNewPass" type="Password" class="txt01" />
                        </td>
                    </tr>
                    <tr>
                        <td>确认密码：</td>
                        <td>
                            <input id="txtRePass" type="Password" class="txt01" />
                        </td>
                    </tr>
                </table>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)">
                    确定</a> <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
            </div>
        </div>
    </div>
    <div id="mm" class="easyui-menu" style="width:150px;">
        <div id="mm-tabupdate">刷新</div>
        <div class="menu-sep"></div>
        <div id="mm-tabclose">关闭</div>
        <div id="mm-tabcloseall">全部关闭</div>
        <div id="mm-tabcloseother">除此之外全部关闭</div>
        <div class="menu-sep"></div>
        <div id="mm-tabcloseright">当前页右侧全部关闭</div>
        <div id="mm-tabcloseleft">当前页左侧全部关闭</div>
        <div class="menu-sep"></div>
        <div id="mm-exit">退出</div>
    </div>
    <div id="message">
        <div class="messageTitle clear">
            <span id="closeWebSocketMessage">X</span>
        </div>
        <div id="messageContent"></div>
    </div>
</body>
<script type="text/javascript">
	function onPageLoad(){
             DwrTest.onPageLoad();
         };
    function showMessage(data){
    	refresh();
    	if (data) {
            $('#message').show(1000);
            document.getElementById('messageContent').innerHTML = data + '<br/>';
        } else {
            $('#message').hide();
        }
    };
    $('#closeWebSocketMessage').click(function(){
    	$('#message').hide();
    });
</script>
</html>
