package ccb.scontact.hibernate.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ccb.scontact.hibernate.dao.IPhoneAndGroupDao;
import ccb.scontact.hibernate.dao.impl.DaoImplHelper.IDaoHandler;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ContactInfo;
import ccb.scontact.pojo.ErrorInfo;
import ccb.scontact.pojo.PhoneAndGroupInfo;
import ccb.scontact.utils.GlobalValue;
import ccb.scontact.utils.ListUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PhoneAndGroupDaoImpl implements IPhoneAndGroupDao{

	@Override
	public BaseInfo joinOrUpdateInGroup(final PhoneAndGroupInfo info) {
		if ( !PhoneAndGroupInfo.isValid(info) )return GlobalValue.MESSAGES.get(GlobalValue.STR_INVALID_REQUEST);
		BaseInfo result;
		result = DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {
			@Override
			public BaseInfo handleSession(Session s) {
//				TODO maybe need to check the validation of group and user
				ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_PHONE_GROUP_ERROR);
				//check whether the contact exist
				BaseInfo pag = checkPhoneAndGroupInfo(info);
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
						} else if ( pag instanceof PhoneAndGroupInfo ){//update a row
							PhoneAndGroupInfo tmp = (PhoneAndGroupInfo) pag;
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
	public BaseInfo exitGroup(final PhoneAndGroupInfo info) {
		final ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_INVALID_REQUEST);
		if ( info == null ){
			return msg;
		}
		BaseInfo result =
				DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {

					@Override
					public BaseInfo handleSession(Session s) {
						BaseInfo check = checkPhoneAndGroupInfo(info);
						if ( check instanceof PhoneAndGroupInfo ){
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
	public BaseInfo checkPhoneAndGroupInfo(final PhoneAndGroupInfo info) {
		if ( info==null
				|| info.getGroupId() == null
				|| info.getUserId() == null )return GlobalValue.MESSAGES.get(GlobalValue.STR_INVALID_REQUEST);
		BaseInfo result = null;
		result = DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {

			@Override
			public BaseInfo handleSession(Session s) {
				String hql = " FROM PhoneAndGroupInfo"
						+ " WHERE group_id = '" + info.getGroupId() + "'"
						+ " AND user_id = '" + info.getUserId() + "'";
				return (BaseInfo) s.createQuery(hql).uniqueResult();
			}
		});
		return result;
	}

	@Override
	public List<PhoneAndGroupInfo> getPhoneAndGroupInfoByGroupId(final Long gid) {
		List<PhoneAndGroupInfo> results = null;
		results = DaoImplHelper.doTask(new IDaoHandler<List<PhoneAndGroupInfo>>() {

			@Override
			public List<PhoneAndGroupInfo> handleSession(Session s) {
				String hql = " FROM PhoneAndGroupInfo"
						+ " WHERE group_id = '" + gid + "'";
		         Query query = s.createQuery(hql);    
		         query.setCacheable(true); // ���û���    
		         List<PhoneAndGroupInfo> uesrs = query.list();
				return uesrs;
			}
		});
		return results;
	}

	@Override
	public PhoneAndGroupInfo getPhoneAndGroupInfoByUserIdAndGroupId(final Long uid , final Long gid) {
		PhoneAndGroupInfo results = null;
		results = DaoImplHelper.doTask(new IDaoHandler<PhoneAndGroupInfo>() {

			@Override
			public PhoneAndGroupInfo handleSession(Session s) {
				String hql = " FROM PhoneAndGroupInfo"
						+ " WHERE user_id = '" + uid + "'"
						+ " AND group_id = '" + gid + "'";
		         Query query = s.createQuery(hql);    
		         query.setCacheable(true); // ���û���    
		         PhoneAndGroupInfo uesrs = (PhoneAndGroupInfo) query.uniqueResult();
				return uesrs;
			}
		});
		return results;
	}

}
