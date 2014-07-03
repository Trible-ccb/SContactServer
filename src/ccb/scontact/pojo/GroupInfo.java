package ccb.scontact.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import ccb.scontact.hibernate.dao.IGroupDao;
import ccb.scontact.utils.StringUtil;

@Entity
@Table(name=IGroupDao.TABLE_NAME)
@XmlRootElement
public class GroupInfo extends BaseInfo implements Serializable{

	private static final long serialVersionUID = 5992369539881658675L;
	private Integer status,capacity,identify;
	private int groupMembers;

	private Long createTime,updateTime,id,ownerId;
	private String displayName,description,type;

	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "group_id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="group_owner_user_id")
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	
	@Column(name="group_status")
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status : 0 used,1,deleted,2 frozen
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name="group_capacity")
	public Integer getCapacity() {
		return capacity;
	}
	/**
	 * @param capacity depend on the type
	 */
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	
	@Column(name="group_display_name")
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	@Column(name="group_description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="group_type")
	public String getType() {
		return type;
	}
	/**
	 * @param type normal,dev,level1,leve2
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="group_create_time")
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="group_update_time")
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name="group_identify")
	public Integer getIdentify() {
		return identify;
	}
	@Transient
	public int getGroupMembers() {
		return groupMembers;
	}
	public void setGroupMembers(int groupMembers) {
		this.groupMembers = groupMembers;
	}
	/**
	 * @param identify : 0 or null no identify ,1 need identify msg when user join in 
	 */
	public void setIdentify(Integer identify) {
		this.identify = identify;
	}
	@Transient
	public static boolean isValidGroup(GroupInfo info) {
		return info == null ? false : StringUtil.isValidName(info.getDisplayName())
				&& info.getOwnerId() != null;
	}
}
