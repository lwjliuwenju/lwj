package com.sj.privilegemanagement.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.sj.common.utils.AjaxUtils;
import com.sj.common.utils.DateUtil;
import com.sj.common.utils.EncryptUtil;
import com.sj.common.utils.JSONUtils;
import com.sj.common.utils.MapOfObject;
import com.sj.common.utils.enums.DateStyle;
import com.sj.login.UserLoginAction;
import com.sj.privilegemanagement.manager.BaseManager;
/**
 * 
 * @author Administrator
 *
 * @param <T>  制定对应的manage
 * @param <E>  实体类型
 */
public class BaseAction<T,E> extends ActionSupport {

	protected BaseManager baseManager;
	private static Logger logger = LoggerFactory.getLogger(UserLoginAction.class);
	
	private Class<T> entityClassT; 
	private Class<E> entityClassE; 
	
	private String url;
	
	public BaseAction() {  
        Type genType = getClass().getGenericSuperclass();  
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
        entityClassT = (Class) params[0];
        entityClassE = (Class) params[1];
    }
	/**
	 * 添加用户
	 * @throws Exception 
	 */
	public void add() throws Exception {
		Map map = new LinkedHashMap();
		add(map);
	}
	/**
	 * 传入特殊数据
	 * @param map
	 * @throws Exception
	 */
	public void add(Map map) throws Exception {
		String sex = getRequest().getParameter("sex");
		String password = getRequest().getParameter("password");
		Map mapT = getRequest().getParameterMap();
		map.putAll(mapT);
		if(StringUtils.isNotBlank(sex))
			map.put("sex", "true".equals(sex) ? true : false);
		if(StringUtils.isNotBlank(password))
			map.put("password", EncryptUtil.encrypt(password));
		map.put("createStamp", new Date());
		map.put("modifyStamp", new Date());
		map.put("enable", true);
		E e = (E) MapOfObject.mapToObject(map, entityClassE);
		try {
			this.getBaseManager().insert(e);
		} catch (Exception e2) {
			AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.UNKNOWN_ERROR));
			return;
		}
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 更新
	 * @throws Exception 
	 */
	public void update() throws Exception {
		String id = getRequest().getParameter("id");
		String password = getRequest().getParameter("password");
		if(StringUtils.isBlank(id)){
			AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.NO_FIND_INFO_BY_TID));
			return;
		}
		E e = (E)getBaseManager().findById(Long.valueOf(id));
		Map map = new LinkedHashMap();
		Map mapE = MapOfObject.objectToMap(e);
		Map mapT = getRequest().getParameterMap();
		map.putAll(mapE);
		map.putAll(mapT);
		if(StringUtils.isNotBlank(password))
			map.put("password", EncryptUtil.encrypt(password));
		if(map.get("createStamp") == null)
			map.put("createStamp", new Date());
		map.put("modifyStamp", new Date());
		e = (E) MapOfObject.mapToObject(map, entityClassE);
		this.getBaseManager().getSession().clear();
		try {
			this.getBaseManager().update(e);
		} catch (Exception e2) {
			AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.UNKNOWN_ERROR));
			return;
		}
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 查询所有
	 * @throws IOException 
	 */
	public void findAll() throws IOException {
		this.findAll(null);
	}
	public void findAll(String hql) throws IOException {
		String page = this.getRequest().getParameter("page");
		String rows = this.getRequest().getParameter("rows");
		String depId = this.getRequest().getParameter("id");
		if(StringUtils.isBlank(page) || StringUtils.isBlank(rows)){
			page = "1";
			rows = "10";
		}
		List<E> es = getBaseManager().findAll(Integer.valueOf(page), Integer.valueOf(rows),hql);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rows", JSONUtils.toJSONString(es));
		jsonObject.put("total", getBaseManager().findCountNum());
		AjaxUtils.ajaxResponse(jsonObject.toString());
	}
	public void findCountNum(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("number", getBaseManager().findCountNum());
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(jsonObject));
	}
	/**
	 * 根据id查询单条数据
	 */
	public void findById(){
		String id = getRequest().getParameter("id");
		if(StringUtils.isBlank(id)){
			AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.NO_FIND_INFO_BY_TID));
			return;
		}
		E e = (E)getBaseManager().findById(Long.valueOf(id));
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(JSONObject.fromObject((e))));
	}
	/**
	 * 根据id删除
	 * @throws Exception 
	 */
	public void delete() throws Exception{
		String id = getRequest().getParameter("id");
		String[] ids = getRequest().getParameterValues("id[]");
		if(StringUtils.isBlank(id) && ids != null)
			for (String str : ids) {
				E e = (E)getBaseManager().findById(Long.valueOf(str));
				Map map = new LinkedHashMap();
				Map mapE = MapOfObject.objectToMap(e);
				map.putAll(mapE);
				map.put("enable", true);
				map.put("modifyStamp", new Date());
				e = (E) MapOfObject.mapToObject(map, entityClassE);
				this.getBaseManager().getSession().clear();
				this.getBaseManager().update(e);
			}
		else{
			E e = (E)getBaseManager().findById(Long.valueOf(id));
			Map map = new LinkedHashMap();
			Map mapE = MapOfObject.objectToMap(e);
			map.putAll(mapE);
			map.put("enable", true);
			map.put("modifyStamp", new Date());
			e = (E) MapOfObject.mapToObject(map, entityClassE);
			this.getBaseManager().getSession().clear();
			this.getBaseManager().update(e);
		}
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
		return;
	}
    
	public T getManager() {
		return (T)this.getBaseManager();
	}
	/**
	 * 首页跳转
	 * @return
	 */
	public String index(){
		setUrl("index");
		return SUCCESS;
	}
	/**
	 * 添加页面跳转
	 * @return
	 */
	public String enterAdd(){
		setUrl("add");
		return SUCCESS;
	}
	/**
	 * 进入编辑页面
	 * @return
	 */
	public String enterEdit(){
		setUrl("edit");
		return SUCCESS;
	}
	/**
	 * 跳转
	 * @return
	 */
	public String linkUrl(){
		url = getRequest().getParameter("url");
		setUrl(url);
		return SUCCESS;
	}
	
	public BaseManager getBaseManager() {
		return baseManager;
	}

	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	public HttpSession getSession() {
		return this.getRequest().getSession();
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 往request封装数据
	 */
	public void setAttribute(String key,Object object) {
		this.getRequest().setAttribute(key, object);
	}
	public void setAttribute(Map<String,Object> map){
		for(Entry<String, Object> str : map.entrySet()){
			this.setAttribute(str.getKey(), str.getValue());
		}
	}
	/**
	 * 从request 域取数据
	 * @return
	 */
	public Object getAttribute(String param){
		return this.getRequest().getAttribute(param);
	}
	public <P> P getAttribute(String param,Class<P> clz){
		Object object = this.getAttribute(param);
		if(object != null)
			return (P)object;
		return null;
	}
	/**
	 * 从表单获取数据
	 * @param param
	 * @return
	 */
	public String getParameter(String param) {
		return this.getRequest().getParameter(param);
	}
	public Date getEasyUiDateParameter(String param) {
		String str = this.getParameter(param);
		Date date = DateUtil.StringToDate(str, DateStyle.EASY_UI_MM_DD_YYYY);
		return date;
	}
	public Integer getIntegerParameter(String param) {
		String str = this.getParameter(param);
		Integer in = 0; 
		try {
			in = Integer.valueOf(str);
			return in;
		} catch (Exception e) {
			
		}
		return in;
	}
	public String getState(int i) {
		switch (i) {
		case 1:return "<span style='background-color:red;display:inline-block;width:100%;height:100%;padding:0px;color:#fff;line-height:2.4;'>异常</span>";
		case 2:return "<span style='background-color:green;display:inline-block;width:100%;height:100%;padding:0px;color:#fff;line-height:2.4;'>待响应</span>";
		case 3:return "<span style='background-color:yellow;display:inline-block;width:100%;height:100%;padding:0px;line-height:2.4;'>响应中</span>";
		case 4:return "<span style='background-color:#000;display:inline-block;width:100%;height:100%;padding:0px;color:#fff;line-height:2.4;'>已完成</span>";
		case 5:return "<span style='background-color:#158dfa;display:inline-block;width:100%;height:100%;padding:0px;color:#fff;line-height:2.4;'>已终止</span>";
		case 6:return "<span style='background-color:#ccc;display:inline-block;width:100%;height:100%;padding:0px;color:#fff;line-height:2.4;'>已评价</span>";
		case 7:return "<span style='background-color:#00FFFF;display:inline-block;width:100%;height:100%;padding:0px;color:#fff;line-height:2.4;'>挂起</span>";
		default:break;
		}
		return null;
	}
}
