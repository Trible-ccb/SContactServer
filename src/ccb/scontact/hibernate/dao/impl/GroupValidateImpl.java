package ccb.scontact.hibernate.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ccb.scontact.hibernate.dao.IGroupValidateDao;
import ccb.scontact.hibernate.dao.impl.DaoImplHelper.IDaoHandler;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ValidateInfo;

public class GroupValidateImpl implements IGroupValidateDao {

	@Override
	public BaseInfo addOneValidate(final ValidateInfo info) {
		BaseInfo result;
		result = DaoImplHelper.doTask(new IDaoHandler<BaseInfo> () {
			@Override
			public BaseInfo handleSession(Session s) {
				Serializable result = s.save(info);
				if(result instanceof Long){
					info.setId((Long) result);
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
						+ " WHERE end_user_id = '" + info.getEnd_user_id()+"'"
						+ " GROUP BY id";
				Query query = s.createQuery(hql);
				query.setCacheable(true); // ���û���    
		         List<ValidateInfo> MyValidateList = query.list();
		         
		         return MyValidateList;
			}
		});
		return result;
	}

}
