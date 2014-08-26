/**
 * 
 */
package ngat.icm.test;

import java.rmi.Naming;
import java.util.Iterator;
import java.util.List;

import ngat.icm.InstrumentCapabilities;
import ngat.icm.InstrumentCapabilitiesProvider;
import ngat.icm.InstrumentDescriptor;
import ngat.icm.InstrumentRegistry;

/**
 * @author eng
 *
 */
public class FindRotatorAngle {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		try {
		
			InstrumentRegistry ireg = (InstrumentRegistry)Naming.lookup("rmi://ltsim1/InstrumentRegistry");
		
			List inst = ireg.listInstruments();
			
			for (int i = 0; i < inst.size(); i++) {
				
				InstrumentDescriptor id = (InstrumentDescriptor)inst.get(i);
				
				System.err.println("Found: "+id);
				
				InstrumentCapabilitiesProvider cap = ireg.getCapabilitiesProvider(id);
				
				InstrumentCapabilities icap = cap.getCapabilities();
				
				double rotoff = icap.getRotatorOffset();
				
				System.err.println("Rot off for "+id.getInstrumentName()+" is "+Math.toDegrees(rotoff));
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
