

public class MinimumTime {

	public static void main(String[] args) {
		
		int[] arrayint = new int[8];
		arrayint[0]=1;
		arrayint[1]=1;
		arrayint[2]=2;
		arrayint[3]=3;
		arrayint[4]=4;
		arrayint[5]=5;
		arrayint[6]=4;
		arrayint[7]=5;
		int missingNumber = solution(arrayint,5);
		System.out.println("missing number is "+missingNumber);
	}
	
	
	public static int solution(int[] A,int X) {
		int steps = X;
		boolean flag[]= new boolean[steps+1];
		for(int i =0;i<A.length;i++) {
			if(!flag[A[i]]) {
				flag[A[i]]=true;
				steps--;
			}
			if(steps ==0) {
				return i;
			}
		}
		
		return -1;
	}

	
}
