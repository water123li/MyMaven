package cn.shaviation.mymaven.student.dao.impl;

import java.io.Serializable;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.shaviation.mymaven.common.dao.impl.GenericDaoImpl;
import cn.shaviation.mymaven.student.dao.StudentDao;
import cn.shaviation.mymaven.student.model.Student;

@Repository("studentDao")
public class StudentDaoImpl extends GenericDaoImpl<Object, Serializable> implements StudentDao {

//	private SessionFactory sessionFactory;
	DataSource dataSource;

//	public SessionFactory getSessionFactory() {
//		return this.sessionFactory;
//	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Student getStudent(Long studentId) {
		return  (Student) sessionFactory.getCurrentSession().get(Student.class, studentId);
	}
	
	@Override
	public void saveOrUpdateStudent(Student student) {
//		student.setName("哈哈");
//		sessionFactory.getCurrentSession().update(student);
		sessionFactory.getCurrentSession().saveOrUpdate(student);
	}

}
