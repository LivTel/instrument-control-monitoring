/**
 * 
 */
package ngat.icm.test;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

import ngat.icm.*;

/**
 * @author snf
 *
 */
public class TestInstrumentUpdateListener extends UnicastRemoteObject implements InstrumentStatusUpdateListener {

	/** A name to identify this listener , e.g. which system or GUI tool it is attached to.*/
	protected String name;
	
	/**
	 * @throws RemoteException
	 */
	public TestInstrumentUpdateListener(String name) throws RemoteException {
		super();
		this.name = name;
	}

	

	/* (non-Javadoc)
	 * @see ngat.icm.InstrumentStatusUpdateListener#instrumentStatusUpdated(ngat.icm.InstrumentDescriptor, ngat.icm.InstrumentStatus)
	 */
	public void instrumentStatusUpdated(InstrumentStatus status) throws RemoteException {
	    System.err.println(name+":: Recieved update: "+status+status.getStatus());
	}

	public String toString() {
		return name;
	}
	
}
