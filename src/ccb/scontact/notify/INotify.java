package ccb.scontact.notify;

public interface INotify {

	String notifyOne(String uid,String title,String content);
	String notifyAllPerson(String title,String content);
}
