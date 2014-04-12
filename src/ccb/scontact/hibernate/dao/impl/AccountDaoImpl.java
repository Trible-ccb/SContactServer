package ccb.scontact.hibernate.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ccb.scontact.hibernate.HibernateSessionFactory;
import ccb.scontact.hibernate.dao.IAccountDao;
import ccb.scontact.hibernate.dao.IContactDao;
import ccb.scontact.hibernate.dao.IPhoneAndGroupDao;
import ccb.scontact.hibernate.dao.impl.DaoImplHelper.IDaoHandler;
import ccb.scontact.pojo.AccountInfo;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ContactInfo;
import ccb.scontact.pojo.ErrorInfo;
import ccb.scontact.pojo.PhoneAndGroupInfo;
import ccb.scontact.utils.GlobalValue;
import ccb.scontact.utils.ListUtil;
import ccb.scontact.utils.StringUtil;

public class AccountDaoImpl implements IAccountDao {

	
	@Override
	public BaseInfo createAccount(final AccountInfo info) {
		if (  !AccountInfo.isValidAccount(info) )return GlobalValue.MESSAGES.get(
				GlobalValue.STR_ACCOUNT_INVALID);
		BaseInfo result;
		result = DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {
			@Override
			public BaseInfo handleSession(Session s) {
				//check whether the info exists or not;
				BaseInfo existName = searchAccountInfo(info.getDisplayName());
				if ( existName instanceof AccountInfo ){
					return GlobalValue.MESSAGES.get(GlobalValue.STR_NAME_UNAVAILABAL);
				} else if ( existName instanceof ErrorInfo ){
					return existName;
				} else {//add a new user
					info.setId(null);//auto add;
					info.setCreateTime(System.currentTimeMillis());
					if ( info.getGender() == null ){
						info.setGender(GlobalValue.UGENDER_UNSET);
					}
					if ( info.getStatus() == null ){
						info.setStatus(GlobalValue.USTATUS_NORMAL);
					}
					if ( info.getType() == null ){
						info.setType(GlobalValue.UTYPE_NORMAL);
					}
					Serializable result = s.save(info);
					if ( result instanceof Long ){
						info.setId((Long) result);
					} else {
						return GlobalValue.MESSAGES.get(GlobalValue.STR_REGISTER_ERROR);
					}
					return info;
				}
				
			}
		});
		//if the field phone number has value, then store in ContactInfo table
		if ( result instanceof AccountInfo ){
			final AccountInfo tmp = (AccountInfo) result;
			DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {

				@Override
				public BaseInfo handleSession(Session s) {
					IContactDao icd = new ContactDaoImpl();
					ContactInfo contact = new ContactInfo();
					contact.setContact(tmp.getPhoneNumber());
					contact.setUserId(tmp.getId());
					contact.setStatus(GlobalValue.CSTATUS_USED);
					icd.addContact(contact);
					return null;//no need to return
				}
			});
		}
		return result;
	}

	@Override
	public BaseInfo updateAccount(AccountInfo info) {
		SessionFactory sf = HibernateSessionFactory.getSessionFactory();
		Session session = null;
		Transaction tran = null;
		try {
			session = sf.openSession();
			tran = session.beginTransaction();
			session.update(info);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return info;
	}

	@Override
	public BaseInfo getAccountInfo(Long id) {
		if ( id == null )return GlobalValue.MESSAGES.get(
				GlobalValue.STR_INVALID_REQUEST);
		SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();   
        Session s = null;  
        Transaction t = null;  
        AccountInfo user = null;  
        try{  
	         s = sessionFactory.openSession();  
	         t = s.beginTransaction();
	         String hql = "FROM AccountInfo "  
	        		 + " WHERE user_id ='" + id + "'";
	         Query query = s.createQuery(hql);
	         user = (AccountInfo) query.uniqueResult();   
	         t.commit();  
        }catch(Exception err){  
	        t.rollback();  
	        err.printStackTrace();  
        }finally{  
        	s.close();  
        }  
        return user;
	}

	@Override
	public BaseInfo deleteAccountInfo(Long id) {
		BaseInfo info = getAccountInfo(id);
		if ( info instanceof AccountInfo ){
			AccountInfo a = (AccountInfo) info;
			a.setStatus(GlobalValue.USTATUS_DELETED);
			return updateAccount(a);
		} else {
			if ( info == null ){
				return GlobalValue.MESSAGES.get(GlobalValue.CODE_DELETE_ERROR);
			}
			return info;
		}
	}

	@Override
	public BaseInfo searchAccountInfo(String queryStr) {
		if ( !StringUtil.isValidName(queryStr) )return GlobalValue.MESSAGES.get(
				GlobalValue.STR_INVALID_REQUEST);
		SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();   
        Session s = null;  
        Transaction t = null;  
        AccountInfo user = null;  
        try{  
	         s = sessionFactory.openSession();  
	         t = s.beginTransaction();
	         String hql = "FROM AccountInfo "  
	        		 + " WHERE user_display_name='" + queryStr + "'";
	         Query query = s.createQuery(hql);
	         user = (AccountInfo) query.uniqueResult();   
	         t.commit();  
        }catch(Exception err){  
	        t.rollback();  
	        err.printStackTrace();  
        }finally{  
        	s.close();  
        }  
        return user;
	}

	@Override
	public BaseInfo loginAccount(AccountInfo info) {
		if ( !AccountInfo.isValidAccount(info) )return GlobalValue.MESSAGES.get(
				GlobalValue.STR_ACCOUNT_INVALID);
		String name = info.getDisplayName();
		String pwd = info.getPassword();
		if ( name == null || pwd == null )return GlobalValue.MESSAGES.get(
				GlobalValue.STR_INVALID_REQUEST);
		SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();   
        Session s = null;  
        Transaction t = null;  
        AccountInfo user = null;  
        try{  
	         s = sessionFactory.openSession();  
	         t = s.beginTransaction();
	         String hql = "FROM AccountInfo "  
	        		 + " WHERE user_display_name='" + name + "'"
	        		 + " AND user_password='" + pwd + "'";    
	         Query query = s.createQuery(hql);
	         user = (AccountInfo) query.uniqueResult();   
	         t.commit();  
        }catch(Exception err){  
	        t.rollback();  
	        err.printStackTrace();  
        }finally{  
        	s.close();  
        }  
        return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountInfo> getAllAccount() {
		SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();   
        Session s = null;  
        Transaction t = null;  
        List<AccountInfo> uesrs = null;  
        try{  
	         s = sessionFactory.openSession();  
	         t = s.beginTransaction();  
	         String hql = "from AccountInfo";    
	         Query query = s.createQuery(hql);    
	         query.setCacheable(true); // …Ë÷√ª∫¥Ê    
	         uesrs = query.list();    
	         t.commit();  
        }catch(Exception err){  
	        t.rollback();  
	        err.printStackTrace();
        }finally{  
        	s.close();  
        }  
        return uesrs;
	}

	@Override
	public List<AccountInfo> getAccountsOfGroup(final Long gid) {
		List<AccountInfo> results = null;
		results = DaoImplHelper.doTask(new IDaoHandler<List<AccountInfo>>() {
			@Override
			public List<AccountInfo> handleSession(Session s) {
				String hql = " FROM AccountInfo a"
						+ " WHERE a.id In "
						+ "("
						+ " SELECT pag.userId FROM PhoneAndGroupInfo pag"
						+ " WHERE group_id = '" + gid + "'"
						+ " GROUP BY pag.userId"
						+ " )"
						+ " AND user_status = '" + GlobalValue.USTATUS_NORMAL + "'"
						;  
		         Query query = s.createQuery(hql);    
		         query.setCacheable(true); // …Ë÷√ª∫¥Ê    
		         List<AccountInfo> users = query.list();
		         if ( ListUtil.isNotEmpty(users) ){
		        	 IPhoneAndGroupDao ipad= new PhoneAndGroupDaoImpl();
		        	 IContactDao icd = new ContactDaoImpl();
		        	 for ( AccountInfo a : users ){
		        		 PhoneAndGroupInfo pg = ipad.getPhoneAndGroupInfoByUserIdAndGroupId(a.getId(), gid);
		        		 Type t = new TypeToken<List<Long>>(){}.getType();
		        		 List<Long> contactIds = new Gson().fromJson(pg.getContactIds(),t);
		        		 List<ContactInfo> contacts = new ArrayList<ContactInfo>();
		        		 if ( ListUtil.isNotEmpty(contactIds) ){
		        			 for ( Long id : contactIds ){
		        				 BaseInfo tmp = icd.getContactInfo(id);
		        				 if ( tmp instanceof ContactInfo ){
		        					 contacts.add((ContactInfo) tmp);
		        				 }
		        			 }
		        		 }
		        		 a.setContactsList(contacts);
		        	 }
		         }
				return users;
			}
		});
		return results;
	}



}
