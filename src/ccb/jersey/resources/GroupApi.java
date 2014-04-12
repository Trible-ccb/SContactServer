package ccb.jersey.resources;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ccb.scontact.hibernate.dao.IGroupDao;
import ccb.scontact.hibernate.dao.impl.GroupDaoImpl;
import ccb.scontact.pojo.AccountInfo;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ErrorInfo;
import ccb.scontact.pojo.GroupInfo;
import ccb.scontact.pojo.PhoneAndGroupInfo;
import ccb.scontact.utils.GlobalValue;

import com.google.gson.Gson;

@Path("/group")
public class GroupApi {
	
	/** 
	 * @param json{"id":?}
	 * @return
	 */
	@POST
	@Path("/get_group")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo getGroup(
			@QueryParam("json") String json) {
		GroupInfo info ;
		BaseInfo result = null;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_GROUP_ERROR);
		try {
			info = new Gson().fromJson(json, GroupInfo.class);
			IGroupDao icd = new GroupDaoImpl();
			if ( info instanceof GroupInfo ){
				result = icd.getGroupInfo(info.getId());
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
	 * @param json {"displayName":?}
	 * @return
	 */
	@POST
	@Path("/search_group")
	@Produces(MediaType.APPLICATION_JSON)
	public Object searchGroup(
			@QueryParam("json") String json) {
		GroupInfo info ;
		List<GroupInfo> result = null;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_GROUP_ERROR);
		try {
			info = new Gson().fromJson(json, GroupInfo.class);
			IGroupDao icd = new GroupDaoImpl();
			if ( info instanceof GroupInfo ){
				result = icd.searchGroupInfo(info.getDisplayName());
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
	 * @param json must contain owner userId, such as{"ownerId":"?","displayName":"?"}
	 * @param json2  must contain contactIds, such as{"contactIds":"[4,5]"}
	 * @return
	 */
	@POST
	@Path("/add_group")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo addGroup(
			@QueryParam("json") String json
			,@QueryParam("contactIds") String json2
			) {
 		GroupInfo info;
 		BaseInfo  result;
 		IGroupDao icd = new GroupDaoImpl();
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_GROUP_ERROR);
 		try {
			info = new Gson().fromJson(json, GroupInfo.class);
			PhoneAndGroupInfo info2 = new Gson().fromJson(json2, PhoneAndGroupInfo.class);
			result = icd.createGroup(info,info2);
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
	@Path("/delete_group")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo deletegroup(
			@QueryParam("json") String json) {
 		GroupInfo info;
 		BaseInfo  result = null;
 		IGroupDao icd = new GroupDaoImpl();
 		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_GROUP_ERROR);
 		try {
			info = new Gson().fromJson(json, GroupInfo.class);
			if ( info != null ){
				result = icd.deleteGroupInfo(info.getId());
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
	 * @param json
	 * @return
	 */
	@POST
	@Path("/update_group")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo updategroup(
			@QueryParam("json") String json) {
 		GroupInfo info;
 		BaseInfo  result = null;
 		IGroupDao icd = new GroupDaoImpl();
 		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_GROUP_ERROR);
 		try {
			info = new Gson().fromJson(json, GroupInfo.class);
			if ( info != null ){
				result = icd.updateGroup(info);
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
	@Path("/get_all_groups")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getAllgroup() {
 		List<GroupInfo>  result = null;
 		IGroupDao icd = new GroupDaoImpl();
 		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_GROUP_ERROR);
 		try {
			result = icd.getAllGroup();
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
	@Path("/get_user_groups")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getUserGroups(
			@QueryParam("json") String json) {
 		List<GroupInfo>  result = null;
 		IGroupDao icd = new GroupDaoImpl();
 		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_GROUP_ERROR);
 		try {
 			AccountInfo info ;
 			info = new Gson().fromJson(json, AccountInfo.class);
 			
 			if ( info != null ){
 				result = icd.getUserGroup(info.getId());
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