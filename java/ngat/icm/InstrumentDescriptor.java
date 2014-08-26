/**
 * 
 */
package ngat.icm;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

/** Description of an instrument.
 * @author snf
 *
 */
public class InstrumentDescriptor implements Serializable {

    /** The instrument's name.*/
	protected String instrumentName;
	
    /** Hierarchical description of the instrument class. Can be used to match
     * the instrument-class field of an InstrumentSelectorConfig.
     */
    protected String instrumentClass;

    /** The model of instrument (as supplied by manufacturer).*/
	protected String instrumentModel;
	
    /** Name of instrument's manufacturer.*/
	protected String manufacturer;
	
    /** Serial number/code (as supplied by manufacturer).*/
	protected String serialNumber;

	/** Alias name of this instrument.*/
	protected String alias;

    /** True if the ID is a subcomponent ID.*/
    protected boolean subComponent;

    /** ID of this child component's owner.*/
    protected InstrumentDescriptor owner;

    /** List of child component IDs.*/
    protected List<InstrumentDescriptor> children;

    /** Keyword prefix for temperature.*/
	protected String temperatureKeywordPrefix;
	
	/** Keyword suffix for temperature.*/
	protected String temperatureKeywordSuffix;
	
	/** Create an InstrumentDescriptor.
	 * @param instrumentName
	 */
	public InstrumentDescriptor(String instrumentName) {
		this.instrumentName = instrumentName;
		children = new Vector<InstrumentDescriptor>();
	}

	/**
	 * @return the instrumentName
	 */
	public String getInstrumentName() {
		return instrumentName;
	}

	/**
	 * @param instrumentName the instrumentName to set
	 */
	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}

	
	
    /**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
     * @return the instrumentClass
     */
    public String getInstrumentClass() {
	return instrumentClass;
    }

    /**
     * @param instrumentClass the instrumentClass to set
     */
    public void setInstrumentClass(String instrumentClass) {
	this.instrumentClass = instrumentClass;
    }



	/**
	 * @return the instrumentModel
	 */
	public String getInstrumentModel() {
		return instrumentModel;
	}

	/**
	 * @param instrumentModel the instrumentModel to set
	 */
	public void setInstrumentModel(String instrumentModel) {
		this.instrumentModel = instrumentModel;
	}

	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	
   
	/**
	 * @return the temperatureKeywordPrefix
	 */
	public String getTemperatureKeywordPrefix() {
		return temperatureKeywordPrefix;
	}

	/**
	 * @param temperatureKeywordPrefix the temperatureKeywordPrefix to set
	 */
	public void setTemperatureKeywordPrefix(String temperatureKeywordPrefix) {
		this.temperatureKeywordPrefix = temperatureKeywordPrefix;
	}

	/**
	 * @return the temperatureKeywordSuffix
	 */
	public String getTemperatureKeywordSuffix() {
		return temperatureKeywordSuffix;
	}

	/**
	 * @param temperatureKeywordSuffix the temperatureKeywordSuffix to set
	 */
	public void setTemperatureKeywordSuffix(String temperatureKeywordSuffix) {
		this.temperatureKeywordSuffix = temperatureKeywordSuffix;
	}

	/** Add a child instrument (sub-component).
     * @param cid The ID of the child sub-component.
     */
    public void addSubcomponent(InstrumentDescriptor cid) {
	children.add(cid);
    }
    
    public List<InstrumentDescriptor> listSubcomponents() {
	return children;
    }

    public void setOwner(InstrumentDescriptor oid) {
	this.owner = oid;
    }

    public InstrumentDescriptor getOwner() {
	return owner;
    }


    public boolean isSubcomponent() {
	return (owner != null);
    }


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instrumentName == null) ? 0 : instrumentName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final InstrumentDescriptor other = (InstrumentDescriptor) obj;
//		if (instrumentModel == null) {
//			if (other.instrumentModel != null)
//				return false;
//		} else if (!instrumentModel.equals(other.instrumentModel))
//			return false;
		if (instrumentName == null) {
			if (other.instrumentName != null)
				return false;
		} else if (!instrumentName.equals(other.instrumentName))
			return false;
		return true;
	}
	
	/** 
	 * @return Readable description of the instrument.
	 */
	public String toString() {
		return instrumentClass+": ["+instrumentName+"/"+alias+"] "+manufacturer+", "+instrumentModel+" #"+serialNumber+
		    (owner != null ? ", Subcomponent of: "+owner.getInstrumentName() : "");
			// e.g. CCD.OPTICAL: [RATCAM/RATCAM] Marconi, EEV422CCD #3737-V3646
	}
		
}
