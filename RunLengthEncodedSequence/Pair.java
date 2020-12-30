/**
 * Pair class - creates object Pairs for V2 compression
 * Structuring : (count, value)
 * @author Christina Duan - cd3136
 * 
 */


package RLESeq;

public class Pair {

	public int count;
	public int value;

	/**
	 * Constructor - initializes count and value variables
	 * @param count the amount of times the value occurs
	 * @param value the integer value
	 */
	public Pair(int count, int value) {
		this.count = count;
		this.value = value;
	}

	/**
	 * Checks if pairs are the same 
	 * @param second the other pair that is being compared to
	 * @return isEqual true == same, false == different 
	 */
	public boolean equals(Pair second) {
		boolean isEqual = true;
		if ((this.count != second.count) || (this.value != second.value)) {
			isEqual = false;
		}
		return isEqual;
	}

	
	//maybe getCount? 
}
