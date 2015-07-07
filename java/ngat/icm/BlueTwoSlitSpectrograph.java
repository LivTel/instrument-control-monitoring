/**
 * 
 */
package ngat.icm;

import org.jdom.Element;

import ngat.phase2.ICalibration;
import ngat.phase2.IExposure;
import ngat.phase2.IInstrumentConfig;
import ngat.phase2.XBlueTwoSlitSpectrographInstrumentConfig;

/**
 * LOTUS type instrument.
 * 
 * @author eng
 * 
 */
public class BlueTwoSlitSpectrograph extends BasicInstrumentCapabilities {

	/**
	 * 
	 */
	public BlueTwoSlitSpectrograph() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ngat.icm.InstrumentCapabilities#getReconfigurationTime(ngat.phase2.
	 * IInstrumentConfig, ngat.phase2.IInstrumentConfig)
	 */
	public long getReconfigurationTime(IInstrumentConfig config1,
			IInstrumentConfig config2) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ngat.icm.BasicInstrumentCapabilities#getCalibrationTime(ngat.phase2.
	 * ICalibration)
	 */
	@Override
	public long getCalibrationTime(ICalibration calib) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ngat.icm.BasicInstrumentCapabilities#getExposureTime(ngat.phase2.IExposure
	 * )
	 */
	@Override
	public long getExposureTime(IExposure exposure) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ngat.icm.BasicInstrumentCapabilities#getWavelength(ngat.phase2.
	 * IInstrumentConfig)
	 */
	@Override
	public Wavelength getWavelength(IInstrumentConfig config) {
			return new WavelengthNm(500.0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ngat.icm.BasicInstrumentCapabilities#isValidConfiguration(ngat.phase2
	 * .IInstrumentConfig)
	 */
	@Override
	public boolean isValidConfiguration(IInstrumentConfig config) {

		if (!(config instanceof XBlueTwoSlitSpectrographInstrumentConfig))
			return false;		
		return true;

	}

	public void configure(Element cfgNode) throws Exception {
		super.configure(cfgNode);
	}
	
	public String toString() {
		return super.toString();
	}
}
