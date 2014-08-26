/**
 * 
 */
package ngat.icm;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import ngat.net.telemetry.StatusCategory;


/**
 * @author snf
 *
 */
public class InstrumentStatus implements Serializable, StatusCategory {

	public static final int OPERATIONAL_STATUS_OKAY = 1;
	public static final int OPERATIONAL_STATUS_WARN = 2;
	public static final int OPERATIONAL_STATUS_FAIL = 3; 
	public static final int OPERATIONAL_STATUS_UNAVAILABLE = 4;
	
	private InstrumentDescriptor instrumentDescriptor;
	
	/** Stores a number of status parameters identified by keys.*/
	protected Map status;

	/** True if the instrument's control system is online.*/
	protected boolean online;
	
	/** True if the instrument is fully? functional.*/
	protected boolean functional;
	
	protected long statusTimeStamp;

	protected boolean enabled;

	private int ns = 0;
	
	/**
	 * 
	 */
	public InstrumentStatus() {
		
	}


	
	/**
	 * @return the instrumentDescriptor
	 */
	public InstrumentDescriptor getInstrument() {
		return instrumentDescriptor;
	}



	/**
	 * @param instrumentDescriptor the instrumentDescriptor to set
	 */
	public void setInstrument(InstrumentDescriptor instrumentDescriptor) {
		this.instrumentDescriptor = instrumentDescriptor;
	}



	/**
	 * @return the status
	 */
	public Map getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(Map status) {
		this.status = status;
	}


	/**
	 * @return the online
	 */
	public boolean isOnline() {
		return online;
	}


	/**
	 * @param online the online to set
	 */
	public void setOnline(boolean online) {
		this.online = online;
	}


	/**
	 * @return the functional
	 */
	public boolean isFunctional() {
		return functional;
	}


	/**
	 * @param functional the functional to set
	 */
	public void setFunctional(boolean functional) {
		this.functional = functional;
	}

	
	
	
	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled() {
		return enabled; 
	}


	/**
	 * @param isEnabled the isEnabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	/**
	 * @return the statusTimeStamp
	 */
	public long getStatusTimeStamp() {
		return statusTimeStamp;
	}


	/**
	 * @param statusTimeStamp the statusTimeStamp to set
	 */
	public void setStatusTimeStamp(long statusTimeStamp) {
		this.statusTimeStamp = statusTimeStamp;
	}
	

	/**
	 * @return the ns
	 */
	public int getNs() {
		return ns;
	}


	/**
	 * @param ns the ns to set
	 */
	public void setNs(int ns) {
		this.ns = ns;
	}


	public String toString() {
	    return "InstrumentStatus: "+instrumentDescriptor.getInstrumentName()+"("+ns+")"
	    		+(enabled ?
					 (online ? 
					  (functional ? "OKAY" : "ERROR") : "OFFLINE") : "DISABLED");
	}


	public String getCategoryName() {
		return instrumentDescriptor.getInstrumentName();
		// TODO OR ICM ???????
	}
    
}
