package ccb.scontact.hibernate.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ccb.scontact.hibernate.dao.IGroupValidateDao;
import ccb.scontact.hibernate.dao.impl.DaoImplHelper.IDaoHandler;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.GroupValidateInfo;

public class GroupValidateImpl implements IGroupValidateDao {

	@Override
	public BaseInfo addOneValidate(final GroupValidateInfo info) {
		BaseInfo result;
		result = DaoImplHelper.doTask(new IDaoHandler<BaseInfo> () {
			@Override
			public BaseInfo handleSession(Session s) {
				Serializable result = s.save(info);
				if(result instanceof Long)
					info.setId((Long) result);
				return info;
			}
		});
		return result;
	}

	@Override
	public BaseInfo deleteOneValidate(final GroupValidateInfo info) {
		BaseInfo result;
		result = DaoImplHelper.doTask(new IDaoHandler<BaseInfo> () {
			@Override
			public BaseInfo handleSession(Session s) {
				String hql = "delete FROM group_validate"
						+ " WHERE id = '" + info.getId()+"'";
				s.createQuery(hql);
				return info;
			}
		});
		return result;
	}

	@Override
	public List<GroupValidateInfo> getMyValidateList(final GroupValidateInfo info) {
		List<GroupValidateInfo> result;
		result = DaoImplHelper.doTask(new IDaoHandler<List<GroupValidateInfo>> () {
			@Override
			public List<GroupValidateInfo> handleSession(Session s) {
				
				String hql = "FROM group_validate"
						+ " WHERE end_user_id = '" + info.getEnd_user_id()+"'";
				Query query = s.createQuery(hql);
				query.setCacheable(true); // …Ë÷√ª∫¥Ê    
		         List<GroupValidateInfo> MyValidateList = query.list();
		         
		         return MyValidateList;
			}
		});
		return result;
	}

}
