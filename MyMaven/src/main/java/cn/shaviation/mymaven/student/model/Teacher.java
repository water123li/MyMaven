package cn.shaviation.mymaven.student.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Teacher implements Cloneable{

	Long id;
	String name;
	List<Student> students = new LinkedList<Student>();
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@OneToMany(mappedBy="teacher")
	@Cascade(CascadeType.ALL)
	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + ", list=" + students + "]";
	}
	
	@Override
	public Object clone(){
		Teacher o =null; 
		try {
			o=(Teacher) super.clone();
//			o.list  = new LinkedList<Student>(list);
			o.students  = new LinkedList<Student>();
			for (Student student : students) {
				student = (Student) student.clone();
				o.students.add(student);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}
}
