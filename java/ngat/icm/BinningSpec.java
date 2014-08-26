/**
 * 
 */
package ngat.icm;

import java.io.Serializable;

/** Stores details of a valid bin combination.
 * @author snf
 *
 */
public class BinningSpec implements Serializable {

	public int x;
	
	public int y;

	/**
	 * @param x
	 * @param y
	 */
	public BinningSpec(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return "["+x+"x"+y+"]";
	}
}
