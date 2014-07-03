package ccb.scontact.hibernate.dao;

import java.util.List;

import ccb.scontact.pojo.AccountInfo;
import ccb.scontact.pojo.BaseInfo;

public interface IAccountDao {

	String TABLE_NAME = "usersinfo";
	
	/**
	 * @param info
	 * @param updateIfNeed if the account existed , update it. otherwise return created error 
	 * @return
	 */
	BaseInfo createAccount(AccountInfo info,boolean updateIfNeed);
	BaseInfo updateAccount(AccountInfo info);
	BaseInfo loginAccount(AccountInfo info);
	BaseInfo loginWithThirdpartyAccount(AccountInfo info);
	BaseInfo getAccountInfo(Long id);
	BaseInfo deleteAccountInfo(Long id);
	List<AccountInfo> searchAccountInfo(AccountInfo info,boolean like);
	List<AccountInfo> getAllAccount();
	List<AccountInfo> getAccountsOfGroup(Long gid);
	List<AccountInfo> getFriendsOfUser(Long uid);
	/**
	 * @param cid 
	 * @return the friends who can see the contact
	 */
	List<AccountInfo> getFriendsOfUserContactId(Long cid);
}
