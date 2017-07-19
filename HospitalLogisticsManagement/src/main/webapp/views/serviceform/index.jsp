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
    <link rel="stylesheet" type="text/css" href="../../css/serviceform.css">
</head>

<body>
    <div class="serviceformIndex">
        <div class="serviceFormlist">
        <div align="left" id="serviceFormTools">
                <div style="margin:2px 0px;"><label class="desc">申请人:</label> 
                    <input id="submitperson" style="width:200px;margin:0px;"></input>
                    <label class="desc">状态:</label>
                    <SELECT id='state' class="easyui-combobox" style="width:200px;margin:0px;">
                        <option value="0">请选择</option>
                        <option value="1">异常</option>
                        <option value="2">待响应</option>
                        <option value="3">响应中</option>
                        <option value="4">已完成</option>
                        <option value="5">已终止</option>
                        <option value="6">已评价</option>
                    </SELECT>
                   <label class="desc">排序方式:</label>
                    <SELECT id='shunxu' class="easyui-combobox" style="width:200px;margin:0px;">
                        <option value="0">请选择</option>
                        <option value="1">状态</option>
                        <option value="2">时间</option>
                    </SELECT>
                </div>
                <div style="margin:2px 0px;">
                    <label class="desc">申请部门:</label>
                    <input id="shenqingdepartment" style="width:200px;margin:0px;"></input>
                   <label class="desc">响应部门:</label>
                    <input id="jieshoubumen" style="width:200px;margin:0px;"></input>
                   <label class="desc"> 响应人员:</label>
                    <input id="zhixingperson" style="width:200px;margin:0px;"></input>
                </div>
                <div style="margin:2px 0px;">
                   <label class="desc">开始时间:</label>
                    <input id="startTime" name="startTime" class="easyui-datebox" style="width:200px;margin:0px;">
                    </input>
              <label class="desc">结束时间:</label>
                    <input id="endTime" name="endTime" class="easyui-datebox" style="width:200px;margin:0px;"></input>
                </div> 
                <ul class="buttonList clear">
        	<li>
        		<input type="button" class="search button" name="" id="searchServiceform" value="查询" >
        	</li>
        	<li>
        		<input type="button" class="add button" name="" value="添加申请单" id="insertServiceform">
        	</li>
        	<li>
        		<input type="button" class="seeDetail button" id="seeDetails" name="" value="查看详情" >
        	</li>
        	<li>
        		<input type="button" class="evealute button" name="" value="评价" id="evaluate">
        	</li>
        	<li>
        		<input type="button" name="" class="supplies button" value="耗材" id="supplies_cai">
        	</li>
        	<li>
        		<input type="button" name="" class="end button" value="完成" id="complete">
        	</li>
        	<li>
        		<input type="button" name="" class="call button" value="物流通知"  id="logisticscall">
        	</li>
        	<li>
        		<input type="button" name="" class="write button" value="物流填写" id="logisticsinfo">
        	</li>
        </ul>
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
</body>
		<script type="text/javascript" src='../../Js/json2.js'></script>
        <script type="text/javascript" src="../../Js/jquery.min.js"></script>
        <script type="text/javascript" src="../../Js/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="../../Js/vilidate.js"></script>
        <script type="text/javascript" src="../../Js/pinyin.js"></script>
        <script type="text/javascript" src="../../common/serviceform/index.js"></script>
</html>


      
        
  

