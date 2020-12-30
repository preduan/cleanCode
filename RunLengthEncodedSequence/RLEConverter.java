/**
 * 
 * Converts sequences between V1 and V2 of RLESeq
 * 
 * @author Christina Duan - cd3136
 * 
 */

package RLESeq;

import java.util.Iterator;

import java.util.LinkedList;

public class RLEConverter {


	public RLESeqV1 v1Object;


	public RLEConverter() {

	}

	/**
	 * toV1 - converts a V2 object to a V1 object
	 * 
	 * @param v2Object the version 2 object
	 * @return v1Object version 1 object 
	 */
	public RLESeqV1 toV1(RLESeqV2 v2Object) {

		v1Object = new RLESeqV1();
		int length = v2Object.getLength();
		int index = 0;
		int[] tempSeq = new int[length];
		Iterator<Pair> iterator = v2Object.sequence.iterator();
		while (iterator.hasNext()) {
			Pair pairedNode = iterator.next();

			for (int i = 0; i < pairedNode.count; i++) {
				tempSeq[index] = pairedNode.value;

				index++;
			}
		}

		v1Object.sequence = tempSeq;
		return v1Object;
	}


	/**
	 * tov2 - converts a V1 object into a V2 sequence
	 * @param v1Object version 1 object
	 * @return v2Sequence version 2 sequence
	 */
	public LinkedList<Pair> toV2(RLESeqV1 v1Object) {

		LinkedList<Pair> v2Sequence = new LinkedList<Pair>();
		int count = 0, value = 0;

		for (int i = 0; i < v1Object.getLength(); i++) {
			count = 1;
			value = v1Object.sequence[i];

			while ((i + 1 < v1Object.getLength()) && (v1Object.sequence[i] == v1Object.sequence[i + 1])) {
				count++;
				i++;
			}
			v2Sequence.add(new Pair(count, value));
		}

		return v2Sequence;
	}

	public String toString() {
		String debugString;
		debugString = v1Object.toString();
		return debugString;
	}

}
