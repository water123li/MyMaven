package cn.shaviation.mymaven.user.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.shaviation.mymaven.user.model.User;
import cn.shaviation.mymaven.user.service.UserService;

@ParentPackage("default")
@Namespace("/user")
public class UserAction extends ActionSupport {
	private static final long serialVersionUID = 4711490294515400621L;
	private String username;
	private String password;
	private UserService userService;
	private List<Object> entities;
	private Object jsonObject;
	private User user = new User();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Object> getEntities() {
		return entities;
	}

	public void setEntities(List<Object> entities) {
		this.entities = entities;
	}

	public Object getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(Object jsonObject) {
		this.jsonObject = jsonObject;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Action(value = "listPage", results = {@Result(name = "success", location = "/WEB-INF/pages/user/user.jsp") })
	public String listPage() {
		return SUCCESS;
	}

	@Action(value = "userLogin", results = { @Result(name = "success", params = { "root", "result" }, type = "json") })
	public String userLogin() {
		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(ServletActionContext.getServletContext());
		System.out.println(applicationContext.getBeanDefinitionNames().toString());
		Map<String, Object> result = new HashMap<String, Object>();
		if (userService.isUserExisted(username, password)) {
			result.put("isSuccessed", true);
		} else {
			result.put("isSuccessed", false);
		}
		ActionContext.getContext().put("result", result);
		return SUCCESS;
	}
//设置默认拦截器后不需要配置interceptorRefs
	//	@Action(value = "userRegist", results = {
//			@Result(name = "success", params = { "root", "result" }, type = "json") }, interceptorRefs = {
//					@InterceptorRef(value = "json", params = { "root", "user" }) })
	@Action(value = "userRegist", results = { @Result(name = "success", params = { "root", "result"}, type = "json") })
	public String userRegist() {
		if (user != null) {
			System.out.println("username:"+user.getUsername() +"   password:"+user.getPassword());
		}
		Map<String, Object> result = new HashMap<String, Object>();

 		if (userService.isUserExisted(username, password)) {
			result.put("isSuccessed", false);
		} else {
			userService.addUser(username, password);
			result.put("isSuccessed", true);
		}
		ActionContext.getContext().put("result", result);
		return SUCCESS;
	}
}
