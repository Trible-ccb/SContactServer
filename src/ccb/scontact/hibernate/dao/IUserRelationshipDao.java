package ccb.scontact.hibernate.dao;

import java.util.List;

import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.UserRelationshipInfo;

public interface IUserRelationshipDao {

	String TABLE_NAME = "user_relationship_info";
	
	/**
	 * @param info to add
	 * @return UserRelationshipInfo with id  if success
	 */
	BaseInfo addOrUpdateRelationshipBetweenUser(UserRelationshipInfo info);
	/**
	 * @param info to remove
	 * @return UserRelationshipInfo be removed if success
	 */
	BaseInfo removeRelationshipBetweenUser(UserRelationshipInfo info);
	/**
	 * @param info to check whether exits
	 * @return UserRelationshipInfo  if success
	 */
	BaseInfo checkUserRelationshipInfo(UserRelationshipInfo info);
	/**
	 * @param uid : get the friends list by uid
	 * @return 
	 */
	List<UserRelationshipInfo> getUserRelationshipInfoByUserId(Long uid);
	/**
	 * @param uid
	 * @param fid
	 * @return UserRelationshipInfo identified by uid and fid
	 */
	UserRelationshipInfo getUserRelationshipInfoByUserIdAndGroupId(Long uid,Long fid);
	
}
