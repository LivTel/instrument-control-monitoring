/**
 * 
 */
package ngat.icm.test;

import java.util.List;

import ngat.icm.BasicInstrument;
import ngat.icm.BasicInstrumentCapabilities;
import ngat.icm.BasicInstrumentRegistry;
import ngat.icm.Imager;
import ngat.icm.InstrumentDescriptor;
import ngat.icm.Wavelength;
import ngat.phase2.ICalibration;
import ngat.phase2.IExposure;
import ngat.phase2.IInstrumentConfig;

/** Create and readback in descending order a list of instruments.
 * @author eng
 *
 */
public class CreateReadbackAcqList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		final String[] insts = new String[] {"RATCAM", "SPRAT", "IO:O2", "IO:THOR", "IO:I3", "IO:ODIN", "RISE", "SUPIRCAM5"};
		
		
		try {
			
			BasicInstrumentRegistry bir = new BasicInstrumentRegistry();
			
			System.err.println("Creating instrument registry with random acquisition priorities");
			System.err.println("---------------------------------------------------------------");
			// load up the registry...
			for (int i = 0; i < insts.length; i++) {
				InstrumentDescriptor id = new InstrumentDescriptor(insts[i]);
				int acp = (int)(1.0 +Math.random()*50.0);
				
				Imager imager = new Imager();
				imager.setAcquisitionPriority(acp);
				
				BasicInstrument inst = new BasicInstrument(id);
				inst.setCapabilities(imager);
				bir.addInstrument(id, inst);
				System.err.printf("Add instrument: %10.10s : %4d\n",id.getInstrumentName(), acp);
				
			}
			
			// readback
			System.err.println("");
			System.err.println("Reading back acquisition priorities in descending order");
			System.err.println("-------------------------------------------------------");
			System.err.println("");
			List<InstrumentDescriptor> plist = bir.listAcquisitionInstruments();
			for (int i = 0; i < plist.size(); i++ ) {
				InstrumentDescriptor id = plist.get(i);
				System.err.printf("Priority: %4d -> %10.10s : %4d\n",
						i,
						id.getInstrumentName(), 
						bir.getCapabilitiesProvider(id).getCapabilities().getAcquisitionPriority());
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
