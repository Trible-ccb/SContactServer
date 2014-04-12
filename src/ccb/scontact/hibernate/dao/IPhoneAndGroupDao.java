package ccb.scontact.hibernate.dao;

import java.util.List;

import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.PhoneAndGroupInfo;

public interface IPhoneAndGroupDao {

	String TABLE_NAME = "phone_group_info";
	
	BaseInfo joinOrUpdateInGroup(PhoneAndGroupInfo info);
	BaseInfo exitGroup(PhoneAndGroupInfo info);
	BaseInfo checkPhoneAndGroupInfo(PhoneAndGroupInfo info);
	List<PhoneAndGroupInfo> getPhoneAndGroupInfoByGroupId(Long gid);
	PhoneAndGroupInfo getPhoneAndGroupInfoByUserIdAndGroupId(Long uid,Long gid);
	
}
