package cn.shaviation.mymaven.common.dao.impl;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.CollectionType;
import org.hibernate.type.DateType;
import org.hibernate.type.MaterializedClobType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;

import cn.shaviation.mymaven.bean.ExtendedPair;
import cn.shaviation.mymaven.bean.Pair;
import cn.shaviation.mymaven.common.dao.GenericDao;
import cn.shaviation.mymaven.common.dao.PersistenceSet;
import cn.shaviation.mymaven.common.query.PageQueryCondition;
import cn.shaviation.mymaven.common.query.PageQueryResult;
import cn.shaviation.mymaven.util.CommonUtil;
/**
 * 
 * @author Administrator
 *
 * @param <T>
 * @param <PK>
 */
public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

	protected HibernateTemplate hibernateTemplate;
	protected SessionFactory sessionFactory;
	
	/**
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <E> E getOne(Class<E> entityClass) {
		final StringBuilder hql = new StringBuilder(" from ").append(entityClass.getName());
		List<E> dataList = this.hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql.toString());
				query.setMaxResults(1);
				return query.list();
			}
			
		});
		if (dataList.size() > 0) {
			return dataList.get(0);
		}else {
			return null;
		}
	}

	@Override
	public <E> List<E> getAll(Class<E> entityClass) {
		return this.hibernateTemplate.loadAll(entityClass);
	}

	@Override
	public <E> E getOne(Class<E> entityClass, Map<String, Object> conditions, boolean matchExact) {
		PageQueryCondition queryCondition = new PageQueryCondition()
				.from(entityClass).where(conditions).limit(1, 1);
		PageQueryResult result = this.get(queryCondition, matchExact);
		return CommonUtil.getOne(result.getRows(entityClass));
	}

	@Override
	public <E> List<E> get(Class<E> entityClass, Map<String, Object> conditions, boolean matchExact) {
		PageQueryCondition queryCondition = new PageQueryCondition()
				.from(entityClass).where(conditions).limit(1, 1);
		PageQueryResult result = this.get(queryCondition, matchExact);
		return result.getRows(entityClass);
	}

	@Override
	public <E> E getOne(Class<E> entityClass, String key, Object value) {
		return this.getOne(entityClass, key, value, false);
	}

	@Override
	public <E> List<E> get(Class<E> entityClass, String key, Object value) {
		return this.get(entityClass, key, value, false);
	}

	@Override
	public <E> E getOne(Class<E> entityClass, String key, Object value, boolean matchExact) {
		PageQueryCondition queryCondition = new PageQueryCondition()
				.from(entityClass).where(key, value).limit(1, 1);
		PageQueryResult result = this.get(queryCondition, matchExact);
		return CommonUtil.getOne(result.getRows(entityClass));
	}

	@Override
	public <E> List<E> get(Class<E> entityClass, String key, Object value, boolean matchExact) {
		PageQueryCondition queryCondition = new PageQueryCondition()
				.from(entityClass).where(key, value);
		PageQueryResult result = this.get(queryCondition, matchExact);
		return result.getRows(entityClass);
	}

	@Override
	public <E> E getOne(Class<E> entityClass, String propertyName, Object propertyValue, boolean matchExact,
			String orderPropertyName, boolean order) {
		PageQueryCondition queryCondition = new PageQueryCondition()
				.from(entityClass).where(propertyName, propertyValue)
				.order(orderPropertyName, order).limit(1, 1);
		PageQueryResult result = this.get(queryCondition, matchExact);
		return CommonUtil.getOne(result.getRows(entityClass));
	}

	@Override
	public <E> List<E> get(Class<E> entityClass, String propertyName, Object propertyValue, boolean matchExact,
			String orderPropertyName, boolean order) {
		PageQueryCondition queryCondition = new PageQueryCondition()
				.from(entityClass).where(propertyName, propertyValue)
				.order(orderPropertyName, order);
		PageQueryResult result = this.get(queryCondition, matchExact);
		return result.getRows(entityClass);
	}

	@Override
	public <E> E getOne(Class<E> entityClass, Map<String, Object> conditions, Map<String, Boolean> orders,
			boolean matchExact) {
		PageQueryCondition queryCondition = new PageQueryCondition()
				.from(entityClass).where(conditions).order(orders).limit(1, 1);
		PageQueryResult result = this.get(queryCondition, matchExact);
		return CommonUtil.getOne(result.getRows(entityClass));
	}

	@Override
	public <E> List<E> get(Class<E> entityClass, Map<String, Object> conditions, Map<String, Boolean> orders,
			boolean matchExact) {
		PageQueryCondition queryCondition = new PageQueryCondition()
				.from(entityClass).where(conditions).order(orders);
		PageQueryResult result = this.get(queryCondition, matchExact);
		return result.getRows(entityClass);
	}

	@Override
	public <E> E getOne(Class<E> entityClass, Map<String, Object> conditions) {
		return this.getOne(entityClass, conditions, false);
	}

	@Override
	public <E> List<E> get(Class<E> entityClass, Map<String, Object> conditions) {
		return this.get(entityClass, conditions, false);
	}

	@Override
	public <E> void delete(Class<E> entityClass, List<PK> ids) {
		for (PK id : ids) {
			this.delete(entityClass, id);
		}
	}

	@Override
	public <E> void delete(List<E> entities) {
		String idField = "id";
		for (Object entity : entities) {
			try {
				Object id = PropertyUtils.getProperty(entity, idField);
				entity = this.hibernateTemplate.get(entity.getClass(), (Long)id);
				if (entity != null) {
					this.hibernateTemplate.delete(entity);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
	}

	@Override
	public <E> void delete(Class<E> entityClass, PK id) {
		this.hibernateTemplate.delete(this.hibernateTemplate.get(entityClass, (Long)id));
	}

	@Override
	public void delete(T entity) {
		List<T> entities = new ArrayList<>();
		entities.add(entity);
		this.delete(entities);
	}

	@Override
	public <E> List<E> saveAll(List<E> entities, List<Map<String, Object>> modifiedData, boolean isJson) {
		E entity = null;
		List<E> result = new ArrayList<>();
		for (int i = 0; i < entities.size(); i++) {
			entity = entities.get(i);
			entity = this.saveOrUpdate(entity, modifiedData == null ? null : modifiedData.get(i), isJson);
			result.add(entity);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E saveOrUpdate(E entity, Map<String, Object> json) {
		if (json != null) {
			entity = (E) this.loadEntityByJson(entity, json);
		}
		this.hibernateTemplate.saveOrUpdate(entity);
		
		return entity;
	}

	@Override
	public <E> E saveOrUpdate(E entity) {
		return this.saveOrUpdate(entity, null, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E saveOrUpdate(E entity, Map<String, Object> modifiedData, boolean isJson) {
		if (modifiedData != null) {
			if (isJson) {
				entity = (E) this.loadEntityByJson(entity, modifiedData);
			} else {
				entity = (E) this.loadEntityByParam(entity, modifiedData);
			}
			this.saveOrUpdate(entity);
		}
		
		return entity;
	}

	@Override
	public <E> E get(Class<E> entityClass, PK id) {
		return this.hibernateTemplate.get(entityClass, id);
	}

	@Override
	public PageQueryResult get(PageQueryCondition queryCondition) {
		return this.get(queryCondition, false);
	}

	@Override
	public <E> Long getCount(Class<E> entityClass, String key, Object value, boolean matchExact) {
		PageQueryCondition queryCondition = new PageQueryCondition().from(entityClass).where(key, value);
		return this.getCount(queryCondition, matchExact);
	}

	@Override
	public <E> Long getCount(Class<E> entityClass, Map<String, Object> conditions, boolean matchExact) {
		PageQueryCondition queryCondition = new PageQueryCondition().from(entityClass).where(conditions);
		return this.getCount(queryCondition, matchExact);
	}

	@Override
	public <E> Long getCount(PageQueryCondition queryCondition, boolean matchExact) {
		return this.get(queryCondition, matchExact, true, true).getRecords();
	}

	@Override
	public PageQueryResult get(PageQueryCondition queryCondition, boolean matchExact) {
		return this.get(queryCondition, matchExact, false, true);
	}

	@Override
	public PageQueryResult getComplexPK(PageQueryCondition queryCondition, boolean matchExact) {
		return this.get(queryCondition, matchExact, false, false);
	}

	@Override
	public <E> Long getCount(Class<E> entityClass) {
		final String countHql = "select count(*) from " + entityClass.getSimpleName();
		return this.excuteCountHql(countHql);
	}

	@Override
	public Set<String> getColumnNames(String tableName) {
		Set<String> columnNameSet = new HashSet<>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		List<Map<String, Object>> columnNames = jdbcTemplate.queryForList("show colums from "+tableName);
		for (Map<String, Object> map : columnNames) {
			columnNameSet.add(map.get("Field").toString().toLowerCase());
		}
		return columnNameSet;
	}

	@Override
	public int executeUpdate(final String ql, final boolean isNative, final Object... params) {
		return this.hibernateTemplate.execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				Query query = isNative ? session.createSQLQuery(ql) : session.createQuery(ql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				return query.executeUpdate();
			}
		});
	}

	@Override
	public int executeUpdate(final String ql, final boolean isNative, final Map<String, Object> params) {
		return this.hibernateTemplate.execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				Query query = isNative ? session.createSQLQuery(ql) : session.createQuery(ql);
				if (params != null) {
					for (String key : params.keySet()) {
						query.setParameter(key, params.get(key));
					}
				}
				return query.executeUpdate();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> executeQuery(final String ql, final boolean isNative, final Object... params) {
		return this.hibernateTemplate.execute(new HibernateCallback<List<E>>() {
			@Override
			public List<E> doInHibernate(Session session) throws HibernateException {
				Query query = isNative ? session.createSQLQuery(ql) : session.createQuery(ql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> executeQuery(final String ql, final boolean isNative, final Map<String, Object> params) {
		return this.hibernateTemplate.execute(new HibernateCallback<List<E>>() {
			@Override
			public List<E> doInHibernate(Session session) throws HibernateException {
				Query query = isNative ? session.createSQLQuery(ql) : session.createQuery(ql);
				if (params != null) {
					for (String key : params.keySet()) {
						query.setParameter(key, params.get(key));
					}
				}
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> executeQuery(final String ql, final boolean isNative, final Map<String, Object> params, final int firstRecord, final int maxRecords) {
		return this.hibernateTemplate.execute(new HibernateCallback<List<E>>() {
			@Override
			public List<E> doInHibernate(Session session) throws HibernateException {
				Query query = isNative ? session.createSQLQuery(ql) : session.createQuery(ql);
				if (params != null) {
					for (String key : params.keySet()) {
						query.setParameter(key, params.get(key));
					}
				}
				query.setFirstResult(firstRecord);
				query.setMaxResults(maxRecords);
				return query.list();
			}
		});
	}
	
	private Long excuteCountHql(final String hql){
		return this.hibernateTemplate.execute(new HibernateCallback<Long>() {
			@Override
			public Long doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				return (Long) query.uniqueResult();
			}
		});
	}
	
	private Type getPropertyType(Object entity, String propertyName){
		ClassMetadata metadata = this.hibernateTemplate.getSessionFactory().getClassMetadata(entity.getClass());
		Type type = metadata.getPropertyType(propertyName);
		return type;
	}
	
	private Date parseDate(String dateString){
		Date date = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	private Date parseTimestamp(String timestampString){
		Date date = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = dateFormat.parse(timestampString);
		} catch (ParseException e) {
			e.printStackTrace();
			date = parseDate(timestampString);
		}
		return date;
	}
	
	private void setCustomPropertyValue(Object targetBean, String typeName, String key, Object value) throws Exception{
		if (value.getClass().isArray()) {
			value = ((String[])value)[0];
		}
		if (typeName.equals("date")) {
			if (((String)value).trim().isEmpty()) {
				value = null;
			}else {
				value = parseDate((String)value);
			}
		}
		if (typeName.equals("timestamp")) {
			if (((String)value).trim().isEmpty()) {
				value = null;
			}else {
				value = parseTimestamp((String)value);
			}
		}
		if (typeName.equals("long")) {
			if (((String)value).trim().isEmpty()) {
				value = null;
			}else {
				value = Long.parseLong((String)value);
			}
		}
		if (typeName.equals("double")) {
			if (((String)value).trim().isEmpty()) {
				value = null;
			}else {
				value = Double.parseDouble((String)value);
			}
		}
		if (typeName.equals("boolean")) {
			if (((String)value).equals("1")) {
				value = null;
			}else {
				value = Boolean.parseBoolean((String)value);
			}
		}
		PropertyUtils.setProperty(targetBean, key, value);
		
	}
	
	private void setCustomPropertyValue(Object targetBean, Type type, String key, Object value) throws Exception{
		String typeName = type.getName();
		this.setCustomPropertyValue(targetBean, typeName, key, value);
	}
	
	private void setPropertyValue(Object targetBean, String key, Object value) throws Exception{
		if (targetBean instanceof Object && key.startsWith("cp.")) {
			Type type = getPropertyType(targetBean, key);
			String typeName = type.getName();
			if (typeName.equals("date")) {
				if (((String)value).trim().isEmpty()) {
					value = null;
				}else {
					value = parseDate((String)value);
				}
			}
			if (typeName.equals("timestamp")) {
				if (((String)value).trim().isEmpty()) {
					value = null;
				}else {
					value = parseTimestamp((String)value);
				}
			}
			if (typeName.equals("long")) {
				if (((String)value).trim().isEmpty()) {
					value = null;
				}else {
					value = Long.parseLong((String)value);
				}
			}
			if (typeName.equals("double")) {
				if (((String)value).trim().isEmpty()) {
					value = null;
				}else {
					value = Double.parseDouble((String)value);
				}
			}
			if (typeName.equals("boolean")) {
				if (((String)value).equals("1")) {
					value = null;
				}else {
					value = Boolean.parseBoolean((String)value);
				}
			}
			PropertyUtils.setProperty(targetBean, key, value);
			
		}
	}
	
	private Map<String, Map<String, Object>> filterParameters(Map<String, Object> parameters){
		Map<String, Map<String, Object>> tempResult = new HashMap<>();
		Map<String, Map<String, Object>> result = new HashMap<>();
		Object value = null;
		for (String param : parameters.keySet()) {
			int index = param.indexOf(".");
			value = parameters.get(param);
			if (index == -1) {
				if (value instanceof String && ((String)value).isEmpty()) {
					continue;
				}
				tempResult.put(param, null);
			}else {
				String key = param.substring(0, index);
				Map<String, Object> temp = tempResult.get(key);
				if (temp == null) {
					temp = new HashMap<>();
					tempResult.put(key, temp);
				}
				if (value instanceof String && ((String)value).isEmpty()) {
					continue;
				}
				temp.put(param.substring(index + 1), value);
			}
		}
		
		for (Map.Entry<String, Map<String, Object>> entry : tempResult.entrySet()) {
			Map<String, Object> v1 = entry.getValue();
			Object v2 = null;
			Class<?> c = null;
			Class<?> elementType = null;
			if (v1 != null && v1.size() == 1) {
				v2 = v1.values().iterator().next();
				c = v2.getClass();
				if (c.isArray()) {
					elementType = c.getComponentType();
					if (elementType == String.class && ((String[])v2).length == 1 && ((String[])v2)[0].isEmpty()) {
						continue;
					}
				}
			}
			
			result.put(entry.getKey(), entry.getValue());
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private T get(String entityName, PK id) {
		return (T) this.hibernateTemplate.get(entityName, id);
	}
	
	@SuppressWarnings("unchecked")
	private Object loadEntityByJson(Object srcBean, Object json){
		Object targetBean = null,tempObject;
		try {
			if (!(srcBean instanceof Collection) && !(srcBean instanceof Map)) {
				ClassMetadata metadata = this.hibernateTemplate.getSessionFactory().getClassMetadata(srcBean.getClass());
//				String idField = "id";
				String idField = metadata.getIdentifierPropertyName();
				Object id = PropertyUtils.getProperty(srcBean, idField);
				if (id != null) {
					if (id.equals(new Long(-1))) {
						return null;
					} else {
						targetBean = this.get(metadata.getEntityName(), (PK)id);
					}
				}
			}
			
			if (targetBean == null) {
				targetBean = srcBean.getClass().getInterfaces();
			}
			
			if (targetBean instanceof Map) {
				Iterator<String> iterator = ((Map<String,?>)json).keySet().iterator();
				Map jsonMap = (Map) json;
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					Object subJsons = jsonMap.get(key);
					tempObject = ((Map)srcBean).get(key);
					if (tempObject != null && tempObject instanceof String) {
						Object value = loadEntityByJson(tempObject, subJsons);
						((Map)targetBean).put(key, value);
					}
					
				}
				
			}else if(targetBean instanceof Collection){
				Iterator iterator = ((Collection)json).iterator();
				for (Object value : (Collection)srcBean) {
					Object subBean = loadEntityByJson(value, iterator.next());
					((Collection)targetBean).add(subBean);
				}
			}else {
				Map jsonMap = (Map) json;
				Iterator ite = jsonMap.keySet().iterator();
				while (ite.hasNext()) {
					String key = (String) ite.next();
					Object subJsons = jsonMap.get(key);
					Object value = null;
					value = PropertyUtils.getProperty(srcBean, key);
					
					if (subJsons instanceof Collection || subJsons instanceof Map) {
						Object subBean = loadEntityByJson(value, subJsons);
						Collection originalSet = null, originalList = null;
						if (subBean instanceof Set) {
							originalSet = (Collection) PropertyUtils.getProperty(targetBean, key);
							if (originalSet == null) {
								PropertyUtils.setProperty(targetBean, key, subBean);
							}else if (jsonMap.get("fixation") != null && ((Map)jsonMap.get("fixation")).get(key) != null) {
								originalSet.clear();
								this.hibernateTemplate.getSessionFactory().getCurrentSession().flush();
								originalSet.addAll((Set)subBean);
							}else {
								originalSet.addAll((Collection)subBean);
							}
						}else if (subBean instanceof Map) {
							Map originalMap = (Map) PropertyUtils.getProperty(targetBean, key);
							//911L
							if (originalMap != null && ((Map)subJsons).get("internalState") == null) {
								originalMap.putAll((Map)subBean);
							} else {
								if (originalMap != null) {
									originalMap.clear();
									originalMap.putAll((Map)subBean);
								} else {
									setPropertyValue(targetBean, key, subBean);
								}
								
							}
							
						}else if (subBean instanceof List) {
							originalList = (Collection) PropertyUtils.getProperty(targetBean, key);
							originalList.clear();
							originalList.addAll((Collection)subBean);
						}else {
							setPropertyValue(targetBean, key, subBean);
						}
						
					}else {
						setPropertyValue(targetBean, key, value);
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return targetBean;
	}
	
	
	@SuppressWarnings("unchecked")
	private Object loadEntityByParam(Object srcBean, Map<String, Object> parameters){
		Object targetBean = null, temp;
		String[] idArray;
		try {
			if (!(srcBean instanceof Collection) && !(srcBean instanceof Map)) {
				ClassMetadata metadata = this.hibernateTemplate.getSessionFactory().getClassMetadata(srcBean.getClass());
				String idField = "id";
				Type type = metadata.getIdentifierType();
				Object id = PropertyUtils.getProperty(srcBean, idField);
				if (id == null) {
					temp = parameters.get(idField);
					if (temp != null && temp.getClass().isArray()) {
						idArray = (String[])temp;
						if (idArray.length > 0 && !(idArray[0].isEmpty())) {
							if (type.getName().equals("long")) {
								id = Long.parseLong(idArray[0]);
							} else {
								id = idArray[0];
							}
						}
					}
				}
				
				if (id != null) {
					if (id.equals(new Long(-1))) {
						return null;
					} else {
						targetBean = this.get(metadata.getEntityName(), (PK)id);
					}
				}
			}
			if (targetBean == null) {
				targetBean = srcBean.getClass().getInterfaces();
			}
			
			Map<String, Map<String, Object>> filtedParams = filterParameters(parameters);
			if(targetBean instanceof Collection){
				Map<String, Object> subParams = filtedParams.values().iterator().next();
				for (Object value : (Collection)srcBean) {
					Object subBean = loadEntityByParam(value, subParams);
					((Collection)targetBean).add(subBean);
				}
			}else {
				for (Map.Entry<String, Map<String, Object>> entry : filtedParams.entrySet()) {
					String key = entry.getKey();
					Map<String, Object> subParams = entry.getValue();
					Object value = PropertyUtils.getProperty(srcBean, key);
					if (subParams == null) {
						if (value instanceof Set) {
							value = new HashSet<>();
						} else if(value instanceof List){
							value = new ArrayList<>();
						}
						if (value != null && value.getClass().isArray()) {
							String[] tempValue  = (String[])value;
							if (tempValue.length == 1) {
								PropertyUtils.setProperty(targetBean, key, tempValue[0]);
								continue;
							}
						}
						
						PropertyUtils.setProperty(targetBean, key, value);
					} else {
						
						if (targetBean instanceof Object && key.equals("cp")) {
							String customPropertyKey = entry.getKey();
							Object customPropertyValue = entry.getValue();
							this.setCustomPropertyValue(PropertyUtils.getProperty(targetBean, "cp"),
									getPropertyType(targetBean, "cp." + customPropertyKey), customPropertyKey,
									customPropertyValue);

						} else {
							Object subBean = loadEntityByParam(value, subParams);
							PropertyUtils.setProperty(targetBean, key, subBean);
						}
						
					}
					
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return targetBean;
	}
	
	private Type getFieldClass(Class<?> fieldClass, String fieldName) {
		ClassMetadata metadata = this.hibernateTemplate.getSessionFactory().getClassMetadata(fieldClass);
		Type type = null;
		int index = fieldName.indexOf("."),lastIndex = 0;
		String tempName = null, identityFieldName = null;
		StringBuilder tempPropertyName = new StringBuilder();
		if (index != -1) {
			while(index != -1){
				tempName = fieldName.substring(lastIndex, index);
				tempPropertyName.append(tempName);
				type = metadata.getPropertyType(tempName);
				if (type.isAssociationType()) {
					metadata = this.hibernateTemplate.getSessionFactory().getClassMetadata(type.getReturnedClass());
					tempPropertyName.setLength(0);
				}
				lastIndex = index + 1;
				index = fieldName.indexOf(".", lastIndex);
				if (tempPropertyName.length() > 0) {
					tempPropertyName.append(".");
				}
			}
			fieldName = fieldName.substring(lastIndex);
			tempPropertyName.append(tempName);
		}else {
			tempPropertyName.append(tempName);
		}
		fieldName = tempPropertyName.toString();
		identityFieldName = "id";
		if (identityFieldName != null && identityFieldName.equals(fieldName)) {
			type = metadata.getIdentifierType();
		}else {
			type = metadata.getPropertyType(fieldName);
		}
		return type;
	}
	
	private boolean hasPersistenceSetAnnotation(Class<?> entityClass, String fieldName) {
		try {
			PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(entityClass.newInstance(), fieldName);
			if (propertyDescriptor != null) {
				Method method = propertyDescriptor.getReadMethod();
				if (method.getAnnotation(PersistenceSet.class) != null) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	private String generatorConditionStatement(Class<?> entityClass, String aliasName, List<Pair<String, Object>> lstField, boolean matchExact) {
		StringBuilder content = new StringBuilder();
		if (lstField == null) {
			return content.toString();
		}
		
		Iterator<Pair<String, Object>> iterator = lstField.iterator();
		
		while (iterator.hasNext()) {
			Pair<String, Object> field = iterator.next();
			try {
				String srcFieldName = field.getName(), fieldName = aliasName + "." +srcFieldName;
				Object value = field.getValue();
				Type propertyType = getFieldClass(entityClass, srcFieldName);
				if (propertyType instanceof DateType || propertyType instanceof TimestampType) {
					content.append(constructDateFieldCondition(fieldName, value, propertyType));
				}else if (propertyType instanceof StringType || propertyType instanceof MaterializedClobType) {
					content.append(constructStringFieldCondition(entityClass, srcFieldName, fieldName, value, matchExact));
				}else {
					content.append(fieldName);
					if (value == null) {
						content.append(" is null ");
					}else if (value instanceof Collection || value.getClass().isArray()) {
						content.append(" in (");
						Iterator<?> iteValues = null;
						if (value.getClass().isArray()) {
							iteValues = CommonUtil.convertArrayToCollection(value).iterator();
						}else {
							iteValues = ((Collection<?>) value).iterator();
						}
						while (iteValues.hasNext()) {
							content.append(iteValues.next().toString());
							if (iteValues.hasNext()) {
								content.append(",");
							}
						}
						
						content.append(")");
					}else {
						content.append("=");
						content.append(value);
					}
				}
				
			} catch (Exception e) {
			}
			
			if (iterator.hasNext()) {
				 content.append(" and ");
			}
		}
		
		return content.toString();
	}
	
	private String constructStringFieldCondition(Class<?> entityClass, String srcFieldName, String fieldName, Object value, boolean matchExact) {
		StringBuilder content = new StringBuilder();
		if (value == null) {
			content.append(fieldName);
			content.append(" is null ");
		}else if (value instanceof Collection || value.getClass().isArray()) {
			Iterator<?> iteValues = null;
			if (value.getClass().isArray()) {
				iteValues = CommonUtil.convertArrayToCollection(value).iterator();
			}else {
				iteValues = ((Collection<?> )value).iterator();
			}
			if (this.hasPersistenceSetAnnotation(entityClass, srcFieldName)) {
				content.append("(");
				while (iteValues.hasNext()) {
					Object temp = iteValues.next();
					content.append(fieldName);
					content.append(" like ");
					content.append("'%,");
					content.append(temp.toString());
					content.append(",%'");
					if (iteValues.hasNext()) {
						content.append(" or ");
					}
				}
				content.append(")");
				
			} else {
				content.append(fieldName);
				content.append(" in ('");
				while (iteValues.hasNext()) {
					Object temp = iteValues.next();
					content.append(temp);
					content.append("'");
					if (iteValues.hasNext()) {
						content.append(",'");
					}
				}
				content.append(")");
			}
			
			
		}else {
			content.append(fieldName);
			if (matchExact) {
				content.append(" = '");
				content.append(value.toString().replaceAll("'", "''").replaceAll("\\\\", "\\\\\\\\"));
				content.append("'");
			}else {
				content.append(" like '%");
				content.append(value.toString().replaceAll("'", "''").replaceAll("\\\\", "\\\\\\\\\\\\\\\\")
						.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_"));
				content.append("%'");
			}
		}
		
		return content.toString();
	}
	
	private String constructDateFieldCondition(String fieldName,Object value, Type propertyType) {
		StringBuilder content = new StringBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date tempDate;
		if (value instanceof Map) {
			Calendar endCal = null;
			Map<String, ?> dateConditions = (Map<String, ?>)value;
			String startDateString = (String) dateConditions.get("startDate");
			String endDateString = (String) dateConditions.get("endDate");
			if (endDateString  != null) {
				endCal = Calendar.getInstance();
				if (propertyType instanceof TimestampType && endDateString.trim().length() > "yyyy-MM-dd".length()) {
					tempDate = parseTimestamp(endDateString);
					endCal.setTime(tempDate);
				}else {
					tempDate = parseDate(endDateString);
					endCal.setTime(tempDate);
					endCal.add(Calendar.DAY_OF_MONTH, 1);
				}
				
			}
			if (startDateString != null) {
				content.append(fieldName);
				content.append(" >='");
				content.append(startDateString);
				content.append("'");
			}
			if (endDateString != null) {
				if (startDateString != null) {
					content.append(" and ");
				}
				content.append(fieldName);
				if (propertyType instanceof TimestampType && endDateString.trim().length() > "yyyy-MM-dd".length()) {
					content.append(" <='");
					content.append(timestampFormat.format(endCal.getTime()));
				}else {
					content.append(" <'");
					content.append(dateFormat.format(endCal.getTime()));
				}
				content.append("'");
			}
			
		} else {
			Calendar startCal = Calendar.getInstance();
			Calendar endCal = Calendar.getInstance();
			if (value == null) {
				content.append(fieldName);
				content.append(" is null ");
			}else if (propertyType instanceof DateType) {
				tempDate = this.parseDate((String)value);
				startCal.setTime(tempDate);
				endCal.setTime(tempDate);
				endCal.add(Calendar.DAY_OF_MONTH, 1);
				content.append(fieldName);
				content.append(" >= '");
				content.append(dateFormat.format(startCal.getTime()));
				content.append("'");
				content.append(" and ");
				content.append(fieldName);
				content.append(" <'");
				content.append(dateFormat.format(endCal.getTime()));
				content.append("'");
				
			}else {
				tempDate = this.parseTimestamp((String)value);
				startCal.setTime(tempDate);
				endCal.setTime(tempDate);
				endCal.add(Calendar.DAY_OF_MONTH, 1);
				content.append(fieldName);
				content.append(" >= '");
				content.append(timestampFormat.format(startCal.getTime()));
				content.append("'");
				content.append(" and ");
				content.append(fieldName);
				content.append(" <'");
				content.append(timestampFormat.format(endCal.getTime()));
				content.append("'");
			}
		}
		
		return content.toString();
	}
	
	
	private PageQueryResult get(final PageQueryCondition queryCondition, boolean matchExact, boolean justQueryDataCount, boolean isNeedDistinct) {
		String propertyPrefix = queryCondition.getPropertyPrefix();
		PageQueryResult result = new PageQueryResult();
		List<String> fields = queryCondition.getFields();
		Map<String, Object> conditions = queryCondition.getConditions();
		Map<String, Boolean> orders = queryCondition.getOrders();
		Class<?> entityClass = queryCondition.getEntityClass();
		String extendedHql = queryCondition.getExtendedHql();
		String classAlias = null;
		StringBuffer query = new StringBuffer();
		StringBuffer fromStatementBuffer = new StringBuffer(" from ");
		StringBuffer conditionStatement = new StringBuffer(" where ");
		
		if (propertyPrefix != null && !propertyPrefix.trim().isEmpty()) {
			
			int length = propertyPrefix.length() + 1;
			if (fields != null) {
				for (int index = 0; index < fields.size(); index++) {
					fields.set(index, fields.get(index).substring(length));
				}
			}
			if (conditions != null) {
				Map<String, Object> tempConditions = new HashMap<>();
				for (Map.Entry<String, Object> entry : conditions.entrySet()) {
					tempConditions.put(entry.getKey().substring(length), entry.getValue());
				}
				conditions = tempConditions;
			}
			if (orders != null) {
				Map<String, Boolean> temporders = new HashMap<>();
				for (Map.Entry<String, Boolean> entry : orders.entrySet()) {
					temporders.put(entry.getKey().substring(length), entry.getValue());
				}
				orders = temporders;
			}
		}
		
		if (conditions != null && conditions.size() > 0) {
			Map<ExtendedPair<Class<?>, String>, Map<String, Object>> class2Fields = new HashMap<>();
			Map<ExtendedPair<Class<?>, String>, String> class2Alias = new HashMap<>();
			List<Pair<String, Object>> collectionFields;
			Iterator<Map.Entry<String, Object>> ite = conditions.entrySet().iterator();
			ExtendedPair<Class<?>, String> rootClassFieldPair = new ExtendedPair<Class<?>, String>(entityClass, null);
			class2Fields.put(rootClassFieldPair, new HashMap<String, Object>());
			
			while (ite.hasNext()) {
				Class<?> elementClass;
				Map.Entry<String, Object> entry = ite.next();
				Object value = entry.getValue();
				String srcFieldName = entry.getKey(),subCollectionName = srcFieldName, fieldName;
				int beginIndex = srcFieldName.indexOf(".");
				if (beginIndex != -1) {
					subCollectionName = srcFieldName.substring(0, beginIndex);
				}
				Type propertyType = this.getFieldClass(entityClass, subCollectionName);
				if (propertyType.isCollectionType()) {
					CollectionType collectionType = (CollectionType) propertyType;
					Type elementType = collectionType.getElementType((SessionFactoryImplementor)this.sessionFactory);
					elementClass = elementType.getReturnedClass();
					fieldName = srcFieldName.substring(beginIndex + 1);
				}else {
					elementClass = entityClass;
					fieldName = srcFieldName;
				}
				
				ExtendedPair<Class<?>, String> classSubCollectionNamePair = new ExtendedPair<Class<?>, String>(elementClass, null);
				if (propertyType.isCollectionType()) {
					classSubCollectionNamePair.setValue(subCollectionName);
				}
				Map<String, Object> classProperties = class2Fields.get(classSubCollectionNamePair);
				if (classProperties == null) {
					classProperties = new HashMap<>();
					classProperties.put("subCollectionName", subCollectionName);
					class2Fields.put(classSubCollectionNamePair, classProperties);
				}
				collectionFields = (List<Pair<String, Object>>) classProperties.get("fields");
				if (collectionFields == null) {
					collectionFields= new ArrayList<>();
					classProperties.put("fields", collectionFields);
				}
				collectionFields.add(new Pair<String, Object>(fieldName, value));
			}
			//构造from数据
			List<ExtendedPair<Class<?>, String>> lstClass = new ArrayList<>(class2Fields.keySet());
			lstClass.remove(rootClassFieldPair);
			lstClass.add(0, rootClassFieldPair);
			Iterator<ExtendedPair<Class<?>, String>> classIterator = lstClass.iterator();
			
			//循环构造left join语句
			Map<Class<?>, Integer> classCountMap  =  new HashMap<>();
			while (classIterator.hasNext()) {
				ExtendedPair<Class<?>, String> classFieldPair = classIterator.next();
				Class<?> tempClass = classFieldPair.getName();
				Integer classCount = classCountMap.get(tempClass);
				if (classCount == null) {
					classCount = 0;
				} else {
					classCount++;
				}
				classCountMap.put(tempClass, classCount);
				String simpleClassName = tempClass.getSimpleName();
				String aliasName = simpleClassName + "_" +classCount;
				String tempCondition;
				class2Alias.put(classFieldPair, aliasName);
				if (tempClass == entityClass) {
					fromStatementBuffer.append(simpleClassName);
				}else {
					fromStatementBuffer.append(class2Alias.get(rootClassFieldPair));
					fromStatementBuffer.append(".");
					fromStatementBuffer.append(class2Fields.get(classFieldPair).get("subCollectionName"));
				}
				fromStatementBuffer.append(" as ");
				fromStatementBuffer.append(aliasName);
				if (classIterator.hasNext()) {
					fromStatementBuffer.append(" left join ");
				}
				tempCondition = generatorConditionStatement(tempClass, aliasName, (List<Pair<String, Object>>) class2Fields.get(classFieldPair).get("fields"), matchExact);
				
				conditionStatement.append(" ");
				conditionStatement.append(tempCondition);
				if (!tempCondition.trim().isEmpty() && classIterator.hasNext()) {
					conditionStatement.append(" and ");
				}
			}
			classAlias = class2Alias.get(rootClassFieldPair);
			query.append(fromStatementBuffer);
			query.append(conditionStatement);
			
		}else {
			classAlias = entityClass.getSimpleName() + "_0";
			query.append(" from ");
			query.append(entityClass.getSimpleName());
			query.append(" as ");
			query.append(classAlias);
		}
		
		//附加查询条件
		if (extendedHql != null &&extendedHql.trim().isEmpty()) {
			if (conditions != null && conditions.size() > 0) {
				query.append(" and ");
			}else {
				query.append(" where ");
			}
			query.append(extendedHql);
		}
		//查询数据总条数
		if (queryCondition.getPage() != null && justQueryDataCount) {
			String countHql = "";
			if (isNeedDistinct) {
				countHql = "select count(distinct " + classAlias + ")" + query.toString();
			} else {
				countHql = "select count(*) " + query.toString();
			}
			Long count = this.excuteCountHql(countHql);
			result.setRecords(count);
		}
		if (justQueryDataCount) {
			return result;
		}
		
		//构造投影查询的字段列表
		boolean canUseProjectQuery = true;
		StringBuffer fieldStringBuffer = new StringBuffer("select distinct ");
		if (fields != null) {
			for (int index = 0; index < fields.size(); index++) {
				String field = fields.get(index);
				if (field.contains(".")) {
					canUseProjectQuery = false;
					break;
				}
				if (this.isTransient(entityClass, field) || this.getFieldClass(entityClass, field).isAssociationType()) {
					canUseProjectQuery = false;
				}
				fieldStringBuffer.append(classAlias).append(".").append(field);
				if (index != fields.size() - 1) {
					fieldStringBuffer.append(",");
				}
			}
		}
		
		String orderByHql = "";
		if (fields == null || fields.isEmpty() || canUseProjectQuery) { //使用自查询查询出符合条件的对象
			String tempAlias = classAlias + "_temp";
			StringBuilder tempHql = new StringBuilder(" select ");
			tempHql.append(tempAlias).append(" from ").append(entityClass.getSimpleName()).append(" as ").append(tempAlias)//差一个append
			.append(classAlias).append(".id");
			query.insert(0, tempHql.toString());
			query.append(") ");
			orderByHql = this.generateOrderByHql(orders, tempAlias, entityClass);
			query.append(orderByHql);
		}else {//使用投影查询
			query.insert(0, fieldStringBuffer.toString());
			orderByHql = this.generateOrderByHql(orders, classAlias, entityClass);
			query.append(orderByHql);
		}
		
		final String dataSql = query.toString();
		List<?> dataSet = this.hibernateTemplate.execute(new HibernateCallback<List<?>>() {

			@Override
			public List<?> doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.createQuery(dataSql);
				Integer page = queryCondition.getPage();
				Integer numberPage = queryCondition.getNumberPage();
				if (page != null && page > 0 && numberPage != null && numberPage > 0) {
					queryObject.setFirstResult((page - 1) * numberPage);
					queryObject.setMaxResults(numberPage);
				}
				return queryObject.list();
			}
		});
		
		List<Object> resultData = new ArrayList<>();
		if (fields == null || fields.size() == 0) {
			result.setRows(dataSet);
		} else {
			if (canUseProjectQuery) {
				for (Object tempRowData : dataSet) {
					List<String> data = new ArrayList<>();
					if (tempRowData instanceof Object[]) {
						for (Object tempFieldData : (Object[])tempRowData) {
							data.add(CommonUtil.formatData(tempFieldData));//缺少代码
						}
					}else {
						data.add(tempRowData == null ? null : tempRowData.toString());
					}
					resultData.addAll(data);
				}
				
			}else {
				for (Object object : dataSet) {
					List<String> data = new ArrayList<>();
					for(String key : queryCondition.getFields()){
						try {
							if (key.contains(".")) {
								int index = key.lastIndexOf(".");
								Object tempObject = PropertyUtils.getProperty(object, key.substring(0, index));
								if (tempObject instanceof Set) {
									StringBuilder sb = new StringBuilder();
									for(Object o : (Set<?>)tempObject){
										if (!sb.toString().isEmpty()) {
											sb.append(",");
											sb.append(CommonUtil.formatData(PropertyUtils.getProperty(object, key.substring(0, index + 1))));
										}
									}
									data.add(sb.toString());
								}else {
									data.add(CommonUtil.formatData(PropertyUtils.getProperty(object, key)));
								}
							}else {
								data.add(CommonUtil.formatData(PropertyUtils.getProperty(object, key)));
							}
							
						} catch (Exception e) {
							data.add(null);
						}
					}
					resultData.add(data);
				}
			}
			result.setRows(resultData);
		}
		
		return result;
	}
	
	private String generateOrderByHql(Map<String, Boolean> orders, String classAlias, Class<?> entityClass) {
//		boolean isMysql = ((SessionFactoryImplementor)this.sessionFactory).getSettings().getDialect() instanceof MySQLDialect;
		boolean isMysql = true;
		StringBuilder orderBy = new StringBuilder();
		if (orders != null &&orders.size() > 0) {
			orderBy.append(" order by ");
			Iterator<Map.Entry<String, Boolean>> ite = orders.entrySet().iterator();
			while (ite.hasNext()) {
				Map.Entry<String, Boolean> entry = ite.next();
				String propertyName = entry.getKey();
				Type propertyType = this.getFieldClass(entityClass, propertyName);
				boolean isString = propertyType instanceof StringType || propertyType instanceof MaterializedClobType;
				if (isMysql && isString) {
					orderBy.append(" gbk( ");
				}
				if (classAlias != null) {
					orderBy.append(classAlias).append(".");
				}
				if (isMysql && isString) {
					orderBy.append(" ) ");
				}
				orderBy.append(entry.getValue() ? " asc " : " desc ");
				if (ite.hasNext()) {
					orderBy.append(",");
				}
			}
				
		}
		
		return orderBy.toString();
	}
	
	private boolean isTransient(Class<?> entityClass, String propertyName) {
		try {
			PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(entityClass.newInstance(), propertyName);
			if (propertyDescriptor == null) {
				return false;
			}
				Method method = propertyDescriptor.getReadMethod();
				if (method.getAnnotation(Transient.class) != null) {
					return true;
				}else {
					return false;
				}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		
	}
	
}
