/**
 * 
 * RLESeqV2 - stores input in a compressed form
 * Saves space, but operates as if it were not compressed at all
 * Useful if much of a file repeats or reuses many of the same values
 * 
 * Notes which values repeat - represent the seequence as pairs
 * uses internal structuring class called pair 
 * 
 * @author Christina Duan - cd3136
 * 
 */
package RLESeq;

import java.util.*;

public class RLESeqV2 implements RLESeq {

	public int DEFAULT_LENGTH = 10;

	public LinkedList<Pair> sequence;

	public RLEConverter converter;

	/**
	 * Constructor - creates sequence that stores "length" number of the same
	 * "commonValue"
	 * 
	 * @param length      the amount of commonValue there is
	 * @param commonValue the repeated value in the array
	 */
	public RLESeqV2(int length, int commonValue) {
		try {
			if ((commonValue >= 0) && (commonValue <= 255) && !(length <= 0)) {
				sequence = new LinkedList<Pair>();
				converter = new RLEConverter();
				sequence.add(new Pair(length, commonValue));
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Please insert a positive integer for the length.");
		}

	}

	/**
	 * Constructor creates sequence with "length" amount of values Values are in
	 * increasing order, making a gradient length must be between 0 and 255
	 * 
	 * @param length the amount of values
	 */
	public RLESeqV2(int length) {

		try {
			if ((length <= 255) && (length > 0)) {

				sequence = new LinkedList<Pair>();
				converter = new RLEConverter();
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
	public RLESeqV2() {
		sequence = new LinkedList<Pair>();
		converter = new RLEConverter();
		int randomNum = 0;
		for (int i = 0; i < DEFAULT_LENGTH; i++) {
			randomNum = (int) (Math.random() * (256)) + 0;
			sequence.add(new Pair(1, randomNum));
		}

	}

	/**
	 * Gets the length of the RLESeq
	 * 
	 */
	public int getLength() {
		int length = 0;
		Iterator<Pair> iterator = sequence.iterator();
		while (iterator.hasNext()) {
			Pair item = iterator.next();
			length += item.count;
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
		if (index > this.getLength()) {
			System.out.println("Invalid index - out of bounds");
			return;
		}
		RLESeqV1 v1Object = converter.toV1(this);

		v1Object.insert(value, index);

		sequence = converter.toV2(v1Object);

	}

	/**
	 * Removes a legal value at a legal index, and closes up the gap Considers if
	 * index is 0, the end of the array, or somewhere in the middle of the array
	 * 
	 * @param index index where value is being removed
	 */
	public void remove(int index) {

		if (index >= this.getLength()) {
			System.out.println("Invalid index - out of bounds");
			return;
		}
		RLESeqV1 v1Object = converter.toV1(this);

		v1Object.remove(index);

		sequence = converter.toV2(v1Object);
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

		RLESeqV2 secondV2 = (RLESeqV2) second;
		if (secondV2.getLength() != this.getLength()) {
			return false;
		}
		itr1 = sequence.iterator();
		itr2 = secondV2.sequence.iterator();

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
			Pair item = iterator.next();
			for (int i = 0; i < item.count; i++) {
				convertedSeq += item.value + " ";
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

		RLESeqV1 v1Object = converter.toV1(this);
		RLESeqV1 v1Object2 = converter.toV1((RLESeqV2) second);
		v1Object.concat(v1Object2);
		sequence = converter.toV2(v1Object);
	}

}
