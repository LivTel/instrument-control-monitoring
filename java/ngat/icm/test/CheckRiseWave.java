/**
 * 
 */
package ngat.icm.test;

import ngat.icm.Imager;
import ngat.icm.Wavelength;
import ngat.phase2.XDetectorConfig;
import ngat.phase2.XFilterSpec;
import ngat.phase2.XImagerInstrumentConfig;

/**
 * @author eng
 *
 */
public class CheckRiseWave {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			XImagerInstrumentConfig risecfg = new XImagerInstrumentConfig("test");
			XDetectorConfig dc = new XDetectorConfig();
			dc.setName("dctest");
			dc.setXBin(1);
			dc.setYBin(1);
			
			risecfg.setDetectorConfig(dc);
			
			XFilterSpec filt = new XFilterSpec();
			filt.addFilter(null);
			
			risecfg.setFilterSpec(filt);
			
			System.err.println("Testing config: "+risecfg);
			
			Imager rise = new Imager();
			
			Wavelength wave = rise.getWavelength(risecfg);
			
			System.err.println("Extracted wavelength: "+wave.getValueNm());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
