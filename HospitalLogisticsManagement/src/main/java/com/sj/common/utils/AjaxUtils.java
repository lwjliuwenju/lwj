package com.sj.common.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

public class AjaxUtils {  
  
	
	
	/**
	 * ************************************************************** 00开头错误
	 * **************************************************************
	 * 
	 */
	/**
	 * 未知错误
	 */
	public final static int UNKNOWN_ERROR = 00001;// 00001
	
	/**
	 * 客户端会话过期
	 */
	public final static int SESSIONINFO_ERROR = 00002;// 00002

	/**
	 * **************************************************************
	 * 11开头是和参数有关的错误
	 * **************************************************************
	 * 
	 */
	/**
	 * "缺少请求参数";
	 */
	public final static int REQUEST_PARAMETER_LACK = 11001;// 11001

	/**
	 * 未知来源
	 */
	public final static int UNKNOWN_SOURCE = 11002;// 11002

	/**
	 * 没有数据
	 */
	public final static int NO_DATA = 11003;// 11003
	/**
	 * flag验证失败
	 */
	public final static int FLAG_ERROR = 11004;// 11004
	/**
	 * 签名验证失败
	 */
	public final static int SIGN_ERROR = 11005;// 11005
	/**
	 * 命令验证失败
	 */
	public final static int SERVER_ERROR = 11006;// 11006
	/**
	 * 第三方调用数据处理异常
	 */
	public final static int THIRD_ERROR = 11007;// 11007
	/**
	 * 时间格式错误
	 */
	public final static int TIME_FORMAT_ERROR = 11008;// 11008
	/**
	 * 图片错误
	 */
	public final static int PIC_ERROR = 11009;// 11009
	/**
	 * 请求参数有误
	 */
	public final static int PARAMETER_ERROR = 11010;
	/**
	 * 用户标识有误
	 */
	public final static int USER_SESSIONID_ERROR = 11011;
	/**
	 * 不支持的服务
	 */
	public final static int NOT_SUPPORT_ERROR = 11012;

	/**
	 * ***************************************************************
	 * 22开头是和用户信息有关的错误
	 * ***************************************************************
	 * 
	 */
	/**
	 * "根据token获取用户信息失败";
	 */
	public final static int NO_FIND_USER_BY_TOKEN = 22001; // 22001
	/**
	 * "用户没有绑卡";
	 */
	public final static int NO_BAND_CARD = 22002; // 22002
	/**
	 * "没有找到该用户参与活动的信息";
	 */
	public final static int NO_PARTICIPATE = 22003;// 22003
	/**
	 * "根据分享的uuid找不到用户信息";
	 */
	public final static int NO_FIND_USER_BY_UUID = 22004;// 22004
	/**
	 * "没有找到用户信息
	 */
	public final static int NO_USER = 22005;// 22005
	/**
	 * "根据tId找不到信息";
	 */
	public final static int NO_FIND_INFO_BY_TID = 22006;// 22006
	/**
	 * 原密码错误
	 */
	public final static int OLD_PWD_ERROR = 22007;
	/**
	 * *************************************************************** 33开头是业务错误
	 * ***************************************************************
	 * 
	 */
	/**
	 * 已经投过票了
	 */
	public final static int ALREADY_HAD_VOTED = 33001; // 33001
	/**
	 * 已经过了投票时间
	 */
	public final static int ALREADY_MISS_VOTE = 33002; // 33002
	/**
	 * 根据id找不到对象
	 */
	public final static int NO_OBJECT = 33003; // 33003
	/**
	 * 密码错误
	 */
	public final static int PASS_ERROR = 40444; //

