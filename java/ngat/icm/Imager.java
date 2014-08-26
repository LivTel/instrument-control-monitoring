/**
 * 
 */
package ngat.icm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.jdom.Element;

import ngat.phase2.ICalibration;
import ngat.phase2.IExposure;
import ngat.phase2.IFilterSpec;
import ngat.phase2.IInstrumentConfig;
import ngat.phase2.XArc;
import ngat.phase2.XBias;
import ngat.phase2.XDark;
import ngat.phase2.XDetectorConfig;
import ngat.phase2.XFilterDef;
import ngat.phase2.XFilterSpec;
import ngat.phase2.XImagerInstrumentConfig;
import ngat.phase2.XInstrumentConfig;
import ngat.phase2.XLampFlat;
import ngat.phase2.XMultipleExposure;

/**
 * Describes an Imager instrument's capabilities.
 * 
 * @author snf
 * 
 */
public class Imager extends BasicInstrumentCapabilities implements Serializable {

	public static final double CCD_READOUT_TIME_MULT_FACTOR = 10.667;
	public static final double CCD_READOUT_TIME_FIXED_OVERHEAD = 9.33;
	public static final double IRCAM_DEFAULT_READOUT_TIME = 12000.0;
	public static final double POLARIMETER_READOUT_TIME = 20000.0;
	public static final double LOWRES_SPEC_READOUT_TIME = 20000.0;
	public static final double RISE_DEFAULT_READOUT_TIME = 35.0;
	public static final double GENERIC_DEFAULT_READOUT_TIME = 10000.0;

	/** Stores the sets of filters in each wheel mapped to the Wheel number. */
	// protected Map<Integer, FilterSet> wheel;
	protected Map<String, FilterSet> wheel;

	/** Time required for a bias calibration. */
	private double biasCalibrationTime;

	/** Time required for an arc calibration. */
	private double arcCalibrationTime;

	/** Time required for a lamp calibration. */
	private double lampCalibrationTime;

	/**
	 * Create an Imager.
	 * 
	 */
	public Imager() {
		super();
		// wheel = new HashMap<Integer, FilterSet>();
		wheel = new HashMap<String, FilterSet>();

	}

	/**
	 * Add a FilterSet to the next wheel and increment the wheel count.
	 * 
	 * @param fs
	 *            The FilterSet to add.
	 * @return The number of wheels in use after adding this one.
	 */
	public void addFilterSet(String fsname, FilterSet fs) throws IllegalArgumentException {
		wheel.put(fsname, fs);
		System.err.println("Imager::AddFilterSet:" + fsname + " : " + fs);
	}

	/**
	 * @return True if the imager has any filterwheels.
	 */
	public boolean hasFilterWheels() {
		return (wheel.size() > 0);
	}

	/**
	 * @return The number of filter wheels.
	 */
	public int getFilterWheelCount() {
		return wheel.size();
	}

	public FilterSet getFilterSet(String fsname) throws Exception {

		FilterSet fs = (FilterSet) wheel.get(fsname);
		if (fs == null)
			throw new IllegalArgumentException("Imager:: Unable to locate filterset: " + fsname);

		if (fs.getName().equalsIgnoreCase(fsname))
			return fs;

		throw new IllegalArgumentException("Imager:: Unable to locate filterset: " + fsname);
	}

