/**
 * 
 */
package ngat.icm;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author snf
 *
 */
public class BasicInstrumentUpdateListener extends UnicastRemoteObject implements InstrumentStatusUpdateListener {

	/** A name to identify this listener , e.g. which system or GUI tool it is attached to.*/
	protected String name;
	
	/**
	 * @throws RemoteException
	 */
	public BasicInstrumentUpdateListener(String name) throws RemoteException {
		super();
		this.name = name;
	}

	

	/* (non-Javadoc)
	 * @see ngat.icm.InstrumentStatusUpdateListener#instrumentStatusUpdated(ngat.icm.InstrumentDescriptor, ngat.icm.InstrumentStatus)
	 */
	public void instrumentStatusUpdated(InstrumentStatus status) throws RemoteException {
		System.err.println(name+":: Recieved update: "+status);
	}

	public String toString() {
		return name;
	}
	
}
