/**
 * 
 */
package ngat.icm.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import ngat.icm.BasicInstrument;
import ngat.icm.BasicInstrumentRegistry;
import ngat.icm.InstrumentController;
import ngat.icm.InstrumentDescriptor;

/**
 * Load a registry then look for an element chosen by the user
 * 
 * @author snf
 * 
 */
public class LoadRegistry {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			BasicInstrumentRegistry bir = new BasicInstrumentRegistry();

			InstrumentDescriptor ratcamId = new InstrumentDescriptor("RATCAM");
			ratcamId.setInstrumentClass("IMAGER_CCD");
			ratcamId.setInstrumentModel("EEV 42-40");
			ratcamId.setManufacturer("Marconi");
			ratcamId.setSerialNumber("1278-B7");
			BasicInstrument ratcam = new BasicInstrument(ratcamId);
			System.err.println("Adding " + ratcam);
			bir.addInstrument(ratcamId,ratcam);

			InstrumentDescriptor supircamId = new InstrumentDescriptor(
					"SUPIRCAM");
			supircamId.setInstrumentClass("IMAGER_IR");
			supircamId.setInstrumentModel("PICNIC HgCdTe");
			supircamId.setManufacturer("IR Labs");
			supircamId.setSerialNumber("767279884");
			BasicInstrument supircam = new BasicInstrument(supircamId);
			System.err.println("Adding " + supircam);
			bir.addInstrument(supircamId,supircam);

			InstrumentDescriptor ringoId = new InstrumentDescriptor("RINGO");
			ringoId.setInstrumentClass("POLARIMETER");
			ringoId.setInstrumentModel("AIMO E2V CCD42-40");
			ringoId.setManufacturer("Apogee");
			ringoId.setSerialNumber("786868-N76");
			BasicInstrument ringo = new BasicInstrument(ringoId);
			System.err.println("Adding " + ringo);
			bir.addInstrument(ringoId,ringo);

			// All in place, lets list them out...

			Iterator insts = bir.listInstruments().iterator();
			while (insts.hasNext()) {
				Object obj = insts.next();
				System.err.println("List, found a " + obj.getClass().getName());
				// BasicInstrument bi = (BasicInstrument)insts.next();
				// System.err.println("List, found: "+bi);
			}

			// now lets see if we can find one by name..
			InputStream in = System.in;
			BufferedReader bin = new BufferedReader(new InputStreamReader(in));

			String srcName = null;
			String srcModel = null;
			do {
				System.err.print("Please enter an instrument name: ? ");
				srcName = bin.readLine();
				if (srcName != null) {
					InstrumentDescriptor srcId = new InstrumentDescriptor(
							srcName);
					System.err.print("Looking for: "
							+ srcId.getInstrumentName() + "...");
					InstrumentController bic = bir.getController(srcId);
					if (bic == null) {
						System.err.println("NO controller for: " + srcName);
					} else {
						System.err.println("Found controller: " + bic);
						System.err.println(", about to run test...");
						bic.test("hello this is a test");
					}

				}
			} while (srcName != null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