	/**
	 * @see ngat.icm.InstrumentCapabilities#isValidConfiguration(ngat.phase2.IInstrumentConfig)
	 */
	public boolean isValidConfiguration(IInstrumentConfig config) {

		if (!(config instanceof XImagerInstrumentConfig))
			return false;

		XImagerInstrumentConfig xim = (XImagerInstrumentConfig) config;

		XFilterSpec filters = xim.getFilterSpec();

		// TODO [1] A temporary fudge to cope with an XIm with no filterspec
		if (filters == null)
			filters = new XFilterSpec();

		Map<String, FilterSet> checkWheel = new HashMap<String, FilterSet>(wheel);

		List filterList = filters.getFilterList();

		// TODO [2] Another temporary fudge in case the real filterspec has no
		// filter list.
		if (filterList == null)
			filterList = new Vector();

		// TODO [3] Really each imager is a bit unique in terms of what filters are in which wheel
		// or in which slide or other mechanism. e.g. from IO:O we really do need the filters to be 
		// ordered as:  (wheel, lower_nd, upper_nd) so we should really test the filters against
		// the specific wheel they should be in....but that needs instrument specific coding...
		
		
		Iterator iff = filterList.iterator();
		while (iff.hasNext()) {

			XFilterDef xfd = (XFilterDef) iff.next();
			String filterName = xfd.getFilterName();

			// make up a descriptor based on details in the filterspec
			// we dont have a filter class name at present in the xim.
			FilterDescriptor fd = new FilterDescriptor(filterName, null);

			boolean filterInAnyWheel = false;

			Iterator<String> iw = checkWheel.keySet().iterator();
			while (iw.hasNext()) {

				String fsname = iw.next();
				FilterSet filterWheel = (FilterSet) wheel.get(fsname);

				if (filterWheel.containsFilter(fd)) {
					filterInAnyWheel = true;
					break;
				}
			}

			// We've checked the supplied filter in all wheels, was it there ?
			if (!filterInAnyWheel)
				return false;

		}
		// Havnt failed to find any filter in some wheel so should be ok
		return true;
	}

	public long getCalibrationTime(ICalibration calib) {

		if (calib instanceof XBias)
			return (long) biasCalibrationTime;
		else if (calib instanceof XArc)
			return (long) arcCalibrationTime;
		else if (calib instanceof XLampFlat)
			return (long) lampCalibrationTime;
		else if (calib instanceof XDark)
			return (long) ((XDark) calib).getExposureTime();

		return 0;
	}

