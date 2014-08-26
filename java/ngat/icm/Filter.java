/**
 * 
 */
package ngat.icm;

import java.io.Serializable;

import org.jdom.Element;

import ngat.util.XmlConfigurable;

/**
 * Represents a filter in the light path of an instrument.
 * @author snf
 *
 */
public class Filter implements XmlConfigurable, Serializable {
	
	/** Name of the filter.*/
	protected String filterName;
	
	/** Class of filter.*/
	protected String filterClass;
	
	/** Nominal central wavelength (nm).*/
	protected Wavelength centralWavelength;
	
	/** Optical thickness (mm),*/
	protected double opticalThickness;
	
	/** Filter's wavelength spectral efficency (throughput).*/
	protected SpectralEfficiencyFunction spectrum;

	public Filter() {}
	
	/** Create a Filter of the specified name and class.
	 * @param filterName The name of the filter.
	 * @param filterClass The class of filter.
	 */
	public Filter(String filterName, String filterClass) {
		this();
		this.filterName = filterName;
		this.filterClass = filterClass;
	}

	/**
	 * @return the centralWavelength
	 */
	public Wavelength getCentralWavelength() {
		return centralWavelength;
	}

	/**
	 * @param centralWavelength the centralWavelength to set
	 */
	public void setCentralWavelength(Wavelength centralWavelength) {
		this.centralWavelength = centralWavelength;
	}

	/**
	 * @return the filterClass
	 */
	public String getFilterClass() {
		return filterClass;
	}

	/**
	 * @param filterClass the filterClass to set
	 */
	public void setFilterClass(String filterClass) {
		this.filterClass = filterClass;
	}

	/**
	 * @return the filterName
	 */
	public String getFilterName() {
		return filterName;
	}

	/**
	 * @param filterName the filterName to set
	 */
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	/**
	 * @return the opticalThickness
	 */
	public double getOpticalThickness() {
		return opticalThickness;
	}

	/**
	 * @param opticalThickness the opticalThickness to set
	 */
	public void setOpticalThickness(double opticalThickness) {
		this.opticalThickness = opticalThickness;
	}

	/**
	 * @return the spectrum
	 */
	public SpectralEfficiencyFunction getSpectralResponse() {
		return spectrum;
	}

	/**
	 * @param spectrum the spectrum to set
	 */
	public void setSpectralResponse(SpectralEfficiencyFunction spectrum) {
		this.spectrum = spectrum;
	}

	/** 
	 * @return A descriptor of this filter.
	 */
	public FilterDescriptor getFilterDescriptor() {
		return new FilterDescriptor(filterName, filterClass);
	}
	
	public String toString() {
		return "Filter: "+filterClass+" ["+filterName+"]";
		// e.g. Filter: SDSS-B [Ratcam-SDSS-B-01]
	}

	public void configure(Element fNode) throws Exception {	
		filterName = fNode.getChildTextTrim("name");
		filterClass = fNode.getChildTextTrim("class");
		// note we enter the data in Angstroms which are nm * 10.
		if (fNode.getChildTextTrim("central-wavelength") != null )
			centralWavelength = new WavelengthNm(Double.parseDouble(fNode.getChildTextTrim("central-wavelength"))/10.0);
		if (fNode.getChildTextTrim("optical-thickness") != null)
			opticalThickness = Double.parseDouble(fNode.getChildTextTrim("optical-thickness"));
		 //Element specNode = fNode.getChild("spectralResponse");
		 //spectrum = new BasicSpectrum(); // this is just wavelength/spectrum value elements
		 //spectrum.configure(specNode);
		 
	}
	
}
