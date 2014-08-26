/**
 * 
 */
package ngat.icm.iss;

import ngat.message.ISS_INST.INST_TO_ISS;
import ngat.message.base.COMMAND;
import ngat.net.JMSServerProtocolRequestHandler;
import ngat.net.JMSServerProtocolRequestHandlerFactory;
import ngat.tcm.Telescope;

/**
 * @author snf
 *
 */
public class ISSRequestHandlerFactory implements
		JMSServerProtocolRequestHandlerFactory {
	
	Iss iss;
	
	Telescope telescope;
	
	/**
	 * @param telescope
	 */
	public ISSRequestHandlerFactory(Iss iss, Telescope telescope) {
		// TODO Auto-generated constructor stub
		this.iss = iss;
		this.telescope = telescope;
	}

	/* (non-Javadoc)
	 * @see ngat.net.JMSServerProtocolRequestHandlerFactory#createRequestHandler()
	 */
	public JMSServerProtocolRequestHandler createRequestHandler(COMMAND command) {
	    
	    System.err.println("ISS_ReqHdlrFacty::Received command: "+command);
	    
		if (! (command instanceof INST_TO_ISS))
			return new BadCommandHandler(666, "illegal command class");
		
		return new IssGenericCommandHandler(iss, telescope);
			
		}

}
