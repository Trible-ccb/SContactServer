package ccb.scontact.hibernate.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ccb.scontact.hibernate.dao.IAccountDao;
import ccb.scontact.hibernate.dao.IGroupDao;
import ccb.scontact.hibernate.dao.impl.DaoImplHelper.IDaoHandler;
import ccb.scontact.pojo.AccountInfo;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.GroupInfo;
import ccb.scontact.pojo.PhoneAndGroupInfo;
import ccb.scontact.utils.GlobalValue;
import ccb.scontact.utils.StringUtil;

public class GroupDaoImpl implements IGroupDao {

	@Override
	public BaseInfo createGroup(final GroupInfo info,final PhoneAndGroupInfo info2) {
		BaseInfo result = null;
		if ( !GroupInfo.isValidGroup(info) 
				|| !PhoneAndGroupInfo.isValidContactIds(info2.getContactIds() ))return GlobalValue.MESSAGES.get(GlobalValue.STR_GROUP_ERROR);
		result = DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {
			@Override
			public BaseInfo handleSession(Session s) {
				BaseInfo user = null;
				IAccountDao iad = new AccountDaoImpl();
				user = iad.getAccountInfo(info.getOwnerId());
				if ( user instanceof AccountInfo ){
					//check the limited of create group nums; 
					AccountInfo tmp = (AccountInfo) user;
					List<GroupInfo> owners = getUserGroup(tmp.getId());
					int cn =  owners == null ? 0 : owners.size();
					int mn = GlobalValue.CREATE_GROUP_NUMBERS_RULE.get(tmp.getType());
					if ( mn > cn ){//still can create group
						info.setId(null);
						info.setCreateTime(System.currentTimeMillis());
						info.setUpdateTime(info.getCreateTime());
						
						if ( info.getCapacity() == null ){
							info.setCapacity(GlobalValue.GROUP_CAPACITY_RULE.get(GlobalValue.GTYPE_NORMAL));
						}
						if ( info.getStatus() == null ){
							info.setStatus(GlobalValue.GSTATUS_USED);
						}
						if ( info.getIdentify() == null ){
							info.setIdentify(GlobalValue.GIDENTIFY_NONE);
						}
						if ( info.getType() == null ){
							info.setType(GlobalValue.GTYPE_NORMAL);
						}
						Serializable r = s.save(info);
						if ( r instanceof  Long ){//success
							info.setId((Long) r);
							info2.setId(null);
							info2.setGroupId(info.getId());
							info2.setUserId(info.getOwnerId());
							s.save(info2);
							return info;
						} else {
							return GlobalValue.MESSAGES.get(GlobalValue.STR_GROUP_ERROR);
						}
					} else {
						return GlobalValue.MESSAGES.get(GlobalValue.STR_TO_MAX_ERROR);
					}
				} else {
					return GlobalValue.MESSAGES.get(GlobalValue.STR_ACCOUNT_INVALID);
				}
			}
		});
		return result;
	}

	@Override
	public BaseInfo updateGroup(final GroupInfo info) {
		if ( info == null || info.getId() == null ){
			return GlobalValue.MESSAGES.get(GlobalValue.STR_GROUP_ERROR);
		}
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
	public BaseInfo getGroupInfo(final Long id) {
		if ( id == null )return GlobalValue.MESSAGES.get(GlobalValue.STR_INVALID_REQUEST);
		BaseInfo result;
		result = DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {
			@Override
			public BaseInfo handleSession(Session s) {
				String hql = "FROM GroupInfo "  
		        		 + " WHERE id ='" + id + "'";
		         Query query = s.createQuery(hql);
		         return (BaseInfo) query.uniqueResult();  
			}
		});
		return result;
	}

	@Override
	public BaseInfo deleteGroupInfo(Long id) {
		BaseInfo tmp = getGroupInfo(id);
		if ( tmp  instanceof GroupInfo ){
			GroupInfo c = (GroupInfo) tmp;
			c.setStatus(GlobalValue.GSTATUS_DELETED);
			tmp = updateGroup(c);
		}
		return tmp;
	}

	@Override
	public List<GroupInfo> searchGroupInfo(final String query) {
		if ( !StringUtil.isValidName(query) )return null;
		List<GroupInfo> result = null;
		result = DaoImplHelper.doTask(new IDaoHandler<List<GroupInfo>>() {
			@Override
			public List<GroupInfo> handleSession(Session s) {
				String hql = "FROM GroupInfo "  
		        		 + " WHERE group_display_name like '%" + query + "%'"
		        		 + " AND group_status = '" + GlobalValue.GSTATUS_USED + "'"
		        		 ;
		         Query query = s.createQuery(hql);
		         return query.list();  
			}
		});
		return result;
	}

	@Override
	public List<GroupInfo> getAllGroup() {
		List<GroupInfo> results = null;
		results = DaoImplHelper.doTask(new IDaoHandler<List<GroupInfo>>() {

			@Override
			public List<GroupInfo> handleSession(Session s) {
				String hql = "FROM GroupInfo";    
				Query query = s.createQuery(hql);    
				query.setCacheable(true); // ���û���    
				return  query.list();
			}
		});
		return results;
	}

	@Override
	public List<GroupInfo> getUserGroup(final Long id) {
		List<GroupInfo> results = null;
		results = DaoImplHelper.doTask(new IDaoHandler<List<GroupInfo>>() {

			@Override
			public List<GroupInfo> handleSession(Session s) {
				String hql = " FROM GroupInfo g"
						+ " WHERE g.id In "
						+ "("
						+ " SELECT pag.groupId FROM PhoneAndGroupInfo pag"
						+ " WHERE user_id = '" + id + "'"
						+ " GROUP BY pag.groupId"
						+ " )"
						+ " AND group_status <> '" + GlobalValue.GSTATUS_DELETED + "'"
						;
		         Query query = s.createQuery(hql);    
		         query.setCacheable(true);
		         List<GroupInfo> result = query.list();
				return result;
			}
		});
		return results;
	}
	
}
