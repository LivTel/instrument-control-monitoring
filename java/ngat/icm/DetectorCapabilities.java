/**
 * 
 */
package ngat.icm;

import java.io.Serializable;

/** Provides details of a detector's configuration capabilities.
 * @author snf
 *
 */
public class DetectorCapabilities implements Serializable {

	/** Stores the BinningSpec combinations.*/
	protected BinSet binning;

	/** Unbinned pixel size (um=microns).*/
	protected double pixelSize;
	
	/** Scale of unbinned detector array (arcsec/pixel).*/
	protected double plateScale;
	
	/** Detector array width (pixels).*/
	protected int arrayWidth;
	
	/** Detector array height (pixels).*/
	protected int arrayHeight;
	
	/**
	 * 
	 */
	public DetectorCapabilities() {
		super();
	}

	/**
	 * @return the binning
	 */
	public BinSet getBinning() {
		return binning;
	}

	/**
	 * @param binning the binning to set
	 */
	public void setBinning(BinSet binning) {
		this.binning = binning;
	}
	
	
	/**
	 * @return the pixelSize
	 */
	public double getPixelSize() {
		return pixelSize;
	}

	/**
	 * @param pixelSize the pixelSize to set
	 */
	public void setPixelSize(double pixelSize) {
		this.pixelSize = pixelSize;
	}

	/**
	 * @return the plateScale
	 */
	public double getPlateScale() {
		return plateScale;
	}

	/**
	 * @param plateScale the plateScale to set
	 */
	public void setPlateScale(double plateScale) {
		this.plateScale = plateScale;
	}
	
	
	/**
	 * @return the arrayHeight
	 */
	public int getArrayHeight() {
		return arrayHeight;
	}

	/**
	 * @param arrayHeight the arrayHeight to set
	 */
	public void setArrayHeight(int arrayHeight) {
		this.arrayHeight = arrayHeight;
	}

	/**
	 * @return the arrayWidth
	 */
	public int getArrayWidth() {
		return arrayWidth;
	}

	/**
	 * @param arrayWidth the arrayWidth to set
	 */
	public void setArrayWidth(int arrayWidth) {
		this.arrayWidth = arrayWidth;
	}

	/**
	 * @return A readable description of the detector capabilities.
	 */
	public String toString() {
		return "Array: "+arrayWidth+"x"+arrayHeight+", scale="+plateScale+" as/px, size="+pixelSize+" um, BinningSpec:"+binning;
		// e.g. Array: 1024x1240, scale=0.135 as/px, size=1.345 um, binning: [1x1],[2x2],[4x4]
	}
	
}
