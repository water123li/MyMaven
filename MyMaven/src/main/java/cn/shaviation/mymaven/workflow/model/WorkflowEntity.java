package cn.shaviation.mymaven.workflow.model;

import javax.persistence.Entity;

import cn.shaviation.mymaven.custom.model.BaseEntity;
/**
 * 工作流对象实体
 * @author rli
 *
 */
@Entity
public class WorkflowEntity extends BaseEntity{
	
	private String code;		//工作流对象标识
	private String name;		//工作流对象名称
	private String entityClass;	//工作流对象实体
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEntityClass() {
		return entityClass;
	}
	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}
	
	
}
