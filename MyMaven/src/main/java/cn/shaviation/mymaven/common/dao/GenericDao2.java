package cn.shaviation.mymaven.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;

import cn.shaviation.mymaven.common.query.PageQueryCondition;
import cn.shaviation.mymaven.common.query.PageQueryResult;

/**
 * 
 * @author Administrator
 *
 * @param <T>实体类
 * @param <PK>实体主键类型
 */
public interface GenericDao2<T, PK extends Serializable> {

	public SessionFactory getSessionFactory();

	public HibernateTemplate getHibernateTemplate();
	
	public void delete(T entity);
	public <E> void delete(Class<E> entityClass, PK id);
	public <E> void delete(Class<E> entityClass, List<PK> ids);
	public <E> void delete(List<E> entities);

	
	public <E> List<E> getAll(Class<E> entityClass);
	public <E> List<E> get(Class<E> entityClass, Map<String, Object> conditions, boolean matchExact);
	public <E> List<E> get(Class<E> entityClass, String key, Object value);
	public <E> List<E> get(Class<E> entityClass, String key, Object value, boolean matchExact);
	public <E> List<E> get(Class<E> entityClass, Map<String, Object> conditions, Map<String, Boolean> orders,
			boolean matchExact);
	public <E> E get(Class<E> entityClass, PK id);
	public <E>  List<E> get(Class<E> entityClass, String propertyName, Object propertyValue, boolean matchExact,
			String orderPropertyName, boolean order);
	
	public PageQueryResult get(final PageQueryCondition queryCondition);
	public <E> List<E> get(Class<E> entityClass, Map<String, Object> conditions);
	public PageQueryResult get(final PageQueryCondition queryCondition, boolean matchExact);
	public PageQueryResult getComplexPK(final PageQueryCondition queryCondition, boolean matchExact);
	

	public <E> E getOne(Class<E> entityClass);
	public <E> E getOne(Class<E> entityClass, Map<String, Object> conditions);
	public <E> E getOne(Class<E> entityClass, Map<String, Object> conditions, boolean matchExact);
	public <E> E getOne(Class<E> entityClass, Map<String, Object> conditions, Map<String, Boolean> orders,
			boolean matchExact);
	
	public <E> E getOne(Class<E> entityClass, String key, Object value);
	
	public <E> E getOne(Class<E> entityClass, String key, Object value, boolean matchExact);
	public <E> E getOne(Class<E> entityClass, String propertyName, Object propertyValue, boolean matchExact,
			String orderPropertyName, boolean order);
	
	
	
	public <E> E saveOrUpdate(E entity);
	public <E> E saveOrUpdate(E entity, Map<String, Object> json);
	public <E> E saveOrUpdate(E entity, Map<String, Object> json, boolean isJson);
	public <E> List<E> saveAll(List<E> entities, List<Map<String, Object>> modifiedData, boolean isJson);


	public <E> Long getCount(Class<E> entityClass, String key, Object value, boolean matchExact);
	public <E> Long getCount(final PageQueryCondition queryCondition, boolean matchExact);
	public <E> Long getCount(Class<E> entityClass);
	public <E> Long getCount(Class<E> entityClass, Map<String, Object> conditions, boolean matchExact);


	public int executeUpdate(final String ql, final boolean isNative, final Object... params);
	public int executeUpdate(final String ql, final boolean isNative, final Map<String, Object> params);

	
	public <E> List<E> executeQuery(final String ql, final boolean isNative, final Object... params);
	public <E> List<E> executeQuery(final String ql, final boolean isNative, final Map<String, Object> params);

	
	public Set<String> getColumnNames(final String tableName);



}
