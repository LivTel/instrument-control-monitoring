package ngat.icm;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InstrumentRegistryChangeListener extends Remote {

    /** Called to notify that an instrument has joined the registry.*/
    public void instrumentAdded(String name, InstrumentController controller) throws RemoteException;

    /** Called to notify that an instrument has left the registry.*/
    public void instrumentRemoved(InstrumentController controller) throws RemoteException;

}
