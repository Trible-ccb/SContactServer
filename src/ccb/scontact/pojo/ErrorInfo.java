package ccb.scontact.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorInfo extends BaseInfo implements Serializable {

	private static final long serialVersionUID = 7631954908857734023L;
	int code;
	String messgae;
	
	public ErrorInfo(){};
	public ErrorInfo(int code, String messgae) {
		super();
		this.code = code;
		this.messgae = messgae;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessgae() {
		return messgae;
	}
	public void setMessgae(String messgae) {
		this.messgae = messgae;
	}
	
}
