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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ccb.scontact.hibernate.dao.IContactDao;
import ccb.scontact.utils.GlobalValue;
import ccb.scontact.utils.StringUtil;

@Entity
@Table(name=IContactDao.TABLE_NAME)
@XmlRootElement
public class ContactInfo extends BaseInfo implements Serializable{
	private static final long serialVersionUID = 2279560755875633905L;

	private Long id;
	private Long userId;
	private String contact;
	private Integer status;
	private long lastestUsedTime;
	private String type;
	
	@Column(name = "type")
	public String getType() {
		if ( type == null ){
			return GlobalValue.CTYPE_PHONE;
		}
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
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
	
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Column(name="contact_string")
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Column(name="status")
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status : 0 used, 1 deleted.
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name="latest_used_time")
	public long getLastestUsedTime() {
		return lastestUsedTime;
	}
	public void setLastestUsedTime(long lastestUsedTime) {
		this.lastestUsedTime = lastestUsedTime;
	}
	@Transient
	public static boolean isValidContact(ContactInfo info) {
		if ( info == null || info.getContact() == null )return false;
		if ( StringUtil.isValidPhone(info.getContact() ) )return true;
		return false;
	}
	@Transient
	public static List<Long> stringToList(String contactids){
		List<Long> contacts = null;
		TypeToken<List<Long>> tt = new TypeToken<List<Long>>(){};
		try {
			contacts = new Gson().fromJson(contactids, tt.getType());
			return contacts;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
