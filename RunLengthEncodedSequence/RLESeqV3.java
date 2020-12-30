/**
 * 
 * RLESeqV3 - stores input in an even more compressed form
 * Saves space and time by operating on internal representation directly
 * uses same improved data structures for V2 but uses different algorithms
 * 
 * @author Christina Duan - cd3136
 * 
 */
package RLESeq;

import java.util.Iterator;
import java.util.LinkedList;

public class RLESeqV3 implements RLESeq {

	public int DEFAULT_LENGTH = 10;

	public LinkedList<Pair> sequence;

	/**
	 * Constructor - creates sequence that stores "length" number of the same
	 * "commonValue"
	 * 
	 * @param length      the amount of commonValue there is
	 * @param commonValue the repeated value in the array
	 */
	public RLESeqV3(int length, int commonValue) {

		try {
			if ((commonValue >= 0) && (commonValue <= 255) && !(length <= 0)) {
				sequence = new LinkedList<Pair>();
				sequence.add(new Pair(length, commonValue));
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Please insert a positive integer for the length and"
					+ " a positive integer between 0 and 255 for the commonValue.");
		}
	}

	/**
	 * Constructor creates sequence with "length" amount of values Values are in
	 * increasing order, making a gradient length must be between 0 and 255
	 * 
	 * @param length the amount of values
	 */
	public RLESeqV3(int length) {
		try {
			if ((length <= 255) && (length > 0)) {

				sequence = new LinkedList<Pair>();
				int increasingValue = 0;

				for (int i = 0; i < length; i++) {
					sequence.add(new Pair(1, increasingValue));
					increasingValue++;
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Please enter a positive integer less than 255.");
		}
	}

	/**
	 * Default constructor- creates sequence of default length with random values
	 * DEFAULT_LENGTH - 10 objects in the sequence Values are in between 0 and 255
	 * 
	 */
	public RLESeqV3() {
		sequence = new LinkedList<Pair>();
		int randomNum = 0;
		for (int i = 0; i < DEFAULT_LENGTH; i++) {
			randomNum = (int) (Math.random() * (256)) + 0;
			sequence.add(new Pair(1, randomNum));
		}
	}

	/**
	 * Gets the length of the RLESeq
	 * @return length the amount of items in the sequence
	 */
	public int getLength() {
		int length = 0;
		Iterator<Pair> iterator = sequence.iterator();
		while (iterator.hasNext()) {
			Pair pairedNode = iterator.next();
			length += pairedNode.count;
		}

		return length;
	}

	/**
	 * Inserts a legal value into sequence at a legal index, after making room for
	 * it Considers if index is 0, the end of the array, or somewhere in the middle
	 * of the array
	 * 
	 * @param value value being inserted
	 * @param index index where value is being inserted
	 */
	public void insert(int value, int index) {
		int currentIndex = 0, nodeIndex = 0;
		if (index >= this.getLength()) {
			System.out.println("Please enter a valid index. Something less than " 
		+ index + " but greater than 0." );
			return;
		}
		Iterator<Pair> iterator = sequence.iterator();
		while (iterator.hasNext()) {
			Pair pairedNode = iterator.next();
			if ((pairedNode.count + currentIndex) > index) {
				if (pairedNode.value == value) {
					pairedNode.count++;
					break;
				} else {
					// split the current node to two and insert the new one between
					int left = currentIndex + pairedNode.count - index;
					pairedNode.count -= left;
					sequence.add(nodeIndex, new Pair(1, value));
					sequence.add(++nodeIndex, new Pair(left, pairedNode.value));
					break;
				}
			}

			nodeIndex++;
			currentIndex += pairedNode.count;
		}
	}

	/**
	 * Removes a legal value at a legal index, and closes up the gap Considers if
	 * index is 0, the end of the array, or somewhere in the middle of the array
	 * 
	 * @param index index where value is being removed
	 */
	public void remove(int index) {
		int currentIndex = 0;
		if (index >= this.getLength()) {
			System.out.println("Please enter a valid index.");
			return;
		}
		Iterator<Pair> iterator = sequence.iterator();
		while (iterator.hasNext()) {
			Pair pairedNode = iterator.next();
			if ((pairedNode.count + currentIndex) > index) {
				pairedNode.count--;
				break;
			}

			currentIndex += pairedNode.count;
		}
	}

	/**
	 * Determines if two sequences are the same
	 * 
	 * @param second the other RLESeq
	 * @return isEqual same = true, different = false
	 */
	public boolean equals(RLESeq second) {
		boolean isEqual = true;
		Iterator<Pair> itr1, itr2;
		Pair seq1Pair, seq2Pair;

		RLESeqV3 secondV3 = (RLESeqV3) second;
		if (secondV3.getLength() != this.getLength()) {
			return false;
		}
	
		itr1 = sequence.iterator();
		itr2 = secondV3.sequence.iterator();

		while (itr1.hasNext()) {
			seq1Pair = itr1.next();
			seq2Pair = itr2.next();
			if (!seq1Pair.equals(seq2Pair)) {
				isEqual = false;
				break;
			}
		}

		return isEqual;
	}

	/**
	 * Creates a printable String from the sequence
	 * 
	 * @return convertedSeq the sequence in String form
	 */
	public String toString() {
		String convertedSeq = "< ";

		Iterator<Pair> iterator = sequence.iterator();
		while (iterator.hasNext()) {
			Pair pairedNode = iterator.next();
			for (int i = 0; i < pairedNode.count; i++) {
				convertedSeq += pairedNode.value + " ";
			}
		}
		convertedSeq += ">";

		return convertedSeq;
	}

	/**
	 * Concatenates two RLESeqs
	 * 
	 * @param second the other RLESeq being added to the end of this RLESEq
	 * 
	 */
	public void concat(RLESeq second) {
		RLESeqV3 secondV3 = (RLESeqV3) second;
		Pair last, first;
		LinkedList<Pair> headofsecond;
		last = this.sequence.getLast();
		first = secondV3.sequence.getFirst();

		if (last.value == first.value) {

			secondV3.sequence.removeFirst();
			last.count += first.count;
		}
		// if the last node of current object has the same value as the second object
		// merge them

		headofsecond = secondV3.sequence;
		// Check whether we can merge the last with the first of the another list.

		this.sequence.addAll(headofsecond);
	}

}
