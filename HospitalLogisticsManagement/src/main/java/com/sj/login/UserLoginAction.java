package com.sj.login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import com.opensymphony.xwork2.ActionSupport;
import com.sj.common.impl.FilterChainDefinitionsService;
import com.sj.common.utils.AjaxUtils;
import com.sj.common.utils.EncryptUtil;
import com.sj.login.shiro.ShiroDbRealm;
import com.sj.privilegemanagement.entity.DepEntity;
import com.sj.privilegemanagement.entity.User;
import com.sj.privilegemanagement.manager.MenuManager;
import com.sj.privilegemanagement.manager.UserManager;

@SuppressWarnings("serial")
@Controller
public class UserLoginAction extends ActionSupport {
	private static Logger logger = LoggerFactory.getLogger(UserLoginAction.class);
	private UserManager userService;
	private MenuManager menuManager;
	private static int mark = -1;
	
	/**
	 * 初始登陆界面
	 * @param request
	 * @return
	 */
	public void tologin(){
		logger.debug("来自IP[" + ServletActionContext.getRequest().getRemoteHost() + "]的访问");
		AjaxUtils.ajaxResponse("demo");
	}
	
	/**
	 * 验证用户名和密码
	 * @param request
	 * @return
	 */
	public void login() {
		logger.debug("\n\n----->lwj:login\n\n");
		if(mark < 0){
			mark = 1;
			menuManager.saveMenuInfo();
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String result = "login.do";
		// 取得用户名
		String username = request.getParameter("username");
		//取得 密码，并用MD5加密
		String password = EncryptUtil.encrypt(request.getParameter("password"));
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		
		Subject currentUser = SecurityUtils.getSubject();
		JSONObject jsonObject = new JSONObject();
		try {
			System.out.println("----------------------------");
			if (!currentUser.isAuthenticated()){//使用shiro来验证
				token.setRememberMe(true);
				try {  
					currentUser.login(token);//验证角色和权限
					} catch ( UnknownAccountException uae ) { //用户名未知...  
						AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.NO_USER));
						return;
					} catch ( IncorrectCredentialsException ice ) {//凭据不正确，例如密码不正确 ...  
						AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.PASS_ERROR));
						return;
					} catch ( LockedAccountException lae ) { //用户被锁定，例如管理员把某个用户禁用...  
						AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.PASS_ERROR));
						return;
					} catch ( ExcessiveAttemptsException eae ) {//尝试认证次数多余系统指定次数 ...  
						AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.PASS_ERROR));
						return;
					} catch ( AuthenticationException ae ) {  
						AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.UNKNOWN_ERROR));
						return;
					//其他未指定异常  
					}
			}
			System.out.println("result: " + result);
			User user = userService.findByUserName(username);// 用户基础信息
			JSONArray members = null;
			List<DepEntity> depEntities = null;
			if(user == null){
				user = new User();
				user.setId(0);
				user.setUserName(username);
				user.setPassword(password);
				depEntities = userService.getDepByUserId(0l);
			}else{
				depEntities = userService.getDepByUserId(user.getId());
			}
			members = menuManager.findMenuTreeByUserId(user.getId());
			ServletActionContext.getRequest().getSession().setAttribute("userInfo", user);
			ServletActionContext.getRequest().getSession().setAttribute("userId", user.getId());
			jsonObject.put("menus", members);
			jsonObject.put("user", user);
			JSONArray depArray = new JSONArray();
			for (DepEntity depEntity : depEntities) {
				JSONObject depJSON = new JSONObject();
				depJSON.put("text", depEntity.getDepName());
				depJSON.put("id", depEntity.getId());
				depArray.add(depJSON);
			}
//			if(user.getId() == 0){
//				JSONObject depJSON = new JSONObject();
//				depJSON.put("text", "admin");
//				depJSON.put("id", 0);
//				depArray.add(depJSON);
//			}
			jsonObject.put("deps", depArray);
			AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(jsonObject));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.UNKNOWN_ERROR));
		}
		
	}
    /**
     * 退出
     * @return
     */
    public String logout() {  
    	logger.debug("\n\n----->lwj:logout\n\n");
        Subject currentUser = SecurityUtils.getSubject();  
        String result = "logout";  
        currentUser.logout();  
        return result;  
    }

	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}

	public void setUserService(UserManager userService) {
		this.userService = userService;
	}

}
