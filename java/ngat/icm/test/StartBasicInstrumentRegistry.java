/**
 * 
 */
package ngat.icm.test;

import java.io.File;

import ngat.icm.BasicInstrumentRegistry;
import ngat.util.XmlConfigurator;

/**
 * @author eng
 *
 */
public class StartBasicInstrumentRegistry {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			
			BasicInstrumentRegistry bir = new BasicInstrumentRegistry();		
			XmlConfigurator.use(new File(args[0])).configure(bir);
					
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
