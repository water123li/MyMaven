package cn.shaviation.mymaven.treestruct.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class TreeStruct {
	private Long id;
	private String name;
	private String code;
	private Long num;
	private Set<TreeStruct> children;
	private TreeStruct parent;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	
	@OrderBy(value = "num asc")
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
//	@OneToMany(mappedBy = "parent", fetch=FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	@OrderBy(value = "name asc")
	public Set<TreeStruct> getChildren() {
		return children;
	}
	public void setChildren(Set<TreeStruct> children) {
		this.children = children;
	}
	
	@ManyToOne
	public TreeStruct getParent() {
		return parent;
	}
	public void setParent(TreeStruct parent) {
		this.parent = parent;
	}
	
	
}
