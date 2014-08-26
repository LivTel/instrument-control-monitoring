/**
 * 
 */
package ngat.icm;

import java.io.Serializable;

import org.jdom.Element;

import ngat.phase2.ICalibration;
import ngat.phase2.IExposure;
import ngat.phase2.IInstrumentConfig;
import ngat.phase2.XDualBeamSpectrographInstrumentConfig;

/**
 * Describes a Spectrograph with fixed format instrument's capabilities.
 * 
 * @author eng
 * 
 */
public class DualBeamSpectrograph extends BasicInstrumentCapabilities implements Serializable {
	

	/**
	 * @see ngat.icm.InstrumentCapabilities#isValidConfiguration(ngat.phase2.IInstrumentConfig)
	 */
	public boolean isValidConfiguration(IInstrumentConfig config) {

		//System.err.println("DualBeamSpectrograph:: isValidConfiguration(): " + config);

		// fail if its not a DBS config
		if (!(config instanceof XDualBeamSpectrographInstrumentConfig))
			return false;

		XDualBeamSpectrographInstrumentConfig xdual = (XDualBeamSpectrographInstrumentConfig) config;

		// check the resolution
		if (xdual.getResolution() != XDualBeamSpectrographInstrumentConfig.LOW_RESOLUTION
				&& xdual.getResolution() != XDualBeamSpectrographInstrumentConfig.HIGH_RESOLUTION)
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
