/**
 * 
 */
package ngat.icm.test;

import java.util.Vector;

import ngat.message.ISS_INST.GET_FITS_DONE;
import ngat.message.base.COMMAND_DONE;
import ngat.net.TestResponseHandler;

/**
 * @author snf
 *
 */
public class GetFitsResponseHandler extends TestResponseHandler {

	public Vector headers;
	
	public void handleDone(COMMAND_DONE done) throws Exception {
		if (! done.getSuccessful()) {
			System.err.println("Command ["+done.getId()+"] failed due to: "+done.getErrorNum()+" : "+done.getErrorString());
		} else {
			if (done instanceof GET_FITS_DONE) {
				GET_FITS_DONE gfd = (GET_FITS_DONE)done;
				headers = gfd.getFitsHeader();
			}
		}
	}
	
}
