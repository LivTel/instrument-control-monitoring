// MoptopPolarimeter.java
package ngat.icm;

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
import ngat.phase2.XDetectorConfig;
import ngat.phase2.XFilterDef;
import ngat.phase2.XFilterSpec;
import ngat.phase2.XMoptopInstrumentConfig;

/**
 * Describes the Moptop polarimeter instrument's capabilities.
 * @author cjm
 * @see BasicInstrumentCapabilities
 */
public class MoptopPolarimeter extends BasicInstrumentCapabilities
{
	/** 
	 * Stores the sets of filters in each wheel mapped to the Wheel number. 
	 */
	protected Map<String, FilterSet> filterWheel;

	/**
	 * Constructor. Initialises the filterWheel map.
	 * @see #filterWheel
	 */
	public MoptopPolarimeter() 
	{
		super();
		filterWheel = new HashMap<String, FilterSet>();
	}

	/**
	 * Add a FilterSet to the specified wheel name.
	 * @param filterWheelName The name of this filter wheel.
	 * @param filterSet The FilterSet to add.
	 * @see #filterWheel
	 */
	public void addFilterSet(String filterWheelName, FilterSet filterSet) throws IllegalArgumentException 
	{
		filterWheel.put(filterWheelName, filterSet);
		System.err.println("MoptopPolarimeter::AddFilterSet:" + filterWheelName + " : " + filterSet);
	}

	/**
	 * @return True if the moptop polarimeter has any filterwheels.
	 * @see #filterWheel
	 */
	public boolean hasFilterWheels() 
	{
		return (filterWheel.size() > 0);
	}

	/**
	 * Get the number of filter wheels.
	 * @return The number of filter wheels.
	 * @see #filterWheel
	 */
	public int getFilterWheelCount() 
	{
		return filterWheel.size();
	}

	/**
	 * Get the filter set belonging to the specified filter wheel.
	 * @param filterWheelName Which filter wheel to retrieve the filter set for.
	 * @return A set of filters in the specified wheel.
	 * @throws Exception Thrown if the filter wheel cannot be found in the map of wheels, or there are
	 * 	                  no filter wheels.
	 * @see #filterWheel
	 * @see FilterSet
	 */
	public FilterSet getFilterSet(String filterWheelName) throws Exception 
	{
		FilterSet filterSet = (FilterSet) filterWheel.get(filterWheelName);
		if (filterSet == null)
			throw new IllegalArgumentException("MoptopPolarimeter:: Unable to locate filterset: " + filterWheelName);

		if (filterSet.getName().equalsIgnoreCase(filterWheelName))
			return filterSet;

		throw new IllegalArgumentException("MoptopPolarimeter:: Unable to locate filterset: " + filterWheelName);
	}

	/**
	 * Check whether the specified instrument config is a Moptop polarimeter config.
	 * @see ngat.icm.InstrumentCapabilities#isValidConfiguration(ngat.phase2.IInstrumentConfig)
	 */
	public boolean isValidConfiguration(IInstrumentConfig config) 
	{
		System.err.println("MoptopPolarimeter:: isValidConfiguration(): "+config);
		
		// fail if its not a moptop config
		if (! (config instanceof XMoptopInstrumentConfig))
			return false;
		
		XMoptopInstrumentConfig moptopConfig = (XMoptopInstrumentConfig) config;
	
		XFilterSpec filterSpec = moptopConfig.getFilterSpec();
		if (filterSpec == null)
			filterSpec = new XFilterSpec();

		Map<String, FilterSet> checkWheel = new HashMap<String, FilterSet>(filterWheel);
		List filterList = filterSpec.getFilterList();

		// TODO [2] Another temporary fudge in case the real filterspec has no
		// filter list.
		if (filterList == null)
			filterList = new Vector();
		// There should be one and one only filter in the list.
		if(filterList.size() != 1)
			return false;
		
		// get filter def from filter list
		XFilterDef filterDef = (XFilterDef)filterList.get(0);
		String filterName = filterDef.getFilterName();
		FilterDescriptor fd = new FilterDescriptor(filterName, null);
	
		boolean filterInAnyWheel = false;

		Iterator<String> iw = checkWheel.keySet().iterator();
		while (iw.hasNext()) 
		{
				String fsname = iw.next();
				FilterSet filterWheelFilterSet = (FilterSet) filterWheel.get(fsname);

				if (filterWheelFilterSet.containsFilter(fd)) 
				{
					filterInAnyWheel = true;
					break;
				}
		}
		// We've checked the supplied filter in all wheels, was it there ?
		if (!filterInAnyWheel)
			return false;
		// Havnt failed to find any filter in some wheel so should be ok
		return true;
	}
	
	public long getCalibrationTime(ICalibration calib) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	
	public long getExposureTime(IExposure exposure) 
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see ngat.icm.InstrumentCapabilities#getReconfigurationTime(ngat.phase2.IInstrumentConfig, ngat.phase2.IInstrumentConfig)
	 */
	public long getReconfigurationTime(IInstrumentConfig config1, IInstrumentConfig config2) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public Wavelength getWavelength(IInstrumentConfig config)
	{

		System.err.println("MoptopPolarimeter:getWaveLength() using: " + config);

		if (config == null)
			return new WavelengthNm(500.0);

		// we use the selected filter(s) and extract its central wavelength
		XMoptopInstrumentConfig moptopConfig = (XMoptopInstrumentConfig) config;

		XFilterSpec filters = moptopConfig.getFilterSpec();

		// TODO [1] A temporary fudge to cope with an moptopConfig with no filterspec
		if (filters == null)
			filters = new XFilterSpec();

		List flist = filters.getFilterList();

		if (flist == null)
			flist = new Vector();

		System.err.println("MoptopPolarimeter:getWaveLength:getWaveLength(): Flist contains: " + flist.size() + " elements");

		if (flist.size() == 1) 
		{
			XFilterDef f = (XFilterDef) flist.get(0);
			System.err.println("MoptopPolarimeter:getWaveLength(): Flist 0: " + f);
			if (f == null)
					return new WavelengthNm(500.0);
			FilterDescriptor fd = new FilterDescriptor(f.getFilterName(), null);

			Iterator<String> iw = filterWheel.keySet().iterator();
			while (iw.hasNext()) 
			{
				String fsname = iw.next();
				FilterSet fs = filterWheel.get(fsname);
				if (fs.containsFilter(fd))
					return fs.getFilter(fd).getCentralWavelength();
			}

			// its not in the filter wheel apparently
			return new WavelengthNm(500.0);
		}
		// If we don't have only 1 filter guess
		return new WavelengthNm(500.0);
	}
	
	public void configure(Element cfgNode) throws Exception 
	{
		super.configure(cfgNode);
		System.err.println("MoptopPolarimeter:Configured generics now attempting Moptop-specifics...");
		// extract filters
		int nfs = 0;
		List fsList = cfgNode.getChildren("filterset");
		Iterator ifs = fsList.iterator();
		while (ifs.hasNext()) 
		{
			Element fsNode = (Element) ifs.next();
			FilterSet fs = new FilterSet();
			fs.configure(fsNode);
			addFilterSet(fs.getName(), fs);
		}
	}
	
	public String toString() 
	{
		StringBuffer buffer = new StringBuffer();
		Iterator iw = filterWheel.keySet().iterator();
		while (iw.hasNext()) 
		{
			buffer.append("[" + iw + "] " + iw.next());
		}
		buffer.append(":").append(super.toString());
		return buffer.toString();
	}
}
