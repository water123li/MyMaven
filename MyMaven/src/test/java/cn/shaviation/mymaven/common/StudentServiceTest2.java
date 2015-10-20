package cn.shaviation.mymaven.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import cn.shaviation.mymaven.student.model.Student;
import cn.shaviation.mymaven.student.service.StudentService;
import cn.shaviation.mymaven.treestruct.dao.TreeStructDao;
import cn.shaviation.mymaven.treestruct.model.TreeStruct;
import cn.shaviation.mymaven.treestruct.service.TreeStructService;
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
//		User userSave = userService.addUser("哈哈", "1234");
		TreeStructService treeStructService = (TreeStructService) applicationContext.getBean("treeStructService");
		List<TreeStruct> treeStructs = treeStructService.get(TreeStruct.class, "parent", null);
		for (TreeStruct treeStruct : treeStructs) {
			System.out.println(treeStruct.getName());
			Set<TreeStruct> children = treeStruct.getChildren();
			if (children != null && children.size() > 0) {
				for (TreeStruct treeStruct2 : children) {
					System.out.println(treeStruct2.getName());
				}
			}
		}
	} 
	
	@Test
	public void getTree(){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(TreeStruct.class);
		criteria.add(Restrictions.gt("code", "a"));
		List<TreeStruct> treeStructs = criteria.list();
		for (TreeStruct treeStruct : treeStructs) {
			System.out.println(treeStruct.getName());
		}
		
		session.getTransaction().commit();
	}
	@Test
	public void deleteUser() {
		User user = userService.getUser(6L);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(user);
		session.getTransaction().commit();
		System.out.println("用户ID：" + user.getId() + " 用户姓名：" + user.getUsername());
	} 
	@Test
	public void getUsers() {
		List<User> users = this.testGetUsers(1L, 2L);
		for (User user : users) {
			System.out.println("用户ID：" + user.getId() + " 用户姓名：" + user.getUsername());
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<User> testGetUsers(Long... ids){
		System.out.println(StringUtils.join(ids));
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String hql = "from User where id in (" + StringUtils.join(ids, ",") +")";
		List<User> users = session.createQuery(hql).list();
		session.getTransaction().commit();
		return users;
	}

	@AfterClass
	public static void teardown() throws Exception {
		if (dataSource != null) {
			dataSource.close();
		}
	}
}
