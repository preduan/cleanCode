/**
 * Tester -walks through script to do testing
 * 
 * @author Christina Duan - cd3136
 */

package RLESeq;

public class Tester {

	public RLESeq test1;
	public RLESeq test2;
	public RLESeq test3;

	
	public Tester() {

	}

	/**
	 * Tests out V3 more
	 */
	public void advancedV3() {
		
		test1 = new RLESeqV3(7,10);
		test2 = new RLESeqV3(3, 6);
		
		String t = test1.toString();
		String e = test2.toString();
		
		System.out.println(t);
		System.out.println(e);
		
		test1.insert(10, 6);
		t = test1.toString();
		System.out.println(t);
		
		test1.insert(4, 0);
		t = test1.toString();
		System.out.println(t);
		

		test2.insert(6, 0);
		e = test2.toString();
		System.out.println(e);
		
		test1.concat(test2);
		t = test1.toString();
		System.out.println(t);
	

	}
	
	
	public void testNegative() {
		System.out.println("------Negative Test Start -------\n");

		test1 = new RLESeqV1(-6);
		test2 = new RLESeqV1(-5, -8);
		test3 = new RLESeqV1();
		
		String t = test1.toString();
		String e = test2.toString();
		String s = test3.toString();
		
		System.out.println(t);
		System.out.println(e);
		System.out.println(s);
		
		test1.insert(10, 5); 
		t = test1.toString();
		System.out.println(t);
		
		test1.remove(6);
		t = test1.toString();
		System.out.println(t);
		
		test1.concat(test2);
		t = test1.toString();
		System.out.println(t);
		
		if(test1.equals(test3)) {
			System.out.println("it's the same");
		}
		else {
			System.out.println("it's not the same");
		}
		System.out.println("------Negative Test End -------\n");
	}

	

	public void testV1() {
		System.out.println("------V1 Test Start -------\n");

		test1 = new RLESeqV1(7);
		test2 = new RLESeqV1(5, 8);
		test3 = new RLESeqV1();
		basicTest(test1,  test2,  test3);
	
		System.out.println("------V1 Test End -------\n");
	
	}

	public void testV2() {
		System.out.println("\n------V2 Test Start -------");

		test1 = new RLESeqV1(7);
		test2 = new RLESeqV1(5, 8);
		test3 = new RLESeqV1();
		
		basicTest(test1,  test2,  test3);

		System.out.println("------V2 Test End -------\n");
	

	}

	public void testV3() {
		System.out.println("\n------V3 Test Start -------");

		test1 = new RLESeqV1(7);
		test2 = new RLESeqV1(5, 8);
		test3 = new RLESeqV1();
		
		basicTest(test1,  test2,  test3);

		
		System.out.println("------V3 Test End -------\n");

	}

	/**
	 * Tests basic tests for the different versions
	 */
	public void testAllBasic() {
		testV1();
		testV2();
		testV3();
	}
	
	
	private void basicTest(RLESeq test1, RLESeq test2, RLESeq test3) {

		String t = test1.toString();
		String e = test2.toString();
		String s = test3.toString();
		
		System.out.println(t);
		System.out.println(e);
		System.out.println(s);
		
		test1.insert(10, 5); //what about if at end , 6 that does not exist yet?
		t = test1.toString();
		System.out.println(t);
		
		test1.remove(6);
		t = test1.toString();
		System.out.println(t);
		
		test1.concat(test2);
		t = test1.toString();
		System.out.println(t);
		
		if(test1.equals(test3)) {
			System.out.println("it's the same");
		}
		else {
			System.out.println("it's not the same");
		}
			
	}

	

}
