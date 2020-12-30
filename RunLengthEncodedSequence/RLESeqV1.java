/**
 * 
 * RLESeqV1 - stores input in its plainest, uncompressed form
 * 
 * 
 * @author Christina Duan - cd3136
 * 
 */

package RLESeq;


public class RLESeqV1 implements RLESeq {

	public int[] sequence;
	public int DEFAULT_LENGTH = 10;

	/**
	 * Constructor - creates sequence that stores "length" number of the same
	 * "commonValue"
	 * 
	 * @param length      the amount of commonValue there is; should be greater than
	 *                    0
	 * @param commonValue the repeated value in the array; should be between 0 and
	 *                    255
	 */

	public RLESeqV1(int length, int commonValue) {

		try {
			if ((commonValue >= 0) && (commonValue <= 255) && !(length <= 0)) {
				sequence = new int[length];
				for (int i = 0; i < sequence.length; i++) {
					sequence[i] = commonValue;
				}
			}

		} catch (IllegalArgumentException e) {
			System.out.println(
					"Please insert a positive integer for the length and a positive integer between 0 and 255 for the commonValue.");
		}

	}

	/**
	 * Constructor creates sequence with "length" amount of values Values are in
	 * increasing order, making a gradient length must be between 0 and 255
	 * 
	 * @param length the amount of values
	 */
	public RLESeqV1(int length) {

		try {
			if ((length <= 255) && (length > 0)) {
				sequence = new int[length];
				int increasingValue = 0; // gradient

				for (int i = 0; i < sequence.length; i++) {
					sequence[i] = increasingValue;
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
	public RLESeqV1() {
		sequence = new int[DEFAULT_LENGTH];
		int randomNum;

		for (int i = 0; i < sequence.length; i++) {
			randomNum = (int) (Math.random() * (256)) + 0;
			sequence[i] = randomNum;
		}
	}

	/**
	 * Gets the length of the RLESeq
	 * 
	 */
	public int getLength() {
		int seqLength = sequence.length;
		return seqLength;
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

		try {
			if (index < sequence.length) {
				int[] tempSeq = new int[sequence.length + 1];

				if (index == 0) {

					tempSeq[0] = value;

					for (int i = index + 1; i < tempSeq.length; i++) {

						tempSeq[i] = sequence[i - 1];
					}
				}

				else if (index == sequence.length - 1) {

					for (int i = 0; i < index; i++) {

						tempSeq[i] = sequence[i];
					}

					tempSeq[index] = value;

					tempSeq[index + 1] = sequence[index];
				}

				else {
					for (int i = 0; i < index; i++) {

						tempSeq[i] = sequence[i];

					}

					tempSeq[index] = value;

					for (int i = index + 1; i < tempSeq.length; i++) {

						tempSeq[i] = sequence[i - 1];
					}
				}

				sequence = tempSeq;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("The index provided is out of bounds - please provide a valid index."
					+ "Integers between 0 and the length of the sequence minus 1 is valid.");
		}

	}

	/**
	 * Removes a legal value at a legal index, and closes up the gap Considers if
	 * index is 0, the end of the array, or somewhere in the middle of the array
	 * 
	 * @param index index where value is being removed
	 */
	public void remove(int index) {
		try {
			if (index < sequence.length) {

				int[] tempSeq = new int[sequence.length - 1];

				if (index == 0) {

					for (int i = index; i < tempSeq.length; i++) {

						tempSeq[i] = sequence[i + 1];
					}
				}

				else if (index == sequence.length) {
					for (int i = 0; i < index; i++) {
						tempSeq[i] = sequence[i];
					}
				}

				else {
					for (int i = 0; i < index; i++) {
						tempSeq[i] = sequence[i];
					}

					for (int i = index; i < tempSeq.length; i++) {

						tempSeq[i] = sequence[i + 1];
					}
				}
				sequence = tempSeq;
			}
		}

		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("The index provided is out of bounds - please provide a valid index."
					+ "Integers between 0 and the length of the sequence minus 1 is valid.");
		}
	}

	/**
	 * Determines if two sequences are the same
	 * 
	 * @param second the other RLESeq
	 * @return isEqual true == same, false == different
	 */
	public boolean equals(RLESeq second) {
		RLESeqV1 secondV1 = (RLESeqV1) second;

		boolean isEqual = true;

		int secondLength = secondV1.getLength();

		if (sequence.length != secondLength) {
			isEqual = false;
			return isEqual;
		}

		for (int i = 0; i < secondLength; i++) {
			if (sequence[i] != secondV1.sequence[i]) {
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

		for (int i = 0; i < sequence.length; i++) {
			convertedSeq += (Integer.toString(sequence[i]) + " ");
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
		RLESeqV1 secondV1 = (RLESeqV1) second;
		int seqNum = 0;

		int secondLength = secondV1.getLength();

		int[] tempSeq = new int[sequence.length + secondLength];

		for (int i = 0; i < sequence.length; i++) {
			tempSeq[i] = sequence[i];
		}

		for (int i = sequence.length; i < tempSeq.length; i++) {
			tempSeq[i] = secondV1.sequence[seqNum];
			seqNum++;
		}

		sequence = tempSeq;

	}

}
