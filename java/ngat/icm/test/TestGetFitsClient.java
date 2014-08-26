package ngat.icm.test;

import ngat.message.ISS_INST.GET_FITS;
import ngat.message.base.COMMAND;
import ngat.net.JMSClientProtocolImplementor;
import ngat.net.SocketConnection;
import ngat.net.TestResponseHandler;
import ngat.util.*;

public class TestGetFitsClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		try {

		    CommandTokenizer parser = new CommandTokenizer("--");
		    parser.parse(args);
		    ConfigurationProperties config = parser.getMap();
		    
		    String host = config.getProperty("host", "localhost");

		    int    port = config.getIntValue("port", 7766);
		    
		    COMMAND command = new GET_FITS("test");
		  
		    JMSClientProtocolImplementor impl = new
			JMSClientProtocolImplementor();
		    impl.setTimeout(80000L);

		    GetFitsResponseHandler gfh = new GetFitsResponseHandler();
		    
		    impl.implementProtocol(gfh,
					   new SocketConnection(host,port),
					   command);
		    
		    System.err.println("GetFits: Returned with: "+gfh.headers);
		    
		} catch (Exception e) {
		    e.printStackTrace();

		    System.err.println("Usage: java ngat.icm.test.TestGetFitsClient --host <host> --port <port> ]");
		    return;
		}
		
	}

}
