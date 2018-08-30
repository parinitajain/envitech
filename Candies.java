import java.util.HashSet;

public class Candies {

	public static void main(String[] args) {
		int[] arrayint = new int[6];
		arrayint[0]=7;
		arrayint[1]=7;
		arrayint[2]=7;
		arrayint[3]=7;
		arrayint[4]=7;
		arrayint[5]=7;

		int count=distributeCandies(arrayint);
		System.out.println("count is "+count);

	}

	   public static  int distributeCandies(int[] T) {
	        HashSet < Integer > set = new HashSet < > ();
	        for (int candy: T) {
	            set.add(candy);
	        }
	        return Math.min(set.size(), T.length / 2);
	    }
	
}
