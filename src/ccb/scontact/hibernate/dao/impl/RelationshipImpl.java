package ccb.scontact.hibernate.dao.impl;

import org.hibernate.Session;

import com.sun.xml.bind.v2.TODO;

import ccb.scontact.hibernate.dao.IGroupDao;
import ccb.scontact.hibernate.dao.IGroupValidateDao;
import ccb.scontact.hibernate.dao.IPhoneAndGroupDao;
import ccb.scontact.hibernate.dao.IRelationshipDao;
import ccb.scontact.hibernate.dao.impl.DaoImplHelper.IDaoHandler;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.GroupInfo;
import ccb.scontact.pojo.GroupValidateInfo;
import ccb.scontact.pojo.PhoneAndGroupInfo;
import ccb.scontact.utils.GlobalValue;

public class RelationshipImpl implements IRelationshipDao {

	@Override
	public BaseInfo addRelationship(final GroupValidateInfo info) {
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
							if(info.getIs_group_to_user().endsWith("1"))
								phoneinfo.setUserId(info.getEnd_user_id());
							else
								phoneinfo.setUserId(info.getStart_user_id());
							return ipgd.joinOrUpdateInGroup(phoneinfo);
						}
					}
				} else {//relationship between user and user;
//					TODO 
				}
				return null;
			}
		});
		return result;
	}

	@Override
	public BaseInfo removeRelationship(GroupValidateInfo info) {
		return null;
	}

}
