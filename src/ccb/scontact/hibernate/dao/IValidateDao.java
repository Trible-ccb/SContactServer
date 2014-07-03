package ccb.scontact.hibernate.dao;

import java.util.List;

import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ValidateInfo;

public interface IValidateDao {

	String TABLE_NAME = "group_validate";
	BaseInfo addOneValidate(ValidateInfo info);
	BaseInfo deleteOneValidate(ValidateInfo info);
	
	BaseInfo getOneValidate(Long id);
	
	List<ValidateInfo> getMyValidateList(ValidateInfo info);
}
