package cn.shaviation.mymaven.workflow.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import cn.shaviation.mymaven.common.model.BaseEntity;

/**
 * 工作流状态
 * 
 * @author rli
 *
 */
@Entity
public class WorkflowStatus extends BaseEntity {

	private String name;
	private Integer position;
	private Workflow workflow;
	private WorkflowScreen workflowScreen;
	private WorkflowPoint workflowPoint;

	private Integer typeCode;
	private Boolean branchStatus; // 是否是分支

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	@ManyToOne(optional = true)
	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	@ManyToOne(optional = true)
	public WorkflowScreen getWorkflowScreen() {
		return workflowScreen;
	}

	public void setWorkflowScreen(WorkflowScreen workflowScreen) {
		this.workflowScreen = workflowScreen;
	}

	@OneToOne(cascade = CascadeType.ALL)
	public WorkflowPoint getWorkflowPoint() {
		return workflowPoint;
	}

	public void setWorkflowPoint(WorkflowPoint workflowPoint) {
		this.workflowPoint = workflowPoint;
	}

	public Integer getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(Integer typeCode) {
		this.typeCode = typeCode;
	}

	public Boolean getBranchStatus() {
		return branchStatus;
	}

	public void setBranchStatus(Boolean branchStatus) {
		this.branchStatus = branchStatus;
	}

}
