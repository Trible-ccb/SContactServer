package ccb.scontact.hibernate.dao;

import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ValidateInfo;

public interface IRelationshipDao {

	/**
	 * @param info
	 * groupId if not null,the user of start_user_id joins in the group,
	 * otherwise the user of start_user_id make friend with the user of end_user_id
	 * @return
	 */
	BaseInfo addRelationship(ValidateInfo info);
	BaseInfo updateRelationship(ValidateInfo info);
	BaseInfo removeRelationship(ValidateInfo info);
	/**
	 * @param info 
	 * To modify the group's owner
	 * start_user_id is the pre owner
	 * groupId the group's id
	 * end_user_id is next owner
	 * @return
	 */
//	BaseInfo changeRelationship(ValidateInfo info);
	
}
