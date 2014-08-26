/**
 * 
 */
package ngat.icm;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author eng
 *
 */
public interface InstrumentCalibrationProvider extends Remote {

	public InstrumentCalibration getCalibrationRequirements() throws RemoteException;
	
	public InstrumentCalibrationHistory getCalibrationHistory() throws RemoteException;
	
}
