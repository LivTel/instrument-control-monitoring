/**
 * 
 */
package ngat.icm.test;

import java.rmi.Naming;
import java.util.Iterator;

import ngat.icm.DetectorCapabilities;
import ngat.icm.InstrumentCapabilities;
import ngat.icm.InstrumentCapabilitiesProvider;
import ngat.icm.InstrumentDescriptor;
import ngat.icm.InstrumentRegistry;

/**
 * Pulls instrument capabilities off the IREG.
 * 
 * @author snf
 * 
 */
public class CapClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			InstrumentRegistry ireg = (InstrumentRegistry) Naming.lookup("InstrumentRegistry");
			System.err.println("Located IREG in rmi registry");

			Iterator insts = ireg.listInstruments().iterator();
			while (insts.hasNext()) {
				InstrumentDescriptor id = (InstrumentDescriptor) insts.next();
				System.err.println("Instrument: " + id.getInstrumentName() + "\n     class: " + id.getInstrumentClass()
						+ "\n       mfr: " + id.getManufacturer() + "\n     model: " + id.getInstrumentModel()
						+ "\n     serNo: " + id.getSerialNumber());

				InstrumentCapabilitiesProvider icp = ireg.getCapabilitiesProvider(id);
				System.err.println("Located Component Provider: " + icp.getClass().getName());

				InstrumentCapabilities icap = icp.getCapabilities();
				System.err.println("Located capabilities: " + icap);

				//System.err.println("  Capabilities:" + "\n  Acquire "
					//	+ "\n   Offset: " + icap.getAcquisitionTargetPosition() + "\n  Aperture: "
					//	+ icap.getApertureOffset() + "\n  Rotation: " + Math.toDegrees(icap.getRotatorOffset())
					//	+ "\n  SkyP:" + icap.isSkyModelProvider());

				DetectorCapabilities detector = icap.getDetectorCapabilities();
				System.err.println("    Detector: " + detector);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
