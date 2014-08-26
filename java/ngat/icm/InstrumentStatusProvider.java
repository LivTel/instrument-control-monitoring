/**
 * 
 */
package ngat.icm;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ngat.phase2.IInstrumentConfig;

/**
 * @author snf
 *
 */
public interface InstrumentStatusProvider extends Remote {

	public void addInstrumentStatusUpdateListener(InstrumentStatusUpdateListener ul) throws RemoteException;
	
	public void removeInstrumentStatusUpdateListener(InstrumentStatusUpdateListener ul) throws RemoteException;
	
	public InstrumentStatus getStatus()  throws RemoteException;

    public IInstrumentConfig getCurrentConfig() throws RemoteException;

}
