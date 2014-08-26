/**
 * 
 */
package ngat.icm;

import java.io.Serializable;

/** Represents a spectrum.
 * @author snf
 *
 */
public interface Spectrum extends Serializable {

	/** @return the minimum wavelength for the spectrum.*/
	public Wavelength getMinimumWavelength();
	
	/** @return the maximum wavelength for the spectrum.*/
	public Wavelength getMaximumWavelength();
	
	/** @return the spectrum at specified wavelength.*/
	public double getSpectralValue(Wavelength w);
	
}
