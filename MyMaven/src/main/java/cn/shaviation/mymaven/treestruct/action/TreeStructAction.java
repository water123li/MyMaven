package cn.shaviation.mymaven.treestruct.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.shaviation.mymaven.treestruct.model.TreeStruct;
import cn.shaviation.mymaven.treestruct.service.TreeStructService;
@ParentPackage("default")
@Namespace("/treestruct")
public class TreeStructAction extends ActionSupport {
	private static final long serialVersionUID = 571475357195910954L;
	private TreeStructService treeStructService;
	private TreeStruct treeStruct;
	
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TreeStruct getTreeStruct() {
		return treeStruct;
	}

	public void setTreeStruct(TreeStruct treeStruct) {
		this.treeStruct = treeStruct;
	}

	public TreeStructService getTreeStructService() {
		return treeStructService;
	}

	public void setTreeStructService(TreeStructService treeStructService) {
		this.treeStructService = treeStructService;
	}
	
	/**
	 * 进入界面
	 * @return
	 */
	@Action(value = "initTreeStruct", results = {@Result(name = "success", location = "/WEB-INF/pages/treestruct/dataElement.jsp") })
	public String listPage() {
		return SUCCESS;
	}

	/**
	 * 获取树
	 * @return
	 */
	@Action(value = "getTreeData", results = { @Result(name = "success", params = { "root", "result" }, type = "json") })
	public String getTreeData() {
		ActionContext.getContext().put("result", treeStructService.getTreeData());
		return SUCCESS;
	}
	/**
	 * 保存树结构
	 * @return
	 */
	@Action(value = "saveTreeStruct", results = { @Result(name = "success", params = { "root", "result" }, type = "json") })
	public String saveTreeStruct() {
		Map<String, Object> result = new HashMap<>();
		try {
			TreeStruct treeStructTemp = treeStructService.saveOrUpdateTreeStruct(treeStruct);
			result.put("id", treeStructTemp.getId());
			result.put("isSuccessed", true);
		} catch (Exception e) {
			result.put("isSuccessed", false);
			e.printStackTrace();
		}
		ActionContext.getContext().put("result", result);
		return SUCCESS;
	}
	/**
	 * 保存树结构
	 * @return
	 */
	@Action(value = "deleteTreeStruct", results = { @Result(name = "success", params = { "root", "result" }, type = "json") })
	public String deleteTreeStruct() {
		Map<String, Object> result = new HashMap<>();
		try {
			treeStructService.deleteTreeStruct(id);
			result.put("isSuccessed", true);
		} catch (Exception e) {
			result.put("isSuccessed", false);
			e.printStackTrace();
		}
		ActionContext.getContext().put("result", result);
		return SUCCESS;
	}
}
