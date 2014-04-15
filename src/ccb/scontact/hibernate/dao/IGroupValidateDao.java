package ccb.scontact.hibernate.dao;

import java.util.List;

import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.GroupValidateInfo;

public interface IGroupValidateDao {

	String TABLE_NAME = "group_validate";
	BaseInfo addOneValidate(GroupValidateInfo info);
	BaseInfo deleteOneValidate(GroupValidateInfo info);
	List<GroupValidateInfo> getMyValidateList(GroupValidateInfo info);
}
