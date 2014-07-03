package ccb.scontact.hibernate.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import ccb.scontact.hibernate.QueryParamsHelper;
import ccb.scontact.hibernate.dao.IAccountDao;
import ccb.scontact.hibernate.dao.IContactDao;
import ccb.scontact.hibernate.dao.IPhoneAndGroupDao;
import ccb.scontact.hibernate.dao.IUserRelationshipDao;
import ccb.scontact.hibernate.dao.impl.DaoImplHelper.IDaoHandler;
import ccb.scontact.pojo.AccountInfo;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ContactInfo;
import ccb.scontact.pojo.PhoneAndGroupInfo;
import ccb.scontact.pojo.UserRelationshipInfo;
import ccb.scontact.utils.GlobalValue;
import ccb.scontact.utils.ListUtil;
import ccb.scontact.utils.SecurityMethod;
import ccb.scontact.utils.StringUtil;

public class AccountDaoImpl implements IAccountDao {

	
	@Override
	public BaseInfo createAccount(final AccountInfo info,final boolean updateIfNeed) {
		if (  !AccountInfo.isValidAccount(info) )return GlobalValue.MESSAGES.get(
				GlobalValue.STR_ACCOUNT_INVALID);
		BaseInfo result;
		result = DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {
			@Override
			public BaseInfo handleSession(Session s) {
				//check whether the info exists or not;
				String cookieStr = null ;
				List<AccountInfo> existnames = searchAccountInfo(info,false);
				if ( ListUtil.isNotEmpty(existnames) ){
					if ( updateIfNeed ){
						AccountInfo one = existnames.get(0);
						one.setDisplayName(info.getDisplayName());
						one.setPhotoUrl(info.getPhotoUrl());
						one.setGender(info.getGender());
						one.setDescription(info.getDescription());
						one.setThirdPartyId(info.getThirdPartyId());
						one.setRealName(info.getRealName());
						cookieStr = DigestUtils.shaHex(
								one.getDisplayName()+System.currentTimeMillis());
						one.setCookie(cookieStr);
						updateAccount(one);
						return one;
					} else {
						return GlobalValue.MESSAGES.get(GlobalValue.STR_NAME_UNAVAILABAL);
					}
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
					cookieStr = DigestUtils.shaHex(info.getDisplayName()
							+System.currentTimeMillis());
					info.setCookie(cookieStr);
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
			if ( StringUtil.isEmpty(tmp.getPhoneNumber()) )return result;
			String decphone = SecurityMethod.getAESInstance().Decryptor(tmp.getPhoneNumber());
			if ( decphone == null )return result;
			contact.setContact(tmp.getPhoneNumber());
			contact.setUserId(tmp.getId());
			contact.setStatus(GlobalValue.CSTATUS_USED);
			contact.setType(GlobalValue.CTYPE_PHONE);
			contact.setLastestUsedTime(System.currentTimeMillis());
			if ( StringUtil.isValidPhoneNumber(decphone)){
				contact.setType(GlobalValue.CTYPE_PHONE);
			} else if ( StringUtil.isValidEmail(decphone)){
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
		        		 + " WHERE user_id =:id";
		         Query query = s.createQuery(hql).setParameter("id", id);
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
	public List<AccountInfo> searchAccountInfo(final AccountInfo queryinfo,final boolean like) {
		
		return DaoImplHelper.doTask(new IDaoHandler<List<AccountInfo>>() {
			@Override
			public List<AccountInfo> handleSession(Session s) {
				List<AccountInfo> result = new ArrayList<AccountInfo>();
				String hql = "";
				String queryStr;
				if ( !StringUtil.isEmpty(queryinfo.getThirdPartyId()) ){
					queryStr = queryinfo.getThirdPartyId();
				} else {
					queryStr = queryinfo.getDisplayName();
				}
				QueryParamsHelper qph = new QueryParamsHelper();
				String realnameq = null;
				if ( queryinfo.getRealName() != null ){
					realnameq = " user_real_name=:user_real_name OR ";
				}
				hql = " FROM AccountInfo "  
		        		 + " WHERE ";
				if ( realnameq != null ){
					hql += realnameq;
				}
				String querysId = queryinfo.getThirdPartyId();
				if ( !StringUtil.isEmpty(querysId) ){
					qph.add("=","user_third_usid" , querysId);
				} else {
					if ( like ){
//						hql = hql.replace("#", "like");
						queryStr = queryStr + "%";
						qph.add("like","user_display_name" , queryStr);
					} else {
//						hql = hql.replace("#", "=");
						qph.add("=","user_display_name" , queryStr);
					}
				}
//				Query query = s.createQuery(hql).setParameter("name", q);
				Query query = qph.buildQuery(s, hql);
				if ( hql.contains(":user_real_name") ){
					query.setParameter("user_real_name", queryinfo.getRealName());
				}
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
	public BaseInfo loginAccount(final AccountInfo info) {
		if ( !AccountInfo.isValidAccount(info) )return GlobalValue.MESSAGES.get(
				GlobalValue.STR_ACCOUNT_INVALID);
		final String name = info.getDisplayName();
		final String pwd = info.getPassword();
		if ( StringUtil.isEmpty(name) || StringUtil.isEmpty(pwd) )return GlobalValue.MESSAGES.get(
				GlobalValue.STR_INVALID_REQUEST);
		return DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {

			@Override
			public BaseInfo handleSession(Session s) {
				String hql = "FROM AccountInfo "  
		        		 + " WHERE user_display_name=:name"
		        		 + " AND user_password=:psw";    
		         Query query = s.createQuery(hql)
		        		 .setParameter("name", name)
		        		 .setParameter("psw", pwd);
		         AccountInfo result = (AccountInfo) query.uniqueResult();
		         if (!StringUtil.isEmpty(info.getNotifyId()) && result != null){
		        	 result.setNotifyId(info.getNotifyId());
		        	 updateAccount(result);
		         }
		         return result;
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
						+ " WHERE group_id =:gid"
						+ " GROUP BY pag.userId"
						+ " )"
						+ " AND user_status = '" + GlobalValue.USTATUS_NORMAL + "'"
						;  
		         Query query = s.createQuery(hql).setParameter("gid", gid);    
		         query.setCacheable(true); 
		         List<AccountInfo> users = query.list();
		         if ( ListUtil.isNotEmpty(users) ){
		        	 IPhoneAndGroupDao ipad= new PhoneAndGroupDaoImpl();
		        	 IContactDao icd = new ContactDaoImpl();
		        	 for ( AccountInfo a : users ){
		        		 PhoneAndGroupInfo pg = ipad.getPhoneAndGroupInfoByUserIdAndGroupId(a.getId(), gid);
		        		 a.setContactsList(icd.getContactInfosByContactString(pg.getContactIds()));
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
						+ " WHERE userId =:uid"
						+ " GROUP BY uag.followUserId"
						+ " )"
						+ " AND user_status = '" + GlobalValue.USTATUS_NORMAL + "'"
						;  
		         Query query = s.createQuery(hql).setParameter("uid", uid);    
		         query.setCacheable(true); 
		         List<AccountInfo> users = query.list();
		         if ( ListUtil.isNotEmpty(users) ){
		        	 IUserRelationshipDao iurd = new UserRelationshipImpl();
		        	 IContactDao icd = new ContactDaoImpl();
		        	 for ( AccountInfo a : users ){
		        		 //get the contacts which friend a expose to me(uid)
		        		 UserRelationshipInfo pg = iurd.getUserRelationshipInfoByUserIdAndGroupId(a.getId(), uid);
		        		 a.setContactsList(icd.getContactInfosByContactString(pg.getContactIds()));
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

	@Override
	public BaseInfo loginWithThirdpartyAccount(AccountInfo info) {
		BaseInfo result = createAccount(info,true);
		return result;
	}

}
