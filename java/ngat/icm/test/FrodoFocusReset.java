package ngat.icm.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.SimpleTimeZone;

import ngat.message.ISS_INST.GET_STATUS;
import ngat.message.ISS_INST.GET_STATUS_DONE;
import ngat.message.ISS_INST.REBOOT;
import ngat.message.ISS_INST.REBOOT_DONE;
import ngat.message.base.ACK;
import ngat.message.base.COMMAND_DONE;
import ngat.net.JMSClientProtocolImplementor;
import ngat.net.JMSClientProtocolResponseHandler;
import ngat.net.SocketConnection;
import ngat.util.CommandTokenizer;
import ngat.util.ConfigurationProperties;
import ngat.util.logging.BasicLogFormatter;
import ngat.util.logging.ConsoleLogHandler;
import ngat.util.logging.LogGenerator;
import ngat.util.logging.LogManager;
import ngat.util.logging.Logger;

public class FrodoFocusReset {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
	private SimpleDateFormat fdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private SimpleTimeZone UTC = new SimpleTimeZone(0, "UTC");
	private static String FRODO_FOCUS_KEY_RED_LOW = "frodospec.focus.red.low.value";
	private static String FRODO_FOCUS_KEY_RED_HIGH = "frodospec.focus.red.high.value";
	private static String FRODO_FOCUS_KEY_BLUE_LOW = "frodospec.focus.blue.low.value";
	private static String FRODO_FOCUS_KEY_BLUE_HIGH = "frodospec.focus.blue.high.value";

	/** ICS host address. */
	private String host;

	/** ICS port. */
	private int port;

	/** True if we should reboot. */
	private boolean doReboot;

	/** LOCAL config file. */
	private String configFileName;

	// conversion params...TBD

	/** Logger. */
	private LogGenerator logger;

	private double frodoRedOffsetLow;
	private double frodoRedGradientLow;
	private double frodoBlueOffsetLow;
	private double frodoBlueGradientLow;

	private double frodoRedOffsetHigh;
	private double frodoRedGradientHigh;
	private double frodoBlueOffsetHigh;
	private double frodoBlueGradientHigh;

	/**
	 * 
	 */
	public FrodoFocusReset() {

		sdf.setTimeZone(UTC);
		fdf.setTimeZone(UTC);
		
		Logger alogger = LogManager.getLogger("");
		alogger.setLogLevel(5);
		logger = alogger.generate().system("ICM").subSystem("OpticalAlignment")
				.srcCompClass(this.getClass().getSimpleName()).srcCompId("f1");
		ConsoleLogHandler console = new ConsoleLogHandler(new BasicLogFormatter(150));
		console.setLogLevel(5);

		alogger.addExtendedHandler(console);

	}

	private void configure(ConfigurationProperties config) throws Exception {

		logger.create().info().level(3).extractCallInfo().msg("Starting configuration").send();

		// comms
		host = config.getProperty("host", "localhost");
		port = config.getIntValue("port", 1024);

		doReboot = (config.getProperty("reboot") != null);

		configFileName = config.getProperty("config");

		// focus calculation
		frodoRedOffsetLow = config.getDoubleValue("redOffsetLow");
		frodoRedGradientLow = config.getDoubleValue("redGradientLow");
		frodoBlueOffsetLow = config.getDoubleValue("blueOffsetLow");
		frodoBlueGradientLow = config.getDoubleValue("blueGradientLow");

		frodoRedOffsetHigh = config.getDoubleValue("redOffsetHigh");
		frodoRedGradientHigh = config.getDoubleValue("redGradientHigh");
		frodoBlueOffsetHigh = config.getDoubleValue("blueOffsetHigh");
		frodoBlueGradientHigh = config.getDoubleValue("blueGradientHigh");

		logger.create().info().level(3).extractCallInfo().msg("Configuration completed successfully").send();

	}

