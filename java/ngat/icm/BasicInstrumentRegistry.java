/**
 * 
 */
package ngat.icm;

import java.io.File;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import org.jdom.Element;
import org.w3c.dom.Node;

import ngat.icm.test.TestInstrumentCalibration;
import ngat.phase2.ICalibration;
import ngat.phase2.IExposure;
import ngat.phase2.IInstrumentConfig;
import ngat.util.XmlConfigurable;

/**
 * @author snf
 * 
 */
public class BasicInstrumentRegistry extends UnicastRemoteObject implements InstrumentRegistry, XmlConfigurable {

	/**
	 * @author eng
	 * 
	 */
	public class Priority implements Comparable<Priority>, Serializable {

		private int priorityLevel;

		private InstrumentDescriptor instId;

		/**
		 * @param priorityLevel
		 * @param instId
		 */
		public Priority(int priorityLevel, InstrumentDescriptor instId) {
			super();
			this.priorityLevel = priorityLevel;
			this.instId = instId;
		}

		/**
		 * @return the priorityLevel
		 */
		public int getPriorityLevel() {
			return priorityLevel;
		}

		/**
		 * @param priorityLevel
		 *            the priorityLevel to set
		 */
		public void setPriorityLevel(int priorityLevel) {
			this.priorityLevel = priorityLevel;
		}

		/**
		 * @return the instId
		 */
		public InstrumentDescriptor getInstId() {
			return instId;
		}

		/**
		 * @param instId
		 *            the instId to set
		 */
		public void setInstId(InstrumentDescriptor instId) {
			this.instId = instId;
		}

		public int compareTo(Priority that) {
			if (this.getPriorityLevel() > that.getPriorityLevel())
				return -1;
			if (this.getPriorityLevel() == that.getPriorityLevel())
				return 0;
			return 1;
		}
		
		public String toString() {
				return "AcqPriority: "+(instId != null ? instId.getInstrumentName():"NULL")+" "+priorityLevel;
		}

	}

	/**
	 * Maps instrument-descriptors to instruments. This is used to generate the
	 * instrument list.
	 */
	protected Map<InstrumentDescriptor, BasicInstrument> instruments;

	/** Maps the instrument's name to its ID. Subinsts are mapped to owner's ID. */
	protected Map<String, InstrumentDescriptor> nameMap;

	protected SortedSet<Priority> acquisitionSet;

	private List<InstrumentDescriptor> acquisitionList;

	/**
	 * @param port
	 * @throws RemoteException
	 */
	public BasicInstrumentRegistry() throws RemoteException {
		super();
		// instruments = new HashMap<InstrumentDescriptor, BasicInstrument>();
		instruments = new HashMap<InstrumentDescriptor, BasicInstrument>();
		nameMap = new HashMap<String, InstrumentDescriptor>();
		acquisitionSet = new TreeSet<BasicInstrumentRegistry.Priority>();
		acquisitionList = new Vector<InstrumentDescriptor>();
	}

