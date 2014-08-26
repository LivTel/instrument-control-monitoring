/**
 * 
 */
package ngat.icm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import ngat.phase2.ICalibration;
import ngat.phase2.IExposure;
import ngat.phase2.IInstrumentConfig;
import ngat.phase2.XAcquisitionConfig;

/**
 * @author snf
 * 
 */
public abstract class BasicInstrumentCapabilities implements Serializable, InstrumentCapabilities {

	/** Detector array target position for target instrument. */
	protected Map<InstrumentDescriptor, DetectorArrayPosition> acquisitionTargetPosition;

	/** Aperture offset position. */
	protected FocalPlaneOffset apertureOffset;

	/** True if the instrument is a skymodel provider. */
	protected boolean skyModelProvider;

	/** True if the instrument can be used for focussing the telescope. */
	protected boolean focusInstrument;

	/** Rotator offset position (rads). */
	protected double rotatorOffset;

	/** The Detector capabilities. */
	protected DetectorCapabilities detector;

	/** Acquisition priority of this instrument. */
	protected int acquisitionPriority;

	/** Create a BasicInstrumentCapabilities. */
	public BasicInstrumentCapabilities() {
		super();
		acquisitionTargetPosition = new HashMap<InstrumentDescriptor, DetectorArrayPosition>();
	}

	/**
	 * @param acquisitionDetectorPosition
	 *            the acquisitionDetectorPosition to set
	 */
	public void setAcquisitionTargetPosition(InstrumentDescriptor aid, DetectorArrayPosition atp) {
		this.acquisitionTargetPosition.put(aid, atp);
	}

	/**
	 * @return the acquisitionPriority
	 */
	public int getAcquisitionPriority() {
		return acquisitionPriority;
	}

	/**
	 * @param acquisitionPriority
	 *            the acquisitionPriority to set
	 */
	public void setAcquisitionPriority(int acquisitionPriority) {
		this.acquisitionPriority = acquisitionPriority;
	}

	/**
	 * @param apertureOffset
	 *            the apertureOffset to set
	 */
	public void setApertureOffset(FocalPlaneOffset apertureOffset) {
		this.apertureOffset = apertureOffset;
	}

	/**
	 * @param rotatorOffset
	 *            the rotatorOffset to set
	 */
	public void setRotatorOffset(double rotatorOffset) {
		this.rotatorOffset = rotatorOffset;
	}

	/**
	 * @param skyModelProvider
	 *            the skyModelProvider to set
	 */
	public void setSkyModelProvider(boolean skyModelProvider) {
		this.skyModelProvider = skyModelProvider;
	}

	/**
	 * @return the focusInstrument
	 */
	public boolean isFocusInstrument() {
		return focusInstrument;
	}

	/**
	 * @param focusInstrument
	 *            the focusInstrument to set
	 */
	public void setFocusInstrument(boolean focusInstrument) {
		this.focusInstrument = focusInstrument;
	}

