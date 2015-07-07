/**
 * 
 */
package ngat.icm;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.jdom.Element;

import ngat.message.ISS_INST.GET_STATUS;
import ngat.message.ISS_INST.GET_STATUS_DONE;
import ngat.message.base.ACK;
import ngat.message.base.COMMAND;
import ngat.message.base.COMMAND_DONE;
import ngat.net.JMSClientProtocolImplementor;
import ngat.net.JMSClientProtocolResponseHandler;
import ngat.net.SocketConnection;
import ngat.phase2.IExposure;
import ngat.phase2.IInstrumentConfig;
import ngat.util.logging.LogGenerator;
import ngat.util.logging.LogManager;
import ngat.util.logging.Logger;

/**
 * @author snf
 * 
 */
/**
 * @author eng
 * 
 */
public class BasicInstrument extends UnicastRemoteObject implements InstrumentController, InstrumentStatusProvider,
		InstrumentCapabilitiesProvider, InstrumentCalibrationProvider {

	/** The instrument descriptor. */
	protected InstrumentDescriptor id;

	/** Status update listeners. */
	protected List listeners;

	/** Listeners scheduled for removal. */
	protected List unresponsiveListeners;

	private InstrumentCapabilities capabilities;

	private InstrumentCalibration calib;

	private InstrumentCalibrationHistory calibHistory;

	private InstrumentStatus status;

	private boolean enabled = true; // unless otherwise specified

	private IInstrumentConfig config;

	private ControllerProxy controllerProxy;

	private LogGenerator logger;

	/**
	 * @throws RemoteException
	 */
	public BasicInstrument() throws RemoteException {
		super();
		listeners = new Vector();
		unresponsiveListeners = new Vector();
		status = new InstrumentStatus();
		Logger alogger = LogManager.getLogger("ICM");
		logger = alogger.generate().system("ICM").subSystem("").srcCompClass(this.getClass().getSimpleName());
	}

	/**
	 * @param port
	 * @throws RemoteException
	 */
	public BasicInstrument(InstrumentDescriptor id) throws RemoteException {
		this();
		this.id = id;
		logger.srcCompId(id.getInstrumentName());
	}

	public InstrumentDescriptor getId() {
		return id;
	}

	/** Do a simple test of some sort. */
	public void test(String msg) throws RemoteException {
		System.err.println("This is instrument controller for " + id.getInstrumentName() + "/"
				+ id.getInstrumentModel() + " and the test message is:  " + msg);
	}

	public void configure() throws RemoteException {
		logger.create().info().level(2).extractCallInfo().msg("Configuring").send();
	}

	/**
	 * Add a listener to the list of registered InstrumentStatusUpdateListeners.
	 * Fails silently if the listener is already registered.
	 */
	public void addInstrumentStatusUpdateListener(InstrumentStatusUpdateListener ul) throws RemoteException {
		if (listeners.contains(ul))
			return;
		listeners.add(ul);

		logger.create().info().level(3).extractCallInfo().msg("Registering listener: " + ul).send();
	}

	/**
	 * Remove a listener from he list of registered
	 * InstrumentStatusUpdateListeners. Fails silently if the listener is not
	 * actually registered.
	 */
	public void removeInstrumentStatusUpdateListener(InstrumentStatusUpdateListener ul) throws RemoteException {
		if (!listeners.contains(ul))
			return;
		// dangerous, needs synchronizing with listener updates
		listeners.remove(ul);
		logger.create().info().level(3).extractCallInfo().msg("De-registering listener: " + ul).send();
	}

	/** @return The current status of the instrument. */
	public InstrumentStatus getStatus() throws RemoteException {
		return status;
	}

	/** @return the current instrument configuration. */
	public IInstrumentConfig getCurrentConfig() throws RemoteException {
		return config;
	}

	public void setCurrentConfig(IInstrumentConfig config) {
		this.config = config;
	}

	public void setControllerProxy(ControllerProxy controllerProxy) {
		this.controllerProxy = controllerProxy;
	}

	public void configure(Element instrNode) throws Exception {
		System.err.println("Parse basic instrument using node: " + instrNode);

		String iName = instrNode.getAttributeValue("name");
		String iType = instrNode.getAttributeValue("type");

		String iEnab = instrNode.getAttributeValue("enabled");
		if (iEnab.equalsIgnoreCase("true"))
			enabled = true;
		else
			enabled = false;

		System.err.println("Instrument: " + iName + " is a " + iType);
		Element dNode = instrNode.getChild("descriptor");
		id = parseDescriptor(iName, dNode);

		System.err.println("Instrument " + (enabled ? "is enabled" : "is disabled"));

		// configure generic capabilities
		BasicInstrumentCapabilities icap = parseCapabilities(instrNode);
		setCapabilities(icap);

	}

	private InstrumentDescriptor parseDescriptor(String name, Element dnode) throws Exception {

		InstrumentDescriptor instId = new InstrumentDescriptor(name);
		String alias = dnode.getAttributeValue("alias");
		if (alias == null || alias.equals(""))
			alias = name;
		instId.setAlias(alias);
		instId.setInstrumentClass(dnode.getChildTextTrim("class"));
		return instId;

	}

	private BasicInstrumentCapabilities parseCapabilities(Element inode) throws Exception {

		String type = inode.getAttributeValue("type");

		BasicInstrumentCapabilities icap = null;

		Element cfgNode = inode.getChild("config");

		// configure the icap type based on the type string above
		if (type.equalsIgnoreCase("imager")) {
			icap = new Imager();
			icap.configure(cfgNode);
		} else if (type.equalsIgnoreCase("tiptiltimager")) {
			icap = new TipTiltImager();
			icap.configure(cfgNode);
		} else if (type.equalsIgnoreCase("polarimeter")) {
			icap = new Polarimeter();
			icap.configure(cfgNode);
		} else if (type.equalsIgnoreCase("dualbeamspectrograph")) {
			icap = new DualBeamSpectrograph();
			icap.configure(cfgNode);
		} else if (type.equalsIgnoreCase("imagingspectrograph")) {
			icap = new ImagingSpectrograph();
			icap.configure(cfgNode);
		} else if (type.equalsIgnoreCase("bluetwoslitspectrograph")) {
			icap = new BlueTwoSlitSpectrograph();
			icap.configure(cfgNode);
		} else {
			throw new Exception("Unknown instrument type: " + type);
		}

		return icap;
	}

	/**
	 * Notify any registered listeners of a status update. If a listeners fails
	 * to accept the update it is automatically removed from the list.
	 * 
	 * @param status
	 *            The status to notify to the registered listeners.
	 */
	protected void notifyListeners(InstrumentStatus status) {

		// copy status into a local version
		InstrumentStatus mstatus = new InstrumentStatus();
		mstatus.setInstrument(id);
		mstatus.setStatusTimeStamp(status.getStatusTimeStamp());
		
		// block to remove for now till we find out the answer to the question below !
		/*if (enabled) {
			mstatus.setEnabled(true);
			mstatus.setOnline(status.isOnline());
			mstatus.setFunctional(status.isFunctional());
			if (status.getStatus() != null)
				mstatus.setStatus(new HashMap(status.getStatus()));
		} else {
			System.err.println("InstMonitor [" + id.getInstrumentName() + " notify: disabled");
			mstatus.setEnabled(false);
		}*/
		
		// new block replacing one above - weird, why would we not update the status elements ?
		mstatus.setEnabled(enabled);
		mstatus.setOnline(status.isOnline());
		mstatus.setFunctional(status.isFunctional());
		if (status.getStatus() != null)
			mstatus.setStatus(new HashMap(status.getStatus()));
		
		
		mstatus.setNs(status.getNs());
		// System.err.println("InstMonitor [" + id.getInstrumentName() +
		// "]: Purging unresponsive listeners...");
		// first remove any dodgy candidates from the list
		for (int k = 0; k < unresponsiveListeners.size(); k++) {
			InstrumentStatusUpdateListener ul = (InstrumentStatusUpdateListener) unresponsiveListeners.get(k);
			if (listeners.contains(ul)) {

				try {
					listeners.remove(ul);
					System.err.println("InstMonitor [" + id.getInstrumentName() + "]: Removing unresponsive listener: "
							+ ul);
					logger.create().info().level(3).extractCallInfo().msg("Removing unresponsive listener: " + ul)
							.send();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// System.err.println("InstMonitor [" + id.getInstrumentName() +
		// "]: Notifying active listeners...");
		for (int i = 0; i < listeners.size(); i++) {
			InstrumentStatusUpdateListener ul = (InstrumentStatusUpdateListener) listeners.get(i);
			try {
				ul.instrumentStatusUpdated(mstatus);
			} catch (Exception e) {
				e.printStackTrace();
				logger.create().info().level(3).extractCallInfo().msg("Noting unresponsive listener: " + ul).send();
				unresponsiveListeners.add(ul);
			}
		}

	}

	/** Start a monitoring test. */
	public void startMonitorTest(long interval) {
		MonitorTestThread mtt = new MonitorTestThread(interval);
		mtt.start();
	}

	private class MonitorTestThread extends Thread {
		private long interval;

		MonitorTestThread(long interval) {
			super("MTT");
			this.interval = interval;
		}

		public void run() {
			Hashtable test = new Hashtable();
			while (true) {
				test.put("temperature", "" + (Math.random() * 50.0));
				test.put("binning", "2x2");
				test.put("exposure", "" + (Math.random() * 100000));
				InstrumentStatus testStatus = new InstrumentStatus();
				testStatus.setStatus(test);
				testStatus.setOnline(Math.random() < 0.8);
				testStatus.setFunctional(Math.random() < 0.75);
				System.err.println("InstMonitor [" + id.getInstrumentName() + "]: Ready notify listeners...");
				notifyListeners(testStatus);
				try {
					Thread.sleep(interval);
				} catch (InterruptedException ix) {
				}
			}

		}

	}

	/** Start a monitoring test. */
	public void startMonitoring(String host, int port, long interval, HealthMonitor hm) {
		MonitorThread mtt = new MonitorThread(host, port, interval, hm);
		mtt.start();
	}

	/**
	 * A thread to perform monitoring of status.
	 * 
	 * @author eng
	 * 
	 */
	private class MonitorThread extends Thread {

		/** Polling interval. */
		private long interval;

		/** ICS host. */
		private String host;

		/** ICS command port. */
		private int port;

		/**
		 * Handler for instruments which do not provide health status. Null if
		 * instrument supplies its own status
		 */
		private HealthMonitor hm;

		/**
		 * @param host
		 *            The ICS host.
		 * @param port
		 *            ICS command port.
		 * @param interval
		 *            Polling interval.
		 */
		public MonitorThread(String host, int port, long interval, HealthMonitor hm) {
			this.host = host;
			this.port = port;
			this.interval = interval;
			this.hm = hm;
		}

		/** Poll the ICS server with GET_STATUS requests. */
		public void run() {

			int nstat = 0; // count requests
			while (true) {
				try {
					Thread.sleep(interval);
				} catch (InterruptedException ix) {
				}
				nstat++;
				JMSClientProtocolImplementor jms = new JMSClientProtocolImplementor();
				// InstrumentStatus status = new InstrumentStatus();
				try {

					jms.setTimeout(20000L);

					ICSResponseHandler handler = new ICSResponseHandler();

					SocketConnection connection = new SocketConnection(host, port); // RATCAM_ICS@ltsim1

					GET_STATUS command = new GET_STATUS(id.getInstrumentName() + "/" + nstat);
					command.setLevel(1);

					jms.implementProtocol(handler, connection, command);

					status.setStatusTimeStamp(System.currentTimeMillis());
					status.setEnabled(enabled);
					if (handler.isError()) {

						status.setOnline(false); // ? is that true ?

					} else {

						status.setOnline(true);

						Map statusMap = handler.getHash();
						int mode = handler.getMode();

						status.setStatus(statusMap);

						String opstat = (String) statusMap.get(GET_STATUS_DONE.KEYWORD_INSTRUMENT_STATUS);

						// no inst health status available ie keyword missing
						if (opstat == null || "".equals(opstat)) {

							// perhaps we have a health monitor for this
							// instrument so we can deduce the status?
							if (hm != null) {

								// hopefully we have an actual temperature ...
								double temperature = 0.0;
								try {
									temperature = (Double) statusMap.get("Temperature");
									System.err.println("Inst" + id.getInstrumentName() + " : Temp: " + temperature);
								} catch (Exception e) {
									// fill in something useful here
									
								}
								opstat = hm.getTemperatureStatus(temperature);
								statusMap.put(GET_STATUS_DONE.KEYWORD_INSTRUMENT_STATUS, opstat);
								statusMap.put(GET_STATUS_DONE.KEYWORD_DETECTOR_TEMPERATURE_INSTRUMENT_STATUS, opstat);
							} else {
								// we have no idea - have to assume its ok for
								// now
								opstat = GET_STATUS_DONE.VALUE_STATUS_UNKNOWN;
							}

						}

						if (GET_STATUS_DONE.VALUE_STATUS_OK.equals(opstat))
							status.setFunctional(true);// 1
						else if (GET_STATUS_DONE.VALUE_STATUS_WARN.equals(opstat))
							status.setFunctional(true);// 2
						else if (GET_STATUS_DONE.VALUE_STATUS_FAIL.equals(opstat))
							status.setFunctional(false);// 3
						else
							status.setFunctional(true);// 4

					}
				} catch (Exception e) {
					// TODO disabled for logger sanity
					// TODO e.printStackTrace();
					status.setStatusTimeStamp(System.currentTimeMillis());
					status.setOnline(false);
				}

				status.setNs(nstat);
				notifyListeners(status);
			}
		}
	}

	/**
	 * A handler for GET_STATUS requests sent to ICS.
	 * 
	 * @author eng
	 * 
	 */
	private class ICSResponseHandler implements JMSClientProtocolResponseHandler {

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
			// TODO Auto-generated method stub
			// System.err.println("ICSResponseHandler: Recvd ACK: " + ack);

		}

		public void handleDone(COMMAND_DONE done) throws Exception {

			if (done.getSuccessful()) {

				if (done instanceof GET_STATUS_DONE) {
					error = false;
					GET_STATUS_DONE gsd = (GET_STATUS_DONE) done;
					mode = gsd.getCurrentMode();
					hash = gsd.getDisplayInfo();
					// System.err.println("ICSResponseHandler::[" +
					// id.getInstrumentName() + "] STATUS: Mode=" + mode
					// + ", Info=" + hash);

					// here we can do special stuff for individual
					// instruments...

				} else {
					error = true;
					errCode = 111;
					errMsg = "Unknown response class: " + done.getClass().getName();
					logger.create().error().level(3).extractCallInfo()
							.msg("Error in response: " + errCode + ", " + errMsg).send();
				}

			} else {
				error = true;
				errCode = done.getErrorNum();
				errMsg = done.getErrorString();
				logger.create().error().level(3).extractCallInfo().msg("Error in response: " + errCode + ", " + errMsg)
						.send();
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
	 * Perform an exposure.
	 * 
	 * @param exposure
	 *            The Exposure to perform.
	 */
	public void expose(IExposure exposure) throws RemoteException {
		// TODO Auto-generated method stub

	}

	public void setCapabilities(InstrumentCapabilities capabilities) {
		this.capabilities = capabilities;
	}

	public InstrumentCapabilities getCapabilities() throws RemoteException {
		return capabilities;
	}

	public void setCalibrationHistory(InstrumentCalibrationHistory calibHistory) {
		this.calibHistory = calibHistory;
	}

	public InstrumentCalibrationHistory getCalibrationHistory() throws RemoteException {
		return calibHistory;
	}

	public void setCalibrationRequirements(InstrumentCalibration calib) {
		this.calib = calib;
	}

	public InstrumentCalibration getCalibrationRequirements() throws RemoteException {
		return calib;
	}

	public String toString() {
		return (capabilities != null ? capabilities.getClass().getSimpleName() : "Instrument") + id + ":"
				+ (capabilities != null ? capabilities : "");
	}

	public ControllerProxy getControllerProxy() throws RemoteException {
		return controllerProxy;
	}

}
