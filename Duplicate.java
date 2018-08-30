
public class Duplicate {

	public static void main(String[] args) {
		int[] arrayint = new int[8];
		arrayint[0]=7;
		arrayint[1]=1;
		arrayint[2]=1;
		arrayint[3]=4;
		arrayint[4]=7;
		arrayint[5]=2;
		arrayint[6]=7;
		arrayint[7]=2;
		String missingNumber = solution(arrayint);
		System.out.println("missing number is "+missingNumber);

	}

	public static String solution(int[] arrayint) {
		String value = "";
		for(int i =0;i<arrayint.length;i++) {
				if(arrayint[Math.abs(arrayint[i])]>=0) {
			
				arrayint[Math.abs(arrayint[i])]=-arrayint[Math.abs(arrayint[i])];
			}else {
				value = value+Math.abs(arrayint[i])+" ";
			}
		
			
		}
		display(arrayint);
		return value;
	}
	
	public static void display(int[] arrayint) {
		for(int i =0;i<arrayint.length;i++) {
			System.out.println("array is "+arrayint[i]);
		}
	}
}
