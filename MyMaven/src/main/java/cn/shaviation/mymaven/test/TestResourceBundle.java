package cn.shaviation.mymaven.test;

import java.util.Locale;
import java.util.ResourceBundle;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class TestResourceBundle {
	public static void main(String[] args) {
		Locale locale1 = new Locale("zh", "CN");
		ResourceBundle resb1 = ResourceBundle.getBundle("myres", locale1);
		System.out.println(resb1.getString("aaa"));

		ResourceBundle resb2 = ResourceBundle.getBundle("myres", Locale.getDefault());
		System.out.println(resb2.getString("aaa"));

		Locale locale3 = new Locale("en", "US");
		ResourceBundle resb3 = ResourceBundle.getBundle("myres", locale3);
		System.out.println(resb3.getString("aaa"));
		System.out.println("aaa");
	}

	private static SessionFactory configureSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}
	
}
