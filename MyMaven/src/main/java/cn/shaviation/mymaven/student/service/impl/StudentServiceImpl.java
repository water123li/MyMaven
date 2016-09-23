package cn.shaviation.mymaven.student.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.shaviation.mymaven.student.dao.StudentDao;
import cn.shaviation.mymaven.student.model.Student;
import cn.shaviation.mymaven.student.service.StudentService;

@Service("studentService")
public class StudentServiceImpl implements StudentService {
	
	private StudentDao studentDao;

	public StudentDao getStudentDao() {
		return studentDao;
	}
	@Resource(name="studentDao")
	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}


	@Override
	public Student getStudent(Long studentId) {
		Student student = studentDao.getStudent(studentId);
//		this.changStudentName(student);
		return student;
	}
	
	@Override
	public Student changStudentName(Student student) {
		student.setName("哈哈");
		return student;
	}
	
	@Override
	public void saveOrUpdateStudent(Student student) {
		studentDao.saveOrUpdateStudent(student);		
	}
	
	@Override
	public boolean isStudentEqual(Long studentId) {
		Student student1 = studentDao.getStudent(studentId);
		Student student2 = studentDao.getStudent(studentId);
		return student1 == student2;
	}
}
