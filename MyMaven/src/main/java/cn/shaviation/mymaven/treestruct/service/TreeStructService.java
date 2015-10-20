package cn.shaviation.mymaven.treestruct.service;

import java.util.List;
import java.util.Map;

import cn.shaviation.mymaven.treestruct.model.TreeStruct;

public interface TreeStructService{
	
	/**
	 * 保存
	 * @param treeStruct
	 */
	public <E> E saveOrUpdate(E entity);
	/**
	 * 获取
	 * @param studentId
	 * @return
	 */
	public <E> List<E> get(Class<E> entityClass, String key, Object value);
	/**
	 * 获取数结构
	 * @return
	 */
	public List<Map<String, Object>> getTreeData();
	/**
	 * 获取
	 * @param studentId
	 * @return
	 */
	public TreeStruct getTreeStruct(Long treeStructId);

	/**
	 * 保存树结构
	 * @param treeStruct
	 */
	public TreeStruct saveOrUpdateTreeStruct(TreeStruct treeStruct);
	
	/**
	 * 删除树结构
	 * @param treeStructId
	 */
	public void deleteTreeStruct(Long treeStructId);
}
