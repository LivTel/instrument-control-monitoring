/**
 * 
 */
package ngat.icm.iss;

import ngat.message.base.COMMAND;
import ngat.message.base.COMMAND_DONE;
import ngat.net.JMSExecutionMonitor;
import ngat.net.JMSServerProtocolRequestHandler;

/**
 * @author snf
 *
 */
public class BadCommandHandler implements JMSServerProtocolRequestHandler {
	
	String errorMessage;
	int errorCode;
	
	public BadCommandHandler(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public void handleRequest(COMMAND command, JMSExecutionMonitor monitor) throws Exception {
			COMMAND_DONE done = new COMMAND_DONE("badbad command");
			done.setSuccessful(false);
			done.setErrorNum(errorCode);
			done.setErrorString(errorMessage);
			monitor.setReply(done);
	}


}
