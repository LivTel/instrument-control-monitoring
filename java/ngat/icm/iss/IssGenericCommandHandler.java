/**
 * 
 */
package ngat.icm.iss;

import java.util.Vector;

import ngat.fits.FitsHeaderCardImage;
import ngat.message.ISS_INST.*;
import ngat.message.base.*;
import ngat.net.*;
import ngat.tcm.*;
import ngat.tcm.test.*;

/**
 * @author snf
 * 
 */
public class IssGenericCommandHandler implements
		JMSServerProtocolRequestHandler {

	Iss iss;

	Telescope telescope;

	public IssGenericCommandHandler(Iss iss, Telescope telescope) {
		this.iss = iss;
		this.telescope = telescope;
	}

	public void handleRequest(COMMAND command, final JMSExecutionMonitor monitor)
			throws Exception {

		System.err.println("ISS_GenericHandler: Received command: " + command);

		if (command instanceof AG_START) {
			Runnable r = new Runnable() {
				public void run() {
					doAgStart(monitor);
				}};
				(new Thread(r)).start();
		} else if (command instanceof AG_STOP) {
			doAgStop(monitor);
		} else if (command instanceof GET_FITS)
			doGetFits(monitor);
	}
	

	private void doGetFits(JMSExecutionMonitor monitor) {
		GET_FITS_DONE done = new GET_FITS_DONE("iss");
		Vector list = new Vector();
		addHeader(list, "TAG_ID");
		addHeader(list, "USER_ID");
		addHeader(list, "PROP_ID");
		addHeader(list, "GROUP_ID");
		addHeader(list, "OBS_ID");
		
		addHeader(list,"AZ");
		addHeader(list,"ALT");
		addHeader(list,"ROT");
		addHeader(list,"ENC");
		addHeader(list,"FOCUS");
		addHeader(list,"MIRR");
		
		addHeader(list,"WIND");
		addHeader(list,"TEMP");
		addHeader(list,"DEW");
		addHeader(list,"HUM");
		addHeader(list,"RAIN");
		
		done.setFitsHeader(list);
		sendDoneToMonitor(done, monitor);
	}

	private void addHeader(Vector list, String keyword) {
		FitsHeaderProvider p = iss.getFitsHeaderProvider(keyword);
		FitsHeaderCardImage card = p.getFitsHeader(keyword);
		list.add(card);
	}

	private void doAgStop(JMSExecutionMonitor monitor) {

		System.err.println("ISS_GenericHandler: Doing AgStop()");
		/*try {
			Autoguider autoguider = telescope.getAutoguider();
			// AgGuideHandler handler = new IssGuideHandler();
			TestScopeResponseHandler th = new TestScopeResponseHandler();
			autoguider.stopGuiding(th);

			// waitfor handler to complete or error for timeout
			System.err
					.println("ISS_GenericHandler: Sent command, acked client (20S),  waiting notification from CIL...");
			monitor.setTimeToCompletion(20000L);
			try {
				th.waitNotification(20000L);
			} catch (InterruptedException x) {
			}

			if (th.isFailed()) {
				sendFailToMonitor(new AG_STOP_DONE("iss"), monitor, th
						.getCode(), th.getMsg());
			} else {
				sendDoneToMonitor(new AG_STOP_DONE("iss"), monitor);
			}

		} catch (Exception e) {
			e.printStackTrace();
			sendFailToMonitor(new AG_STOP_DONE("iss"), monitor, 666,
					"Exception:" + e);
		}*/

	}

	private void doAgStart(final JMSExecutionMonitor monitor) {
		System.err.println("ISS_GenericHandler: Doing AgStart()");
		// start the AG acquire, report to monitor if cant do it.
	/*	try {
			Autoguider autoguider = telescope.getAutoguider();
			// AgGuideHandler handler = new IssGuideHandler();
			TestScopeResponseHandler th = new TestScopeResponseHandler();
			autoguider.acquire(th);

			// waitfor handler to complete or error for timeout
			System.err
					.println("ISS_GenericHandler: Sent command, acked client (100S), waiting notification from CIL...");
			monitor.setTimeToCompletion(100000L);
			try {
				th.waitNotification(100000L);
			} catch (InterruptedException x) {
			}

			if (th.isFailed()) {
				sendFailToMonitor(new AG_START_DONE("iss"), monitor, th
						.getCode(), th.getMsg());
			} else {
				sendDoneToMonitor(new AG_START_DONE("iss"), monitor);
			}

		} catch (Exception e) {
			e.printStackTrace();
			sendFailToMonitor(new AG_START_DONE("iss"), monitor, 666,
					"Exception:" + e);
		}*/

	}

	private void sendFailToMonitor(COMMAND_DONE done,
			JMSExecutionMonitor monitor, int errorCode, String errorMessage) {
		done.setSuccessful(false);
		done.setErrorNum(errorCode);
		done.setErrorString(errorMessage);
		monitor.setReply(done);
	}

	private void sendDoneToMonitor(COMMAND_DONE done,
			JMSExecutionMonitor monitor) {
		done.setSuccessful(true);
		monitor.setReply(done);
	}

}
