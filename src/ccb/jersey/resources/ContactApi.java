package ccb.jersey.resources;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ccb.scontact.hibernate.dao.IContactDao;
import ccb.scontact.hibernate.dao.impl.ContactDaoImpl;
import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ContactInfo;
import ccb.scontact.pojo.ErrorInfo;
import ccb.scontact.utils.GlobalValue;

import com.google.gson.Gson;

@Path("/contact")
public class ContactApi {
	
	/** 
	 * @param json{"id":?}
	 * @return
	 */
	@POST
	@Path("/get_contact")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo getContact(
			@QueryParam("json") String json) {
		ContactInfo info ;
		BaseInfo result = null;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_CONTACT_ERROR);
		try {
			info = new Gson().fromJson(json, ContactInfo.class);
			IContactDao icd = new ContactDaoImpl();
			if ( info instanceof ContactInfo ){
				result = icd.getContactInfo(info.getId());
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
	 * @param json {"contact":?}
	 * @return
	 */
	@POST
	@Path("/search_contact")
	@Produces(MediaType.APPLICATION_JSON)
	public Object searchContact(
			@QueryParam("json") String json) {
		ContactInfo info ;
		List<ContactInfo> result = null;
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_CONTACT_ERROR);
		try {
			info = new Gson().fromJson(json, ContactInfo.class);
			IContactDao icd = new ContactDaoImpl();
			if ( info instanceof ContactInfo ){
				result = icd.searchContactInfo(info.getContact());
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
	 * @param json {"userId":"?","contact":"?"}
	 * @return
	 */
	@POST
	@Path("/add_contact")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo addContact(
			@QueryParam("json") String json) {
 		ContactInfo info;
 		BaseInfo  result;
 		IContactDao icd = new ContactDaoImpl();
		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_CONTACT_ERROR);
 		try {
			info = new Gson().fromJson(json, ContactInfo.class);
			if ( info != null ){
				info.setId(null);
			}
			result = icd.addContact(info);
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
	@Path("/delete_contact")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo deleteContact(
			@QueryParam("json") String json) {
 		ContactInfo info;
 		BaseInfo  result = null;
 		IContactDao icd = new ContactDaoImpl();
 		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_CONTACT_ERROR);
 		try {
			info = new Gson().fromJson(json, ContactInfo.class);
			if ( info != null ){
				result = icd.deleteContactInfo(info.getId());
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
	@Path("/update_contact")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseInfo updateContact(
			@QueryParam("json") String json) {
 		ContactInfo info;
 		BaseInfo  result = null;
 		IContactDao icd = new ContactDaoImpl();
 		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_CONTACT_ERROR);
 		try {
			info = new Gson().fromJson(json, ContactInfo.class);
			if ( info != null ){
				result = icd.updateContact(info);
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
	@Path("/get_all_contacts")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getAllContact() {
 		List<ContactInfo>  result = null;
 		IContactDao icd = new ContactDaoImpl();
 		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_CONTACT_ERROR);
 		try {
			result = icd.getAllContact();
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
	 * @param json {"userId":?}
	 * @return
	 */
	@POST
	@Path("/get_user_contacts")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getUserContacts(
			@QueryParam("json") String json) {
 		ContactInfo info;
 		List<ContactInfo>  result = null;
 		IContactDao icd = new ContactDaoImpl();
 		ErrorInfo msg = GlobalValue.MESSAGES.get(GlobalValue.STR_CONTACT_ERROR);
 		try {
 			info = new Gson().fromJson(json, ContactInfo.class);
 			if ( info != null ){
 				result = icd.getUsersContact(info.getUserId());
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