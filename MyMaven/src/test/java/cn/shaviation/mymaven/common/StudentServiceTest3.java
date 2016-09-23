package cn.shaviation.mymaven.common;

import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;

import cn.shaviation.mymaven.student.model.Student;
import cn.shaviation.mymaven.student.model.Teacher;
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
				"/cn/shaviation/mymaven/common/applicationContext2.xml");
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
		String sql = "select new map(id as id, name as name) from Student s where s.teacher.id=?";
//		String sql = "select id as ids, name as names from Student s";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//		List<Map<String, Object>> users = jdbcTemplate.queryForList(sqlText);
		List<Map<String, Object>> users = executeQuery(sql, false, 1L);
//		String sql = "select id, name from student where teacher_id=?";
//		List<Object[]> users = executeQuery(sql, true, 1L);
		System.out.println("数据：");
//		for (Object[] objects : users) {
//			System.out.println(objects[0] + "	" + objects[1]);
//		}
		for (Map<String, Object> map : users) {
			System.out.println("id:" + map.get("id") + "	name:" + map.get("name"));
//			System.out.println("id:" + list.get(0) + "	name:" + list.get(1));
		}
	} 
	@Test
	public void testQuery() {
		String sql = "select s.teacher from Student s where s.teacher.id=?";
//		List<Teacher> teachers = executeQuery(sql, false, 1L);
//		for (Teacher teacher : teachers) {
//			System.out.println(teacher.getId() + "   " + teacher.getName());
//		}
		String hql = "SELECT new map(s.id as sid,s.name as sname,t.name as tname) FROM Student s left JOIN s.teacher t ";
		List<Map<String, Object>> users = executeQuery(hql, false);
		for (Map<String, Object> map : users) {
			System.out.println("id:" + map.get("sid") + "	sname:" + map.get("sname") + "	tname:" + map.get("tname"));
		}
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testSession() {
		String sql = "select * from student where teacher_id=?";
		Query query = sessionFactory.openSession().createSQLQuery(sql)
				.addEntity(Student.class)
				.setParameter(0, 1L);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println(student.getId() + "   " + student.getName());
		}
//		List<Object[]> list = query.list();
//		for (Object[] object : list) {
//			System.out.println(object[0] + "  " + object[1]);
//		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCritia() {
		Session session = sessionFactory.openSession();
		List<Student> students = session.createCriteria(Student.class)
				.add(Restrictions.ilike("name", "哈哈")).list();
		for (Student student : students) {
			System.out.println(student.getId() + "   " + student.getName());
		}
		
	}
	
	@Test
	public void testHqlJoin() {
//		String hql = "select distinct t from Teacher t, Student s where t.id=s.teacher.id and s.id = 1";
		String hql = "select distinct t from Teacher t where t.id = 10";
		List<Teacher> teachers = executeQuery(hql, false);
		for (Teacher teacher : teachers) {
			System.out.println(teacher.getId() + "   " + teacher.getName());
		}
	}
	@Test
	public void testSqlReturnMap() {
		//sql查询结果为map
		Session session = sessionFactory.openSession();
		String sql = "SELECT s.id,s.`name` as sname,t.`name` as tname FROM student s LEFT JOIN teacher t ON s.teacher=t.id";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> users = query.list();
		for (Map<String, Object> map : users) {
			System.out.println("id:" + map.get("sid") + "	sname:" + map.get("sname") + "	tname:" + map.get("tname"));
		}	
	}
	@Test
	public void testHqlLeftJoin() {
		String hql = "SELECT new map(s.id as sid,s.name as sname,t.name as tname) FROM Student s left JOIN s.teacher t";
		List<Map<String, Object>> users = executeQuery(hql, false);
		for (Map<String, Object> map : users) {
			System.out.println("id:" + map.get("sid") + "	sname:" + map.get("sname") + "	tname:" + map.get("tname"));
		}	
	}
	
	@Test
	public void testJdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Long> ids = jdbcTemplate.queryForList("select id from student", Long.class);
		for (Long id : ids) {
			System.out.println(id);
		}
	}
	
	
	@AfterClass
	public static void teardown() throws Exception {
		if (dataSource != null) {
			dataSource.close();
		}
		if (sessionFactory != null) {
			sessionFactory.close();
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
