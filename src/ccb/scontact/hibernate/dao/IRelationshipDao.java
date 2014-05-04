package ccb.scontact.hibernate.dao;

import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.GroupValidateInfo;

public interface IRelationshipDao {

	BaseInfo addRelationship(GroupValidateInfo info);
	BaseInfo removeRelationship(GroupValidateInfo info); 
	
}
