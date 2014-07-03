package ccb.scontact.utils;

import java.util.HashMap;

import ccb.scontact.pojo.ErrorInfo;

/**
 * @author Trible Chen
 *
 */
public class GlobalValue {

	//do not forget add MESSAGE when adding message info 
	public static final int CODE_SERVER_ERROR = 1000;
	public static final int CODE_TIME_OUT = 1001;
	public static final int CODE_INVALID_REQUEST = 1002;
	public static final String STR_SERVER_ERROR = "服务器错误";
	public static final String STR_TIME_OUT = "超时";
	public static final String STR_INVALID_REQUEST = "无效请求";
	
	//for users
	private static final int CODE_USER_LEVEL = 2000;
	public static final int CODE_LOGIN_ERROR = 2001;
	public static final int CODE_REGISTER_ERROR = 2002;
	public static final int CODE_NAME_UNAVAILABAL= 2003;
	public static final int CODE_ACCOUNT_INVALID= 2004;
	public static final int CODE_DELETE_ERROR = 2005;
	public static final int CODE_GET_ACCOUNT_ERROR = 2006;
	
	public static final String STR_LOGIN_ERROR  = "登录失败";
	public static final String STR_REGISTER_ERROR = "注册失败";
	public static final String STR_NAME_UNAVAILABAL = "用户名被占用";
	public static final String STR_ACCOUNT_INVALID = "无效账户";
	public static final String STR_DELETE_ERROR  = "删除失败";
	public static final String STR_GET_ACCOUNT_ERROR = "获取用户信息失败";
	
	
	// for contacts
	public static final int CODE_CONTACT_ERROR = 3000;
	public static final String STR_CONTACT_ERROR  = "联系方式错误";
	
	// for groups
	public static final int CODE_GROUP_ERROR = 4000;
	public static final int CODE_TO_MAX_ERROR = 4001;
	public static final String STR_GROUP_ERROR  = "群组错误";
	public static final String STR_TO_MAX_ERROR  = "你创建的群组个数已达最大值";
	// for groups and contacts relationship
	public static final int CODE_PHONE_GROUP_ERROR = 5000;
	public static final String STR_PHONE_GROUP_ERROR  = "建立关系失败";
	
	
	// for group validate
		public static final int CODE_VALIDATE_ERROR = 1;
		public static final String STR_VALIDATE_ERROR  = "验证错误";
	
	
	public static HashMap<String, ErrorInfo> MESSAGES = new HashMap<String, ErrorInfo>();
	private static void putMessage(int code,String msg){
		MESSAGES.put(msg, new ErrorInfo(code, msg));
	}
	static{
		putMessage(CODE_VALIDATE_ERROR,STR_VALIDATE_ERROR);
		
		
		putMessage(CODE_INVALID_REQUEST, STR_INVALID_REQUEST);
		putMessage(CODE_LOGIN_ERROR, STR_LOGIN_ERROR);
		putMessage(CODE_REGISTER_ERROR,STR_REGISTER_ERROR);
		putMessage(CODE_SERVER_ERROR, STR_SERVER_ERROR);
		putMessage(CODE_TIME_OUT, STR_TIME_OUT);
		putMessage(CODE_NAME_UNAVAILABAL, STR_NAME_UNAVAILABAL);
		putMessage(CODE_ACCOUNT_INVALID, STR_ACCOUNT_INVALID);
		putMessage(CODE_DELETE_ERROR, STR_DELETE_ERROR);
		putMessage(CODE_GET_ACCOUNT_ERROR, STR_GET_ACCOUNT_ERROR);
		putMessage(CODE_CONTACT_ERROR, STR_CONTACT_ERROR);
		putMessage(CODE_GROUP_ERROR, STR_GROUP_ERROR);
		putMessage(CODE_TO_MAX_ERROR, STR_TO_MAX_ERROR);
		putMessage(CODE_PHONE_GROUP_ERROR, STR_PHONE_GROUP_ERROR);
	}
	
