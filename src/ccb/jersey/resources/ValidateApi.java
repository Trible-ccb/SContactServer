package ccb.jersey.resources;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ccb.scontact.hibernate.dao.IGroupValidateDao;
import ccb.scontact.hibernate.dao.IPhoneAndGroupDao;
import ccb.scontact.hibernate.dao.IRelationshipDao;
import ccb.scontact.hibernate.dao.IUserRelationshipDao;
import ccb.scontact.hibernate.dao.impl.GroupValidateImpl;
import ccb.scontact.hibernate.dao.impl.PhoneAndGroupDaoImpl;
import ccb.scontact.hibernate.dao.impl.RelationshipImpl;
import ccb.scontact.hibernate.dao.impl.UserRelationshipImpl;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ContactInfo;
import ccb.scontact.pojo.ErrorInfo;
import ccb.scontact.pojo.UserRelationshipInfo;
import ccb.scontact.pojo.ValidateInfo;
import ccb.scontact.pojo.PhoneAndGroupInfo;
import ccb.scontact.utils.GlobalValue;
import ccb.scontact.utils.ListUtil;

import com.google.gson.Gson;

@Path("/group_validate")
public class ValidateApi {

	/**
	 * @param json {start_user_id,contact_ids,groupId,end_user_id,is_group_to_user}
	 * @return
	 */
	@Deprecated
	@POST
	@Path("/addOneValidateMessage")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo addOneValidateMessage(@QueryParam("json") String json)
	{
		ValidateInfo info;
		BaseInfo result = null;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_VALIDATE_ERROR);
		try {
			info = new Gson().fromJson(json, ValidateInfo.class);
			IGroupValidateDao igvd = new GroupValidateImpl();
			if ( info instanceof ValidateInfo ){
				result = igvd.addOneValidate(info);
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
	
	@POST
	@Path("/addRelationship")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo addRelationship(@QueryParam("json") String json)
	{
		ValidateInfo info;
		BaseInfo result = null;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_VALIDATE_ERROR);
		try {
			info = new Gson().fromJson(json, ValidateInfo.class);
			IRelationshipDao igvd = new RelationshipImpl();
			if ( info instanceof ValidateInfo ){
				result = igvd.addRelationship(info);
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

	@POST
	@Path("/removeRelationship")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo removeRelationship(@QueryParam("json") String json)
	{
		ValidateInfo info;
		BaseInfo result = null;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_VALIDATE_ERROR);
		try {
			info = new Gson().fromJson(json, ValidateInfo.class);
			IRelationshipDao igvd = new RelationshipImpl();
			if ( info instanceof ValidateInfo ){
				result = igvd.removeRelationship(info);
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
	 * @param json {end_user_id}
	 * @return
	 */
	@POST
	@Path("/getMyValidateList")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getMyValidateList (@QueryParam("json") String json){
		
		ValidateInfo info;
		Object result = null;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_VALIDATE_ERROR);
		
		try {
			info = new Gson().fromJson(json, ValidateInfo.class);
			IGroupValidateDao igvd = new GroupValidateImpl();
			if ( info instanceof ValidateInfo ){
				result = igvd.getMyValidateList(info);
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
	 * @param json  {start_user_id,contact_ids,groupId,end_user_id,is_group_to_user}
	 * @param optionContactids if accept a friend,this must not be null
	 * @return
	 */
	@POST
	@Path("/AcceptOneValidate")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo AcceptOneValidate (
			@QueryParam("json") String json,
			@QueryParam("optionContactids") String optionContactids){
		ValidateInfo info;
		
		PhoneAndGroupInfo Phoneinfo=new PhoneAndGroupInfo();
		BaseInfo result = null;
	
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_VALIDATE_ERROR);
		
		try {
			info = new Gson().fromJson(json, ValidateInfo.class);
			IGroupValidateDao igvd = new GroupValidateImpl();
			IPhoneAndGroupDao ipgd = new PhoneAndGroupDaoImpl();
			
			if ( info instanceof ValidateInfo ){
				if ( info.getGroupId() != null ){//group and user
					Phoneinfo.setContactIds(info.getContact_ids());
					Phoneinfo.setGroupId(info.getGroupId());
					if(info.getIs_group_to_user().endsWith("1")){
						Phoneinfo.setUserId(info.getEnd_user_id());
					} else {
						Phoneinfo.setUserId(info.getStart_user_id());
					}
					result = ipgd.joinOrUpdateInGroup(Phoneinfo);
				} else {// user and user
					if ( ListUtil.isEmpty(ContactInfo.stringToList(optionContactids)) ){
						return msg;
					}
					UserRelationshipInfo uri = new UserRelationshipInfo();
					uri.setContactIds(info.getContact_ids());
					uri.setFollowUserId(info.getEnd_user_id());
					uri.setUserId(info.getStart_user_id());
					
					UserRelationshipInfo uri2 = new UserRelationshipInfo();
					uri2.setContactIds(optionContactids);
					uri2.setFollowUserId(info.getStart_user_id());
					uri2.setUserId(info.getEnd_user_id());
					IUserRelationshipDao iurd = new UserRelationshipImpl();
					result = iurd.addOrUpdateRelationshipBetweenUser(uri);
					result = iurd.addOrUpdateRelationshipBetweenUser(uri2);
				}
				igvd.deleteOneValidate(info);
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
	 * @param json  {id}
	 * @return
	 */
	@POST
	@Path("/NotAcceptOneValidate")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo NotAcceptOneValidate (@QueryParam("json") String json){
		ValidateInfo info;
		BaseInfo result = null;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_VALIDATE_ERROR);
		
		try {
			info = new Gson().fromJson(json, ValidateInfo.class);
			IGroupValidateDao igvd = new GroupValidateImpl();
			if ( info instanceof ValidateInfo ){
				result = igvd.deleteOneValidate(info);
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
}
