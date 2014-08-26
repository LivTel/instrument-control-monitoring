/**
 * 
 */
package ngat.icm;

import org.jdom.Element;

import ngat.phase2.ICalibration;
import ngat.phase2.IExposure;
import ngat.phase2.IInstrumentConfig;
import ngat.phase2.XImagingSpectrographInstrumentConfig;

/**
 * SPRAT type instrument.
 * 
 * @author eng
 * 
 */
public class ImagingSpectrograph extends BasicInstrumentCapabilities {

	private static final double GRISM_IN_AND_NOT_ROTATED_WAVELENGTH = 500.0;

	private static final double GRISM_IN_AND_ROTATED_WAVELENGTH = 500.0; // bluer

	private static final double GRISM_OUT_WAVELENGTH = 550.0;

	/**
	 * 
	 */
	public ImagingSpectrograph() {
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

		if (config == null)
			return new WavelengthNm(500.0);

		if (!(config instanceof XImagingSpectrographInstrumentConfig))
			return new WavelengthNm(500.0);

		XImagingSpectrographInstrumentConfig xspec = (XImagingSpectrographInstrumentConfig) config;

		int grismPos = xspec.getGrismPosition();
		if (grismPos == XImagingSpectrographInstrumentConfig.GRISM_OUT)
			return new WavelengthNm(GRISM_OUT_WAVELENGTH);

		int grismRot = xspec.getGrismRotation();
		if (grismRot == XImagingSpectrographInstrumentConfig.GRISM_NOT_ROTATED)
			return new WavelengthNm(GRISM_IN_AND_NOT_ROTATED_WAVELENGTH);
		else
			return new WavelengthNm(GRISM_IN_AND_ROTATED_WAVELENGTH);

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

		if (!(config instanceof XImagingSpectrographInstrumentConfig))
			return false;

		//XImagingSpectrographInstrumentConfig xspec = (XImagingSpectrographInstrumentConfig) config;
		// at this stage we could check for rotated but not deployed grism...
		// we could also check if nothing is deployed but that may be ok anyway ???
		
		return true;

	}

	public void configure(Element cfgNode) throws Exception {
		super.configure(cfgNode);
	}
	
	public String toString() {
		return super.toString();
	}
}
