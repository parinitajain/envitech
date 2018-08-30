

public class Counter {

	public static void main(String[] args) {
		
		int[] arrayint = new int[7];
		arrayint[0]=3;
		arrayint[1]=4;
		arrayint[2]=4;
		arrayint[3]=6;
		arrayint[4]=1;
		arrayint[5]=4;
		arrayint[6]=4;

		int[] counter = solution(arrayint,5);
		for(int i =0;i<counter.length;i++) {
			System.out.print(counter[i]+",");
		}
	}
	
	
	public static int[] solution(int[] A,int N) {
		int counter[] = new int[N];
		int count =0;
		int max=0;
		for(int i=0;i<A.length;i++) {
			if(A[i]>N) {
				for(int j=0;j<N;j++) {
					counter[j]=count;
				}
			}else {
				counter[(A[i]-1)]+=1;
				max=counter[(A[i]-1)];
				if( max>count){
					count++;
				}
			}			
		}
		
	return counter;
	}

	
}
