package ccb.scontact.hibernate.dao;

import java.util.List;

import ccb.scontact.pojo.AccountInfo;
import ccb.scontact.pojo.BaseInfo;

public interface IAccountDao {

	String TABLE_NAME = "usersinfo";
	
	BaseInfo createAccount(AccountInfo info);
	BaseInfo updateAccount(AccountInfo info);
	BaseInfo loginAccount(AccountInfo info);
	BaseInfo getAccountInfo(Long id);
	BaseInfo deleteAccountInfo(Long id);
	List<AccountInfo> searchAccountInfo(String query,boolean like);
	List<AccountInfo> getAllAccount();
	List<AccountInfo> getAccountsOfGroup(Long gid);
	List<AccountInfo> getFriendsOfUser(Long uid);
}
