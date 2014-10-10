/**
 * 
 */
package ngat.icm.test;

import ngat.message.ISS_INST.ACQUIRE;
import ngat.message.base.COMMAND;
import ngat.net.JMSClientProtocolImplementor;
import ngat.net.SocketConnection;
import ngat.phase2.TelescopeConfig;
import ngat.util.CommandTokenizer;
import ngat.util.ConfigurationProperties;
import ngat.util.logging.BasicLogFormatter;
import ngat.util.logging.ConsoleLogHandler;
import ngat.util.logging.LogManager;
import ngat.util.logging.Logger;

/** Send an ACQUIRE command to an instrument.
 * @author eng
 *
 */
public class SendAcquireTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			Logger jmslog = LogManager.getLogger("JMS");
			jmslog.setLogLevel(5);
			ConsoleLogHandler console = new ConsoleLogHandler(new BasicLogFormatter(150));
			console.setLogLevel(5);
			jmslog.addHandler(console);
			
		    CommandTokenizer parser = new CommandTokenizer("--");
		    parser.parse(args);
		    ConfigurationProperties config = parser.getMap();
		    
		    String host = config.getProperty("host", "localhost");

		    int    port = config.getIntValue("port", 7766);
		
		    double ra = Math.toRadians(config.getDoubleValue("ra", 120.0));
		    double dec = Math.toRadians(config.getDoubleValue("dec", 50.0));
		    
		    ACQUIRE acq = new ACQUIRE("test");
		    acq.setId("AcqTest");
		    acq.setAcquisitionMode(TelescopeConfig.ACQUIRE_MODE_BRIGHTEST);
		    acq.setXPixel(512);
		    acq.setYPixel(512);
		    acq.setThreshold(1.2);
		    acq.setRA(ra);
		    acq.setDec(dec);
		    System.err.println("SendAcquireTest: Setting non-sidereal parameters");
		    acq.setRARate(0.5);
		    acq.setDecRate(0.5);
		    acq.setCalculationTime(System.currentTimeMillis());
		    acq.setMoving(false);
		    
		    System.err.println("SendAcquireTest: Connecting to: "+host+":"+port);
		    
		    JMSClientProtocolImplementor impl = new
			JMSClientProtocolImplementor(true); // debug logging on
		    impl.setTimeout(80000L);

		    AcquireResponseHandler gfh = new AcquireResponseHandler();
		    
		    impl.implementProtocol(gfh,
					   new SocketConnection(host,port),
					   acq);
		   	  
		    
		} catch (Exception e) {
		    e.printStackTrace();

		    System.err.println("Usage: java ngat.icm.test.SendAcquireTest --host <host> --port <port> ]");
		    return;
		}
		
		
		
		

	}

}
