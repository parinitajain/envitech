
public class OddOccurences {
	public static void main(String[] args) {
		int arr[] = new int[7];
		arr[0]=9;
		arr[1]=9;
		arr[2]=5;
		arr[3]=5;
		arr[4]=5;
		arr[5]=1;
		arr[6]=1;
		
		int xor =solution(arr);
		System.out.println("number is "+xor);
		
	}
	
	public static int solution(int[] arr) {
		int xor =0;
		for(int i = 0;i<arr.length;i++) {
			xor = arr[i]^xor;
			}
		
		return xor;
	}
}
