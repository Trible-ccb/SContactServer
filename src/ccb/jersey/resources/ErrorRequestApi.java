package ccb.jersey.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ccb.scontact.filter.RequestFilter;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.utils.GlobalValue;

@Path("/error")
public class ErrorRequestApi {

	@POST
	@Path("/message")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo errorMessage(){
		return GlobalValue.MESSAGES.get(GlobalValue.STR_INVALID_REQUEST);
	}
	
	@POST
	@Path("/enableFilter")
	public void enableFilter(){
		RequestFilter.enableFilter = true;
	}
	
	@POST
	@Path("/disableFilter")
	public void disableFilter(){
		RequestFilter.enableFilter = false;
	}
}
