

public class Binary2 {

	public static void main(String[] args) {
		int n =20;
		String binary = Integer.toBinaryString(n);
		System.out.println(binary);
		int count=solution(n);
		System.out.println("count is "+count);

	}

	public static int solution(int N) {
		int maxCount = 0;
		int count=0;
		
		int binary[] = new int[40];
		int index =0;
		while(N>0) {
			binary[index++] = N%2;
			N= N/2;
		}
		
		for(int i= index-1; i>=0;i--) {
			if(binary[i]==1) {
				if(maxCount<count) {
					maxCount = count;
					}
				count=0;
			}else {
				count++;
			}
		}
		return maxCount;
	}
	
}
