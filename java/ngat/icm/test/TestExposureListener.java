package ngat.icm.test;

import ngat.icm.*;

import java.rmi.*;
import java.rmi.server.*;
import ngat.message.base.*;
import ngat.message.ISS_INST.*;

public class TestExposureListener extends UnicastRemoteObject implements InstrumentEventListener {

    private volatile boolean ready = false;

    public TestExposureListener() throws RemoteException {
	super();
    }
    
    /** Implementors should override to handle an InstrumentEvent notification.*/
    public void instrumentEventOccurred(InstrumentEvent ev) throws RemoteException{
	System.err.println("InstrumentEventListener: EventOccurred: "+ev);
    }

    public void exposureCompleted(String filename) throws RemoteException {
	System.err.println("TEL::Exposure event: File="+filename);
    }

    public void reductionCompleted(String filename, double seeing) throws RemoteException {
	System.err.println("TEL::Reduction event: File="+filename+" Seeing="+seeing);
    }

}
