package cn.shaviation.mymaven.workflow.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import cn.shaviation.mymaven.common.model.BaseEntity;
/**
 * 工作流界面标签布局
 * @author rli
 *
 */
@Entity
public class WorkflowScreenTab extends BaseEntity{
	
	private String name;		
	private String description;		
	private String meno;		
	private WorkflowScreen workflowScreen; 
	private Set<WorkflowScreenLayout> workflowScreenLayouts; // 界面标签布局列表
	private Integer position;		//排序
	private Integer workflowTabType;		//数据类型
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMeno() {
		return meno;
	}
	public void setMeno(String meno) {
		this.meno = meno;
	}
	@ManyToOne(optional = true)
	public WorkflowScreen getWorkflowScreen() {
		return workflowScreen;
	}
	public void setWorkflowScreen(WorkflowScreen workflowScreen) {
		this.workflowScreen = workflowScreen;
	}
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "workflowScreenTab")
	public Set<WorkflowScreenLayout> getWorkflowScreenLayouts() {
		return workflowScreenLayouts;
	}
	public void setWorkflowScreenLayouts(Set<WorkflowScreenLayout> workflowScreenLayouts) {
		this.workflowScreenLayouts = workflowScreenLayouts;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public Integer getWorkflowTabType() {
		return workflowTabType;
	}
	public void setWorkflowTabType(Integer workflowTabType) {
		this.workflowTabType = workflowTabType;
	}
	
	
	
	
}
