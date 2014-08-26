/**
 * 
 */
package ngat.icm;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ngat.phase2.IExposure;

/**
 * @author snf
 *
 */
public interface InstrumentController extends Remote {

	public void test(String msg) throws RemoteException;
	
	/** Perform an exposure.*/
	public void expose(IExposure exposure) throws RemoteException;
	
	/** Configure the instrument.*/
	public void configure() throws RemoteException;
	
	/** Obtain a reference to a proxy for carrying out control and status requests.*/
	public ControllerProxy getControllerProxy() throws RemoteException;
	
}
