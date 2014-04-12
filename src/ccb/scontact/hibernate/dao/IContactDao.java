package ccb.scontact.hibernate.dao;

import java.util.List;

import ccb.scontact.pojo.BaseInfo;
import ccb.scontact.pojo.ContactInfo;

public interface IContactDao {

	String TABLE_NAME = "raw_phone_numbers";
	
	BaseInfo addContact(ContactInfo info);
	BaseInfo updateContact(ContactInfo info);
	BaseInfo getContactInfo(Long id);
	BaseInfo deleteContactInfo(Long id);
	List<ContactInfo> searchContactInfo(String query);
	List<ContactInfo> getAllContact();
	List<ContactInfo> getUsersContact(Long uid);
}
