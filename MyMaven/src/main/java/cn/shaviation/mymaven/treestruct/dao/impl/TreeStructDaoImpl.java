package cn.shaviation.mymaven.treestruct.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.shaviation.mymaven.treestruct.dao.TreeStructDao;

@Repository("treeStructDao")
public class TreeStructDaoImpl implements TreeStructDao {
	private SessionFactory sessionFactory;
	private HibernateTemplate hibernateTemplate;

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public <E> E get(Class<E> entityClass, Long id) {
		return hibernateTemplate.get(entityClass, id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> get(Class<E> entityClass, String key, Object value) {
		String hql = "from " + entityClass.getSimpleName();
		if (value == null) {
			hql = hql + " where " + key + " is null ";
		}else {
			hql = hql + " where " + key + " = " + value;
		}
		
		return getSessionFactory().getCurrentSession().createQuery(hql).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public <E> E saveOrUpdate(E entity) {
		Serializable identifier;
		try {
			Object id = PropertyUtils.getProperty(entity, "id");
			if (id != null) {
				identifier = (Long)id;
				hibernateTemplate.saveOrUpdate(entity);
			}else {
				identifier = hibernateTemplate.save(entity);
			}
			return (E) hibernateTemplate.get(entity.getClass(), identifier);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public <E> void delete(Class<E> entityClass, Long id) {
		E entity = hibernateTemplate.get(entityClass, id);
		if (entity != null) {
			hibernateTemplate.delete(entity);
		}
	}

}