	// TODO What we really need is getExpTime(exposure, config) as the time may
	// be config-dependent.
	public long getExposureTime(IExposure exposure) {

		if (exposure instanceof XMultipleExposure) {
			XMultipleExposure mult = (XMultipleExposure) exposure;
			return (long) mult.getExposureTime() * mult.getRepeatCount();
		}
		/*
		 * XInstrumentConfig xcfg = (XInstrumentConfig) config; String instName
		 * = xcfg.getInstrumentName().toUpperCase(); XDetectorConfig xdc =
		 * (XDetectorConfig) xcfg.getDetectorConfig(); int xb = xdc.getXBin();
		 * int yb = xdc.getYBin(); int binxy = xb * yb;
		 * 
		 * if (instName.equals("RATCAM")) { return (double) (1000.0 *
		 * (CCD_READOUT_TIME_MULT_FACTOR / (double) binxy +
		 * CCD_READOUT_TIME_FIXED_OVERHEAD)); } else if
		 * (instName.equals("SUPIRCAM")) { return IRCAM_DEFAULT_READOUT_TIME; }
		 * else if (instName.equals("RINGO")) { return POLARIMETER_READOUT_TIME;
		 * } else if (instName.equals("MEABURN")) { return
		 * LOWRES_SPEC_READOUT_TIME; } else if (instName.equals("RISE")) {
		 * return RISE_DEFAULT_READOUT_TIME; } else return
		 * GENERIC_DEFAULT_READOUT_TIME;
		 */
		// TODO we need to add in readout times here

		// } else if (exposure instanceof XSigNoiseExposure) {
		// // need env conditions...
		// XSigNoiseExposure sig = (XSigNoiseExposure) exposure;
		// double snr = sig.getSnr();
		//
		// } else if (exposure instanceof XTimedMultrun) {
		// XTimedMultrun mult = (XTimedMultrun) exposure;
		// return mult.getMaximumTime();
		// }
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seengat.icm.InstrumentCapabilities#getReconfigurationTime(ngat.phase2.
	 * IInstrumentConfig, ngat.phase2.IInstrumentConfig)
	 */
	public long getReconfigurationTime(IInstrumentConfig config1, IInstrumentConfig config2) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Wavelength getWavelength(IInstrumentConfig config) {

		System.err.println("Imager:getWaveLength() using: " + config);

		if (config == null)
			return new WavelengthNm(500.0);

		// we use the selected filter(s) and extract its cenwav
		XImagerInstrumentConfig imc = (XImagerInstrumentConfig) config;

		XFilterSpec filters = imc.getFilterSpec();

		// TODO [1] A temporary fudge to cope with an XIm with no filterspec
		if (filters == null)
			filters = new XFilterSpec();

		List flist = filters.getFilterList();

		if (flist == null)
			flist = new Vector();

		System.err.println("Imager:getWaveLength(): Flist contains: " + flist.size() + " elements");

		// no filters so clear...
		if (flist.size() == 0.0)
			return new WavelengthNm(500.0);

		if (flist.size() == 1) {
			XFilterDef f = (XFilterDef) flist.get(0);
			System.err.println("Imager:getWaveLength(): Flist 0: " + f);
			if (f == null)
					return new WavelengthNm(500.0);
			FilterDescriptor fd = new FilterDescriptor(f.getFilterName(), null);

			Iterator<String> iw = wheel.keySet().iterator();
			while (iw.hasNext()) {
				String fsname = iw.next();
				FilterSet fs = wheel.get(fsname);
				if (fs.containsFilter(fd))
					return fs.getFilter(fd).getCentralWavelength();
			}

			// its not in either apparently !
			return new WavelengthNm(500.0);
		}

		// with 2 named filters, we assume these must be lower and upper
		if (flist.size() == 2) {
			XFilterDef f1 = (XFilterDef) flist.get(0);
			System.err.println("Imager:getWaveLength(): Flist 0: " + f1);
			if (f1 == null)
				return new WavelengthNm(500.0);
			FilterDescriptor fd1 = new FilterDescriptor(f1.getFilterName(), null);

			XFilterDef f2 = (XFilterDef) flist.get(1);
			System.err.println("Imager:getWaveLength(): Flist 1: " + f2);
			if (f2 == null)
				return new WavelengthNm(500.0);
			FilterDescriptor fd2 = new FilterDescriptor(f2.getFilterName(), null);

			// both clear
			if (f1.getFilterName().equals("clear") && f2.getFilterName().equals("clear"))
				return new WavelengthNm(550.0);

			// 1 is clear, 2 not, try 2 in either wheel
			if (f1.getFilterName().equals("clear")) {
				FilterSet lwheel = (FilterSet) wheel.get("lower");
				if (lwheel.containsFilter(fd2))
					return lwheel.getFilter(fd2).getCentralWavelength();

				// now find the corresponding filter in upper wheel
				FilterSet uwheel = (FilterSet) wheel.get("upper");
				if (uwheel.containsFilter(fd2))
					return uwheel.getFilter(fd2).getCentralWavelength();
			}

			// 2 is clear, 1 not, try 1 in either wheel
			if (f2.getFilterName().equals("clear")) {
				FilterSet lwheel = (FilterSet) wheel.get("lower");
				if (lwheel.containsFilter(fd1))
					return lwheel.getFilter(fd1).getCentralWavelength();

				// now find the corresponding filter in upper wheel
				FilterSet uwheel = (FilterSet) wheel.get("upper");
				if (uwheel.containsFilter(fd1))
					return uwheel.getFilter(fd1).getCentralWavelength();
			}

			// give up !
			return new WavelengthNm(500.0);

		}
		
		if (flist.size() == 3) {
			XFilterDef f1 = (XFilterDef) flist.get(0);
			System.err.println("Imager:getWaveLength(): Ignoring f1,f2, Flist 0: " + f1);
			if (f1 == null)
				return new WavelengthNm(500.0);
			FilterDescriptor fd = new FilterDescriptor(f1.getFilterName(), null);
			FilterSet awheel = (FilterSet) wheel.get("wheel");
			if (awheel.containsFilter(fd))
				return awheel.getFilter(fd).getCentralWavelength();
		}

		// maybe should be null...
		return new WavelengthNm(500.0);

	}

	public void configure(Element cfgNode) throws Exception {
		super.configure(cfgNode); // access the capnode
		System.err.println("Configured generics now attempting imager-specifics...");
		// extract filters
		int nfs = 0;
		List fsList = cfgNode.getChildren("filterset");
		Iterator ifs = fsList.iterator();
		while (ifs.hasNext()) {
			Element fsNode = (Element) ifs.next();
			FilterSet fs = new FilterSet();
			fs.configure(fsNode);
			addFilterSet(fs.getName(), fs);
		}
	}

	/**
	 * @return A readable description of this Imager.
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		Iterator iw = wheel.keySet().iterator();
		while (iw.hasNext()) {
			buffer.append("[" + iw + "] " + iw.next());
		}
		buffer.append(":").append(super.toString());
		return buffer.toString();
	}

}
