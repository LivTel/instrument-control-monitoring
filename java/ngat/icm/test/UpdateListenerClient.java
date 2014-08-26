/**
 * 
 */
package ngat.icm.test;

import java.rmi.Naming;
import java.util.Iterator;
import java.util.Set;

import ngat.icm.BasicInstrumentUpdateListener;
import ngat.icm.InstrumentDescriptor;
import ngat.icm.InstrumentRegistry;
import ngat.icm.InstrumentStatus;
import ngat.icm.InstrumentStatusProvider;

/** Sets up a number of Instrument status update listeners for each inst in local registry.
 * @author snf
 *
 */
public class UpdateListenerClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			
			InstrumentRegistry ireg = (InstrumentRegistry)Naming.lookup("InstrumentRegistry");
			System.err.println("Located IREG in rmi registry");
			
			Iterator insts = ireg.listInstruments().iterator();
			while (insts.hasNext()) {
				InstrumentDescriptor id = (InstrumentDescriptor)insts.next();
				System.err.println("Found instrument: "+id);
				BasicInstrumentUpdateListener bul = new BasicInstrumentUpdateListener("BUL_"+id.getInstrumentName());
				InstrumentStatusProvider isp = ireg.getStatusProvider(id);				
				isp.addInstrumentStatusUpdateListener(bul);
							
			}
			
			// keep main alive...
			while (true)
				try {Thread.sleep(60000L);} catch (InterruptedException ix) {}
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
