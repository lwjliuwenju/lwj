package com.sj.login.shiro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.sj.privilegemanagement.entity.Role;
import com.sj.privilegemanagement.entity.User;
import com.sj.privilegemanagement.manager.MenuManager;
import com.sj.privilegemanagement.manager.RoleManager;
import com.sj.privilegemanagement.manager.UserManager;

public class ShiroDbRealm extends AuthorizingRealm {

	private static Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);
	@Autowired
	@Qualifier("punMembershipServiceImpl")
	private MenuManager memberService;// 身份资格

	@Autowired
	@Qualifier("punUserBaseInfoServiceImpl")
	private UserManager userService;// 用户

	@Autowired
	@Qualifier("punRoleInfoServiceImpl")
	private RoleManager roleService;// 角色
	
	// 授权方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
//		this.clearCached();
		logger.debug("\n\n----->lwj:doGetAuthorizationInfo\n\n");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if (null == principals) {
			throw new AuthenticationException("principals can not be null");
		}
		// 获取当前登录的用户名
		String userName = (String) super.getAvailablePrincipal(principals);
		List<String> roles = new ArrayList<String>();
		if("admin".equals(userName)){
			roles.add("adm");
		}else{
			String[] result = userName.split("_");

			Map<String, Object> groupParams = new HashMap<String, Object>();
			groupParams.put("orgCode", result[0]);
			Map<String, Object> userParams = new HashMap<String, Object>();
			userParams.put("userIdCardNumber", userName);
			List<User> users = userService.findAll(
					"from User where jobNumber = :userIdCardNumber", userParams);
			if (users == null || users.size() < 1) {
				return null;
			}
			List<Role> roleVos = roleService.findRolesByUserId(users.get(0).getId());
			
			for (Role vo : roleVos) {
				roles.add(vo.getName());// 角色
			}
		}
		// 给当前用户设置角色
		info.addRoles(roles);
		// 给当前用户设置权限
		// info.addStringPermissions(permissions);
		return info;
	}

	// 验证方法
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		logger.debug("\n\n----->lwj:doGetAuthenticationInfo\n\n");
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		String nameAndOrgcode = token.getUsername();
		Map<String, Object> groupParams = new HashMap<String, Object>();
		groupParams.put("orgCode", nameAndOrgcode);
		User user = new User();
		if(nameAndOrgcode.equals("admin")){
			user.setUserName(nameAndOrgcode);
			user.setPassword(userService.getAdminPass());
			return new SimpleAuthenticationInfo(token.getUsername(), 
					user.getPassword(), this.getName());
		}
		else{
			user = userService.findByUserName(nameAndOrgcode);// 用户基础信息
			if(user == null){
				throw new UnknownAccountException();
			}
		}
		return new SimpleAuthenticationInfo(token.getUsername(), 
				user.getPassword(), this.getName());
	}
	
	//清除缓存
    public void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }

	public void setMemberService(MenuManager memberService) {
		this.memberService = memberService;
	}

	public void setUserService(UserManager userService) {
		this.userService = userService;
	}

	public void setRoleService(RoleManager roleService) {
		this.roleService = roleService;
	}

}
