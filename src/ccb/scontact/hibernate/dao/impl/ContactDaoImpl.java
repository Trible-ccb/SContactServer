package ccb.scontact.hibernate.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ccb.scontact.hibernate.dao.IAccountDao;
import ccb.scontact.hibernate.dao.IContactDao;
import ccb.scontact.hibernate.dao.impl.DaoImplHelper.IDaoHandler;
import ccb.scontact.pojo.AccountInfo;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ContactInfo;
import ccb.scontact.utils.GlobalValue;
import ccb.scontact.utils.StringUtil;

public class ContactDaoImpl implements IContactDao {

	@Override
	public BaseInfo addContact(final ContactInfo info) {
		if ( !ContactInfo.isValidContact(info) )return null;
		BaseInfo result;
		result = DaoImplHelper.doTask(new IDaoHandler<BaseInfo> () {
			@Override
			public BaseInfo handleSession(Session s) {
				IAccountDao iad = new AccountDaoImpl();
				BaseInfo exit = iad.getAccountInfo(info.getUserId());
				//check whether exist the special user
				if ( exit instanceof AccountInfo ){
					List<ContactInfo> exitContacts = getUsersContact(info.getUserId());
					if ( exitContacts != null ){
						for ( ContactInfo ec: exitContacts ){
							if ( info.getContact().equals(ec.getContact()) ){
								return ec;//return already exist the same ContactInfo
							}
						}
					}
					if ( info.getStatus() == null ){
						info.setStatus(GlobalValue.CSTATUS_USED);
					}
					info.setLastestUsedTime(System.currentTimeMillis());
					Serializable result = s.save(info);
					if ( result instanceof Long ){
						info.setId((Long) result);
					}
				} else {
					return exit;
				}
				return info;
			}
		});
		return result;
	}

	@Override
	public BaseInfo updateContact(final ContactInfo info) {
		if ( info == null || info.getId() == null )return null;
		BaseInfo result = DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {
			@Override
			public BaseInfo handleSession(Session s) {
				s.update(info);
				return info;
			}
		});
		return result;
	}

	@Override
	public BaseInfo getContactInfo(final Long id) {
		if ( id == null )return null;
		BaseInfo result;
		result = DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {
			@Override
			public BaseInfo handleSession(Session s) {
				String hql = "FROM ContactInfo "  
		        		 + " WHERE id ='" + id + "'";
		         Query query = s.createQuery(hql);
		         return (BaseInfo)query.uniqueResult();  
			}
		});
		return result;
	}

	@Override
	public BaseInfo deleteContactInfo(Long id) {
		BaseInfo tmp = getContactInfo(id);
		if ( tmp  instanceof ContactInfo ){
			ContactInfo c = (ContactInfo) tmp;
			c.setStatus(GlobalValue.CSTATUS_DELETED);
			tmp = updateContact(c);
		}
		return tmp;
	}

	@Override
	public List<ContactInfo> searchContactInfo(final String query) {
		if ( !StringUtil.isValidName(query) )return null;
		List<ContactInfo> result = null;
		result = DaoImplHelper.doTask(new IDaoHandler<List<ContactInfo>>() {
			@Override
			public List<ContactInfo> handleSession(Session s) {
				String hql = "FROM ContactInfo "  
		        		 + " WHERE phone_number like '" + query + "'";
		         Query query = s.createQuery(hql);
		         return query.list();  
			}
		});
		return result;
	}

	@Override
	public List<ContactInfo> getAllContact() {
		List<ContactInfo> results = null;
		results = DaoImplHelper.doTask(new IDaoHandler<List<ContactInfo>>() {

			@Override
			public List<ContactInfo> handleSession(Session s) {
				String hql = "FROM ContactInfo";    
		         Query query = s.createQuery(hql);    
		         query.setCacheable(true); // …Ë÷√ª∫¥Ê    
		         List<ContactInfo> uesrs = query.list();
				return uesrs;
			}
		});
		return results;
	}

	@Override
	public List<ContactInfo> getUsersContact(final Long uid) {
		List<ContactInfo> results = null;
		results = DaoImplHelper.doTask(new IDaoHandler<List<ContactInfo>>() {

			@Override
			public List<ContactInfo> handleSession(Session s) {
				String hql = " FROM ContactInfo"
						+ " WHERE user_id = '" + uid + "'"
						+ " AND status <> '" + GlobalValue.CSTATUS_DELETED + "'";    
		         Query query = s.createQuery(hql);    
		         query.setCacheable(true); // …Ë÷√ª∫¥Ê    
		         List<ContactInfo> uesrs = query.list();
				return uesrs;
			}
		});
		return results;
	}

}