	/** ===User Static Value Start=== */
//	User Type
	public static final String UTYPE_NORMAL = "normal";
	public static final String UTYPE_DEV = "dev";
	public static final String UTYPE_LEVEL1 = "level1";
	public static final String UTYPE_LEVEL2 = "level2";
	public static final String UTYPE_NULL = null;
	
//	User status 0 normal by default, 1 deleted, 2 frozen
	public static final Integer USTATUS_NORMAL = 0;
	public static final Integer USTATUS_DELETED = 1;
	public static final Integer USTATUS_FROZEN = 2;
	public static final Integer USTATUS_NULL = null;
	
//	gender : 0 unset, 1 female, 2 male, 3 other
	public static final Integer UGENDER_UNSET = 0;
	public static final Integer UGENDER_FEMALE = 1;
	public static final Integer UGENDER_MALE = 2;
	public static final Integer UGENDER_OTHER = 3;
	public static final Integer UGENDER_NULL = null;
	/** ===User Static Value End=== */
	
	/** ===Contact Static Value Start=== */
//	status : 0 used, 1 deleted.
	public static final Integer CSTATUS_USED = 0;
	public static final Integer CSTATUS_DELETED = 1;
	public static final Integer CSTATUS_UNUSED = 2;
	public static final Integer CSTATUS_NULL = CSTATUS_USED;
	
//	contact types values;
	public static final String CTYPE_PHONE = "Phone";
	public static final String CTYPE_EMAIL = "Email";
	public static final String CTYPE_IM = "IM";
	public static final String CTYPE_QQ = "QQ";
	public static final String CTYPE_WEIXIN = "WeiXin";
	public static final String CTYPE_WEIBO = "WeiBo";
	public static final String CTYPE_ZUOJI = "Home Phone";
	public static final String CTYPE_FB = "Facebook";
	public static final String CTYPE_TWITTER = "Twitter";
	public static final String CTYPE_WHATAPP = "WhatsApp";
	
	public static String[] CONTACT_TYPES = {
		CTYPE_PHONE,CTYPE_EMAIL,CTYPE_QQ,
		CTYPE_WEIXIN,CTYPE_WEIBO,CTYPE_ZUOJI,CTYPE_FB,
		CTYPE_TWITTER,CTYPE_WHATAPP};
	/** ===Contact Static Value End=== */
	
	/** ===Group Static Value Start=== */
//	Group Type
	public static final String GTYPE_NORMAL = "normal";
	public static final String GTYPE_DEV = "dev";
	public static final String GTYPE_LEVE1 = "level1";
	public static final String GTYPE_LEVEL2 = "level2";
	public static final String GTYPE_NULL = UTYPE_NORMAL;
	
//	status : 0 used, 1 deleted.2 frozen
	public static final Integer GSTATUS_USED = 0;
	public static final Integer GSTATUS_DELETED = 1;
	public static final Integer GSTATUS_FROZEN = 2;
	public static final Integer GSTATUS_NULL = null;
	
//	identify : 0 or null no identify ,1 need identify msg when user join in 
	public static final Integer GIDENTIFY_NONE = 0;
	public static final Integer GIDENTIFY_NEEDED = 1;
	public static final Integer GIDENTIFY_NULL = null;
	
//	searchable : 
	public static final Integer GSEARCH_PUBLICE = 0;
	public static final Integer GSEARCH_PRIVATE = 1;
	public static final Integer GSEARCH_NULL = null;
	/** ===Group Static Value End=== */
	
	/**
	 * Rules for Group and User
	 * */
	public static HashMap<String, Integer> CREATE_GROUP_NUMBERS_RULE
																= new HashMap<String, Integer>();
	public static HashMap<String, Integer> GROUP_CAPACITY_RULE
																= new HashMap<String, Integer>();
	static{
		CREATE_GROUP_NUMBERS_RULE.put(UTYPE_DEV, Integer.MAX_VALUE);
		CREATE_GROUP_NUMBERS_RULE.put(UTYPE_LEVEL2,Integer.MAX_VALUE);
		CREATE_GROUP_NUMBERS_RULE.put(UTYPE_LEVEL1, 20);
		CREATE_GROUP_NUMBERS_RULE.put(UTYPE_NORMAL,5);
		CREATE_GROUP_NUMBERS_RULE.put(UTYPE_NULL, 3);
		
		GROUP_CAPACITY_RULE.put(GTYPE_DEV, 2000);
		GROUP_CAPACITY_RULE.put(GTYPE_LEVEL2, 2000);
		GROUP_CAPACITY_RULE.put(GTYPE_LEVE1, 500);
		GROUP_CAPACITY_RULE.put(GTYPE_NORMAL, 100);
		GROUP_CAPACITY_RULE.put(GTYPE_NULL, 100);
	}
}
