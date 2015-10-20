package cn.shaviation.mymaven.treestruct.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class DataElement {
	private Long id;
	private String name;
	private String code;
	private TreeStruct treeStruct;
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@ManyToOne
	@Cascade(CascadeType.ALL)
	public TreeStruct getTreeStruct() {
		return treeStruct;
	}
	public void setTreeStruct(TreeStruct treeStruct) {
		this.treeStruct = treeStruct;
	}
	
	
}
