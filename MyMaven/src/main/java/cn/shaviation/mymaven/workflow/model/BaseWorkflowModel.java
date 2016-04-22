package cn.shaviation.mymaven.workflow.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import cn.shaviation.mymaven.common.model.BaseEntity;
import cn.shaviation.mymaven.user.model.User;
/**
 * 工作流基类
 * @author rli
 *
 */
@Entity
public class BaseWorkflowModel extends BaseEntity{
	
	private String code;		//工作流标识
	private User creater;		//创建人
	private User submiter;		//提交人
	private Date submitDate;		
	private	Long workflowId;		//流程Id
	private	Long documentId;		//工作流实例文档Id
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@ManyToOne
	public User getCreater() {
		return creater;
	}
	public void setCreater(User creater) {
		this.creater = creater;
	}
	@ManyToOne
	public User getSubmiter() {
		return submiter;
	}
	public void setSubmiter(User submiter) {
		this.submiter = submiter;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public Long getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}
	public Long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
	
	
}
