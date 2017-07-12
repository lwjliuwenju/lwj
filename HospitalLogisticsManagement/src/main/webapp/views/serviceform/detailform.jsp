<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title>查看详情</title>
    <script type="text/javascript" src="../../Js/jquery.min.js"></script>
<style>
        .content-wrapper{
            margin-left: 0px;
        }
        li{
        list-style:none;
        }
    </style>
</head>
<body>
<div class="wrapper scroll-wrapper">
    <div class="content-wrapper">
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <ul class="timeline">
                        <li class="time-label">
						  <h3 class="timeline-header">申请单详情:</h3>
                        </li>
                        <li>
                            <i class="fa fa-user bg-aqua"></i>
                            <div class="timeline-item">
                                <h3 class="timeline-header">报修信息:</h3>
                                <div class="timeline-body">
                                    <p class="text-aqua">申请人:${sendUser}  </p>
                                    <p>申请内容:${remark }</p>
                                </div>
                            </div>
                        </li>
                         <li id="project" style="display:none;">
                            <i class="fa fa-user bg-aqua"></i>
                            <div class="timeline-item">
                                <h3 class="timeline-header">服务信息:</h3>
                                <div class="timeline-body">
                                    <p>服务名称:${projectName}  </p>
                                    <p>服务分值:${grade }</p>
                                    <p>标准工时:${standardHour }</p>
                                </div>
                            </div>
                        </li>
                        <li>
                            <i class="fa fa-clock-o bg-aqua"></i>
                            <div class="timeline-item">
                                <h3 class="timeline-header">申请单状态:</h3>
                                <div class="timeline-body">
                                     ${sendUser}于${sendTime }提交申请订单到${reponseDepName}部门
                                </div>
                                <div class="timeline-body"  style="display:none;" id="xiangyingzhong">
                                     ${reponseDepName}部门于${startTime }处理该申请单<br> 
                                     <s:if test="#request.staffs.size()!= 0">
                                     	并派
                                       <s:iterator value="#request.staffs"  var="s">
                                     ${s.staffName}:${s.phone } 
                                       </s:iterator> 进行处理
                                     </s:if>
                                </div>
                                <div class="timeline-body"  style="display:none;" id="wancheng">
                                  <s:if test="#request.staffs.size()!= 0">
                                       <s:iterator value="#request.staffs"  var="s">
                                     ${s.staffName}
                                       </s:iterator>已于${endTime }完成本次维修
                                     </s:if>
                                </div>
                                <div class="timeline-body"  style="display:none;" id="shenqingpingjia">
                                    ${sendUser}对该申请单进行了评价
                                </div>
                            </div>
                        </li>
                         <s:if test="#request.supplies.size()!= 0">
                          <li id="haocaixinxi" >
                            <i class="fa fa-comments bg-yellow"></i>
                            <div class="timeline-item">
                                <h3 class="timeline-header">耗材信息:</h3>
                                <div class="timeline-body">
                                <%-- 是否发放:<span id="haocaiState"></span><br> --%>
               				    <table border="1" bordercolor="gray" cellspacing="0" cellpadding="5" style="text-align:center;">
               				    	<tr>
               				    		<td>耗材名称</td>
               				    		<td>耗材数量</td>
               				    		<td>耗材价值</td>
               				    		<td>所属部门</td>
               				    		<td>状态</td>
               				    	</tr>
	               				    	<s:iterator value="#request.supplies" var="supplie">
               				    	<tr>
						               		<td>${supplie.supplieName }</td>
						               		<td>${supplie.supplieNum }</td>
						               		<td>${supplie.supplieValue }</td>
						               		<td>${supplie.depName }</td>
						               		<td>${supplie.mark }</td>
						            </tr>
						                </s:iterator>
               				    </table>
                                </div>
                            </div>
                        </li>
                       </s:if>
                       
                       <s:if test="#request.goods.size()!= 0">
                          <li id="haocaixinxi" >
                            <i class="fa fa-comments bg-yellow"></i>
                            <div class="timeline-item">
                                <h3 class="timeline-header">物品信息:</h3>
                                <div class="timeline-body">
                                <%-- 是否发放:<span id="haocaiState"></span><br> --%>
               				    <table border="1" bordercolor="gray" cellspacing="0" cellpadding="5" style="text-align:center;">
               				    	<tr>
               				    		<td>抽血人</td>
               				    		<td>抽血床位</td>
               				    		<td>抽血时间</td>
               				    		<td>抽血部门</td>
               				    		<td>抽血数量</td>
               				    	</tr>
	               				    	<s:iterator value="#request.goods" var="good">
               				    	<tr>
						               		<td>${good.bloodPeople }</td>
						               		<td>${good.bedNo }</td>
						               		<td>${good.drawbloodTime }</td>
						               		<td>${good.booldDep }</td>
						               		<td>${good.bloodNum }</td>
						            </tr>
						                </s:iterator>
               				    </table>
                                </div>
                            </div>
                        </li>
                       </s:if>
                       <s:if test="#request.goodsName != null">
                        <li>
                            <i class="fa fa-user bg-aqua"></i>
                            <div class="timeline-item">
                                <h3 class="timeline-header">物流信息:</h3>
                                <div class="timeline-body">
			                       <p class="text-aqua">
			                       	${goodsName }等待被取走！
			                       </p>
			                       <div  id="wuliuInfo"  style="display:none;">
                                    <p class="text-aqua">${pickUser}于${createStamp }取走了${goodsName }</p>
                                    <p class="text-aqua">${pickUser}联系方式:${pickPhone }</p>
                                    </div>
                                </div>
                            </div>
                        </li>
                        </s:if>
                        <li id="wangong"  style="display:none;">
                            <i class="fa fa-comments bg-yellow"></i>
                            <div class="timeline-item">
                                <h3 class="timeline-header">完工信息:</h3>
                                <div class="timeline-body" style="display:none;" id="endWork">
                                 <s:if test="#request.staffs.size()!= 0">
                                       <s:iterator value="#request.staffs"  var="s">
                                     ${s.staffName}
                                       </s:iterator>已于${endTime }完成本次维修
                                          </s:if>
                                </div>
                             
                                <div class="timeline-body" id="pingjiadiv" style="display:none;">
                                评价:<span id="pingjiaState"></span><br>
                                 评价内容:<span id="pingjiaText">${appraiseMark}</span>
                                </div>
                                  <div class="timeline-body" id="shenhediv" style="display:none;">
                             	  是否通过:<span id="shenheStatus"></span><br>
                                 审核内容:<span id="shenheText"></span>
                                </div>
                                
                            </div>
                        </li>
                        <li id="zhongzhi" style="display:none;">
                            <i class="fa fa-comments bg-yellow"></i>
                            <div class="timeline-item">
                                <h3 class="timeline-header">终止信息:</h3>
                                <div class="timeline-body">
                                ${endName}已于${endTime }终止本次申请单<br>
                                	终止原因为:${endReason}
                                </div>
                            </div>
                        </li>
                         <li id="repair" style="display:none;">
                            <i class="fa fa-comments bg-yellow"></i>
                            <div class="timeline-item">
                                <h3 class="timeline-header">返修状态:</h3>
                                <div class="timeline-body">
                              		  是否返修:<span id="fanxiuStatus"></span><br>
                                </div>
                            </div>
                        </li>
                        
                    </ul>
                      
                </div>
            </div>
        </section>
    </div>
