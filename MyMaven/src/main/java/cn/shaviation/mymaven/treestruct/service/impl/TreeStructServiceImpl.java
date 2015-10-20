package cn.shaviation.mymaven.treestruct.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.shaviation.mymaven.treestruct.dao.TreeStructDao;
import cn.shaviation.mymaven.treestruct.model.TreeStruct;
import cn.shaviation.mymaven.treestruct.service.TreeStructService;

@Service("treeStructService")
public class TreeStructServiceImpl implements TreeStructService{

	private TreeStructDao treeStructDao;
	
	@Resource
	public void setTreeStructDao(TreeStructDao treeStructDao) {
		this.treeStructDao = treeStructDao;
	}

	@Override
	public <E> E saveOrUpdate(E entity) {
		return treeStructDao.saveOrUpdate(entity);
	}
	@Override
	public <E> List<E> get(Class<E> entityClass, String key, Object value) {
		return treeStructDao.get(entityClass, key, value);
	}
	@Override
	public List<Map<String, Object>> getTreeData() {
		List<Map<String, Object>> result = new ArrayList<>();
		List<TreeStruct> treeStructs = treeStructDao.get(TreeStruct.class, "parent", null);
		Map<String, Object> rootData = new HashMap<>();
		Map<String, Object> rootAttr = new HashMap<>();
		rootData.put("text", "商品分类树");
		rootData.put("attr", rootAttr);
		rootAttr.put("class", "jstree-open");
		rootAttr.put("nType", "root");
		List<Map<String, Object>> rootList = new ArrayList<>();
		for (TreeStruct treeStruct : treeStructs) {
			rootList.add(getTreeStructMap(treeStruct));
		}
		rootData.put("children", rootList);
		result.add(rootData);
		return result;
	}

	/**
	 * 获取数据map集合
	 * @param treeStruct
	 * @return
	 */
	private Map<String, Object> getTreeStructMap(TreeStruct treeStruct) {
		Map<String, Object> nodeData = new HashMap<>();
		Map<String, Object> nodeAttr = new HashMap<>();
		nodeData.put("text", treeStruct.getName());
		nodeData.put("id", treeStruct.getId());
		
		nodeData.put("attr", nodeAttr);
		nodeAttr.put("class", "jstree-open");
		nodeAttr.put("nType", "childNode");
		nodeAttr.put("id", treeStruct.getId());
		nodeAttr.put("code", treeStruct.getCode());
		Set<TreeStruct> children = treeStruct.getChildren();
		if (children != null && children.size() > 0) {
			List<Map<String, Object>> childrenList = new ArrayList<>();
			for (TreeStruct childrenTreeStruct : children) {
				childrenList.add(getTreeStructMap(childrenTreeStruct));
			}
			nodeData.put("children", childrenList);
		}
		return nodeData;
	}

	@Override
	public TreeStruct getTreeStruct(Long treeStructId) {
		return treeStructDao.get(TreeStruct.class, treeStructId);
	}

	@Override
	public TreeStruct saveOrUpdateTreeStruct(TreeStruct treeStruct) {
		return treeStructDao.saveOrUpdate(treeStruct);
	}

	@Override
	public void deleteTreeStruct(Long treeStructId) {
		treeStructDao.delete(TreeStruct.class, treeStructId);
	}
	
	
}
