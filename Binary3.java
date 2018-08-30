

public class Binary3 {

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
		while(N>0) {
			
			if(N%2 ==0) {
				count++;
			}else {
				if(maxCount<count) {
					maxCount=count;
				}
				count=0;
			}
			
			N= N/2;
		}
		return maxCount;
	}
	
}
