/**
 * 
 */
package ngat.icm.test;

import ngat.util.XmlConfigurable;

/** Base class for deriving instrument-specific subclasses.
 * @author snf
 *
 */
public abstract class TestCapabilitiesPlugin implements ITestCapabilitiesPlugin, XmlConfigurable {
	   
	protected Class cfgClass;
	   
	/* (non-Javadoc)
	 * @see ngat.icm.test.ITestCapabilitiesPlugin#getConfigClass()
	 */
	public Class getConfigClass() {
		return cfgClass;
	}

}
