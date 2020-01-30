package com.inventory.factory;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBSessionFactory {

	@Autowired
	private EntityManagerFactory factory;

	SessionFactory sessionFactory;
	Session session;

	@Bean
	public void configure() {
		sessionFactory = factory.unwrap(SessionFactory.class);
	}
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@PreDestroy
	public void close() {
		sessionFactory.close();
		factory.close();
	}
	
	public synchronized Session getSession() {
		if(session==null) {
			session=sessionFactory.openSession();
		}
		return session;
	}
}
