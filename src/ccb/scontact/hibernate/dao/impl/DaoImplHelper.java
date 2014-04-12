package ccb.scontact.hibernate.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ccb.scontact.hibernate.HibernateSessionFactory;

public class DaoImplHelper{
	
	public interface IDaoHandler<T>{
		T handleSession(Session s);
	}
	public static <E> E doTask(IDaoHandler<E>  handler){
		SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();   
        Session s = null;  
        Transaction t = null;
        E result = null;
        try{  
	         s = sessionFactory.openSession();
	         t = s.beginTransaction();
	         result = handler.handleSession(s);   
	         t.commit();  
        }catch(Exception err){
        	if ( t != null ){
        		t.rollback();  
        	}
	        err.printStackTrace();  
        }finally{  
        	s.close();  
        }
        return result;
	}
}
