package cn.shaviation.mymaven.workflow.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import cn.shaviation.mymaven.custom.model.BaseEntity;

/**
 * 工作流对象
 * 
 * @author rli
 *
 */
@Entity
public class WorkflowDataObject extends BaseEntity {

	private WorkflowEntity workflowEntity; // 工作流对象实体
	private Set<WorkflowScreen> workflowScreens; // 工作流界面列表

	@ManyToOne
	public WorkflowEntity getWorkflowEntity() {
		return workflowEntity;
	}

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "workflowDataObject")
	public Set<WorkflowScreen> getWorkflowScreens() {
		return workflowScreens;
	}

	public void setWorkflowEntity(WorkflowEntity workflowEntity) {
		this.workflowEntity = workflowEntity;
	}

	public void setWorkflowScreens(Set<WorkflowScreen> workflowScreens) {
		this.workflowScreens = workflowScreens;
	}

}
