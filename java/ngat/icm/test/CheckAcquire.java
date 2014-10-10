/**
 * 
 */
package ngat.icm.test;



import java.io.File;

import ngat.icm.BasicInstrumentRegistry;
import ngat.icm.DetectorArrayPosition;
import ngat.icm.InstrumentCapabilities;
import ngat.icm.InstrumentCapabilitiesProvider;
import ngat.icm.InstrumentDescriptor;
import ngat.util.XmlConfigurator;

/**
 * @author eng
 *
 */
public class CheckAcquire {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		try {
			
			File file = new File(args[0]);
			String targetName = args[1];
			String acquireName = args[2];
			InstrumentDescriptor target = new InstrumentDescriptor(targetName);
			InstrumentDescriptor acquire = new InstrumentDescriptor(acquireName);
			
			BasicInstrumentRegistry ireg = new BasicInstrumentRegistry();		
			XmlConfigurator.use(file).configure(ireg);
			
			System.err.println("Configuration loaded");
			System.err.println("Check acquisition for "+acquireName+" onto "+targetName);
			
			InstrumentCapabilitiesProvider cap = ireg.getCapabilitiesProvider(target);
			System.err.println("Found cap provider");
			InstrumentCapabilities icap = cap.getCapabilities();
			System.err.println("Found caps for: "+targetName);
			System.err.println("Checking for DAP using: "+acquireName);
			DetectorArrayPosition dap = icap.getAcquisitionTargetPosition(acquire);
			System.err.println("Found DAP: "+dap);
			double threshold = icap.getAcquisitionThreshold(acquire,false);
			System.err.println("Found Low Acquisition Threshold: "+threshold);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
