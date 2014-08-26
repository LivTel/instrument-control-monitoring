/**
 * 
 */
package ngat.icm;

import java.rmi.Remote;
import java.rmi.RemoteException;

/** Recieves notifications of changes to instrument status.
 * @author snf
 *
 */
public interface InstrumentStatusUpdateListener extends Remote {

	/** Implementors should handle the notification of a status change from the specified instrument.*/
	public void instrumentStatusUpdated(InstrumentStatus status) throws RemoteException;
	
}
