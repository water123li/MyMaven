package cn.shaviation.mymaven.workflow.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import cn.shaviation.mymaven.custom.model.BaseEntity;
/**
 * 工作流界面
 * @author rli
 *
 */
@Entity
public class WorkflowScreen extends BaseEntity{
	
	private String name;		
	private String description;		
	private String meno;		
	private Set<WorkflowScreenTab> workflowScreenTabs; // 界面标签列表
	private WorkflowDataObject workflowDataObject; 
	private Boolean coonterSign; 						// 是否是会签界面
	
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
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "workflowScreen")
	public Set<WorkflowScreenTab> getWorkflowScreenTabs() {
		return workflowScreenTabs;
	}
	public void setWorkflowScreenTabs(Set<WorkflowScreenTab> workflowScreenTabs) {
		this.workflowScreenTabs = workflowScreenTabs;
	}
	@ManyToOne(optional = true)
	public WorkflowDataObject getWorkflowDataObject() {
		return workflowDataObject;
	}
	public void setWorkflowDataObject(WorkflowDataObject workflowDataObject) {
		this.workflowDataObject = workflowDataObject;
	}
	public Boolean getCoonterSign() {
		return coonterSign;
	}
	public void setCoonterSign(Boolean coonterSign) {
		this.coonterSign = coonterSign;
	}
	
	
	
	
}
