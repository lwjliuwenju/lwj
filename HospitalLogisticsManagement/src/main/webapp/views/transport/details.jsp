<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'detail?.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="../../Js/jquery.min.js"></script>
  </head>
  
  <body>
   <div class="wrapper scroll-wrapper">
    <div class="content-wrapper">
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <ul class="timeline">
                        <s:if test="#request.goods.size()!= 0">
                          <li id="haocaixinxi" >
                            <i class="fa fa-comments bg-yellow"></i>
                            <div class="timeline-item">
                                <h3 class="timeline-header">物品信息:</h3>
                                <div class="timeline-body">
                                <%-- 是否发放:<span id="haocaiState"></span><br> --%>
               				    <table border="1" bordercolor="#ccc" cellspacing="0" cellpadding="3" style="text-align:center;">
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
                    </ul>
                      
                </div>
            </div>
        </section>
    </div>
</div>
	<script type="text/javascript">
	$(function(){
		  if( '${pickUser}' != ''){
    	$("#wuliuInfo").css({'display':'block'});
    	}else{
			return;
		}
	});
		
	</script>
  </body>
</html>
