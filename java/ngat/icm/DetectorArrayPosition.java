/**
 * 
 */
package ngat.icm;

import java.io.Serializable;

/** A detector array position.
 * @author snf
 *
 */
public class DetectorArrayPosition implements Serializable {

	/** Array position X coordinate.*/
	protected double detectorArrayPositionX;
	
	/** Array position Y coordinate.*/
	protected double detectorArrayPositionY;

	/** Create a DetectorArrayPosition.
	 * @param detectorArrayPositionX
	 * @param detectorArrayPositionY
	 */
	public DetectorArrayPosition(double detectorArrayPositionX, double detectorArrayPositionY) {
		this.detectorArrayPositionX = detectorArrayPositionX;
		this.detectorArrayPositionY = detectorArrayPositionY;
	}

	/**
	 * @return the detectorArrayPositionX
	 */
	public double getDetectorArrayPositionX() {
		return detectorArrayPositionX;
	}

	/**
	 * @param detectorArrayPositionX the detectorArrayPositionX to set
	 */
	public void setDetectorArrayPositionX(double detectorArrayPositionX) {
		this.detectorArrayPositionX = detectorArrayPositionX;
	}

	/**
	 * @return the detectorArrayPositionY
	 */
	public double getDetectorArrayPositionY() {
		return detectorArrayPositionY;
	}

	/**
	 * @param detectorArrayPositionY the detectorArrayPositionY to set
	 */
	public void setDetectorArrayPositionY(double detectorArrayPositionY) {
		this.detectorArrayPositionY = detectorArrayPositionY;
	}
	
	/** Returns a readable description of this DetectorArrayPosition.*/
	public String toString() {
		return "ArrayPosition:["+detectorArrayPositionX+","+detectorArrayPositionY+"]";
	}
	
}
