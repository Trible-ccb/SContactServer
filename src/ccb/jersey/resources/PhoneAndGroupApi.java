package ccb.jersey.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ccb.scontact.hibernate.dao.IPhoneAndGroupDao;
import ccb.scontact.hibernate.dao.impl.PhoneAndGroupDaoImpl;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ErrorInfo;
import ccb.scontact.pojo.PhoneAndGroupInfo;
import ccb.scontact.utils.GlobalValue;

import com.google.gson.Gson;

@Path("/phone_and_group")
public class PhoneAndGroupApi {
	
	/** 
	 * @param json{"groupId":?,"contactIds":"[{"?"},{"?"}]","userId","?"}
	 * @return
	 */
	@POST
	@Path("/join_or_update_in_group")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo joinOrUpdateInGroup(
			@QueryParam("json") String json) {
		PhoneAndGroupInfo info ;
		BaseInfo result = null;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_PHONE_GROUP_ERROR);
		try {
			info = new Gson().fromJson(json, PhoneAndGroupInfo.class);
			IPhoneAndGroupDao icd = new PhoneAndGroupDaoImpl();
			if ( info instanceof PhoneAndGroupInfo ){
				result = icd.joinOrUpdateInGroup(info);
				if ( result != null ){
					return result;
				}
			}
		} catch (Exception e) {
			msg.setMessgae(e.getMessage());
			return msg;
		}
		return msg;
	}

	/** TODO need to modify thie method
	 * @param json {"groupId":?,"userId"}
	 * @return
	 */
	@POST
	@Path("/exit_group")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo exitGroup(
			@QueryParam("json") String json) {
		PhoneAndGroupInfo info ;
		BaseInfo result = null;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_GROUP_ERROR);
		try {
			info = new Gson().fromJson(json, PhoneAndGroupInfo.class);
			IPhoneAndGroupDao icd = new PhoneAndGroupDaoImpl();
			if ( info instanceof PhoneAndGroupInfo ){
				result = icd.exitGroup(info);
				if ( result != null ){
					return result;
				}
			}
		} catch (Exception e) {
			msg.setMessgae(e.getMessage());
			return msg;
		}
		return msg;
	}
	
	/**
	 * @param json {userId:"",groupId:""}
	 * @return
	 */
	@POST
	@Path("/check_phoneandgroupInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo checkPhoneAndGroupInfo(
			@QueryParam("json") String json) {
		PhoneAndGroupInfo info ;
		BaseInfo result = null;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_PHONE_GROUP_ERROR);
		try {
			info = new Gson().fromJson(json, PhoneAndGroupInfo.class);
			IPhoneAndGroupDao icd = new PhoneAndGroupDaoImpl();
			if ( info instanceof PhoneAndGroupInfo ){
				result = icd.checkPhoneAndGroupInfo(info);
				return result;
			}
		} catch (Exception e) {
			msg.setMessgae(e.getMessage());
			return msg;
		}
		return msg;
	}
}