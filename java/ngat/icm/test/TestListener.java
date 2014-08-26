package ngat.icm.test;

import ngat.icm.*;

import java.rmi.*;
import java.rmi.server.*;
import ngat.message.base.*;
import ngat.message.ISS_INST.*;

public class TestListener extends UnicastRemoteObject implements
		InstrumentEventListener {

	private volatile boolean ready = false;

	public TestListener() throws RemoteException {
		super();
	}

	public void instrumentEventOccurred(InstrumentEvent ev) throws RemoteException {
		System.err.println("TL::Received event: " + ev);

		if (ev instanceof CompletionEvent) {
			CompletionEvent cev = (CompletionEvent)ev;
			COMMAND_DONE content = cev.getDone();
			if (content instanceof CONFIG_DONE) {
				CONFIG_DONE done = (CONFIG_DONE) content;
				System.err.println("CONFIG_DONE:Status: "
						+ done.getSuccessful() + ":" + done.getErrorNum()
						+ "->" + done.getErrorString());
			} else if (content instanceof GET_STATUS_DONE) {
				GET_STATUS_DONE done = (GET_STATUS_DONE) content;
				System.err.println("GET_STATUS_DONE:Status: "
						+ done.getSuccessful() + ":" + done.getErrorNum()
						+ "->ErrMsg(" + done.getErrorString()+")");
			}
			// only ready if it was completion...
			ready = true;
			System.err.println("TL::Set ready as completion event");
			
		}
	}

	public boolean isReady() {
		return ready;
	}

}