	/**
	 * Add a new instrument to the registry- fails silently if already
	 * registered.
	 * 
	 * @param inst
	 *            The new instrument to add.
	 */
	public void addInstrument(InstrumentDescriptor id, BasicInstrument inst) {
		if (!instruments.containsKey(id)) {
			instruments.put(id, inst);
			nameMap.put(id.getInstrumentName(), id);

			try {
				int priority = inst.getCapabilities().getAcquisitionPriority();
				if (priority != 0) {
					Priority p = new Priority(priority, id);
					acquisitionSet.add(p);
					System.err.println("BIR::Add acqisition info: "+p);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public InstrumentDescriptor getDescriptor(String instName) throws RemoteException {
		return nameMap.get(instName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seengat.icm.InstrumentRegistry#getCapabilitiesProvider(ngat.icm.
	 * InstrumentDescriptor)
	 */
	public InstrumentCapabilitiesProvider getCapabilitiesProvider(InstrumentDescriptor id) throws RemoteException {
		// find the true ID for this instrument.
		InstrumentDescriptor aid = nameMap.get(id.getInstrumentName());

		if (aid.isSubcomponent())
			aid = aid.getOwner();

		if (instruments.containsKey(aid))
			return (InstrumentCapabilitiesProvider) (instruments.get(aid));
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ngat.icm.InstrumentRegistry#getController(ngat.icm.InstrumentDescriptor)
	 */
	public InstrumentController getController(InstrumentDescriptor id) throws RemoteException {
		// find the true ID for this instrument.
		InstrumentDescriptor aid = nameMap.get(id.getInstrumentName());

		if (aid.isSubcomponent())
			aid = aid.getOwner();

		if (instruments.containsKey(aid))
			return (InstrumentController) (instruments.get(aid));
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ngat.icm.InstrumentRegistry#getStatusProvider(ngat.icm.InstrumentDescriptor
	 * )
	 */
	public InstrumentStatusProvider getStatusProvider(InstrumentDescriptor id) throws RemoteException {
		// find the true ID for this instrument.
		InstrumentDescriptor aid = nameMap.get(id.getInstrumentName());

		if (aid.isSubcomponent())
			aid = aid.getOwner();

		if (instruments.containsKey(aid))
			return (InstrumentStatusProvider) (instruments.get(aid));
		return null;

		/*
		 * // This is the normal code.... if (instruments.containsKey(aid))
		 * return (InstrumentStatusProvider) (instruments.get(aid)); return
		 * null;
		 */

	}

	public InstrumentCalibrationProvider getCalibrationProvider(InstrumentDescriptor id) throws RemoteException {
		// find the true ID for this instrument.
		InstrumentDescriptor aid = nameMap.get(id.getInstrumentName());

		if (aid.isSubcomponent())
			aid = aid.getOwner();

		if (instruments.containsKey(aid))
			return (InstrumentCalibrationProvider) (instruments.get(aid));
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ngat.icm.InstrumentRegistry#listInstruments()
	 */
	public List listInstruments() throws RemoteException {
		// Vector<InstrumentDescriptor> list = new Vector();
		Vector list = new Vector();
		// Iterator<InstrumentDescriptor> keys =
		// instruments.keySet().iterator();
		Iterator keys = instruments.keySet().iterator();
		while (keys.hasNext()) {
			list.add(keys.next());
		}
		return list;
	}

	/**
	 * Configure the registry from a DOM tree node.
	 * 
	 * @param root
	 *            the root element node.
	 * @see ngat.util.XmlConfigurable#configure(org.jdom.Element)
	 */
	public void configure(Element root) throws Exception {
		List rootList = root.getChildren("instrument");
		Iterator iroot = rootList.iterator();
		while (iroot.hasNext()) {
			Element inode = (Element) iroot.next();

			BasicInstrument inst = new BasicInstrument();
			inst.configure(inode);

			InstrumentDescriptor instId = inst.getId();

			addInstrument(instId, inst);
			System.err.println("BIR::Adding: " + inst);

			// configure sub-instrument aliases
			List subList = inode.getChildren("subcomponent");
			Iterator is = subList.iterator();
			while (is.hasNext()) {
				Element aNode = (Element) is.next();
				String aname = aNode.getAttributeValue("name");
				String aclass = aNode.getAttributeValue("class");
				String prefix = aNode.getAttributeValue("prefix");

				Element tNode = aNode.getChild("temperature");
				String tkwPrefix = "";
				tkwPrefix = tNode.getChildTextTrim("prefix");
				String tkwSuffix = "";
				tkwSuffix = tNode.getChildTextTrim("suffix");

				// InstrumentDescriptor aid = new
				// InstrumentDescriptor(instId.getInstrumentName()+prefix+aname);
				InstrumentDescriptor aid = new InstrumentDescriptor(aname);
				aid.setInstrumentClass(aclass);
				aid.setTemperatureKeywordPrefix(tkwPrefix);
				aid.setTemperatureKeywordSuffix(tkwSuffix);

				System.err.println("BIR::Add subinst: " + aid + " to " + instId);
				instId.addSubcomponent(aid);
				aid.setOwner(instId);
				nameMap.put(instId.getInstrumentName() + "_" + aid.getInstrumentName(), aid);

			}

			// configure monitoring
			Element mNode = inode.getChild("monitor");
			if (mNode != null) {
				// we will be monitoring this chappy...
				String host = mNode.getAttributeValue("host");
				int port = Integer.parseInt(mNode.getAttributeValue("port"));
				long interval = Long.parseLong(mNode.getAttributeValue("interval"));

				// TODO if we have a health node here extract the warn and fail
				// levels
				// TODO create a health monitor, and add it to the startMon call
				// hm = new Hm(wlo,whi,flo,fhi)

				Element hNode = mNode.getChild("health");
				if (hNode != null) {
					double failLow = Double.parseDouble(hNode.getChildTextTrim("failLowTemperature"));
					double warnLow = Double.parseDouble(hNode.getChildTextTrim("warnLowTemperature"));
					double failHigh = Double.parseDouble(hNode.getChildTextTrim("failHighTemperature"));
					double warnHigh = Double.parseDouble(hNode.getChildTextTrim("warnHighTemperature"));

					// in ascending temperature order...
					HealthMonitor hm = new HealthMonitor(failLow, warnLow, warnHigh, failHigh);
					System.err.println("BIR::Monitor deduced health status");
					inst.startMonitoring(host, port, interval, hm);					
					inst.setControllerProxy(new ControllerProxy(host, port));
				
				} else {
					System.err.println("BIR::Monitor with supplied health status");
					inst.startMonitoring(host, port, interval, null);
					inst.setControllerProxy(new ControllerProxy(host, port));
				}

			}

			// configure calibration
			Element cNode = inode.getChild("calibration");
			if (cNode != null) {
				TestInstrumentCalibration tic = new TestInstrumentCalibration();
				tic.configure(cNode);
				inst.setCalibrationRequirements(tic);
				System.err.println("BIR::Setting calib to: " + tic);

				// TODO load calibration history
				Element hNode = cNode.getChild("history");
				File file = new File(hNode.getAttributeValue("file"));
				InstrumentCalibrationHistory ich = null;
				try {
					ich = InstrumentCalibrationHistory.load(file);
					System.err.println("BIR::Loaded calib history: " + ich);
				} catch (Exception e) {
					// check it wasnt saved as a null object on first load/save
					// cycle.
					ich = new InstrumentCalibrationHistory(instId.getInstrumentName());
				}
				inst.setCalibrationHistory(ich);

			}
			// TODO load usage history - ie when was instrument last used ??
			// this may help with decisions on doing flats etc

		} // next instrument

	}

	/*
	 * @return A list in descending priority of acquisition instruments.
	 */
	public List<InstrumentDescriptor> listAcquisitionInstruments() throws RemoteException {
		// lazy instantiaion, do this first time its called
		if (acquisitionList.isEmpty()) {

			Iterator<Priority> iap = acquisitionSet.iterator();
			while (iap.hasNext()) {
				Priority ap = iap.next();
				acquisitionList.add(ap.getInstId());
			}
			System.err.println("BIR:ListAcqInsts() CreateList: "+acquisitionList);
		}
		return acquisitionList;
	}

}
