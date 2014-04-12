package ccb.jersey.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ccb.scontact.hibernate.dao.impl.AccountDaoImpl;
import ccb.scontact.pojo.AccountInfo;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ErrorInfo;
import ccb.scontact.pojo.GroupInfo;
import ccb.scontact.utils.GlobalValue;

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
			result = adi.createAccount(info);
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
}