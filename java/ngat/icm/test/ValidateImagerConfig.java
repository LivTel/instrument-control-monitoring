/**
 * 
 */
package ngat.icm.test;

import ngat.icm.Filter;
import ngat.icm.FilterSet;
import ngat.icm.Imager;
import ngat.phase2.XDetectorConfig;
import ngat.phase2.XFilterDef;
import ngat.phase2.XFilterSpec;
import ngat.phase2.XImagerInstrumentConfig;
import ngat.phase2.XWindow;

/** Validate a supplied imager config
 * @author eng
 *
 */
public class ValidateImagerConfig {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			// Make up an Imager..
			
			Imager imager = new Imager();
			
			FilterSet lower = new FilterSet();
			lower.addFilter(new Filter("SDSS-R", "sloane-r"));
			lower.addFilter(new Filter("SDSS-B", "sloane-b"));
			lower.addFilter(new Filter("SDSS-U", "sloane-u"));
			lower.addFilter(new Filter("SDSS-V", "sloane-v"));
			lower.addFilter(new Filter("H-Alpha", "halpha"));
			
			imager.addFilterSet("lower",lower);
			
			FilterSet upper = new FilterSet();
			upper.addFilter(new Filter("Bessell-G", "g"));
			upper.addFilter(new Filter("Bessell-I", "i'"));
			upper.addFilter(new Filter("Bessell-Z", "z"));
			upper.addFilter(new Filter("Bessell-B", "b"));
			upper.addFilter(new Filter("ND-100", "neutral"));
			
			imager.addFilterSet("upper",upper);
			
			// Make up a test filter spec for an imager config
			
			String f1 = args[0];
			String f2 = args[1];
			XImagerInstrumentConfig xim = new XImagerInstrumentConfig("test-B");
			xim.setInstrumentName("RATCAM");		
			XFilterSpec xf = new XFilterSpec();
			xf.addFilter(new XFilterDef(f1));
			xf.addFilter(new XFilterDef(f2));
			xim.setFilterSpec(xf);
			
			XDetectorConfig xdc = new XDetectorConfig();			
			xdc.setXBin(2);
			xdc.setYBin(2);
			xdc.addWindow(new XWindow(252,512,1024,1024));
			xim.setDetectorConfig(xdc);
			
			System.err.println("Specified config: "+xim);
			
			System.err.println("Testing...");
			
			boolean cando = imager.isValidConfiguration(xim);
			
			System.err.println("Test result: "+(cando ? "success":"failure"));
			
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
