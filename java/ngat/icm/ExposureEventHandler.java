package ngat.icm;

import java.rmi.*;
public interface ExposureEventHandler extends Remote {

    public void exposureCompleted(String filename) throws RemoteException;

    public void reductionCompleted(String filename, double seeing) throws RemoteException;

}
