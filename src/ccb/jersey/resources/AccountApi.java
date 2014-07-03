package ccb.jersey.resources;

import java.util.List;

import javax.jms.Session;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

import ccb.scontact.hibernate.dao.IAccountDao;
import ccb.scontact.hibernate.dao.IGroupDao;
import ccb.scontact.hibernate.dao.IUserRelationshipDao;
import ccb.scontact.hibernate.dao.impl.AccountDaoImpl;
import ccb.scontact.hibernate.dao.impl.GroupDaoImpl;
import ccb.scontact.hibernate.dao.impl.UserRelationshipImpl;
import ccb.scontact.pojo.AccountInfo;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ContactInfo;
import ccb.scontact.pojo.ErrorInfo;
import ccb.scontact.pojo.GroupInfo;
import ccb.scontact.pojo.UserRelationshipInfo;
import ccb.scontact.utils.GlobalValue;
import ccb.scontact.utils.StringUtil;

import com.google.gson.Gson;

/**
 * @author Trible Chen
 *Here is a set of Interface of Controlling Account
 */
@Path("/account")
public class AccountApi {
	
	/**
	 * @param loginJson {displayName,password}
	 * @return
	 */
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo login(
			@QueryParam("json") String loginJson) {
		AccountInfo info ;
		BaseInfo result;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_LOGIN_ERROR);
		try {
			info = new Gson().fromJson(loginJson, AccountInfo.class);
			AccountDaoImpl adi = new AccountDaoImpl();
			result = adi.loginAccount(info);
			if ( result != null ){
				
				return result;
			}
		} catch (Exception e) {
			msg.setMessgae(e.getMessage());
			return msg;
		}
		return msg;
	}

	@POST
	@Path("/loginWithThirdParty")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo loginWithThirdParty(
			@QueryParam("json") String loginJson) {
		AccountInfo info ;
		BaseInfo result;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_LOGIN_ERROR);
		try {
			info = new Gson().fromJson(loginJson, AccountInfo.class);
			AccountDaoImpl adi = new AccountDaoImpl();
			result = adi.loginWithThirdpartyAccount(info);
			if ( result != null ){
				return result;
			}
		} catch (Exception e) {
			msg.setMessgae(e.getMessage());
			return msg;
		}
		return msg;
	}
	
	/**
	 * @param registerJson contain attr:displayName and password
	 * @return
	 */
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo register(
			@QueryParam("json") String registerJson
			) {
 		AccountInfo info;
 		BaseInfo  result;
 		AccountDaoImpl adi = new AccountDaoImpl();
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_REGISTER_ERROR);
 		try {
			info = new Gson().fromJson(registerJson, AccountInfo.class);
			if ( info != null ){
				info.setStatus(GlobalValue.USTATUS_NORMAL);
			}
			result = adi.createAccount(info,false);
			if ( result != null ){
				return result;
			}
		} catch (Exception e) {
			msg.setMessgae(e.getMessage());
			return msg;
		}
 		return msg;
	}
	
	/**
	 * @param json {"id" : ?}
	 * @return
	 */
	@POST
	@Path("/get_account")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo getAccount(
			@QueryParam("json") String json) {
 		AccountInfo info;
 		BaseInfo  result = null;
 		AccountDaoImpl adi = new AccountDaoImpl();
 		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_GET_ACCOUNT_ERROR);
 		try {
			info = new Gson().fromJson(json, AccountInfo.class);
			if ( info != null ){
				result = adi.getAccountInfo(info.getId());
			}
			if ( result == null ){
				return msg;
			} else {
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMessgae(e.getMessage());
			return msg;
		}
	}
	
	/**
	 * @param json {"id" : ?}
	 * @return
	 */
	@POST
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo deleteAccount(
			@QueryParam("json") String json) {
 		AccountInfo info;
 		BaseInfo  result = null;
 		AccountDaoImpl adi = new AccountDaoImpl();
 		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_DELETE_ERROR);
 		try {
			info = new Gson().fromJson(json, AccountInfo.class);
			if ( info != null ){
				result = adi.deleteAccountInfo(info.getId());
			}
			if ( result == null ){
				return msg;
			} else {
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMessgae(e.getMessage());
			return msg;
		}
	}
	
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo updateAccount(
			@QueryParam("json") String json) {
 		AccountInfo info;
 		BaseInfo  result = null;
 		AccountDaoImpl adi = new AccountDaoImpl();
 		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_ACCOUNT_INVALID);
 		try {
			info = new Gson().fromJson(json, AccountInfo.class);
			if ( info != null ){
				result = adi.updateAccount(info);
			}
			if ( result == null ){
				return msg;
			} else {
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMessgae(e.getMessage());
			return msg;
		}
	}
	
	@POST
	@Path("/checkIsFriend")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo checkIsFriend(
			@QueryParam("json") String json) {
		UserRelationshipInfo info;
 		BaseInfo  result = null;
 		IUserRelationshipDao iurd = new UserRelationshipImpl();
 		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_DELETE_ERROR);
 		try {
			info = new Gson().fromJson(json, UserRelationshipInfo.class);
			result = iurd.checkUserRelationshipInfo(info);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMessgae(e.getMessage());
			return msg;
		}
	}
	
	/**
	 * @param json {"id":?}
	 * @return
	 */
	@POST
	@Path("/get_friends_of_user")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getFriendsOfUser(
			@QueryParam("json") String json) {
 		AccountDaoImpl adi = new AccountDaoImpl();
 		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_INVALID_REQUEST);
 		try {
			AccountInfo info = new Gson().fromJson(json, AccountInfo.class);
			if ( info != null && info.getId() != null ){
				return adi.getFriendsOfUser(info.getId());
			} else {
				return msg;
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMessgae(e.getMessage());
			return msg;
		}
	}
	
	/**
	 * @param json {"id":?}
	 * @return
	 */
	@POST
	@Path("/get_accounts_of_group")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getAccountsOfGroup(
			@QueryParam("json") String json) {
 		AccountDaoImpl adi = new AccountDaoImpl();
 		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_INVALID_REQUEST);
 		try {
			GroupInfo info = new Gson().fromJson(json, GroupInfo.class);
			if ( info != null && info.getId() != null ){
				return adi.getAccountsOfGroup(info.getId());
			} else {
				return msg;
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMessgae(e.getMessage());
			return msg;
		}
	}
	
	/**
	 * @param json {"id":?}
	 * @return
	 */
	@POST
	@Path("/search_account_infos")
	@Produces(MediaType.APPLICATION_JSON)
	public Object searchAccountInfo(
			@QueryParam("query") String query) {
 		AccountDaoImpl adi = new AccountDaoImpl();
 		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_INVALID_REQUEST);
 		try {
			if ( query != null ){
				//构造查询条件
				AccountInfo info = new AccountInfo();
				info.setDisplayName(query);
				info.setRealName(query);
				return adi.searchAccountInfo(info, true);
			} else {
				return msg;
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMessgae(e.getMessage());
			return msg;
		}
	}
	
	/**
	 * @param json ContactInfo
	 * @return get the friends which have the contact
	 */
	@POST
	@Path("/get_contact_friends")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getFriendsContainContact(
			@QueryParam("json") String json) {
 		List<AccountInfo>  result = null;
 		IAccountDao iad = new AccountDaoImpl();
 		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_GROUP_ERROR);
 		try {
 			ContactInfo info ;
 			info = new Gson().fromJson(json, ContactInfo.class);
 			if ( info != null ){
 				result = iad.getFriendsOfUserContactId(info.getId());
 			}
			if ( result == null ){
				return msg;
			} else {
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMessgae(e.getMessage());
			return msg;
		}
	}
}