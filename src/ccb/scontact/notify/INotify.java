package ccb.scontact.notify;

public interface INotify {

	void notifyOne(String uid,String title,String content);
	void notifyAll(String title,String content);
}
