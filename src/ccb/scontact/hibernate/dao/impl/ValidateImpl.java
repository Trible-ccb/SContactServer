package ccb.scontact.hibernate.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ccb.scontact.hibernate.dao.IAccountDao;
import ccb.scontact.hibernate.dao.IGroupDao;
import ccb.scontact.hibernate.dao.IValidateDao;
import ccb.scontact.hibernate.dao.impl.DaoImplHelper.IDaoHandler;
import ccb.scontact.notify.ActivityNotifyer;
import ccb.scontact.notify.SimpleNotifyer;
import ccb.scontact.pojo.AccountInfo;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.GroupInfo;
import ccb.scontact.pojo.ValidateInfo;
import ccb.scontact.utils.GlobalValue;
import ccb.scontact.utils.ListUtil;

public class ValidateImpl implements IValidateDao {

	@Override
	public BaseInfo addOneValidate(final ValidateInfo info) {
		BaseInfo result;
		result = DaoImplHelper.doTask(new IDaoHandler<BaseInfo> () {
			@Override
			public BaseInfo handleSession(Session s) {
				info.setCreateTime(System.currentTimeMillis());
				Serializable result = s.save(info);
				if(result instanceof Long){
					info.setId((Long) result);
					IAccountDao icd = new AccountDaoImpl();
					BaseInfo base = icd.getAccountInfo(info.getEnd_user_id());
					if ( base instanceof AccountInfo ){
						AccountInfo basea = (AccountInfo) base;
						ActivityNotifyer no = new ActivityNotifyer(SimpleNotifyer.ACTIVITY_INBOX);
						if ( info.getGroupId() != null ){
							no.notifyOne(basea.getNotifyId(),SimpleNotifyer.TITLE, "某人申请加入你的群");
						} else {
							no.notifyOne(basea.getNotifyId(),SimpleNotifyer.TITLE, "某人申请加你为好友");
						}
					}
					return info;
				} else {
					return null;
				}
			}
		});
		return result;
	}

	@Override
	public BaseInfo deleteOneValidate(final ValidateInfo info) {
		BaseInfo result;
		result = DaoImplHelper.doTask(new IDaoHandler<BaseInfo> () {
			@Override
			public BaseInfo handleSession(Session s) {
				s.delete(info);
				return info;
			}
		});
		return result;
	}

	@Override
	public List<ValidateInfo> getMyValidateList(final ValidateInfo info) {
		List<ValidateInfo> result;
		result = DaoImplHelper.doTask(new IDaoHandler<List<ValidateInfo>> () {
			@Override
			public List<ValidateInfo> handleSession(Session s) {
				
				String hql = "FROM ValidateInfo"
						+ " WHERE end_user_id =:uid"
						+ " GROUP BY id";
				Query query = s.createQuery(hql).setParameter("uid", info.getEnd_user_id());
				query.setCacheable(true); 
		         List<ValidateInfo> MyValidateList = query.list();
		         if ( ListUtil.isNotEmpty(MyValidateList) ){
		        	 for ( ValidateInfo i : MyValidateList ){
		        		 IAccountDao iad = new AccountDaoImpl();
		        		 IGroupDao igd = new GroupDaoImpl();
		        		 BaseInfo sa = iad.getAccountInfo(i.getStart_user_id());
		        		 BaseInfo ea =iad.getAccountInfo(i.getEnd_user_id());
		        		 BaseInfo gi = igd.getGroupInfo(i.getGroupId());
		        		 if ( sa instanceof AccountInfo ){
		        			 i.setStartUser((AccountInfo) sa);
		        		 }
		        		 if ( ea instanceof AccountInfo ){
		        			 i.setEndUser((AccountInfo) ea);
		        		 }
		        		 if ( gi instanceof GroupInfo ){
		        			 i.setGroupInfo((GroupInfo) gi);
		        		 }
		        		 i.setContactsList(
		        				 new ContactDaoImpl()
		        				 .getContactInfosByContactString(i.getContact_ids()));
		        	 }
		         }
		         return MyValidateList;
			}
		});
		return result;
	}

	@Override
	public BaseInfo getOneValidate(final Long id) {
		if ( id == null )return GlobalValue.MESSAGES.get(
				GlobalValue.STR_INVALID_REQUEST);
		return DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {
			@Override
			public BaseInfo handleSession(Session s) {
				String hql = "FROM ValidateInfo "  
		        		 + " WHERE id =:id";
		         Query query = s.createQuery(hql).setParameter("id", id);
		         return (ValidateInfo) query.uniqueResult();
			}
		});
	}

}
