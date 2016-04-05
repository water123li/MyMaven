package cn.shaviation.mymaven.student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.XlsDataFileLoader;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import cn.shaviation.mymaven.beantest.Shape;
import cn.shaviation.mymaven.student.model.Student;
import cn.shaviation.mymaven.student.model.Teacher;
import cn.shaviation.mymaven.student.service.StudentService;
import cn.shaviation.mymaven.user.model.User;
import cn.shaviation.mymaven.user.service.UserService;
import cn.shaviation.mymaven.util.DataSourceUtil;

/**
 * 学生接口单元测试
 * 
 * @author Administrator
 * 
 */
public class StudentServiceTest {
	private static StudentService studentService;
	private static UserService userService;
	private static SessionFactoryImplementor sessionFactory;
	private static IDatabaseConnection databaseConnection;
	private static IDataSet dataSet; 
	private static ClassPathXmlApplicationContext applicationContext;
	private static DataSource dataSource;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(
				"/cn/shaviation/mymaven/student/StudentServiceTest-springContext.xml");
		studentService = (StudentService) applicationContext.getBean("studentService");
		userService = (UserService) applicationContext.getBean("userService");
		sessionFactory = (SessionFactoryImplementor) applicationContext.getBean("sessionFactory");
		dataSource = (DataSource) applicationContext.getBean("dataSource");
		databaseConnection = new DatabaseDataSourceConnection(dataSource);
		Map<String, Object> replacements = new HashMap<String, Object>();
		replacements.put("", null);
		
		dataSet = new XlsDataFileLoader(replacements)
				.load("/cn/shaviation/mymaven/student/StudentServiceTest-data.xls");

		DatabaseOperation.REFRESH.execute(databaseConnection, dataSet);
		//hibernate3使用下面的代码
//		TransactionSynchronizationManager.bindResource(
//				sessionFactory,
//				new SessionHolder(SessionFactoryUtils.getSession(sessionFactory, true)));
		TransactionSynchronizationManager.bindResource(
				sessionFactory,
				new SessionHolder(sessionFactory.openSession()));
		
	}

	@Test
	public void getStudent() throws Exception {
		
		Student student = studentService.getStudent(1L);
		student.setName("你好");
		studentService.saveOrUpdateStudent(student);
		System.out.println("学生ID：" + student.getId() + " 学生姓名：" + student.getName());
		
		Teacher teacher = new Teacher();
		BeanUtils.copyProperties(teacher, student);

		System.out.println("老师ID：" + teacher.getId() + " 老师姓名：" + teacher.getName());
		
		Student student2 = (Student) BeanUtils.cloneBean(student);
		System.out.println("学生2ID：" + student2.getId() + " 学生2姓名：" + student2.getName());
		
		Assert.assertEquals(student.getName(), "哈哈");
//		BeanUtils.
//		PropertyUtils.
//		JSONPopulator jsonPopulator = new JSONPopulator();
//		jsonPopulator.populateObject(object, elements);
//		JSONWriter jsonWriter = new JSONWriter();
//		JSONInterceptor;
		
	}
	
	@Test
	public void updateStudent() {
		
		Student student = studentService.getStudent(1L);
		System.out.println("学生ID：" + student.getId() + " 学生姓名：" + student.getName());
		student.setName("你好");
		studentService.saveOrUpdateStudent(student);
		student = studentService.getStudent(1L);
		System.out.println("学生ID：" + student.getId() + " 学生姓名：" + student.getName());
		
	}
	
	@Test
	public void saveUser() {
		
		User user = userService.addUser("哈哈", "1234");
		
		System.out.println("用户ID：" + user.getId() + " 用户姓名：" + user.getUsername());
	} 
	
	@Test
	public void getBeanNames() {
		String[] names = applicationContext.getBeanNamesForType(Shape.class);
		for (String name : names) {
			System.out.println("Bean名称：" + name);
			Shape shape = (Shape) applicationContext.getBean(name);
			Class<?> clazz = applicationContext.getType(name);
			
			System.out.println("Class类型：" + clazz.getName());
			System.out.println("Bean含义：" + shape.getClassName() + "	类名：" + shape.getDataClass());
		}
		
	}
	@Test
	public void dataSourceTest() throws SQLException {
//		HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList("select id, name from student");
		for (Map<String, Object> map : queryForList) {
			System.out.println("id:" + map.get("id") + " name:" + map.get("name"));
		}
	}
	@Test
	public void dataSourceCreate() throws SQLException {
		BasicDataSource source = DataSourceUtil.createBasicDataSource("localhot", "3306", "mymaven", "root", "root");
		JdbcTemplate template = new JdbcTemplate(source);
		try {
			getConnection(source);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
//		Connection connection = source.getConnection();
//		if (connection == null) {
//			System.out.println("获取不到连接");
//		}else {
//			System.out.println("连接成功");
//		}
	}
	
	public static Connection getConnection(BasicDataSource source) throws Exception{
		Connection connection = null;
		try {
			connection = source.getConnection();
		} catch (Exception e) {
			if (source != null) {
				source.close();
			}
			throw new Exception("连接不成功！");
		}
		
		return connection;
	}

	@AfterClass
	public static void teardown() throws Exception {
		try {
			DatabaseOperation.DELETE_ALL.execute(databaseConnection, dataSet);
		} finally {
			databaseConnection.close();
			TransactionSynchronizationManager.unbindResource(sessionFactory);
			sessionFactory.close();
		}

	}
}
