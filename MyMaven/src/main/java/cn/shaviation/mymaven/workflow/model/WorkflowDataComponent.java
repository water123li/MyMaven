package cn.shaviation.mymaven.workflow.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import cn.shaviation.mymaven.common.model.BaseEntity;

/**
 * 工作流对象数据项组件
 * 
 * @author rli
 *
 */
@Entity
public class WorkflowDataComponent extends BaseEntity {

	private String code;		
	private String name;	
	private WorkflowEntity workflowEntity; // 工作流对象实体
	private Integer showType;	
	private Boolean editable;
	
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
	@ManyToOne
	public WorkflowEntity getWorkflowEntity() {
		return workflowEntity;
	}
	public void setWorkflowEntity(WorkflowEntity workflowEntity) {
		this.workflowEntity = workflowEntity;
	}
	public Integer getShowType() {
		return showType;
	}
	public void setShowType(Integer showType) {
		this.showType = showType;
	}
	public Boolean getEditable() {
		return editable;
	}
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}	
	


}
