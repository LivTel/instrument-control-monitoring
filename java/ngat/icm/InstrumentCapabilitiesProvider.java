/**
 * 
 */
package ngat.icm;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

/** Provides details of the components of an instrument.
 * @author snf
 *
 */
public interface InstrumentCapabilitiesProvider extends Remote {

	/** @return The capabilities of the instrument.*/
	public InstrumentCapabilities getCapabilities() throws RemoteException;
	
}
