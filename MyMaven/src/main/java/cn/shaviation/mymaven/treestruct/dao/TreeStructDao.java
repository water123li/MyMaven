package cn.shaviation.mymaven.treestruct.dao;

import java.util.List;

public interface TreeStructDao {
	/**
	 * 获取
	 * @param studentId
	 * @return
	 */
	public <E> E get(Class<E> entityClass, Long id);
	/**
	 * 获取
	 * @param studentId
	 * @return
	 */
	public <E> List<E> get(Class<E> entityClass, String key, Object value);

	/**
	 * 保存
	 * @param treeStruct
	 */
	public <E> E saveOrUpdate(E entity);
	
	/**
	 * 删除树结构
	 * @param treeStructId
	 */
	public <E> void delete(Class<E> entityClass, Long id);
	
	
}
