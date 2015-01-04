package ccb.jersey.resources;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ccb.scontact.hibernate.dao.IAccountDao;
import ccb.scontact.hibernate.dao.IGroupDao;
import ccb.scontact.hibernate.dao.IPhoneAndGroupDao;
import ccb.scontact.hibernate.dao.IRelationshipDao;
import ccb.scontact.hibernate.dao.IUserRelationshipDao;
import ccb.scontact.hibernate.dao.IValidateDao;
import ccb.scontact.hibernate.dao.impl.AccountDaoImpl;
import ccb.scontact.hibernate.dao.impl.GroupDaoImpl;
import ccb.scontact.hibernate.dao.impl.PhoneAndGroupDaoImpl;
import ccb.scontact.hibernate.dao.impl.RelationshipImpl;
import ccb.scontact.hibernate.dao.impl.UserRelationshipImpl;
import ccb.scontact.hibernate.dao.impl.ValidateImpl;
import ccb.scontact.notify.INotify;
import ccb.scontact.notify.SimpleNotifyer;
import ccb.scontact.pojo.AccountInfo;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ContactInfo;
import ccb.scontact.pojo.ErrorInfo;
import ccb.scontact.pojo.GroupInfo;
import ccb.scontact.pojo.PhoneAndGroupInfo;
import ccb.scontact.pojo.UserRelationshipInfo;
import ccb.scontact.pojo.ValidateInfo;
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
			IValidateDao igvd = new ValidateImpl();
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
	@Path("/testNotify")
	@Produces(MediaType.APPLICATION_JSON)
	public String testNotify()
	{
		INotify no = new SimpleNotifyer();
		return no.notifyOne("AqdvUJRGtWrlnnX-5YY87DqGDRMTa2RNYRSqkRp4HolC","个人消息提示", "你有新通知");
//		no.notifyAll("测试标题", "测试内容");
	}
	
	@POST
	@Path("/testNotifyAll")
	@Produces(MediaType.APPLICATION_JSON)
	public String testNotifyAll()
	{
		INotify no = new SimpleNotifyer();
		return no.notifyAllPerson("测试标题", "测试内容");
	}
	
	@POST
	@Path("/updateRelationship")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo updateRelationship(@QueryParam("json") String json)
	{
		ValidateInfo info;
		BaseInfo result = null;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_VALIDATE_ERROR);
		try {
			info = new Gson().fromJson(json, ValidateInfo.class);
			IRelationshipDao igvd = new RelationshipImpl();
			if ( info instanceof ValidateInfo ){
				result = igvd.updateRelationship(info);
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
			IValidateDao igvd = new ValidateImpl();
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

	@POST
	@Path("/getMyInboxNumber")
	@Produces(MediaType.APPLICATION_JSON)
	public Long getMyInboxNumber (@QueryParam("json") String json){
		Object result = getMyValidateList(json);
		if ( result instanceof List<?> && result != null ){
			return (long) ((List<ValidateInfo>)result).size();
		}
		return null;
	} 
	/**
	 * @param json  {id}
	 * @param optionContactids ,if accept a friend or accept invited from group,this must not be null
	 * @return
	 */
	@POST
	@Path("/AcceptOneValidate")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo AcceptOneValidate (
			@QueryParam("json") String json,
			@QueryParam("optionContactids") String optionContactids){
		ValidateInfo info;
		
		PhoneAndGroupInfo phoneInfo=new PhoneAndGroupInfo();
		BaseInfo result = null;
		IValidateDao igvd = new ValidateImpl();
		IPhoneAndGroupDao ipgd = new PhoneAndGroupDaoImpl();
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_VALIDATE_ERROR);
		
		try {
			info = new Gson().fromJson(json, ValidateInfo.class);
			BaseInfo valid = igvd.getOneValidate(info.getId());
			if ( valid instanceof ValidateInfo ){
				info = (ValidateInfo) valid;
				if ( info.getGroupId() != null ){//group and user
					phoneInfo.setGroupId(info.getGroupId());
					if(info.getIs_group_to_user() == 1){//group owner invite user
						phoneInfo.setUserId(info.getEnd_user_id());
						phoneInfo.setContactIds(optionContactids);
					} else {//user wanna join in group
						phoneInfo.setUserId(info.getStart_user_id());
						phoneInfo.setContactIds(info.getContact_ids());
					}
					result = ipgd.joinOrUpdateInGroup(phoneInfo);
					if ( result instanceof PhoneAndGroupInfo ){
						IAccountDao iad = new AccountDaoImpl();
						BaseInfo user = iad.getAccountInfo(phoneInfo.getUserId());
						IGroupDao igd = new GroupDaoImpl();
						BaseInfo group = igd.getGroupInfo(info.getGroupId());
						if ( user != null && group != null ){
							String gname = ((GroupInfo)group).getDisplayName();
							String usid = ((AccountInfo)user).getNotifyId();
							SimpleNotifyer notifyer = new SimpleNotifyer();
							notifyer.notifyOne(usid, SimpleNotifyer.TITLE, "你加入了"+gname);
						}
					}
					
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
					
					BaseInfo user = new AccountDaoImpl().getAccountInfo(info.getStart_user_id());
					AccountInfo u1 = null;
					BaseInfo user2 = new AccountDaoImpl().getAccountInfo(info.getEnd_user_id());
					AccountInfo u2 = null;
					
					if ( user instanceof AccountInfo ){
						u1 = ((AccountInfo)user);
					}
					if ( user2 instanceof AccountInfo ){
						u2 = ((AccountInfo)user2);
					}
					SimpleNotifyer notifyer = new SimpleNotifyer();
					notifyer.notifyOne(u2.getNotifyId(), SimpleNotifyer.TITLE, "你和" + u1.getDisplayName() +"成为了好友");
					notifyer.notifyOne(u1.getNotifyId(), SimpleNotifyer.TITLE, "你和" + u2.getDisplayName() +"成为了好友");
				}
				if ( result != null ){
					if ( !(result instanceof ErrorInfo) ){
						igvd.deleteOneValidate(info);
					}
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
			IValidateDao igvd = new ValidateImpl();
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
