package ccb.scontact.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import ccb.scontact.hibernate.dao.IPhoneAndGroupDao;
import ccb.scontact.utils.Bog;

/**
 * @author Trible Chen
 * this class is the middle mapping for phone numbers and groups
 * 
 */
@Entity
@Table(name=IPhoneAndGroupDao.TABLE_NAME)
@XmlRootElement
public class PhoneAndGroupInfo extends BaseInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long groupId;
	private Long userId;
	private String contactIds;
	
//	@Transient
//	List<Long> contactIds;
	

	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "group_id")
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	@Column(name = "contact_ids")
	public String getContactIds() {
		return contactIds;
	}
	public void setContactIds(String contactIds) {
		this.contactIds = contactIds;
	}
	
	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Transient
	public static boolean isValid(final PhoneAndGroupInfo info) {
		return info == null ? false :  info.getGroupId() != null
				&& info.getUserId() != null 
				&& isValidContactIds(info.getContactIds()) ;
	}
	public static boolean isValidContactIds(String ids){
		return ids == null ? false : ids.matches("^\\[[0-9\\,]*\\]$"); 
	}
	public static void main(String[] arg){
		PhoneAndGroupInfo test = new PhoneAndGroupInfo();
		test.setContactIds("[63]");
		test.setGroupId(1L);
		test.setUserId(1L);
		Bog.v(test + " is valid = " + isValid(test) );
	}
}
