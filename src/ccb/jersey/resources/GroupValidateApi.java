package ccb.jersey.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import ccb.scontact.hibernate.dao.IContactDao;
import ccb.scontact.hibernate.dao.IGroupValidateDao;
import ccb.scontact.hibernate.dao.IPhoneAndGroupDao;
import ccb.scontact.hibernate.dao.IRelationshipDao;
import ccb.scontact.hibernate.dao.impl.ContactDaoImpl;
import ccb.scontact.hibernate.dao.impl.GroupValidateImpl;
import ccb.scontact.hibernate.dao.impl.PhoneAndGroupDaoImpl;
import ccb.scontact.hibernate.dao.impl.RelationshipImpl;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ContactInfo;
import ccb.scontact.pojo.ErrorInfo;
import ccb.scontact.pojo.GroupValidateInfo;
import ccb.scontact.pojo.PhoneAndGroupInfo;
import ccb.scontact.utils.GlobalValue;

@Path("/group_validate")
public class GroupValidateApi {

	/**
	 * @param json {start_user_id,contact_ids,groupId,end_user_id,is_group_to_user}
	 * @return
	 */
	@POST
	@Path("/addOneValidateMessage")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo addOneValidateMessage(@QueryParam("json") String json)
	{
		GroupValidateInfo info;
		BaseInfo result = null;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_GROUP_VALIDATE_ERROR);
		try {
			info = new Gson().fromJson(json, GroupValidateInfo.class);
			IGroupValidateDao igvd = new GroupValidateImpl();
			if ( info instanceof GroupValidateInfo ){
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
		GroupValidateInfo info;
		BaseInfo result = null;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_GROUP_VALIDATE_ERROR);
		try {
			info = new Gson().fromJson(json, GroupValidateInfo.class);
			IRelationshipDao igvd = new RelationshipImpl();
			if ( info instanceof GroupValidateInfo ){
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

	/**
	 * @param json {end_user_id}
	 * @return
	 */
	@POST
	@Path("/getMyValidateList")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getMyValidateList (@QueryParam("json") String json){
		
		GroupValidateInfo info;
		Object result = null;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_GROUP_VALIDATE_ERROR);
		
		try {
			info = new Gson().fromJson(json, GroupValidateInfo.class);
			IGroupValidateDao igvd = new GroupValidateImpl();
			if ( info instanceof GroupValidateInfo ){
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
	 * @return
	 */
	@POST
	@Path("/AcceptOneValidate")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo AcceptOneValidate (@QueryParam("json") String json){
		GroupValidateInfo info;
		
		PhoneAndGroupInfo Phoneinfo=new PhoneAndGroupInfo();
		BaseInfo result = null;
	
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_GROUP_VALIDATE_ERROR);
		
		try {
			info = new Gson().fromJson(json, GroupValidateInfo.class);
			IGroupValidateDao igvd = new GroupValidateImpl();
			IPhoneAndGroupDao ipgd = new PhoneAndGroupDaoImpl();
			
			if ( info instanceof GroupValidateInfo ){
				
				Phoneinfo.setContactIds(info.getContact_ids());
				Phoneinfo.setGroupId(info.getGroupId());
				
				if(info.getIs_group_to_user().endsWith("1"))
					Phoneinfo.setUserId(info.getEnd_user_id());
				else
					Phoneinfo.setUserId(info.getStart_user_id());
				
				result = ipgd.joinOrUpdateInGroup(Phoneinfo);
				
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
		GroupValidateInfo info;
		BaseInfo result = null;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_GROUP_VALIDATE_ERROR);
		
		try {
			info = new Gson().fromJson(json, GroupValidateInfo.class);
			IGroupValidateDao igvd = new GroupValidateImpl();
			if ( info instanceof GroupValidateInfo ){
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
