package ccb.jersey.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ccb.scontact.pojo.BaseInfo;

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
		return null;
	}


	/**
	 * @param json {end_user_id}
	 * @return
	 */
	@POST
	@Path("/getMyValidateList")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getMyValidateList (@QueryParam("json") String json){
		return null;
	}


	/**
	 * @param json  {start_user_id,contact_ids,groupId,end_user_id,is_group_to_user}
	 * @return
	 */
	@POST
	@Path("/AcceptOneValidate")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo AcceptOneValidate (@QueryParam("json") String json){
		return null;
	}

	
	/**
	 * @param json  {id}
	 * @return
	 */
	@POST
	@Path("/NotAcceptOneValidate")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo NotAcceptOneValidate (@QueryParam("json") String json){
		return null;
	}
}
