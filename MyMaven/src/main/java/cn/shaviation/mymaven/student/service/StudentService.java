package cn.shaviation.mymaven.student.service;

import cn.shaviation.mymaven.student.model.Student;

public interface StudentService {

	/**
	 * 根据ID获取学生
	 */
	public Student getStudent(Long studentId);
	/**
	 * 改变学生名称
	 */
	public Student changStudentName(Student student);
	
	/**
	 * 改变学生名称
	 */
	public void saveOrUpdateStudent(Student student);	
}
