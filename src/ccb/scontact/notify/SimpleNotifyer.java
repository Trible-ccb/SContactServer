package ccb.scontact.notify;

import org.apache.commons.codec.digest.DigestUtils;

import ccb.scontact.utils.StringUtil;
import ccb.scontact.utils.URLUtil;

import com.google.gson.JsonObject;

public  class SimpleNotifyer implements INotify {

	public static final String appkey = "53aad85356240bdd8901917f";
	public static final String appMasterSecret = "eyp9n2cwakviknpvn4hrfoixrzscvjch";
	public static final String url = "http://msg.umeng.com/api/send";
    public static final String TITLE = "OO通讯录";
    public static final String DESP = "来自OO通讯录服务器的消息";
    public static final String TICKER = "来自OO通讯录的消息";
    public static final String ACTIVITY_INBOX = "com.trible.scontact.components.activity.MyInboxActivity";
    public static final String TYPE_UNICAST = "unicast";
    public static final String TYPE_BOARDCAST = "boardcast";
    
    protected String getOpenAction() {
		return null;
	}
	@Override
	public void notifyOne(String uid, String title, String content) {
		if ( StringUtil.isEmpty(uid) ){
			System.out.println("notify id null");
			return;
		}
		String a = getOpenAction();
		String t = TYPE_UNICAST;
		notify(t, uid, title, content, a);
	}

	@Override
	public void notifyAll(String title, String content) {
		String a = getOpenAction();
		String t = TYPE_BOARDCAST;
		notify(t,"", title, content, a);
	}
	private void notify(String type , String uid, String title, String content,String actionTarget) {
		
		String timestamp = System.currentTimeMillis()+"";
		String validation_token = DigestUtils.md5Hex(appkey.toLowerCase() + appMasterSecret.toLowerCase() + timestamp);
		String device_tokens = uid;
		String alias = uid;
		String display_type = "notification";
		String description = DESP;
		
		JsonObject messagebody = new JsonObject();
		JsonObject body = new JsonObject();
		String ticker = TICKER;
		body.addProperty("ticker", ticker);
		body.addProperty("title", title);
		body.addProperty("text", content);
		
		String action = null;
		if (actionTarget == null)actionTarget = "";
		if (StringUtil.isValidURL(actionTarget)){
			action = "go_url";
			body.addProperty("url", actionTarget);
		} else if (actionTarget.contains(".")){
			action = "go_activity";
			body.addProperty("activity", actionTarget);
		} else {
			action = "go_app";
		}
		body.addProperty("after_open", action);
		messagebody.addProperty("display_type", display_type);
		messagebody.add("body", body);
		
		JsonObject param = new JsonObject();
		param.addProperty("appkey", appkey);
		param.addProperty("timestamp", timestamp);
		param.addProperty("validation_token", validation_token);
		param.addProperty("type", type);
		param.addProperty("device_tokens", device_tokens);
		param.addProperty("alias", alias);
		param.add("payload", messagebody);
		param.addProperty("description", description);
		
		URLUtil.sendPost(url, param);
	}
}
