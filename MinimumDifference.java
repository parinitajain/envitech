
public class MinimumDifference {

	public static void main(String[] args) {
		int[] arrayint = new int[8];
		arrayint[0]=17;
		arrayint[1]=10;
		arrayint[2]=59;
		arrayint[3]=42;
		arrayint[4]=20;
		arrayint[5]=20;
		arrayint[6]=22;
		arrayint[7]=22;
		int missingNumber = solution(arrayint);
		System.out.println("missing number is "+missingNumber);

	}

	public static int solution(int[] arrayint) {
		int min=Integer.MIN_VALUE;
		int diff = 0;
		for(int i =0;i<arrayint.length;i++) {
		for(int j=0;j<arrayint.length;j++) {
			if(j!=i) {
			diff= Math.abs(arrayint[j]-arrayint[i]);
			if(diff<min || min<0) {
				min = diff;
			}
			
			}
			
			System.out.println("diff is "+diff+" min is "+min+" for i "+i+" for j "+j);
		}
			
		}
		
		return min;
	}
	
	
}
