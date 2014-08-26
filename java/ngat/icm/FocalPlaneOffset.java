/**
 * 
 */
package ngat.icm;

import java.io.Serializable;

/** A Focal plane offset.
 * @author snf
 *
 */
public class FocalPlaneOffset implements Serializable {

	/** Focal plane offset X coordinate (mm).*/
	protected double focalPlaneOffsetX;
	
	/** Focal plane offset Y coordinate (mm).*/
	protected double focalPlaneOffsetY;

	/** Create a FocalPlaneOffset.
	 * @param focalPlaneOffsetX
	 * @param focalPlaneOffsetY
	 */
	public FocalPlaneOffset(double focalPlaneOffsetX, double focalPlaneOffsetY) {
		this.focalPlaneOffsetX = focalPlaneOffsetX;
		this.focalPlaneOffsetY = focalPlaneOffsetY;
	}

	/**
	 * @return the focalPlaneOffsetX
	 */
	public double getFocalPlaneOffsetX() {
		return focalPlaneOffsetX;
	}

	/**
	 * @param focalPlaneOffsetX the focalPlaneOffsetX to set
	 */
	public void setFocalPlaneOffsetX(double focalPlaneOffsetX) {
		this.focalPlaneOffsetX = focalPlaneOffsetX;
	}

	/**
	 * @return the focalPlaneOffsetY
	 */
	public double getFocalPlaneOffsetY() {
		return focalPlaneOffsetY;
	}

	/**
	 * @param focalPlaneOffsetY the focalPlaneOffsetY to set
	 */
	public void setFocalPlaneOffsetY(double focalPlaneOffsetY) {
		this.focalPlaneOffsetY = focalPlaneOffsetY;
	}
	
	/** Returns a readable description of this FocalPlaneOffset.*/
	public String toString() {
		return "FocalPlaneOffset:["+focalPlaneOffsetX+","+focalPlaneOffsetY+"]";
	}
	
	
}
