<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
  <link href="../../css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="../../Js/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="../../Js/themes/icon.css" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link rel="stylesheet" type="text/css" href="../../css/serviceform.css">
 <script type="text/javascript" src="../../Js/jquery.min.js"></script>
   <!--[if lt IE 9]>
   <script type="text/javascript" src='../../Js/json2.js'></script>
   <![endif]-->
  <script type="text/javascript" src="../../Js/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="../../Js/vilidate.js"></script>
<script type="text/javascript" src="../../Js/pinyin.js"></script>
<script type="text/javascript" src="../../common/department/index.js"></script>
<div class="departmentIndex">
		<div align="left" id="departmentTools">
			<label class="desc">科室名称:</label> 
            <input id="depname" style="width:200px;margin:2px 0px;height:24px;"></input> 
            <input type="button" class="search button" name="" id="searchDep" value="查询"  style="margin:2px 0px;">
                 <ul class="buttonList clear">
        	<li>
        		<input type="button" class="add button" name="" value="添加科室" id="insertDepartment">
        	</li>
        		<li>
        		<input type="button" class="edit button" name="" value="设置科长" id="setkezhang">
        	</li>
        </ul>
		</div>
		<div class="departmentlist">
			<div id='departmentlist'></div>
			</div>
		  <div id="dd" class="easyui-dialog"  title ="设置科长"closed="true" style="min-width:300px;min-height:300px;width:400px;height:400px"><table class="easyui-datagrid" id="dg"></table>
		  <div align="center" id="kezhangTools">
			<a id="savekezhang" href="javascript:void(0)" class="easyui-linkbutton">保存</a>
		    <a id="cancelkezhang" href="javascript:void(0)" class="easyui-linkbutton" >关闭</a>
		</div>
		<div id="mm" class="easyui-menu" style="width:120px;">
			<div onclick="del()" data-options="iconCls:'icon-remove'">删除</div>
		</div>
</div>
<!--  添加科室-->
    <div class="easyui-dialog" closed="true" title="添加科室"id="insertDepartmentdia" style="width:500px;height:350px;">
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
     <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save_dep">
        <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close_dep">
    </div>
</div>
