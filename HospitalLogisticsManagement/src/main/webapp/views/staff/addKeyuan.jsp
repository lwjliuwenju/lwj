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
   
      

    <script type="text/javascript">
    var host = window.location.host;
	var windowPath = "http://"+host + "/";
       $("#save_close").click(function(){
            var rows=$("#departmentlist").datagrid('getSelected');
            var keyuanName =$("#keyuanName").val();
            var depId=rows.id;
            $.post(windowPath + "staff_addkeyuan.action",
            {"keyuanName":keyuanName,"depId":depId},  
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
