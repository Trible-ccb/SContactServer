package ccb.scontact.notify;

public class ActivityNotifyer extends SimpleNotifyer {

	String target;
	public ActivityNotifyer(String t){
		target = t;
	}
	@Override
	protected String getOpenAction() {
		return target;
	}
}
