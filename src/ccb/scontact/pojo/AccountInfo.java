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

import ccb.scontact.hibernate.dao.IAccountDao;
import ccb.scontact.utils.StringUtil;

@Entity
@Table(name=IAccountDao.TABLE_NAME)
@XmlRootElement
public class AccountInfo extends BaseInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer status,gender;
	Long birthday,createTime,id;
	private String displayName,phoneNumber ,photoUrl,
	email,realName,description,type,password,cookie;

	private List<ContactInfo> contactsList;
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="user_display_name")
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	@Column(name="user_birthday")
	public Long getBirthday() {
		return birthday;
	}
	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}
	
	@Column(name="user_create_time")
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	@Column(name="user_status")
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status 0 normal by default, 1 deleted, 2 frozen
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name="user_gender")
	public Integer getGender() {
		return gender;
	}
	/**
	 * @param gender : 0 unset, 1 female, 2 male, 3 other
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	
	@Column(name="user_phone_number")
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber  A register number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Column(name="user_email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name="user_real_name")
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@Column(name="user_description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="user_type")
	public String getType() {
		return type;
	}
	/**
	 * @param type : "normal","dev","level1","level2"
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="user_password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="user_cookie")
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	@Column(name="user_photo_url")
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	@Transient
	public List<ContactInfo> getContactsList() {
		return contactsList;
	}
	public void setContactsList(List<ContactInfo> mContactsList) {
		this.contactsList = mContactsList;
	}
	@Transient
	public static boolean isValidAccount(AccountInfo info) {
		return info == null ? false : StringUtil.isValidName(info.getDisplayName())
				&& StringUtil.isValidPwd(info.getPassword());
	}
}
