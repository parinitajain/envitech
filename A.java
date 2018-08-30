
public class A {

	public static void main(String[] args) {
		method(null);
	}
	
	public static void method(String s) {
		System.out.println("String");
	}
	
	public static void method(Object sb) {
		System.out.println("String Buffer");
	}
	
}
