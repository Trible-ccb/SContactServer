package ccb.scontact.hibernate.dao;

import java.util.List;

import ccb.scontact.pojo.GroupInfo;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.PhoneAndGroupInfo;

public interface IGroupDao {

	String TABLE_NAME = "groupsinfo";
	
	BaseInfo createGroup(GroupInfo info,PhoneAndGroupInfo info2);
	BaseInfo updateGroup(GroupInfo info);
	BaseInfo getGroupInfo(Long id);
	BaseInfo deleteGroupInfo(Long id);
	List<GroupInfo> searchGroupInfo(String query);
	List<GroupInfo> getAllGroup();
	/**
	 * @param uid
	 * @return the groups had join
	 */
	List<GroupInfo> getUserGroup(Long uid);
	/**
	 * @param uid
	 * @return the groups had created
	 */
	List<GroupInfo> getUserOwnerGroup(Long uid);
	/**
	 * @param cid
	 * @return the groups which have the contact
	 */
	List<GroupInfo> getGroupOfUserContact(Long cid);
}
