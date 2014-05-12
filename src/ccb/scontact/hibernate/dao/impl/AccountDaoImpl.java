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
import ccb.scontact.hibernate.dao.IGroupDao;
import ccb.scontact.hibernate.dao.IPhoneAndGroupDao;
import ccb.scontact.hibernate.dao.IUserRelationshipDao;
import ccb.scontact.hibernate.dao.impl.DaoImplHelper.IDaoHandler;
import ccb.scontact.pojo.AccountInfo;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ContactInfo;
import ccb.scontact.pojo.ErrorInfo;
import ccb.scontact.pojo.GroupInfo;
import ccb.scontact.pojo.PhoneAndGroupInfo;
import ccb.scontact.pojo.UserRelationshipInfo;
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
				List<AccountInfo> existnames = searchAccountInfo(info.getDisplayName(),false);
				if ( ListUtil.isNotEmpty(existnames) ){
					return GlobalValue.MESSAGES.get(GlobalValue.STR_NAME_UNAVAILABAL);
				}  else {//add a new user
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
			IContactDao icd = new ContactDaoImpl();
			ContactInfo contact = new ContactInfo();
			contact.setContact(tmp.getPhoneNumber());
			contact.setUserId(tmp.getId());
			contact.setStatus(GlobalValue.CSTATUS_USED);
			if ( StringUtil.isValidPhoneNumber(tmp.getPhoneNumber())){
				contact.setType(GlobalValue.CTYPE_PHONE);
			} else if ( StringUtil.isValidEmail(tmp.getPhoneNumber())){
				contact.setType(GlobalValue.CTYPE_EMAIL);
			}
			icd.addContact(contact);
		}
		return result;
	}

	@Override
	public BaseInfo updateAccount(final AccountInfo info) {
		DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {

			@Override
			public BaseInfo handleSession(Session s) {
				s.update(info);
				return null;
			}
		});
		return info;
	}

	@Override
	public BaseInfo getAccountInfo(final Long id) {
		if ( id == null )return GlobalValue.MESSAGES.get(
				GlobalValue.STR_INVALID_REQUEST);
		return DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {

			@Override
			public BaseInfo handleSession(Session s) {
				String hql = "FROM AccountInfo "  
		        		 + " WHERE user_id ='" + id + "'";
		         Query query = s.createQuery(hql);
		         return (AccountInfo) query.uniqueResult();
			}
		});
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
	public List<AccountInfo> searchAccountInfo(final String queryStr,final boolean like) {
		return DaoImplHelper.doTask(new IDaoHandler<List<AccountInfo>>() {
			@Override
			public List<AccountInfo> handleSession(Session s) {
				List<AccountInfo> result = new ArrayList<AccountInfo>();
				String hql = "";
				if ( like ){
					hql = "FROM AccountInfo "  
			        		 + " WHERE user_display_name like '%"  + queryStr  + "%'";
				} else {
					hql = "FROM AccountInfo "  
			        		 + " WHERE user_display_name='"  + queryStr + "'";
				}
		         Query query = s.createQuery(hql);
		         if ( like ){
		        	 result = (List<AccountInfo>)query.list();
		         } else {
		        	 AccountInfo a = (AccountInfo) query.uniqueResult();
		        	 if ( a != null ){
		        		 result.add(a); 
		        	 }
		         }
		         return result;
			}
		});
	}

	@Override
	public BaseInfo loginAccount(AccountInfo info) {
		if ( !AccountInfo.isValidAccount(info) )return GlobalValue.MESSAGES.get(
				GlobalValue.STR_ACCOUNT_INVALID);
		final String name = info.getDisplayName();
		final String pwd = info.getPassword();
		if ( name == null || pwd == null )return GlobalValue.MESSAGES.get(
				GlobalValue.STR_INVALID_REQUEST);
		return DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {

			@Override
			public BaseInfo handleSession(Session s) {
				String hql = "FROM AccountInfo "  
		        		 + " WHERE user_display_name='" + name + "'"
		        		 + " AND user_password='" + pwd + "'";    
		         Query query = s.createQuery(hql);
		         return (AccountInfo) query.uniqueResult();   
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountInfo> getAllAccount() {
		return DaoImplHelper.doTask(new IDaoHandler<List<AccountInfo>>() {

			@Override
			public List<AccountInfo> handleSession(Session s) {
				String hql = "from AccountInfo";    
		         Query query = s.createQuery(hql);    
		         query.setCacheable(true); 
		         return (List<AccountInfo>) query.list(); 
			}
		});
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
		         query.setCacheable(true); 
		         List<AccountInfo> users = query.list();
		         if ( ListUtil.isNotEmpty(users) ){
		        	 IPhoneAndGroupDao ipad= new PhoneAndGroupDaoImpl();
		        	 IContactDao icd = new ContactDaoImpl();
		        	 for ( AccountInfo a : users ){
		        		 PhoneAndGroupInfo pg = ipad.getPhoneAndGroupInfoByUserIdAndGroupId(a.getId(), gid);
		        		 List<Long> contactIds = ContactInfo.stringToList(pg.getContactIds());
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

	@Override
	public List<AccountInfo> getFriendsOfUser(final Long uid) {
		return DaoImplHelper.doTask(new IDaoHandler<List<AccountInfo>>() {
			@Override
			public List<AccountInfo> handleSession(Session s) {
				String hql = " FROM AccountInfo a"
						+ " WHERE a.id In "
						+ "("
						+ " SELECT uag.followUserId FROM UserRelationshipInfo uag"
						+ " WHERE userId = '" + uid + "'"
						+ " GROUP BY uag.followUserId"
						+ " )"
						+ " AND user_status = '" + GlobalValue.USTATUS_NORMAL + "'"
						;  
		         Query query = s.createQuery(hql);    
		         query.setCacheable(true); 
		         List<AccountInfo> users = query.list();
		         if ( ListUtil.isNotEmpty(users) ){
		        	 IUserRelationshipDao iurd = new UserRelationshipImpl();
		        	 IContactDao icd = new ContactDaoImpl();
		        	 for ( AccountInfo a : users ){
		        		 //get the contacts which friend a expose to me(uid)
		        		 UserRelationshipInfo pg = iurd.getUserRelationshipInfoByUserIdAndGroupId(a.getId(), uid);
		        		 List<Long> contactIds = ContactInfo.stringToList(pg.getContactIds());
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
	}

	@Override
	public List<AccountInfo> getFriendsOfUserContactId(Long cid) {
		IContactDao icd = new ContactDaoImpl();
		final BaseInfo contact = icd.getContactInfo(cid);
		List<AccountInfo> results = new ArrayList<AccountInfo>();
		if ( contact instanceof ContactInfo ){
			IUserRelationshipDao iurd = new UserRelationshipImpl();
			final ContactInfo tmp = (ContactInfo) contact;
			List<AccountInfo> uFriends = getFriendsOfUser(tmp.getUserId());//get user friend
			if ( ListUtil.isNotEmpty(uFriends) ){
				for ( AccountInfo info : uFriends ){//check each friend
					UserRelationshipInfo uri = iurd.getUserRelationshipInfoByUserIdAndGroupId(
							tmp.getUserId(), info.getId()
							);
					List<Long> cids = ContactInfo.stringToList(uri.getContactIds());
					if ( ListUtil.isNotEmpty(cids) && cids.contains(cid) ){
						results.add(info);
					}
				}
			}
		}
		return results;
	}

}
