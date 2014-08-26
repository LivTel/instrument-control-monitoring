/**
 * 
 */
package ngat.icm;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author snf
 *
 */
public class BinSet implements Serializable {

	/** Stores the list of binnings.*/
	List bins;
	
	/** default value of binning.*/
	protected BinningSpec defaultBinning;
	
	/** Create a BinSet.*/
	public BinSet() {
		bins = new Vector();
	}
	
	/** Add a binning to the set of valid binnings.*/
	public void addBinning(int x, int y) {
		bins.add(new BinningSpec(x,y));
	}
	
	/** Returns an iterator over the set of valid binnigs.*/
	public Iterator listBins() {
		return bins.iterator();
	}

	/**
	 * @return the defaultBinning
	 */
	public BinningSpec getDefaultBinning() {
		return defaultBinning;
	}

	/**
	 * @param defaultBinning the defaultBinning to set
	 */
	public void setDefaultBinning(BinningSpec defaultBinning) {
		this.defaultBinning = defaultBinning;
	}
	
	/**
	 * @return A readable description of this BinSet.
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		Iterator ib = bins.iterator();
		while (ib.hasNext()) {
			buffer.append(ib.next());
		}
		return buffer.toString();
	}
	
}
