package cn.shaviation.mymaven.workflow.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import cn.shaviation.mymaven.custom.model.BaseEntity;
/**
 * 工作流界面标签布局
 * @author rli
 *
 */
@Entity
public class WorkflowScreenLayout extends BaseEntity{
	
	private String title;		
	private Boolean editable;	//是否可编辑	
	private Boolean required;	//是否必填	
	private Integer row;		//所在行
	private Integer rowspan;	//跨行
	private Integer col;		//所在列
	private Integer colspan;	//夸列
	private WorkflowScreenTab workflowScreenTab; 
	private Integer showType;				//显示类型
	private Integer workflowLayoutType;		//数据类型
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getEditable() {
		return editable;
	}
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Integer getRowspan() {
		return rowspan;
	}
	public void setRowspan(Integer rowspan) {
		this.rowspan = rowspan;
	}
	public Integer getCol() {
		return col;
	}
	public void setCol(Integer col) {
		this.col = col;
	}
	public Integer getColspan() {
		return colspan;
	}
	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}
	@ManyToOne(optional = true)
	public WorkflowScreenTab getWorkflowScreenTab() {
		return workflowScreenTab;
	}
	public void setWorkflowScreenTab(WorkflowScreenTab workflowScreenTab) {
		this.workflowScreenTab = workflowScreenTab;
	}
	public Integer getShowType() {
		return showType;
	}
	public void setShowType(Integer showType) {
		this.showType = showType;
	}
	public Integer getWorkflowLayoutType() {
		return workflowLayoutType;
	}
	public void setWorkflowLayoutType(Integer workflowLayoutType) {
		this.workflowLayoutType = workflowLayoutType;
	}
	
	
	
	
}