	private void exec() throws Exception {
		
		logger.create().info().level(3).extractCallInfo().msg("Starting execution").send();
		
		// Get latest environment status
		GET_STATUS getStatusCommand = new GET_STATUS("frodo_focus_reset");
		getStatusCommand.setLevel(1);

		JMSClientProtocolImplementor jmsStatus = new JMSClientProtocolImplementor();
		jmsStatus.setTimeout(20000L);

		FocusResponseHandler fhandler = new FocusResponseHandler();
		SocketConnection connection = new SocketConnection(host, port);

		logger.create().info().level(3).extractCallInfo().msg("Sending GET_STATUS level 1 to: "+host+":"+port).send();
		
		jmsStatus.implementProtocol(fhandler, connection, getStatusCommand);

		Hashtable statusMap = fhandler.getHash();

		//System.err.println("Map: " + statusMap);

		// extract t.0 (red) and t.2 (blue) ?? or other way round
		double tRed = Double.parseDouble((String) (statusMap.get("Environment.Temperature.0")));
		double tBlue = Double.parseDouble((String) (statusMap.get("Environment.Temperature.2")));

		logger.create().info().level(3).extractCallInfo().msg(
				String .format("Found env temperatures: Red: %4.2f , Blue: %4.2f \n", tRed, tBlue)).send();
		
		// Compute correct focus values for config
		logger.create().info().level(3).extractCallInfo().msg("Computing focus adjustments...").send();
		
		double focusRedLow   = frodoRedOffsetLow + frodoRedGradientLow*tRed;
		double focusRedHigh  = frodoRedOffsetHigh + frodoRedGradientHigh*tRed;
		double focusBlueLow  = frodoBlueOffsetLow + frodoBlueGradientLow*tBlue;
		double focusBlueHigh = frodoBlueOffsetHigh + frodoBlueGradientHigh*tBlue;
	
		logger.create().info().level(3).extractCallInfo().msg("Red  (L): "+focusRedLow).send();
		logger.create().info().level(3).extractCallInfo().msg("Red  (H): "+focusRedHigh).send();
		logger.create().info().level(3).extractCallInfo().msg("Blue (L): "+focusBlueLow).send();
		logger.create().info().level(3).extractCallInfo().msg("Blue (H): "+focusBlueHigh).send();
		
		// Modify the config file (must be running on frodospec1 to do this).
		logger.create().info().level(3).extractCallInfo().msg("Modifying local file: "+configFileName+"...TBD").send();
		
		// open file read lines, rewrite
		String tempFileName = configFileName+".AUTOTMP";
		BufferedReader bin = new BufferedReader(new FileReader(configFileName));
		PrintWriter pout = new PrintWriter(new FileWriter(tempFileName));
		
		String line = null;
		while ((line = bin.readLine()) != null) {
			
			String tline = line.trim();
			
			if (tline.startsWith(FRODO_FOCUS_KEY_RED_LOW)) {
				pout.println("# Auto updated: "+sdf.format(new Date())+ " : "+tline);
				pout.println(FRODO_FOCUS_KEY_RED_LOW+" = "+String.format("%6.4f \n",focusRedLow));
			} else if
			(tline.startsWith(FRODO_FOCUS_KEY_RED_HIGH)) {
				pout.println("# Auto updated: "+sdf.format(new Date())+ " : "+tline);
				pout.println(FRODO_FOCUS_KEY_RED_HIGH+" = "+String.format("%6.4f \n",focusRedHigh));
			} else if
			(tline.startsWith(FRODO_FOCUS_KEY_BLUE_LOW)) {
				pout.println("# Auto updated: "+sdf.format(new Date())+ " : "+tline);
				pout.println(FRODO_FOCUS_KEY_BLUE_LOW+" = "+String.format("%6.4f \n",focusBlueLow));
			} else if
			(tline.startsWith(FRODO_FOCUS_KEY_BLUE_HIGH)) {
				pout.println("# Auto updated: "+sdf.format(new Date())+ " : "+tline);
				pout.println(FRODO_FOCUS_KEY_BLUE_HIGH+" = "+String.format("%6.4f \n",focusBlueHigh));
			} else {
				pout.println(line);				
			}
		}
		
		// move tmp file into file
		String backupfileName = configFileName+".BAK_"+fdf.format(new Date());
		logger.create().info().level(3).extractCallInfo().msg("Backing up to: "+backupfileName).send();
		
		File configFile = new File(configFileName);
		File backupFile = new File(backupfileName);
		configFile.renameTo(backupFile);
		
		File tempFile = new File(tempFileName);
		tempFile.renameTo(configFile);
		logger.create().info().level(3).extractCallInfo().msg("Config file was overwritten").send();
		
		// Issue reboot level 2 or wait for actual daily reboot ?
		if (doReboot) {
		REBOOT rebootCommand = new REBOOT("frodo_focus_reboot");
		rebootCommand.setLevel(2);

		JMSClientProtocolImplementor jms2 = new JMSClientProtocolImplementor();
		jms2.setTimeout(30000L);
		RebootResponseHandler rhandler = new RebootResponseHandler();

		SocketConnection connection2 = new SocketConnection(host, port);

		logger.create().info().level(3).extractCallInfo().msg("Sending REBOOT level 2 to: "+host+":"+port).send();
		jms2.implementProtocol(rhandler, connection2, rebootCommand);

		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		FrodoFocusReset task = new FrodoFocusReset();

		try {

			ConfigurationProperties config = CommandTokenizer.use("--").parse(args);
			task.configure(config);

			task.exec();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * A handler for GET_STATUS requests sent to ICS.
	 * 
	 * @author eng
	 * 
	 */
	private class FocusResponseHandler implements JMSClientProtocolResponseHandler {

		/** Stores hashtable response from GET_STATUS_DONE. */
		private Hashtable hash = null;

		/** Stores currentMode from GET_STATUS_DONE. */
		private int mode;

		/** True if the reply was in error. */
		private volatile boolean error = true;

		/** Possible error code. */
		private volatile int errCode;

		/** Possible error message. */
		private String errMsg;

		public void handleAck(ACK ack) throws Exception {
			logger.create().info().level(3).extractCallInfo().msg("Received ACK: " + ack).send();
		}

		public void handleDone(COMMAND_DONE done) throws Exception {

			logger.create().info().level(3).extractCallInfo().msg("Received CMD_DONE: " + done).send();

			if (done.getSuccessful()) {

				if (done instanceof GET_STATUS_DONE) {
					error = false;
					GET_STATUS_DONE gsd = (GET_STATUS_DONE) done;
					mode = gsd.getCurrentMode();
					hash = gsd.getDisplayInfo();

				} else {
					error = true;
					errCode = 111;
					errMsg = "Unknown response class: " + done.getClass().getName();

				}

			} else {
				error = true;
				errCode = done.getErrorNum();
				errMsg = done.getErrorString();

			}

		}

		/**
		 * @return the hash
		 */
		public Hashtable getHash() {
			return hash;
		}

		/**
		 * @return the mode
		 */
		public int getMode() {
			return mode;
		}

		/**
		 * @return the error
		 */
		public boolean isError() {
			return error;
		}

		/**
		 * @return the errCode
		 */
		public int getErrCode() {
			return errCode;
		}

		/**
		 * @return the errMsg
		 */
		public String getErrMsg() {
			return errMsg;
		}

	}

	/**
	 * A handler for GET_STATUS requests sent to ICS.
	 * 
	 * @author eng
	 * 
	 */
	private class RebootResponseHandler implements JMSClientProtocolResponseHandler {

		/** True if the reply was in error. */
		private volatile boolean error = true;

		/** Possible error code. */
		private volatile int errCode;

		/** Possible error message. */
		private String errMsg;

		public void handleAck(ACK ack) throws Exception {
			logger.create().info().level(3).extractCallInfo().msg("Received ACK: " + ack).send();
		}

		public void handleDone(COMMAND_DONE done) throws Exception {

			logger.create().info().level(3).extractCallInfo().msg("Received CMD_DONE: " + done).send();

			if (done.getSuccessful()) {

				if (done instanceof REBOOT_DONE) {
					error = false;
					REBOOT_DONE gsd = (REBOOT_DONE) done;

				} else {
					error = true;
					errCode = 111;
					errMsg = "Unknown response class: " + done.getClass().getName();

				}

			} else {
				error = true;
				errCode = done.getErrorNum();
				errMsg = done.getErrorString();

			}

		}

		/**
		 * @return the error
		 */
		public boolean isError() {
			return error;
		}

		/**
		 * @return the errCode
		 */
		public int getErrCode() {
			return errCode;
		}

		/**
		 * @return the errMsg
		 */
		public String getErrMsg() {
			return errMsg;
		}

	}

}
