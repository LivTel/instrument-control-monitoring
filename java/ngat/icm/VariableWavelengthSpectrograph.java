/**
 * 
 */
package ngat.icm;

import java.io.Serializable;

import org.jdom.Element;

import ngat.phase2.ICalibration;
import ngat.phase2.IExposure;
import ngat.phase2.IInstrumentConfig;
import ngat.phase2.XSpectrographInstrumentConfig;

/** Describes a Spectrograph with variable wavelength facility instrument's capabilities.
 * @author eng
 *
 */
public class VariableWavelengthSpectrograph extends BasicInstrumentCapabilities implements Serializable  {

	/** Minimum permitted wavelength.*/
	private Wavelength minWavelength;
	
	/** Maximum permitted wavelength.*/
	private Wavelength maxWavelength;
	
	/** Create a VariableWavelengthSpectrograph with specified wavelength limits.
	 * @param minWavelength Minimum permitted wavelength.
	 * @param maxWavelength Maximum permitted wavelength.
	 */
	public VariableWavelengthSpectrograph(Wavelength minWavelength, Wavelength maxWavelength) {
		this.minWavelength = minWavelength;
		this.maxWavelength = maxWavelength;
	}

	/**
	 * @see ngat.icm.InstrumentCapabilities#isValidConfiguration(ngat.phase2.IInstrumentConfig)
	 */
	public boolean isValidConfiguration(IInstrumentConfig config) {
		
		System.err.println("Imager:: isValidConfiguration(): "+config);
		
		// fail if its not a var-wave-spec config
		if (! (config instanceof XSpectrographInstrumentConfig))
			return false;
		
		XSpectrographInstrumentConfig xspec = (XSpectrographInstrumentConfig)config;
		
		double wave = xspec.getWavelength(); // this should be nms or angs BUT WHICH
	
		if (wave < minWavelength.getValueNm() || wave > maxWavelength.getValueNm())
			return false;
		
		return true;
		
	}

	public long getCalibrationTime(ICalibration calib) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getExposureTime(IExposure exposure) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see ngat.icm.InstrumentCapabilities#getReconfigurationTime(ngat.phase2.IInstrumentConfig, ngat.phase2.IInstrumentConfig)
	 */
	public long getReconfigurationTime(IInstrumentConfig config1, IInstrumentConfig config2) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Wavelength getWavelength(IInstrumentConfig config) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void configure(Element cfgNode) throws Exception {
		super.configure(cfgNode);
	}
}
