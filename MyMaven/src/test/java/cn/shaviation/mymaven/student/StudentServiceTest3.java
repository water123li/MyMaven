package cn.shaviation.mymaven.student;

import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;

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
public class StudentServiceTest3 {
	private static StudentService studentService;
	private static UserService userService;
	private static SessionFactory sessionFactory;
	private static ClassPathXmlApplicationContext applicationContext;
	private static BasicDataSource dataSource;
	private static HibernateTemplate hibernateTemplate;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(
				"/cn/shaviation/mymaven/student/applicationContext2.xml");
		studentService = (StudentService) applicationContext.getBean("studentService");
		userService = (UserService) applicationContext.getBean("userService");
		sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
		dataSource = (BasicDataSource) applicationContext.getBean("dataSource");
		hibernateTemplate = new HibernateTemplate(sessionFactory);
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
	
	@Test
	public void testExecuteQuery() {
		String sql = "select new map(s.id as id, s.name as name) from Student s where s.teacher.id=?";
		List<Map<String, Object>> users = executeQuery(sql, false, 1L);
		System.out.println("数据：");
		for (Map<String, Object> map : users) {
			System.out.println("id:" + map.get("id") + "	name:" + map.get("name"));
		}
	} 
	
	
	@AfterClass
	public static void teardown() throws Exception {
		if (dataSource != null) {
			dataSource.close();
		}
	}
	@SuppressWarnings("unchecked")
	public static <E> List<E> executeQuery(final String ql, final boolean isNative, final Object... params) {
		return hibernateTemplate.execute(new HibernateCallback<List<E>>() {
			@Override
			public List<E> doInHibernate(Session session) throws HibernateException {
				Query query = isNative ? session.createSQLQuery(ql) : session.createQuery(ql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				return query.list();
			}
		});
	}
}
