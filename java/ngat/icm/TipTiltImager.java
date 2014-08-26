/**
 * 
 */
package ngat.icm;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom.Element;

import ngat.phase2.ICalibration;
import ngat.phase2.IExposure;
import ngat.phase2.IInstrumentConfig;
import ngat.phase2.XFilterDef;
import ngat.phase2.XFilterSpec;
import ngat.phase2.XTipTiltImagerInstrumentConfig;

/**
 * @author eng
 *
 */
public class TipTiltImager extends BasicInstrumentCapabilities implements Serializable {

	
	private FilterSet filterWheel;
	
	/* (non-Javadoc)
	 * @see ngat.icm.BasicInstrumentCapabilities#getCalibrationTime(ngat.phase2.ICalibration)
	 */
	@Override
	public long getCalibrationTime(ICalibration calib) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see ngat.icm.BasicInstrumentCapabilities#getExposureTime(ngat.phase2.IExposure)
	 */
	@Override
	public long getExposureTime(IExposure exposure) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see ngat.icm.BasicInstrumentCapabilities#getWavelength(ngat.phase2.IInstrumentConfig)
	 */
	@Override
	public Wavelength getWavelength(IInstrumentConfig config) {
		XTipTiltImagerInstrumentConfig xtip = (XTipTiltImagerInstrumentConfig)config;		
		return new WavelengthNm(500.0);		
	}

	/* (non-Javadoc)
	 * @see ngat.icm.BasicInstrumentCapabilities#isValidConfiguration(ngat.phase2.IInstrumentConfig)
	 */
	@Override
	public boolean isValidConfiguration(IInstrumentConfig config) {
		// fail if its not a tip-tilt config
		if (!(config instanceof XTipTiltImagerInstrumentConfig))
			return false;

		XTipTiltImagerInstrumentConfig xtip = (XTipTiltImagerInstrumentConfig)config;
	
		return true;
	}

	/* (non-Javadoc)
	 * @see ngat.icm.InstrumentCapabilities#getReconfigurationTime(ngat.phase2.IInstrumentConfig, ngat.phase2.IInstrumentConfig)
	 */
	public long getReconfigurationTime(IInstrumentConfig config1, IInstrumentConfig config2) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return the filter
	 */
	public FilterSet getFilterWheel() {
		return filterWheel;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilterWheel(FilterSet filter) {
		this.filterWheel = filter;
	}
	
	public void configure(Element cfgNode) throws Exception {
		super.configure(cfgNode);	
	}
}
