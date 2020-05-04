package com.fang.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.fang.domain.Customer;

public class HibernateDemo {
	@Test
	public void demo1() {
		System.out.println("sadasd");
		Configuration cfg = new Configuration().configure();
		SessionFactory sf = cfg.buildSessionFactory();
		Session session = sf.openSession();
		Transaction ts = session.beginTransaction();
		Customer customer = new Customer();
		//customer.setCust_id(1l);
		customer.setCust_name("haha");
		session.save(customer);
		ts.commit();
		
		session.close();
		System.out.println("sada"+customer);
		
		
	}
}
