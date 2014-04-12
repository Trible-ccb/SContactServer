package ccb.scontact.utils;

public class StringUtil {

	public static boolean isValidName(String name){
		return name == null ? false : name.matches("[^\"'%*]+");
	}
	
	public static boolean isValidPwd(String pwd){
		return pwd == null ? false : pwd.matches("[^\"']+");
	}	
	
	public static boolean isValidPhone(String phone){
		return phone == null ? false : phone.matches("[^\"'%*]+");
	}
	
	public static void main(String[] args){
		String testName ="+123*2";
		Bog.v(testName + " isValidName "+ isValidName(testName));
		
	}
}
