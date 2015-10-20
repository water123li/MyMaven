package cn.shaviation.mymaven.user.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONReader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.shaviation.mymaven.student.model.Student;
import cn.shaviation.mymaven.student.model.Teacher;
import cn.shaviation.mymaven.user.model.User;
import cn.shaviation.mymaven.user.service.UserService;
@ParentPackage("default")
@Namespace("/user")
public class UserAction extends ActionSupport {
	private static final long serialVersionUID = 4711490294515400621L;
	private String username;
	private String password;
	private UserService userService;
	private User user;
	private Teacher teacher;
	private List<Student> students;

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	public String userRegist() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		BufferedReader bufferReader = request.getReader();
		
		String line;
		StringBuilder buffer = new StringBuilder();
		while ((line = bufferReader.readLine()) != null) {
			buffer.append(line);
		}
		System.out.println("buffer:" + buffer.toString());
//		JSONReader reader = new JSONReader();
//		Object read = reader.read(buffer.toString());
//		Map<String, Object> map = (Map<String, Object>) read;
//		for (Map.Entry<String, Object> entry : map.entrySet()) {
//			System.out.println("key:" + entry.getKey() + "  value:" + entry.getValue());
//		}
		
//		System.out.println(request.getParameterValues("username")[0]);
//		System.out.println(request.getParameterValues("password")[1]);
		System.out.println(request.getParameter("username"));
		System.out.println(request.getParameter("password"));
		Map<String ,Object> parameterMap = request.getParameterMap();
		for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
			System.out.println("key:" + entry.getKey() + "  value:" + entry.getValue());
			System.out.println("key:"+request.getParameter(entry.getKey()));
			System.out.println("哈哈");
		}
		if (user != null) {
			System.out.println("username:"+user.getUsername() +"   password:"+user.getPassword());
		}
		if (teacher != null) {
			System.out.println("tid:"+teacher.getId() +"   tname:"+teacher.getName());
		}
		if (students != null) {
			for (Student student : students) {
				System.out.println("sid:"+student.getId() +"   sname:"+student.getName());
			}
		}else if (teacher != null) {
			students = teacher.getStudents();
			if (students != null) {
				for (Student student : students) {
					System.out.println("sid:"+student.getId() +"   sname:"+student.getName());
				}
			}
		}
		System.out.println("this.username:"+this.username +"   this.password:"+this.password);
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
	
	public List<Map<String, Object>> getUsers() {
		List<Map<String, Object>> users = new ArrayList<>();
		Map<String, Object> map1 = new HashMap<>();
		map1.put("id", 1);
		map1.put("name", "");
		users.add(map1);
		
		
		return users;
	}
}
