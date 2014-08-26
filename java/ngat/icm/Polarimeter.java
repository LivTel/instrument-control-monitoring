/**
 * 
 */
package ngat.icm;

import org.jdom.Element;

import ngat.phase2.ICalibration;
import ngat.phase2.IExposure;
import ngat.phase2.IInstrumentConfig;
import ngat.phase2.XPolarimeterInstrumentConfig;

/** Describes a Polarimeter instrument's capabilities.
 * @author eng
 *
 */
public class Polarimeter extends BasicInstrumentCapabilities {
	
	/**
	 * @see ngat.icm.InstrumentCapabilities#isValidConfiguration(ngat.phase2.IInstrumentConfig)
	 */
	public boolean isValidConfiguration(IInstrumentConfig config) {
		
		System.err.println("Polarimeter:: isValidConfiguration(): "+config);
		
		// fail if its not a polarimeter config
		if (! (config instanceof XPolarimeterInstrumentConfig))
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
		return new WavelengthNm(348.0);
	}
	
	public void configure(Element cfgNode) throws Exception {
		super.configure(cfgNode);
	}
	
	public String toString() {
		return super.toString();
	}
}
