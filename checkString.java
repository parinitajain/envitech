
public class checkString {
	public static final String USER_LEVEL = "ADMIN";
	
	public static void main(String[] args) {
	
		

	}
	
	private static String getUserLevel() {
		return null;
	}

	private static void checkForNullMethodA(String level) {
		try {
			if(level!=null && level.equals(USER_LEVEL)) {
				System.out.println("--USER Level -- ADMIN");
			}
		}catch(NullPointerException NullException) {
			System.out.println("Null exception");
		}
	}
	
	private static void checkForNullMethodB(String level) {
		try {
			if(level!=null && level.equals(USER_LEVEL)) {
				System.out.println("--USER Level -- ADMIN");
			}
		}catch(NullPointerException NullException) {
			System.out.println("Null exception");
		}
	}
}
