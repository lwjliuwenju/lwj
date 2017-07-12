<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
  <head>
         <style>
         
.PopUpTableBorder {
  border-collapse: collapse;
  border-width: 1px;
  border-style: solid;
  border-color: #a4d5e6;
  -o-border-image: initial;
     border-image: initial;
  background-color: #eef9ff;
  margin: 3px;
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
}
.butList {
  margin: 10px auto;
  width: 300px;
}
         
         </style>
        <table class="PopUpTableBorder" border="0" cellspacing="0" cellpadding="0">
            <tbody>
                 <tr>
                    <td class="PopUpTableTitle" width="200px">
                        <font color="#ff0000">*</font>
                      科室名称:
                    </td>
                    <td width="390px">
                         <input  id ="depName" type="text">
                    </td>
                </tr>
                <tr>
                    <td class="PopUpTableTitle" width="200px">
                        <font color="#ff0000">*</font>
                      是否启用为运输部门:
                    </td>
                    <td width="390px">
                         <input type="radio" name="isAuto" value="1"/>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" checked="checked" name="isAuto" value="0" />否
                    </td>
                </tr>
                 <tr>
                    <td class="PopUpTableTitle" width="200px">
                        <font color="#ff0000">*</font>
                      科室电话:
                    </td>
                    <td width="390px">
                         <input  id ="depPhone" type="text">
                    </td>
                </tr>
                <tr>
                    <td class="PopUpTableTitle" width="200px">
                        <font color="#ff0000">*</font>
                        科室描述:
                    </td>
                    <td width="390px">
                       <textarea id="mark"></textarea>
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
     <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save_close">
        <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="save_continue">
    </div>
</div>
    <script type="text/javascript">
    var host = window.location.host;
	var windowPath = "http://"+host + "/";
       $("#save_close").click(function(){
            var mark =$("#mark").val();
            var depName =$("#depName").val();
            var depPhone = $("#depPhone").val();
            
            var isAutoSend = document.getElementsByName('isAuto');
             var selectvalue=null;   //  selectvalue为radio中选中的值
            for (var i = 0; i < isAutoSend.length; i++) {
                if (isAutoSend[i].checked == true) {
                     selectvalue=isAutoSend[i].value;
                }
            }
            $.post(windowPath + "dep_addDep.action",
            {"mark":mark,"depName":depName,"depPhone":depPhone,isAutoSend:"selectvalue","selectvalue":selectvalue},  
            function(data){
            console.log(data);
             var code = JSON.parse(data).code_;
                        if (code == '0') {
                            location.reload();
                            alert('新建成功');
                        } else {
                            alert('新建失败,请重试!');
                        }
            });
            
       });
    </script>
  </head>
  
</html>
