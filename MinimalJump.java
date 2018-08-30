
public class MinimalJump {
	public static void main(String[] args) {

		
		int steps=solution(10,30,110);
		
			System.out.print(steps);

		
	}
	
	public static int solution(int X,int D,int Y) {
		int distance = Y-X;
		int Q= distance/D;
		if(distance%D >0) {
			Q++;
		}	
		return Q;
	}
}
