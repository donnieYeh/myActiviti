package com.yejf.base;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	protected JSONArray results = new JSONArray();// 返回的json数组，return “JSONArray”时返回

	public JSONArray getResults() {
		return results;
	}

	public void setResults(JSONArray results) {
		this.results = results;
	}

	protected JSONObject result = new JSONObject();// 返回的JSONObject，return “json”时返回

	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}
	
	/**
	 * 系统总参数 Json格式的字符串 ，在前台根据业务自由组装
	 * 通过 queryParams() 统一获取
	*/
	protected String params;

	
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	/**
	 * 返回页面传递回来的JSON数组
	 * @return
	 */
	protected JSONObject queryParams() {
		JSONObject queryParams = JSONObject.fromObject(this.params);
		return queryParams;
	}
	
	/**
	 * 返回session
	 * @return
	 */
	public Map<String,Object> achieveSeesion() {
		return ServletActionContext.getContext().getSession();
	}
	
	/**
	 * 返回application
	 * @return
	 */
	public Map<String,Object> achieveApplication() {
		return ServletActionContext.getContext().getApplication();
	}
	
	/**
	 * 返回request
	 * @return
	 */
	public HttpServletRequest achieveRequest() {
		return (HttpServletRequest) ServletActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
	}
	
	/**
	 * 返回response
	 * @return
	 */
	public HttpServletResponse achieveResponse() {
		return (HttpServletResponse) ServletActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);
	}
	
	/**
	 * 返回Action Method
	 * @return
	 */
	public String getRqstMethod() {
		String requestMethod = ActionContext.getContext().getActionInvocation().getProxy().getMethod().toString();
//		System.out.println("当前路径："+requestMethod);
		return requestMethod;
	}
	
	/**
	 * 返回当前的页面（JSP）
	 * @return
	 */
	public String getCurrentPageName() {  
		String currentPageName = "";
		HttpServletRequest request = achieveRequest();   
		String url = request.getRequestURL().toString();
		String[] urls = url.split("/");
		if(url!=null&&urls!=null){
			currentPageName = urls[urls.length-1];
		}
//		System.out.println("当前页面："+currentPageName);
		return currentPageName;
	}
	
	/**
	 * 返回当前URL例如（/sys/yhwh）
	 * @return
	 */
	public String getCurrentPath() {  //currentPageName
		String path = ActionContext.getContext().getActionInvocation().getProxy().getNamespace().toString();
		return path;
	}
	
	/**
	 * 返回当前URL例如（/sys/yhwh/yhwh）
	 * @return
	 */
	public String getCurrentActionPath() { 
		String nameSpace = ActionContext.getContext().getActionInvocation().getProxy().getNamespace().toString();
		String actionName = ActionContext.getContext().getActionInvocation().getProxy().getActionName().toString();
		String path = nameSpace + "/" + actionName;
		return path;
	}
	
	/**
	 * 返回当前URL例如（/sys/yhwh）
	 * @return  sys
	 */
	public String getCurrentModuleName() {
		String currentModuleName = "";
		String url = ActionContext.getContext().getActionInvocation().getProxy().getNamespace().toString();
		String[] urls = url.split("/");
		if(url!=null&&urls!=null){
			if(urls.length>=3)	currentModuleName = urls[urls.length-2];
		}
//		System.out.println("当前模块名称："+currentModuleName);
		return currentModuleName;
	}
	
	/**
	 * 返回当前URL例如（/sys/yhwh）
	 * @return  yhwh
	 */
	public String getCurrentSubModuleName() {
		String currentSubModuleName = "";
		String url = ActionContext.getContext().getActionInvocation().getProxy().getNamespace().toString();
		String[] urls = url.split("/");
		if(url!=null&&urls!=null){
			if(urls.length>=2)	currentSubModuleName = urls[urls.length-1];
		}
//		System.out.println("当前子模块功能名称："+currentSubModuleName);
		return currentSubModuleName;
	}
}
