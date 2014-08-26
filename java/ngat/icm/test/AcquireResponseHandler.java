/**
 * 
 */
package ngat.icm.test;

import ngat.message.ISS_INST.ACQUIRE_DONE;
import ngat.message.base.COMMAND_DONE;
import ngat.net.TestResponseHandler;

/**
 * @author eng
 *
 */
public class AcquireResponseHandler extends TestResponseHandler {
	
	public void handleDone(COMMAND_DONE done) throws Exception {
		if (! done.getSuccessful()) {
			System.err.println("Command ["+done.getId()+"] failed due to: "+done.getErrorNum()+" : "+done.getErrorString());
		} else {
			if (done instanceof ACQUIRE_DONE) {
				ACQUIRE_DONE acqd = (ACQUIRE_DONE)done;
				System.err.println("Command ["+done.getId()+"] Succeeded");
			}
		}
	}
}
