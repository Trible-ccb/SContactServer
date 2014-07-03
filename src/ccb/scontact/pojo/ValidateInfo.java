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

import ccb.scontact.hibernate.dao.IValidateDao;

/**
 * @author modify by Trible Chen
 *the class is for holding the message 
 *when creating relationship between user and group 
 *or friendship between user and user 
 */
@Entity
@Table(name=IValidateDao.TABLE_NAME)
@XmlRootElement
public class ValidateInfo extends BaseInfo {

	private Long id;
	private Long start_user_id;
	private String contact_ids;
	private Long groupId;
	private Long end_user_id;
	private int is_group_to_user;
	private long createTime;
	
	private AccountInfo startUser,endUser;
	private GroupInfo groupInfo;
	List<ContactInfo> contactsList;
	
	
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
	
	@Column(name = "start_user_id" )
	public Long getStart_user_id() {
		return start_user_id;
	}
	public void setStart_user_id(Long start_user_id) {
		this.start_user_id = start_user_id;
	}
	
	@Column(name = "end_user_id" )
	public Long getEnd_user_id() {
		return end_user_id;
	}
	public void setEnd_user_id(Long end_user_id) {
		this.end_user_id = end_user_id;
	}
	
	@Column(name = "is_group_to_user")
	public int getIs_group_to_user() {
		return is_group_to_user;
	}
	public void setIs_group_to_user(int is_group_to_user) {
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
	@Column(name = "create_time")
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	@Transient
	public AccountInfo getStartUser() {
		return startUser;
	}
	public void setStartUser(AccountInfo startUser) {
		this.startUser = startUser;
	}
	@Transient
	public AccountInfo getEndUser() {
		return endUser;
	}
	public void setEndUser(AccountInfo endUser) {
		this.endUser = endUser;
	}
	@Transient
	public GroupInfo getGroupInfo() {
		return groupInfo;
	}
	public void setGroupInfo(GroupInfo groupInfo) {
		this.groupInfo = groupInfo;
	}
	
	@Transient
	public List<ContactInfo> getContactsList() {
		return contactsList;
	}
	public void setContactsList(List<ContactInfo> contactsList) {
		this.contactsList = contactsList;
	}
	
	
}
