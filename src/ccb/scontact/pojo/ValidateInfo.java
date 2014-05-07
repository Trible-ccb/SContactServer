package ccb.scontact.pojo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import ccb.scontact.hibernate.dao.IGroupValidateDao;

/**
 * @author modify by Trible Chen
 *the class is for holding the message 
 *when creating relationship between user and group 
 *or friendship between user and user 
 */
@Entity
@Table(name=IGroupValidateDao.TABLE_NAME)
@XmlRootElement
public class ValidateInfo extends BaseInfo {

	private Long id;
	private Long start_user_id;
	private String contact_ids;
	private Long groupId;
	private Long end_user_id;
	private String is_group_to_user;
	
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
	
	@Column(name = "start_user_id", nullable = false)
	public Long getStart_user_id() {
		return start_user_id;
	}
	public void setStart_user_id(Long start_user_id) {
		this.start_user_id = start_user_id;
	}
	
	@Column(name = "end_user_id", nullable = false)
	public Long getEnd_user_id() {
		return end_user_id;
	}
	public void setEnd_user_id(Long end_user_id) {
		this.end_user_id = end_user_id;
	}
	
	@Column(name = "is_group_to_user")
	public String getIs_group_to_user() {
		return is_group_to_user;
	}
	public void setIs_group_to_user(String is_group_to_user) {
		this.is_group_to_user = is_group_to_user;
	}
	
	@Column(name = "contact_ids")
	public String getContact_ids() {
		return contact_ids;
	}
	public void setContact_ids(String contact_ids) {
		this.contact_ids = contact_ids;
	}
	
	@Column(name = "group_id")
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	private List<ValidateInfo> GroupValidateInfoList;


	@Transient
	public List<ValidateInfo> getGroupValidateInfoList() {
		return GroupValidateInfoList;
	}
	public void setGroupValidateInfoList(
			List<ValidateInfo> groupValidateInfoList) {
		GroupValidateInfoList = groupValidateInfoList;
	}
}
