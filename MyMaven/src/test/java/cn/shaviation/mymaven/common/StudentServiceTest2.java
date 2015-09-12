package cn.shaviation.mymaven.common;

import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import cn.shaviation.mymaven.student.model.Student;
import cn.shaviation.mymaven.student.service.StudentService;
import cn.shaviation.mymaven.user.model.User;
import cn.shaviation.mymaven.user.service.UserService;

/**
 * 学生接口单元测试
 * 
 * @author Administrator
 * 
 */
public class StudentServiceTest2 {
	private static StudentService studentService;
	private static UserService userService;
	private static SessionFactory sessionFactory;
	private static ClassPathXmlApplicationContext applicationContext;
	private static ComboPooledDataSource dataSource;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(
				"/cn/shaviation/mymaven/common/applicationContext.xml");
		studentService = (StudentService) applicationContext.getBean("studentService");
		userService = (UserService) applicationContext.getBean("userService");
		sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
		dataSource = (ComboPooledDataSource) applicationContext.getBean("dataSource");
	}

	@Test
	public void getStudent() throws Exception {
		
		Student student = studentService.getStudent(2L);
		System.out.println("学生ID：" + student.getId() + " 学生姓名：" + student.getName());
		student.setName("同学");
		studentService.saveOrUpdateStudent(student);
		student = studentService.getStudent(2L);
		System.out.println("学生ID：" + student.getId() + " 学生姓名：" + student.getName());
	}
	
	@Test
	public void saveUser() {
		
		User user = userService.addUser("哈哈", "1234");
		
		System.out.println("用户ID：" + user.getId() + " 用户姓名：" + user.getUsername());
	} 
	

	@AfterClass
	public static void teardown() throws Exception {
		if (dataSource != null) {
			dataSource.close();
		}
	}
}
