package com.inventory.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.factory.DBSessionFactory;

@Service
public class DBService{
	
	@Autowired 
	public DBSessionFactory dbFactory;

	public <T> void save(T obj) {
		// TODO Auto-generated method stub
		Session session=dbFactory.getSession();
		Transaction tx=session.beginTransaction();
		session.save(obj);
		try {
		session.flush();
		tx.commit();
		}catch (Exception e) {
			try {
				tx.rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		session.evict(obj);
		
	}

	
	public <T> void update(T obj) {
		Session session=dbFactory.getSession();
		Transaction tx=(Transaction) session.beginTransaction();
		session.merge(obj);
		try {
			tx.commit();
		} catch (Exception e) {
			try {
				tx.rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		
	}

	
	public <T> void delete(T obj) {
		Session session=dbFactory.getSession();
		Transaction tx=(Transaction) session.beginTransaction();
		session.remove(session.contains(obj)?obj:session.merge(obj));
		try {
			tx.commit();
		} catch (Exception e) {
			try {
				tx.rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
	}

	
	
	
	
}
