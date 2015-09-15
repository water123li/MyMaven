package cn.shaviation.mymaven.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import cn.shaviation.mymaven.student.model.Student;
import cn.shaviation.mymaven.student.model.Teacher;
import cn.shaviation.mymaven.user.model.User;

public class JsonTest {
	private static Teacher tempTeacher = new Teacher();
	
	@BeforeClass
	public static void setUpBeforeClass(){
		//String json = {"id":1,"name":"徐立秋","students":[{"id":1,"name":"李瑞鹏"},{"id":2,"name":"范长安"}]};
		Student student1 = new Student();
		student1.setId(1L);
		student1.setName("李瑞鹏");
		Student student2 = new Student();
		student2.setId(1L);
		student2.setName("范长安");
		
		tempTeacher.setId(1L);
		tempTeacher.setName("徐立秋");
		tempTeacher.getStudents().add(student1);
		tempTeacher.getStudents().add(student2);
	}
	@Test
	public void toJsonTest() {
		Gson gson = new Gson();  
		String teacherJson = gson.toJson(tempTeacher);
		System.out.println(teacherJson);
		
	}
	@Test
	public void toObject(){
		Gson gson = new Gson();  
		String teacherJson = gson.toJson(tempTeacher);
		System.out.println(teacherJson);
		Teacher teacher = gson.fromJson(teacherJson, Teacher.class);
		System.out.println("id:" + teacher.getId() + "  name:" + teacher.getName());
	}
	@Test
	public void listToJson(){
		Gson gson = new Gson();  
		List<String> list = new ArrayList<>();
		list.add("哈哈");
		list.add("呵呵");
		String string = gson.toJson(list);
		System.out.println(string);
	}
	@Test
	public void copyPropertyTest() throws Exception{
		User user1 = new User();
		user1.setId(1L);
		user1.setUsername("李瑞鹏");
		
		User user2 = new User();
		user2.setId(2L);
		user2.setUsername("甘付波");
		user2.setPassword("123456");
		
		PropertyUtils.copyProperties(user2, user1);

		System.out.println(
				"id:" + user2.getId() + "  username:" + user2.getUsername() + "  password:" + user2.getPassword());
	}
	

}
