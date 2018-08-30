
public class Cards {
	public static void main(String[] args) {

		
		int count=solution("53A84T","32A25J");
		
			System.out.print(count);

		
	}
	
	public static int solution(String A,String B) {
		int count =0;
		int AN = 0;
		int BN = 0;
		
		char AArray[] = A.toCharArray();
		char BArray[] = B.toCharArray();
		
		for(int i=0;i<AArray.length;i++) {
			AN = value(Character.toString(AArray[i]));
			BN = value(Character.toString(BArray[i]));
			if(AN>BN) {
				count++;
				}
		}
		
		return count;
	}
	
	public static int value(String card) {
		
		switch(card) {
		
		case "A" :
			return 14;
		case "K" :
			return 13;
		case "Q" :
			return 12;
		case "J":
			return 11;
		case "T":
			return 10;
		default:
			return Integer.parseInt(card);
			}
	}
}
