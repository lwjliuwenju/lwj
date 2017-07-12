package com.sj.common.filter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sj.common.gui.IsKey;
import com.sj.common.init.InitPro;
import com.sj.common.utils.AjaxUtils;
import com.sj.privilegemanagement.manager.MenuManager;
  
public class Interceptor extends AbstractInterceptor {  
	private static Logger logger = LoggerFactory.getLogger(Interceptor.class);
    private MenuManager menuManager;
	@Override  
    public String intercept(ActionInvocation invocation) throws Exception {  
  
		if(!InitPro.key){
			AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.SIGN_ERROR));
			return null;
		}
    	HttpServletResponse response = ServletActionContext.getResponse();
    	HttpServletRequest request = ServletActionContext.getRequest();
    	/**
		 * 解决跨域问题
		 */
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers", "X-Requested-With");
		response.addHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
		response.addHeader("X-Powered-By"," 3.2.1");
		
		Map map = request.getParameterMap();
		System.out.println("\n\n\n");
		map.forEach((e,v)->System.out.println(e.toString() + ":" + v.toString()));
		System.out.println("\n\n\n");
		return invocation.invoke();  
    }  
    
    @Override
    public void init() {
    	super.init();
//    	menuManager.saveMenuInfo();
    }

	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}
  
} 
