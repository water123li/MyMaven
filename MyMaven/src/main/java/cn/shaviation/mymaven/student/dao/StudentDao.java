package cn.shaviation.mymaven.student.dao;

import java.io.Serializable;

import cn.shaviation.mymaven.common.dao.GenericDao;
import cn.shaviation.mymaven.student.model.Student;

public interface StudentDao extends GenericDao<Object, Serializable>{
	/**
	 * 通过ID获取学生信息
	 * 
	 * @param studentId
	 * @return
	 */
	public Student getStudent(Long studentId);

	/**
	 * 改变学生名称
	 */
	public void saveOrUpdateStudent(Student student);

}
