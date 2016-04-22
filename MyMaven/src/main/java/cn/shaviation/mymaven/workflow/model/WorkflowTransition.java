package cn.shaviation.mymaven.workflow.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import cn.shaviation.mymaven.common.model.BaseEntity;

/**
 * 工作流迁移
 * 
 * @author rli
 *
 */
@Entity
public class WorkflowTransition extends BaseEntity {

	private String name;
	private Integer position;
	private WorkflowScreen workflowScreen;
	private Workflow workflow;
	private WorkflowStatus sourceWorkflowStatus;	//源状态
	private WorkflowStatus targetWorkflowStatus;	//目标状态
	private List<WorkflowPoint> workflowPoints;
	private String role;
	private String handler;
	private String handleDate;
	private String businessHandle;
	private Boolean countSign; // 是否是会签
	private String condition; // 迁移条件

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

	@ManyToOne
	public WorkflowScreen getWorkflowScreen() {
		return workflowScreen;
	}

	public void setWorkflowScreen(WorkflowScreen workflowScreen) {
		this.workflowScreen = workflowScreen;
	}

	@ManyToOne
	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	@ManyToOne
	public WorkflowStatus getSourceWorkflowStatus() {
		return sourceWorkflowStatus;
	}

	public void setSourceWorkflowStatus(WorkflowStatus sourceWorkflowStatus) {
		this.sourceWorkflowStatus = sourceWorkflowStatus;
	}

	@ManyToOne
	public WorkflowStatus getTargetWorkflowStatus() {
		return targetWorkflowStatus;
	}

	public void setTargetWorkflowStatus(WorkflowStatus targetWorkflowStatus) {
		this.targetWorkflowStatus = targetWorkflowStatus;
	}

	@OneToMany(mappedBy = "workflowTransition", cascade = CascadeType.ALL)
	public List<WorkflowPoint> getWorkflowPoints() {
		return workflowPoints;
	}

	public void setWorkflowPoints(List<WorkflowPoint> workflowPoints) {
		this.workflowPoints = workflowPoints;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getHandleDate() {
		return handleDate;
	}

	public void setHandleDate(String handleDate) {
		this.handleDate = handleDate;
	}

	public String getBusinessHandle() {
		return businessHandle;
	}

	public void setBusinessHandle(String businessHandle) {
		this.businessHandle = businessHandle;
	}

	public Boolean getCountSign() {
		return countSign;
	}

	public void setCountSign(Boolean countSign) {
		this.countSign = countSign;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
