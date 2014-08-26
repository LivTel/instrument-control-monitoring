/**
 * 
 */
package ngat.icm;

import java.io.Serializable;

/**
 * Represents a wavelength.
 * @author snf
 *
 */
public interface Wavelength extends Serializable {

	/** @return the wavelength in nano-meters.*/
	public double getValueNm();
	
	/** @return the wavelength in Angstroms.*/
	public double getValueAngstrom();
	
}
