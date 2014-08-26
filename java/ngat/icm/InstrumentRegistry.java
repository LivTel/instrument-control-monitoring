package ngat.icm;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * @author eng
 *
 */
public interface InstrumentRegistry extends Remote {

	/**
	 *  The iterator returned is over a set of InstrumentDescriptors.
	 * @return An iterator over the list of available instruments.
	 * @throws RemoteException
	 */
	//public List<InstrumentDescriptor> listInstruments() throws RemoteException;
	public List listInstruments() throws RemoteException;
	
	/**
	 * Generate a list of instruments capable of acquiring in priority order - highest first.
	 * @return A list of instruments capable of acquiring in priority order - highest first.
	 * @throws RemoteException
	 */
	public List<InstrumentDescriptor> listAcquisitionInstruments() throws RemoteException;
	
	public InstrumentDescriptor getDescriptor(String instName) throws RemoteException;
	
	/**
	 * @param id The ID of the instrument.
	 * @return The controller for the specified instrument.
	 * @throws RemoteException if anything goes wrong.
	 */
	public InstrumentController getController(InstrumentDescriptor id) throws RemoteException;
	
	/**
	 * @param id The ID of the instrument.
	 * @return The status-provider for the specified instrument.
	 * @throws RemoteException if anything goes wrong.
	 */
	public InstrumentStatusProvider getStatusProvider(InstrumentDescriptor id) throws RemoteException;
	
	/**  
	 * @param id The ID of the instrument.
	 * @return The capabilities-provider for the specified instrument.
	 * @throws RemoteException if anything goes wrong.
	 */
	public InstrumentCapabilitiesProvider getCapabilitiesProvider(InstrumentDescriptor id) throws RemoteException;
	
	
	/**
	 * @param id The ID of the instrument.
	 * @return The calibration requirements for the specified instrument.
	 * @throws RemoteException if anything goes wrong.
	 */
	public InstrumentCalibrationProvider getCalibrationProvider(InstrumentDescriptor id) throws RemoteException;
	
	
}