	/**
	 * @param tid
	 *            The instrument we may acquire for.
	 * @return True if this instrument can acquire the specified target
	 *         instrument.
	 * @throws UnknownInstrumentException
	 */
	public boolean canAcquire(InstrumentDescriptor tid) {
		if (tid == null)
			return false;
		if (!acquisitionTargetPosition.containsKey(tid))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ngat.icm.InstrumentCapabilities#getAcquisitionTargetPosition()
	 */
	public DetectorArrayPosition getAcquisitionTargetPosition(InstrumentDescriptor tid)
			throws UnknownInstrumentException {
		if (tid == null)
			throw new UnknownInstrumentException("No target instrument specified");

		// we only map to the top level instrument as acquire target
		if (tid.isSubcomponent())
			tid = tid.getOwner();

		if (!acquisitionTargetPosition.containsKey(tid))
			throw new UnknownInstrumentException(tid.getInstrumentName());
		return acquisitionTargetPosition.get(tid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ngat.icm.InstrumentCapabilities#getApertureOffset()
	 */
	public FocalPlaneOffset getApertureOffset() {
		return apertureOffset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ngat.icm.InstrumentCapabilities#getCalibrationTime(ngat.phase2.ICalibration
	 * )
	 */
	public abstract long getCalibrationTime(ICalibration calib);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ngat.icm.InstrumentCapabilities#getExposureTime(ngat.phase2.IExposure)
	 */
	public abstract long getExposureTime(IExposure exposure);

	/*
	 * (non-Javadoc)
	 * 
	 * @see ngat.icm.InstrumentCapabilities#getRotatorOffset()
	 */
	public double getRotatorOffset() {
		return rotatorOffset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ngat.icm.InstrumentCapabilities#getWavelength(ngat.phase2.IInstrumentConfig
	 * )
	 */
	public abstract Wavelength getWavelength(IInstrumentConfig config);

	/*
	 * (non-Javadoc)
	 * 
	 * @see ngat.icm.InstrumentCapabilities#isSkyModelProvider()
	 */
	public boolean isSkyModelProvider() {
		return skyModelProvider;
	}

	/**
	 * @return the detector
	 */
	public DetectorCapabilities getDetectorCapabilities() {
		return detector;
	}

	/**
	 * @param detector
	 *            the detector to set
	 */
	public void setDetectorCapabilities(DetectorCapabilities detector) {
		this.detector = detector;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seengat.icm.InstrumentCapabilities#isValidConfiguration(ngat.phase2.
	 * IInstrumentConfig)
	 */
	public abstract boolean isValidConfiguration(IInstrumentConfig config);

	public void configure(Element cfgNode) throws Exception {
		// System.err.println("Parse basic inst caps using node: "+cfgNode);
		Element cnode = cfgNode.getChild("capabilities");

		// System.err.println("Check generic capabilities using: "+cnode);
		Element smpNode = cnode.getChild("sky-model-provider");
		if (smpNode != null)
			skyModelProvider = smpNode.getTextTrim().equalsIgnoreCase("true");
		Element fNode = cnode.getChild("focus-instrument");
		if (smpNode != null)
			focusInstrument = fNode.getTextTrim().equalsIgnoreCase("true");

		Element rNode = cnode.getChild("rotator-offset");
		if (rNode != null)
			rotatorOffset = Math.toRadians(Double.parseDouble(rNode.getTextTrim()));

		Element acqNode = cnode.getChild("acquisition");
		if (acqNode != null) {
			acquisitionPriority = Integer.parseInt(acqNode.getAttributeValue("priority"));

			List tgtNodes = acqNode.getChildren("target");
			if (tgtNodes != null) {
				for (int ia = 0; ia < tgtNodes.size(); ia++) {
					Element tNode = (Element) tgtNodes.get(ia);
					String tgtName = tNode.getAttributeValue("name");
					double acqx = Double.parseDouble(tNode.getAttributeValue("x"));
					double acqy = Double.parseDouble(tNode.getAttributeValue("y"));
					System.err.println("Found acquire offsets for target: " + tgtName + " -> " + acqx + "," + acqy);
					acquisitionTargetPosition.put(new InstrumentDescriptor(tgtName), new DetectorArrayPosition(acqx,
							acqy));
				}
			}
		}

		Element apNode = cnode.getChild("aperture-offset");
		if (apNode != null) {
			double apx = Double.parseDouble(apNode.getAttributeValue("x"));
			double apy = Double.parseDouble(apNode.getAttributeValue("y"));
			System.err.println("Found aperture offsets: " + apx + "," + apy);
			apertureOffset = new FocalPlaneOffset(apx, apy);
			System.err.println("Set ap to: " + apertureOffset);
		}

		Element dnode = cfgNode.getChild("detector");
		if (dnode == null)
			throw new Exception("Missing detector node");

		// <array-size x="2048" y="2048"/>
		// <plate-scale>0.135</plate-scale>
		Element asNode = dnode.getChild("array-size");
		int ax = Integer.parseInt(asNode.getAttributeValue("width"));
		int ay = Integer.parseInt(asNode.getAttributeValue("height"));

		detector = new DetectorCapabilities();
		detector.setArrayWidth(ax);
		detector.setArrayHeight(ay);

		Element psNode = dnode.getChild("plate-scale");
		if (psNode != null) {
			detector.setPlateScale(Double.parseDouble(psNode.getTextTrim()));
		}

	}

	public String toString() {
		return (skyModelProvider ? " SkyModelProvider" : "") + (focusInstrument ? " FocusInstrument" : "")
				+ " RotatorOffset=" + Math.toDegrees(rotatorOffset) + " ApertureOffset=" + apertureOffset
				+ " AcquisitionOffset=" + acquisitionTargetPosition + " Detector=" + detector;

	}

}
