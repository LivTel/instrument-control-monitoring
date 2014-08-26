package ngat.icm;

/** A representation of the spectral efficiency of an instrument.*/
public interface SpectralEfficiencyFunction {

	/** Returns The efficiency of the instrument at specified wavelength.
	 * @param wavelength The wavelength for which the efficiency is required (nm).
	 * @return The efficiency of the instrument at the specified wavelength.
	 */
	public double getEfficiency(Wavelength wavelength);
	
}