	private static Map<Integer, String> CODE_MESSAGE = new HashMap<Integer, String>() {
		{
			put(UNKNOWN_ERROR, "未知错误");
			put(PASS_ERROR, "密码错误");
			put(NO_FIND_USER_BY_TOKEN, "根据token获取用户信息失败");
			put(NO_BAND_CARD, "用户没有绑卡");
			put(NO_PARTICIPATE, "没有找到该用户参与活动的信息");
			put(REQUEST_PARAMETER_LACK, "缺少请求参数");
			put(NO_FIND_USER_BY_UUID, "根据分享的uuid找不到用户信息");
			put(UNKNOWN_SOURCE, "未知来源");
			put(NO_USER, "没有找到用户信息");
			put(ALREADY_HAD_VOTED, "已经投过票了");
			put(ALREADY_MISS_VOTE, "已经过了投票时间");
			put(NO_DATA, "没有查到相关数据");
			put(NO_OBJECT, "根据id没有找到对象");
			put(FLAG_ERROR, "flag验证失败");
			put(SIGN_ERROR, "签名验证失败");
			put(SERVER_ERROR, "命令验证失败");
			put(THIRD_ERROR, "第三方调用数据处理异常");
			put(TIME_FORMAT_ERROR, "时间格式错误");
			put(PIC_ERROR, "图片错误");
			put(NO_FIND_INFO_BY_TID, "根据tId找不到信息");
			put(SESSIONINFO_ERROR, "客户端会话过期");
			put(PARAMETER_ERROR, "请求参数有误");
			put(USER_SESSIONID_ERROR, "用户标识有误");
			put(NOT_SUPPORT_ERROR, "不支持的服务");
			put(OLD_PWD_ERROR, "原密码错误");
		}
	};
    /** 
     * ajax response method<br/> 
     * 如果出现异常，则返回错误代码505 
     *  
     * @param response 
     * @param text 
     * ajax返回的内容 
     *  
     */  
    public static void ajaxResponse(String text) {  
        try {  
        	ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");  
            ServletActionContext.getResponse().getWriter().write(text);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * <pre> 
     * 注意：ajax请求必须携带：jsonnp请求callback 
     *  
     * eg: 
     * $.getJSON(path + "ajax/news/comment.go?newsId=" + newsId + "&content=" +content+ "&callback=?", function(data) { 
     *   alert(data['val']); 
     * }); 
     *  
     * </pre> 
     *  
     * @param response 
     * @param msg 
     */  
    public static void jsonnpResponse(HttpServletResponse response,  
            String callback, String msg) {  
        try {  
        	ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");  
            ServletActionContext.getResponse().getWriter().write(callback + "(" + msg + ")");  
        } catch (IOException e) {  
  
        }  
    }  
  
    /** 
     * 异常操作，需要浏览器地址返回上一步 
     *  
     * @param response 
     * @param alertMsg 
     *            弹出提示信息 
     */  
    public static void ajaxResponseRollBack(String msg) {  
        try {  
        	ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");  
        	ServletActionContext.getResponse().getWriter().write(  
                    "<script type='text/javascript'>alert('" + msg  
                            + "');window.history.back();</script>");  
        } catch (IOException e) {  
            // LogConsoleUtil.write(Common.class, "ajaxResponseRollBack()",  
            // e.getMessage());  
        }  
    }  
  
    // ajaxJSON返回  
    public static void ajaxJSONResponse(String str, Object object) {  
    	ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");  
        JSONObject json = new JSONObject();  
        json.put(str, object);  
        try {  
        	ServletActionContext.getResponse().getWriter().write(json.toString());  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    public static void ajaxResponseAlert(String msg) {  
        try {  
        	ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");  
        	ServletActionContext.getResponse().getWriter().write(  
                    "<script type='text/javascript'>alert('" + msg  
                            + "');</script>");  
        } catch (IOException e) {  
            // LogConsoleUtil.write(Common.class, "ajaxResponseRollBack()",  
            // e.getMessage());  
        }  
    }  
  
    public static Integer StringToInteger(String name) {  
        String obj = ServletActionContext.getRequest().getParameter(name);  
        Integer result = -1;  
        if (obj != null) {  
            result = Integer.parseInt(obj);  
        }  
        return result;  
    }  
    
    public static String getErrorMessage(int code) {
		JSONObject result = new JSONObject();
		result.put("result_", false);
		result.put("code_", code);
		result.put("message_", CODE_MESSAGE.get(code));
		return result.toString();
	}

	public static String getSuccessMessage(JSONObject data) {
		JSONObject result = new JSONObject();
		result.put("result_", true);
		result.put("code_", 0);
		result.put("message_", "成功");
		if (data != null) {
			result.put("data", data);
		}
		return result.toString();
	}
  
}