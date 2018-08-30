import java.util.Arrays;

public class MinimumDifferenceNlogN {

	public static void main(String[] args) {
		int[] arrayint = new int[6];
		arrayint[0]=8;
		arrayint[1]=24;
		arrayint[2]=3;
		arrayint[3]=20;
		arrayint[4]=1;
		arrayint[5]=17;
		int missingNumber = solution(arrayint);
		System.out.println("missing number is "+missingNumber);

	}

	public static int solution(int[] A) {
		Arrays.sort(A);
		int min=Integer.MIN_VALUE;
		int diff = 0;
		for(int i =0;i<A.length-1;i++) {
			
			diff = A[i+1]-A[i];
			if(diff<min || min <0) {
				min=diff;
			}
			
		}
		
		return min;
	}
	
	
}