</div>
   <script type="text/javascript">
   $(function(){
    var state =${state };
        /*响应中  */
    if(state == 3){
    $('#xiangyingzhong').css({'display':'block'});
    };
    /*服务*/
    if('${projectName}' != ''){
    $("#project").css({'display':'block'});
    }
   
    /* 完工信息 */
    if(state > 3){
    $("#wangong").css({'display':'block'});
   $("#endWork").css({'display':'block'});
     /*完成  */
    if(state == 4){
    $('#xiangyingzhong').css({'display':'block'});
    $('#wancheng').css({'display':'block'});
    $('#repair').css({'display':'block'});
    var fanxiuContent;
    var fanxiuflag = "${repairflag }";
   if(fanxiuflag == ''){
    $('#repair').css({'display':'none'});
    return;
   }
    if(fanxiuflag == 1){
    	fanxiuContent = '是';
    	$('#fanxiuStatus').html(fanxiuContent);
    }else{
    fanxiuContent = '否';
    	$('#fanxiuStatus').html(fanxiuContent);
    }
    };
    /* 审核 */
    if(state >= 4){
    	   var shenheState = ${checkflag};
    	       /* 审核 */
			    if(shenheState == 0){
			    $('#shenheStatus').html('不通过');
			    }else if(shenheState == 1){
			    $('#shenheStatus').html('通过');
			    };
			    if("${checkContent}" ==''){
			    	$('#shenheText').html('无');
			    }else{
			    $('#shenheText').html("${checkContent}");
			    }
    	 $('#shenhediv').css({'display':'block'});
    }
     /*终止  */
     if(state == 5){
    $('#zhongzhi').css({'display':'block'});
    };
    /*评价  */
      if(state == 6){
    $('#xiangyingzhong').css({'display':'block'});
    $('#wancheng').css({'display':'block'});
     $('#shenqingpingjia').css({'display':'block'});
     $('#pingjiadiv').css({'display':'block'});
    
	    var pingjiastate = ${appraise};
	  
	    var pingjiaContent;
	    /* 评价 */
	    if(pingjiastate == 1){
	    	pingjiaContent = '优';
	    	$('#pingjiaState').html(pingjiaContent);
	    }
	    if(pingjiastate == 2){
	    	pingjiaContent = '良';
	    	$('#pingjiaState').html(pingjiaContent);
	    }
	    if(pingjiastate == 3){
	    	pingjiaContent = '中';
	    	$('#pingjiaState').html(pingjiaContent);
	    }
	    if(pingjiastate == 4){
	    	pingjiaContent = '差';
	    	$('#pingjiaState').html(pingjiaContent);
	    };
    }
   

    };
    /* 物流信息 */
    if( '${pickUser}' != ''){
    	$("#wuliuInfo").css({'display':'block'});
    };
   })
    
       
   </script>
</body>
</html>