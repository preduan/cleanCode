/**
 * 
 * 
 * 
 * @author Christina Duan - cd3136
 * 
 */
package RLESeq;

public interface RLESeq {
	
	
	
	public int getLength() ;
	
	public void insert(int value, int index) ;
	
	public void remove (int index) ;

	public boolean equals(RLESeq second) ;
	
	public String toString() ;
	
	public void concat(RLESeq second) ;
	
}
