<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
 <!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title></title>
    <link rel="stylesheet" type="text/css" href="../../Js/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="../../Js/themes/icon.css" />
    <style type="text/css" media="screen">
    * {
        margin: 0;
        padding: 0;
    }
    
    #serviceFormTools input,
    #state,
    #shunxu {
        font-size: 12px;
        border: 0px;
        line-height: 20px;
        height: 20px;
        padding: 0px;
        border: 1px solid #A4BED4;
    }
    
    .PopUpTableBorder {
        margin: 5px auto;
    }
    
    #serviceFormTools div {
        margin: 5px;
    }
    
    li {
        list-style: none;
    }
    
    h1,
    h2,
    h3,
    h4,
    h5 {
        font-weight: normal;
    }
    
    .timeline-header {
        margin: 0;
        color: #00c0ee;
        border-bottom: 1px solid #f4f4f4;
        padding: 10px;
        font-size: 16px;
        line-height: 1.1;
    }
    
    .timeline-body {
        padding: 10px;
    }
    
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
    
    td {
        padding: 4px;
    }
    </style>
</head>

<body>
    <div class="serviceformIndex">
        <div class="serviceFormlist">
            <div align="left" id="serviceFormTools">
                <div style="margin:2px 0px;"> 申&nbsp;&nbsp;&nbsp;&nbsp;请&nbsp;&nbsp;&nbsp;&nbsp;人:
                    <input id="submitperson" style="width:200px;margin:0px;"></input>
                    状&nbsp;&nbsp;&nbsp;&nbsp;态：
                    <SELECT id='state' class="easyui-combobox" style="width:200px;margin:0px;">
                        <option value="0">请选择</option>
                        <option value="1">异常</option>
                        <option value="2">待响应</option>
                        <option value="3">响应中</option>
                        <option value="4">已完成</option>
                        <option value="5">已终止</option>
                        <option value="6">已评价</option>
                    </SELECT>
                    排序方式：
                    <SELECT id='shunxu' class="easyui-combobox" style="width:200px;margin:0px;">
                        <option value="0">请选择</option>
                        <option value="1">状态</option>
                        <option value="2">时间</option>
                    </SELECT>
                </div>
                <div style="margin:2px 0px;">
                    申&nbsp请&nbsp部&nbsp门：
                    <input id="shenqingdepartment" style="width:200px;margin:0px;"></input>
                    响应部门：
                    <input id="jieshoubumen" style="width:200px;margin:0px;"></input>
                    响应人员：
                    <input id="zhixingperson" style="width:200px;margin:0px;"></input>
                </div>
                <div style="margin:2px 0px;">
                    开&nbsp始&nbsp时&nbsp间:
                    <input id="startTime" name="startTime" class="easyui-datebox" style="width:200px;margin:0px;"></input>
                    &nbsp结束时间:
                    <input id="endTime" name="endTime" class="easyui-datebox" style="width:200px;margin:0px;"></input>
                </div>
                <div>
                    <a id="searchServiceform" href="javascript:void(0)" iconCls="icon-search" class="easyui-linkbutton">查询</a>
                    <a id="insertServiceform" href="javascript:void(0)" iconCls="icon-add" class="easyui-linkbutton">添加申请单</a>
                    <a id="seeDetails" href="javascript:void(0)" iconCls="icon-more" class="easyui-linkbutton">查看详情</a>
                    <a id="evaluate" href="javascript:void(0)" iconCls="icon-large-chart" class="easyui-linkbutton">评价</a>
                    <a id="supplies_cai" href="javascript:void(0)" iconCls="icon-large-shapes" class="easyui-linkbutton">耗材</a>
                    <a id="complete" iconCls="icon-ok" href="javascript:void(0)" class="easyui-linkbutton">完成</a>
                    <a id="logisticscall" href="javascript:void(0)" iconCls="icon-back" class="easyui-linkbutton">物流通知</a>
                    <a id="logisticsinfo" href="javascript:void(0)" iconCls="icon-large-smartart" class="easyui-linkbutton">物流填写</a>
                </div>
            </div>
            <div id='serviceFormlist' style="min-height:500px;"></div>
        </div>
        <div>
            <div class="menutree">
                <div id='menutree'></div>
            </div>
            <div id="mm" class="easyui-menu" style="width:120px;">
                <div onclick="add()" data-options="iconCls:'icon-add'">选择服务</div>
                <div onclick="edit()" data-options="iconCls:'icon-edit'">修改服务</div>
            </div>
        </div>
        <!-- 添加耗材管理 -->
        <div id="suppliesDialog" class="easyui-dialog" title="选择耗材" closed="true" style="width:500px;height:500px;">
            部门：
            <input type="text" name="" id="supDep">
            <div id="suppliesDatagrid"></div>
            <div class="butList" style="width:200px;">
                <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save-s">
                <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close-s">
            </div>
        </div>
        <!-- 通知物流部门 -->
        <div class="easyui-dialog" title="物流通知" closed="true" id="callLogistics" style="width:500px;height:450px;">
            <table class="PopUpTableBorder" border="0" cellspacing="0" cellpadding="0" style="width:100%;">
                <tbody>
                    <tr>
                        <td class="PopUpTableTitle" width="150px">
                            <font color="#ff0000">*</font> 物流部门名称:</td>
                        <td width="390px">
                            <input type="text" name="" class="easyui-combobox" id="wuliuteam" value="" placeholder="" style="width: 96%;">
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="150px">
                            <font color="#ff0000">*</font> 物品名称:</td>
                        <td width="390px">
                            <input type="text" name="" id="wupinName" value="" placeholder="" style="width: 96%;">
                        </td>
                    </tr>
                    <tr>
                        <td width="400px" colspan="2" align="center">注释: 所有加
                            <font color="#ff0000">*</font>为必填项
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="butList" style="margin: 20px auto;width:300px">
                <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save-call">
                <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close-call">
            </div>
        </div>
        <!-- 物流具体信息填写 -->
        <div class="easyui-dialog" title="物流具体信息填写" id="infoLogistics" closed="true" style="width:650px;height:400px;">
            <table border="1" class="PopUpTableBorder" style="width:100%;">
                <thead>
                    <tr align="centter">
                        <td style="text-align: center; width:250px;">抽血人</td>
                        <td style="text-align: center;width:250px;">抽血床位</td>
                        <td style="text-align: center;width:250px;">抽血时间</td>
                        <td style="text-align: center;width:250px;">抽血科室</td>
                        <td style="text-align: center;width:250px;">抽血数量</td>
                        <td style="text-align: center;">
                            <input type="button" value="添加" class="ButtonStyle_Blue" id="add">
                        </td>
                    </tr>
                    <thead>
                        <tbody id="tb">
                            <tr class="lst">
                                <td align="center">
                                    <input type="text" style="width: 90%;" class="bloodUser">
                                </td>
                                <td align="center">
                                    <input type="text" style="width: 90%;" class="bloodPos">
                                </td>
                                <td align="center">
                                    <input type="text" style="width: 90%;" class="bloodDate easyui-datetimebox" />
                                </td>
                                <td align="center">
                                    <input type="text" style="width: 90%;" class="bloodDep easyui-combobox">
                                </td>
                                <td align="center">
                                    <input type="number" style="width: 90%;" min="1" class="bloodNum">
                                </td>
                                <td align="center">
                                    <input type="button" value="删除" class="ButtonStyle_Blue del">
                                </td>
                            </tr>
                            <tr id="pick">
                                <td align="center" style="width:550px;">取货人姓名:</td>
                                <td colspan="5" align="center">
                                    <input id="pickUser" type="text" name="" value="" placeholder="" style="width: 90%;">
                                </td>
                            </tr>
                            <tr>
                                <td align="center" style="width:550px;">取货人联系方式:</td>
                                <td colspan="5" align="center">
                                    <input id="pickPhone" type="text" name="" value="" placeholder="" style="width: 90%;">
                                </td>
                            </tr>
                        </tbody>
            </table>
            <div class="butList" style="margin: 20px auto;width:300px">
                <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save-info">
                <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close-info">
            </div>
        </div>
        <!--  添加申请单-->
        <div class="easyui-dialog" title="添加申请单" id="addServiceform" closed="true" style="width:500px;height:350px">
            <table class="PopUpTableBorder" border="0" cellspacing="0" cellpadding="0">
                <tbody>
                    <tr>
                        <td class="PopUpTableTitle" width="200px">
                            <font color="#ff0000">*</font>
                            申请人:
                        </td>
                        <td width="390px">
                            <input id="proposerName" type="text" disabled="disabled" value="${ username}"></input>
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="200px">
                            <font color="#ff0000">*</font>
                            申请部门：
                        </td>
                        <td width="390px">
                            <input id="shenqingdepartment2" style="width:130px;margin:0px;"></input>
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="200px">
                            <font color="#ff0000">*</font>
                            响应科室名称:
                        </td>
                        <td width="500px">
                            <input id="responseDeptName" name="dept" style="width:130px;"></input>
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="200px">
                            <font color="#ff0000">*</font>
                            申请内容:
                        </td>
                        <td width="390px">
                            <textarea id="mark"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td width="400px" colspan="2" align="center">
                            注释: 所有加
                            <font color="#ff0000">*</font>为必填项
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="butList">
                <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save_add"></input>
                <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close_add"></input>
            </div>
        </div>
        <!-- 评价申请单 -->
        <div class="easyui-dialog" title="评价申请单" id="evaluateform" closed="true" style="width:500px;height:350px">
            <table class="PopUpTableBorder" border="0" cellspacing="0" cellpadding="0">
                <tbody>
                    <tr>
                        <td class="PopUpTableTitle" width="150px">
                            <font color="#ff0000">*</font>
                            完成时间:
                        </td>
                        <td width="390px">
                            <input id="endtime" type="text" disabled="disabled">
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="150px">
                            <font color="#ff0000">*</font>
                            响应时间:
                        </td>
                        <td width="390px">
                            <input id="xiangyingtime" type="text" disabled="disabled">
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="150px">
                            <font color="#ff0000">*</font>
                            申请部门:
                        </td>
                        <td width="390px">
                            <input id="proposerDepName" type="text" disabled="disabled">
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="150px">
                            <font color="#ff0000">*</font>
                            评价:
                        </td>
                        <td width="390px">
                            <label>优:
                                <input type="radio" name="a" class="mt" data-option="1" checked="checked">
                            </label>
                            <label>良:
                                <input type="radio" name="a" class="mt" data-option="2">
                            </label>
                            <label>中:
                                <input type="radio" name="a" class="mt" data-option="3">
                            </label>
                            <label>差:
                                <input type="radio" name="a" class="mt" data-option="4">
                            </label>
                        </td>
                        <!--  $('.mt').attr(' data-option') -->
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="150px">
                            <font color="#ff0000">*</font>
                            评价内容:
                        </td>
                        <td width="390px">
                            <textarea id="mark1"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td width="400px" colspan="2" align="center">
                            注释: 所有加
                            <font color="#ff0000">*</font>为必填项
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="butList">
                <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save">
                <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close">
            </div>
        </div>
        <!-- 选择服务 -->
        <div class="easyui-dialog" title="选择服务" id="selectProject" closed="true" style="width:500px;height:350px">
            <table class="PopUpTableBorder" border="0" cellspacing="0" cellpadding="0">
                <tbody>
                    <tr>
                        <td class="PopUpTableTitle" width="200px">
                            <font color="#ff0000">*</font>
                            响应科室名称:
                        </td>
                        <td width="500px">
                            <input id="responsedepname" name="dept"></input>
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="200px">
                            <font color="#ff0000">*</font>
                            响应科室项目:
                        </td>
                        <td width="500px">
                            <input id="responseDeptitem" name="dept"></input>
                        </td>
                    </tr>
                    <tr>
                        <td width="400px" colspan="2" align="center">
                            注释: 所有加
                            <font color="#ff0000">*</font>为必填项
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="butList">
                <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save_pro"></input>
                <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close_pro"></input>
            </div>
        </div>
        <!-- 修改服务 -->
        <div class="easyui-dialog" title="修改服务" id="edit1Project" closed="true" style="width:500px;height:350px">
            <table class="PopUpTableBorder" border="0" cellspacing="0" cellpadding="0">
                <tbody>
                    <tr>
                        <td class="PopUpTableTitle" width="200px">
                            <font color="#ff0000">*</font>
                            响应科室名称:
                        </td>
                        <td width="500px">
                            <input id="repdepname" name="dept"></input>
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="200px">
                            <font color="#ff0000">*</font>
                            响应科室项目:
                        </td>
                        <td width="500px">
                            <input id="responsedeptitem" name="dept"></input>
                        </td>
                    </tr>
                    <tr>
                        <td width="400px" colspan="2" align="center">
                            注释: 所有加
                            <font color="#ff0000">*</font>为必填项
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="butList">
                <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save_edit"></input>
                <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close_edit"></input>
            </div>
        </div>
</body>
		<script type="text/javascript" src='../../Js/json2.js'></script>
        <script type="text/javascript" src="../../Js/jquery.min.js"></script>
        <script type="text/javascript" src="../../Js/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="../../Js/vilidate.js"></script>
        <script type="text/javascript" src="../../Js/pinyin.js"></script>
        <script type="text/javascript" src="../../common/serviceform/index.js"></script>
</html>


      
        
  

