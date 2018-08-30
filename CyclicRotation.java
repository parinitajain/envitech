
public class CyclicRotation {
	public static void main(String[] args) {
		int arr[] = new int[7];
		arr[0]=9;
		arr[1]=8;
		arr[2]=7;
		arr[3]=6;
		arr[4]=5;
		arr[5]=4;
		arr[6]=1;
		
		int B[]=solution(arr,7,9);
		for(int i =0;i<B.length;i++) {
			System.out.print(" "+B[i] +" ");
		}
		
	}
	
	public static int[] solution(int[] A,int N,int K) {
		int B[] = new int[N];	
	for(int i =0;i<N;i++) {
		B[(i+K) % N]=A[i];
	}
		return B;
	}
}
