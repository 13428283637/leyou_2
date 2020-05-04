package com.fang.demo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.fang.domain.Customer;
import com.fang.domain.LinkMan;
import com.fang.utils.HibernateUtils;



//一对多|多对一关系操作
public class Demo {
	
	
	@Test
	public void save() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		Customer customer  = new Customer();
		LinkMan linkMan1 = new LinkMan();
		LinkMan linkMan2 = new LinkMan();

		customer.setCust_name("小房");
		
		linkMan1.setLkm_name("联系人1");
		linkMan2.setLkm_name("联系人2");
//		linkMan1.setCustomer(customer);
//		linkMan2.setCustomer(customer);
		
		customer.getLinkMans().add(linkMan1);
		customer.getLinkMans().add(linkMan2);
		session.save(customer);
		
		tx.commit();
		
	}
	
	@Test
	public void add() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		Customer customer = session.get(Customer.class, 35l);
		LinkMan linkMan = new LinkMan();
		linkMan.setLkm_position("dasd");
		customer.getLinkMans().add(linkMan);
		
		
		tx.commit();
		
	}
	
	@Test
	public void remove() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Customer customer = session.get(Customer.class, 35l);
		LinkMan linkMan = session.get(LinkMan.class, 17l);


		linkMan.setLkm_position("dasd");
		customer.getLinkMans().remove(linkMan);
		
		
		tx.commit();
		
	}
	@Test
	public void delete() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();

		LinkMan linkMan = session.get(LinkMan.class, 17l);


		session.delete(linkMan);
		
		
		tx.commit();
		
	}
		
}
