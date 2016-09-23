package cn.shaviation.mymaven.workflow.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import cn.shaviation.mymaven.custom.model.BaseEntity;

/**
 * 流程图控制点坐标
 * 
 * @author rli
 *
 */
@Entity
public class WorkflowPoint extends BaseEntity {

	private int x;
	private int y;
	private WorkflowStatus sourceWorkflowStatus;
	private WorkflowStatus targetWorkflowStatus;
	private WorkflowTransition workflowTransition;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@OneToOne
	public WorkflowStatus getSourceWorkflowStatus() {
		return sourceWorkflowStatus;
	}
	public void setSourceWorkflowStatus(WorkflowStatus sourceWorkflowStatus) {
		this.sourceWorkflowStatus = sourceWorkflowStatus;
	}
	@OneToOne
	public WorkflowStatus getTargetWorkflowStatus() {
		return targetWorkflowStatus;
	}
	public void setTargetWorkflowStatus(WorkflowStatus targetWorkflowStatus) {
		this.targetWorkflowStatus = targetWorkflowStatus;
	}
	@ManyToOne
	public WorkflowTransition getWorkflowTransition() {
		return workflowTransition;
	}
	public void setWorkflowTransition(WorkflowTransition workflowTransition) {
		this.workflowTransition = workflowTransition;
	}
	

	
}
