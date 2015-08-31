package cn.shaviation.mymaven.user.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.shaviation.mymaven.user.dao.UserDao;
import cn.shaviation.mymaven.user.model.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
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
	public List<User> getAll() {
		return hibernateTemplate.loadAll(User.class);
	}
	@Override
	public void saveUser(User user) {
		//法1
//		sessionFactory.getCurrentSession().save(user);
		//法二
		hibernateTemplate.save(user);
//		 Object object = org.springframework.aop.aspectj.TypePatternClassFilter;
	}

}
