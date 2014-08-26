package ngat.icm;

import java.rmi.*;

/** An event listener to handle notification of Iss events.*/
public interface InstrumentEventListener extends Remote {

    /** Implementors should override to handle an InstrumentEvent notification.*/
    public void instrumentEventOccurred(InstrumentEvent ev) throws RemoteException;

}
