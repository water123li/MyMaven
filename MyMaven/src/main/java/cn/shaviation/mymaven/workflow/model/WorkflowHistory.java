package cn.shaviation.mymaven.workflow.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import cn.shaviation.mymaven.common.model.BaseEntity;
import cn.shaviation.mymaven.user.model.User;

/**
 * 工作流历史
 * 
 * @author rli
 *
 */
@Entity
public class WorkflowHistory extends BaseEntity {

	private User user;
	private Date operateDate;
	private String operation;
	private String description;
	private BaseWorkflowModel baseWorkflowModelId;
	private Long workflowTransitionId; // 关联的迁移Id
	private Long workflowStatusId; // 迁移的原状态Id
	
	@ManyToOne
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BaseWorkflowModel getBaseWorkflowModelId() {
		return baseWorkflowModelId;
	}
	public void setBaseWorkflowModelId(BaseWorkflowModel baseWorkflowModelId) {
		this.baseWorkflowModelId = baseWorkflowModelId;
	}
	public Long getWorkflowTransitionId() {
		return workflowTransitionId;
	}
	public void setWorkflowTransitionId(Long workflowTransitionId) {
		this.workflowTransitionId = workflowTransitionId;
	}
	public Long getWorkflowStatusId() {
		return workflowStatusId;
	}
	public void setWorkflowStatusId(Long workflowStatusId) {
		this.workflowStatusId = workflowStatusId;
	}

	
}
