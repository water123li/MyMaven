package cn.shaviation.mymaven.workflow.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import cn.shaviation.mymaven.custom.model.BaseEntity;

/**
 * 工作流
 * 
 * @author rli
 *
 */
@Entity
public class Workflow extends BaseEntity {

	private String name;
	private Integer status;
	private Set<WorkflowStatus> WorkflowStatusSet;
	private WorkflowDataObject workflowDataObject;
	private String rule;
	private Boolean released; // 是否已发布

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(mappedBy = "workflow")
	public Set<WorkflowStatus> getWorkflowStatusSet() {
		return WorkflowStatusSet;
	}

	public void setWorkflowStatusSet(Set<WorkflowStatus> workflowStatusSet) {
		WorkflowStatusSet = workflowStatusSet;
	}

	@ManyToOne
	public WorkflowDataObject getWorkflowDataObject() {
		return workflowDataObject;
	}

	public void setWorkflowDataObject(WorkflowDataObject workflowDataObject) {
		this.workflowDataObject = workflowDataObject;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Boolean getReleased() {
		return released;
	}

	public void setReleased(Boolean released) {
		this.released = released;
	}

}
