package ngat.icm.test;

import ngat.icm.*;
import ngat.icm.iss.*;

import ngat.net.*;
import ngat.util.*;

import java.net.*;
import ngat.message.base.*;
import ngat.message.ISS_INST.*;

public class TestAgClient {
    
    public static void main(String args[]) {
	
	try {

	    CommandTokenizer parser = new CommandTokenizer("--");
	    parser.parse(args);
	    ConfigurationProperties config = parser.getMap();
	    
	    String host = config.getProperty("host", "localhost");

	    int    port = config.getIntValue("port", 7766);
	    
	    boolean on = (config.getProperty("on") != null);

	    COMMAND command = null;
	    if (on) 
		command = new AG_START("test");
	    else
		command = new AG_STOP("test");

	    JMSClientProtocolImplementor impl = new
		JMSClientProtocolImplementor();
	    impl.setTimeout(80000L);

	    impl.implementProtocol(new TestResponseHandler(),
				   new SocketConnection(host,port),
				   command);
	    
	} catch (Exception e) {
	    e.printStackTrace();

	    System.err.println("Usage: java ngat.icm.test.TestAgClient --host <host> --port <port> [--on]");
	    return;
	}
	
    }

}
