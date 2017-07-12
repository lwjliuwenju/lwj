<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<head id="Head1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title></title>
    <link href="/Css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="/js/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="/js/themes/icon.css" />
	 <script type="text/javascript" src="../../Js/jquery.min.js"></script>
  <script type="text/javascript" src="../../Js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src='/js/outlook2.js'>
    </script>
    <style type="text/css" media="screen">
    .PopUpTableBorder {
        border-collapse: collapse;
        border-width: 1px;
        border-style: solid;
        border-color: #a4d5e6;
        -o-border-image: initial;
        border-image: initial;
        background-color: #eef9ff;
        margin: 5px auto;
    }
    
    .PopUpTableBorder .PopUpTableTitle {
        background-color: #eef9ff;
        text-align: right;
    }
    
    .PopUpTableBorder td {
        border-collapse: collapse;
        line-height: 30px;
        border-width: 1px;
        border-style: solid;
        border-color: #a4d5e6;
        -o-border-image: initial;
        border-image: initial;
    }
    
    .PopUpTableBorder .role,
    .confirm .PopUpTableBorder .username {
        line-height: 20px;
        height: 20px;
        border-width: 1px;
        border-style: solid;
        border-color: #629fcf;
        -o-border-image: initial;
        border-image: initial;
        margin: 4px;
    }
    
    .PopUpTableBorder .tishi {
        padding-left: 25px;
        font-size: 12px;
        line-height: 22px;
        vertical-align: middle;
        background: url(../imgages/onShow.gif) no-repeat #fff2e9;
    }
    
    .PopUpTableBorder .onfocus {
        background: url(../imgages/onBlue.png) no-repeat #fff2e9;
    }
    
    .PopUpTableBorder .onerror {
        background: url(../imgages/onError.gif) no-repeat #fff2e9;
    }
    
    .PopUpTableBorder .onCurrect {
        background: url(../imgages/onCorrect.gif) no-repeat #fff2e9;
    }
    
    .ButtonStyle_Blue {
        height: 22px;
        line-height: 22px;
        color: #000000;
        cursor: pointer;
        vertical-align: middle;
        margin-right: 4px;
        padding-left: 10px;
        padding-right: 10px;
        border-width: 1px;
        border-style: solid;
        border-color: #80b5dd;
        -o-border-image: initial;
        border-image: initial;
        background: url(../imgages/button_bg_blue.gif) repeat-x;
        border-radius: 5px;
    }
    .PopUpTableBorder td{
        padding: 0px 10px;
    }
    .butList {
        margin: 10px auto;
        width: 300px;
    }
    </style>

    <body class="easyui-layout" style="overflow-y: hidden" scroll="no">
        <table class="PopUpTableBorder" border="0" cellspacing="0" cellpadding="0">
            <tbody>
            	 <tr>
                    <td class="PopUpTableTitle" width="150px">
                        <font color="#ff0000">*</font>
                        申请单ID:
                    </td>
                    <td width="390px">
                        <input id="proposerid" type="text" disabled="disabled">
                    </td>
                </tr>
                <tr>
                    <td class="PopUpTableTitle" width="150px">
                        <font color="#ff0000">*</font>
                        耗材名称:
                    </td>
                    <td width="500px">
                        <input id="suppliesName" name="dept">
                    </td>
                </tr> 
                <tr>
                    <td class="PopUpTableTitle" width="150px">
                        <font color="#ff0000">*</font>
                        耗材数量:
                    </td>
                    <td width="500px">
                     <input id="suppliesNum" name="dept">
                    </td>
                </tr>
                <tr>
                    <td class="PopUpTableTitle" width="150px">
                        <font color="#ff0000">*</font>
                     创建时间 :
                    </td>
                    <td width="390px">
                        <input id="createTime" type="number">小时
                    </td>
                </tr>
                  <tr>
                    <td  width="400px" colspan="2" align="center">
                       注释: 所有加<font color="#ff0000">*</font>为必填项
                    </td>
                </tr>
            </tbody>
        </table>
        <div class="butList">
            <input type="button" name="" value="保存并关闭" class="ButtonStyle_Blue" id="save_close">
            <input type="button" name="" value="保存并继续" class="ButtonStyle_Blue" id="save_continue">
        </div>
        <script type="text/javascript">
          $('#suppliesName').combobox({
              	url:windowPath+'supplies_findAll.action', 
                valueField: 'id',
                textField: 'text'
            });
          $(function() {
			var host = window.location.host;
			var windowPath = "http://"+host + "/";
            $("#save_close").click(function() {
                var createTime = $("#createTime").val();
                var suppliesNum = $("#suppliesNum").val();
                var suppliesName = $("#suppliesName").combobox('getText');
                var node = $('#serviceFormlist').datagrid('getSelected');
                var proposerId = node.ID_;
                url2 = windowPath+'proposer_saveSupliesform.action';
                $.post(url2, {
                        suppliesNum: suppliesNum,
                        suppliesName: suppliesName,
                        proposerId:proposerId
                    },
                    function(data) {
                        var code = JSON.parse(data).code_;
                        if (code == '0') {
                            location.reload();
                            alert('申请成功');
                        } else {
                            alert('申请失败,请重试!');
                        }
                    });
            });
        });
		</script>
    </body>

</html>
