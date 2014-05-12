package ccb.scontact.hibernate.dao.impl;

import org.hibernate.Session;

import ccb.scontact.hibernate.dao.IGroupDao;
import ccb.scontact.hibernate.dao.IGroupValidateDao;
import ccb.scontact.hibernate.dao.IPhoneAndGroupDao;
import ccb.scontact.hibernate.dao.IRelationshipDao;
import ccb.scontact.hibernate.dao.IUserRelationshipDao;
import ccb.scontact.hibernate.dao.impl.DaoImplHelper.IDaoHandler;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.GroupInfo;
import ccb.scontact.pojo.PhoneAndGroupInfo;
import ccb.scontact.pojo.UserRelationshipInfo;
import ccb.scontact.pojo.ValidateInfo;
import ccb.scontact.utils.GlobalValue;

public class RelationshipImpl implements IRelationshipDao {

	@Override
	public BaseInfo addRelationship(final ValidateInfo info) {
		if ( info == null )return null;
		
		BaseInfo result  = null;
		result = DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {

			@Override
			public BaseInfo handleSession(Session s) {
				if ( info.getGroupId() != null ){//relationship between group and user;
					IGroupDao igd = new GroupDaoImpl();
					BaseInfo ginfo = igd.getGroupInfo(info.getGroupId());
					if ( ginfo instanceof GroupInfo ){
						GroupInfo tmp = (GroupInfo) ginfo;
				 		if ( GlobalValue.GIDENTIFY_NEEDED.equals(tmp.getIdentify()) ){
							IGroupValidateDao igvd = new GroupValidateImpl();
							return igvd.addOneValidate(info);
						} else {
							IPhoneAndGroupDao ipgd = new PhoneAndGroupDaoImpl();
							PhoneAndGroupInfo phoneinfo=new PhoneAndGroupInfo();
							phoneinfo.setContactIds(info.getContact_ids());
							phoneinfo.setGroupId(info.getGroupId());
							if(info.getIs_group_to_user().endsWith("1")){
								phoneinfo.setUserId(info.getEnd_user_id());
							} else {
								phoneinfo.setUserId(info.getStart_user_id());
							}
							ipgd.joinOrUpdateInGroup(phoneinfo);
							if ( info.getId() == null ){
								info.setId(0L);//actually info is not exist,the id only is a success flag
							}
							return info;
						}
					}
				} else {//relationship between user and user;
					IGroupValidateDao igvd = new GroupValidateImpl();
					return igvd.addOneValidate(info);
				}
				return null;
			}
		});
		return result;
	}

	@Override
	public BaseInfo removeRelationship(final ValidateInfo info) {
		if ( info == null )return null;
		return DaoImplHelper.doTask(new IDaoHandler<BaseInfo>() {
			@Override
			public BaseInfo handleSession(Session s) {
				IUserRelationshipDao iurd = new UserRelationshipImpl();
				UserRelationshipInfo r1 = new UserRelationshipInfo();
				r1.setFollowUserId(info.getEnd_user_id());
				r1.setUserId(info.getStart_user_id());
				iurd.removeRelationshipBetweenUser(r1);
				UserRelationshipInfo r2 = new UserRelationshipInfo();
				r2.setFollowUserId(info.getStart_user_id());
				r2.setUserId(info.getEnd_user_id());
				return iurd.removeRelationshipBetweenUser(r2);
			}
		});
	}

	@Override
	public BaseInfo changeRelationship(ValidateInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

}
