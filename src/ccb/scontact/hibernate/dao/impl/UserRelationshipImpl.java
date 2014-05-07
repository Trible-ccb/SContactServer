package ccb.scontact.hibernate.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ccb.scontact.hibernate.dao.IUserRelationshipDao;
import ccb.scontact.hibernate.dao.impl.DaoImplHelper.IDaoHandler;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ContactInfo;
import ccb.scontact.pojo.ErrorInfo;
import ccb.scontact.pojo.UserRelationshipInfo;
import ccb.scontact.pojo.UserRelationshipInfo;
import ccb.scontact.pojo.UserRelationshipInfo;
import ccb.scontact.utils.GlobalValue;
import ccb.scontact.utils.ListUtil;

public class UserRelationshipImpl implements IUserRelationshipDao{

	@Override
	public BaseInfo addOrUpdateRelationshipBetweenUser(final UserRelationshipInfo info) {
		if ( !UserRelationshipInfo.isValid(info) )return GlobalValue.MESSAGES.get(GlobalValue.STR_INVALID_REQUEST);
		BaseInfo result;
		result = DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {
			@Override
			public BaseInfo handleSession(Session s) {
//				TODO maybe need to check the validation of following user and user
				ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_PHONE_GROUP_ERROR);
				//check whether the contact exist
				BaseInfo pag = checkUserRelationshipInfo(info);
				if ( !(pag instanceof ErrorInfo) ){
					List<Long> contacts = ContactInfo.stringToList(info.getContactIds());
					if ( ListUtil.isEmpty(contacts) ){//do not allow empty contacts
						return msg;
					} else {
						if ( pag == null ){//add a row
							info.setId(null);
							Serializable r = s.save(info);
							if ( r instanceof Long ){
								info.setId((Long) r);
								return info;
							} else {
								return msg;
							}
						} else if ( pag instanceof UserRelationshipInfo ){//update a row
							UserRelationshipInfo tmp = (UserRelationshipInfo) pag;
							tmp.setContactIds(info.getContactIds());
							s.update(tmp);
							return tmp;
						} else {
							return msg;
						}
					}
				} else {
					return msg;
				}
			}
		});
		return result;
	}

	@Override
	public BaseInfo removeRelationshipBetweenUser(final UserRelationshipInfo info) {
		final ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_INVALID_REQUEST);
		if ( info == null ){
			return msg;
		}
		BaseInfo result =
				DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {

					@Override
					public BaseInfo handleSession(Session s) {
						BaseInfo check = checkUserRelationshipInfo(info);
						if ( check instanceof UserRelationshipInfo ){
							s.delete(check);
						} else {
							return msg;
						}
						return check;
					}
				});
		return result;
	}

	@Override
	public BaseInfo checkUserRelationshipInfo(final UserRelationshipInfo info) {
		if ( info==null
				|| info.getFollowUserId() == null
				|| info.getUserId() == null )return GlobalValue.MESSAGES.get(GlobalValue.STR_INVALID_REQUEST);
		BaseInfo result = null;
		result = DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {
			@Override
			public BaseInfo handleSession(Session s) {
				String hql = " FROM UserRelationshipInfo"
						+ " WHERE follow_user_id = '" + info.getFollowUserId() + "'"
						+ " AND user_id = '" + info.getUserId() + "'";
				return (BaseInfo) s.createQuery(hql).uniqueResult();
			}
		});
		return result;
	}

	@Override
	public List<UserRelationshipInfo> getUserRelationshipInfoByUserId(final Long uid) {
		List<UserRelationshipInfo> results = null;
		results = DaoImplHelper.doTask(new IDaoHandler<List<UserRelationshipInfo>>() {

			@Override
			public List<UserRelationshipInfo> handleSession(Session s) {
				String hql = " FROM UserRelationshipInfo"
						+ " WHERE user_id = '" + uid + "'";
		         Query query = s.createQuery(hql);    
		         query.setCacheable(true); 
		         List<UserRelationshipInfo> uesrs = query.list();
				return uesrs;
			}
		});
		return results;
	}

	@Override
	public UserRelationshipInfo getUserRelationshipInfoByUserIdAndGroupId(
			final Long uid, final Long fid) {
		UserRelationshipInfo results = null;
		results = DaoImplHelper.doTask(new IDaoHandler<UserRelationshipInfo>() {

			@Override
			public UserRelationshipInfo handleSession(Session s) {
				String hql = " FROM UserRelationshipInfo"
						+ " WHERE user_id = '" + uid + "'"
						+ " AND follow_user_id = '" + fid + "'";
		         Query query = s.createQuery(hql);    
		         query.setCacheable(true); 
		         UserRelationshipInfo uesrs = (UserRelationshipInfo) query.uniqueResult();
				return uesrs;
			}
		});
		return results;
	}
}
