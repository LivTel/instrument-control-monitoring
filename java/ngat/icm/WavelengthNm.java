/**
 * 
 */
package ngat.icm;

/**
 * @author eng
 *
 */
public class WavelengthNm implements Wavelength {

	private double wnm;
	
	/**
	 * @param wnm
	 */
	public WavelengthNm(double wnm) {
		this.wnm = wnm;
	}

	/* (non-Javadoc)
	 * @see ngat.icm.Wavelength#getValueAngstrom()
	 */
	public double getValueAngstrom() {
		return wnm*10.0;
	}

	/* (non-Javadoc)
	 * @see ngat.icm.Wavelength#getValueNm()
	 */
	public double getValueNm() {
	return wnm;
	}

}
