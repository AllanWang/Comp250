import java.math.BigInteger;

public class Tester1 {
	
	public static void main(String[] args) throws Exception {
						
		//  You can test the correctness of your NaturalNumber implementation 
		//  by using Java's BigInteger class.  
		
		//  Here is an example.  
		
		//String s1 = "42345614234561000343453440000343453440";
		//String s2 = "22320000423456100034345344000042";

		/*   test with slow multiply and divide
		 * 
		 */
		String s1 = "999";
		String s2 = "9999";
//		String s1 = "452390145";
//		String s2 = "23";
		/*
		 * 
		 */
		
		int base = 10;
		
		BigInteger big1 = new BigInteger(s1,base);
		BigInteger big2 = new BigInteger(s2,base);
		
		NaturalNumber n1 = new NaturalNumber(s1, base);
		System.out.println("n1 is    " + n1);
		NaturalNumber n2 = new NaturalNumber(s2, base);
		System.out.println("n2 is    " + n2);
		System.out.println("");
		      
		//  The BigInteger class uses an 'add' method for addition, but NaturalNumber 
		//  uses 'plus' instead, to avoid confusion with the LinkedList's 'add' method
		//  which inserts an element.
		
		System.out.print("sum: big1+big2 =        (");
		System.out.println(big1.add(big2).toString(base) + ")_" + base );  // BigInteger
		System.out.print("sum: n1+n2     =        ");
		System.out.println(n1.plus(n2));                     // NaturalNumber

		//  The BigInteger class uses a 'subtract' method for addition, but NaturalNumber 
		//  uses 'minus' instead.  This name was chosen because it was a better match 
		//  for 'plus'.

		System.out.print("diff: big1-big2 =       (");
		System.out.println(big1.subtract(big2).toString(base)  + ")_" + base );  // BigInteger
		System.out.print("diff: n1-n2     =       ");
		System.out.println(n1.minus(n2));                         // NaturalNumber

		//  The BigInteger class uses a 'multiply' method for addition. NaturalNumber 
		//  uses 'times' instead.  

		System.out.print("multiply: big1*big2   = (");              // BigInteger
		System.out.println(big1.multiply(big2).toString(base)  + ")_" + base);
		
		System.out.print("multiply: n1*n2       = ");               // NaturalNumber
		System.out.println(n1.times(n2));
		
		System.out.print("slow multiply: n1*n2  = ");               // NaturalNumber
		System.out.println(n1.slowTimes(n2));
				
		System.out.print("divide: big1/big2     = (");              // BigInteger  
		System.out.println(big1.divide(big2).toString(base)  + ")_" + base);

		System.out.print("divide: n1/n2         = ");                 // NaturalNumber
		System.out.println(n1.divide(n2));

		System.out.print("slow divide: n1/n2    = ");               // NaturalNumber
		System.out.println(n1.slowDivide(n2));

		/*   mod
		
		System.out.print("mod = ");
		System.out.println(big1.subtract(big1.divide(big2).multiply(big2)));
		System.out.println(big1.mod(big2));
		
		*/
	}
	
}
