/**
 * 
 */
package ngat.icm.test;

import java.io.Serializable;

import org.jdom.Element;

import ngat.icm.*;
import ngat.util.*;

/**
 * @author snf
 *
 */
public class TestInstrumentCalibration implements InstrumentCalibration, PropertiesConfigurable, XmlConfigurable, Serializable {

    private static final long DEFAULT_MORNING_SKYFLAT_CALIBRATION_INTERVAL = 24*3600*1000L;
    private static final long DEFAULT_MORNING_SKYFLAT_START_OFFSET = 0L;
    private static final long DEFAULT_MORNING_SKYFLAT_END_OFFSET   = 1800*1000L;
    private static final long DEFAULT_MORNING_SKYFLAT_PREF_WINDOW_SIZE = 15*60*1000L;

    private static final long DEFAULT_EVENING_SKYFLAT_CALIBRATION_INTERVAL = 24*3600*1000L;
    private static final long DEFAULT_EVENING_SKYFLAT_START_OFFSET = 0L;
    private static final long DEFAULT_EVENING_SKYFLAT_END_OFFSET   = 1800*1000L;
    private static final long DEFAULT_EVENING_SKYFLAT_PREF_WINDOW_SIZE = 15*60*1000L;

    private static final long DEFAULT_STANDARD_CALIBRATION_INTERVAL = 24*3600*1000L;

    /** Calibration priority.*/
    int calibrationPriority;

    boolean requiresRealTimeReduction;
    
    /** True if morning SKYFLAT calibration should be performed.*/
    boolean doMorningSkyflatCalibration;
    
    /** True if evening SKYFLAT calibration should be performed.*/
    boolean doEveningSkyflatCalibration;
	
    /** True if STANDARD calibration should be performed.*/
    boolean doStandardCalibration;
    
    /** Interval between morning SKYFLAT calibrations (ms).*/
    long morningSkyflatCalibrationInterval;

    /** Time after start of astro-twilight when start SKYFLAT calibrations (ms).*/
    long morningSkyflatStartTimeOffset;

    /** Time after start of astro-twilight when end SKYFLAT calibrations (ms).*/
    long morningSkyflatEndTimeOffset;

    /** Preferred size of window for SKYFLAT calibrations (ms).*/
    long morningSkyflatPreferredWindowSize;

    /** Interval between evening SKYFLAT calibrations (ms).*/
    long eveningSkyflatCalibrationInterval;
    
    /** Time after start of astro-twilight when start SKYFLAT calibrations (ms).*/
    long eveningSkyflatStartTimeOffset;
    
    /** Time after start of astro-twilight when end SKYFLAT calibrations (ms).*/
    long eveningSkyflatEndTimeOffset;

    /** Preferred size of window for SKYFLAT calibrations (ms).*/
    long eveningSkyflatPreferredWindowSize;

    /** Interval between STANDARD calibrations (ms).*/
    long standardCalibrationInterval;
	
    /**
	 * @return the requiresRealTimeReduction
	 */
	public boolean requiresRealTimeReduction() {
		return requiresRealTimeReduction;
	}

	/**
	 * @param requiresRealTimeReduction the requiresRealTimeReduction to set
	 */
	public void setRequiresRealTimeReduction(boolean requiresRealTimeReduction) {
		this.requiresRealTimeReduction = requiresRealTimeReduction;
	}

	// MORNING SKYFLATS
    /**
     * @return the doMorningSkyflatCalibration
     */
    public boolean doMorningSkyflatCalibration() {
	return doMorningSkyflatCalibration;
    }

    /**
     * @param doMorningSkyflatCalibration the doMorningSkyflatCalibration to set
     */
    public void setDoMorningSkyflatCalibration(boolean doMorningSkyflatCalibration) {
	this.doMorningSkyflatCalibration = doMorningSkyflatCalibration;
    }

    /**
     * @return the morningSkyflatCalibrationInterval
     */
    public long getMorningSkyflatCalibrationInterval() {
	return morningSkyflatCalibrationInterval;
    }
    
    /**
     * @param morningSkyflatCalibrationInterval the morningSkyflatCalibrationInterval to set
     */
    public void setMorningSkyflatCalibrationInterval(long pmorningSkyflatCalibrationInterval) {
	this.morningSkyflatCalibrationInterval = morningSkyflatCalibrationInterval;
    }

    /**
     * @return Offset from start of astro-twilight when skyflats can be started.
     */
    public long getMorningSkyflatStartTimeOffset() {
	return morningSkyflatStartTimeOffset;
    }
    
    /** 
     * @param off The offset to use.
     */
    public void setMorningSkyflatStartTimeOffset(long off) {
	this.morningSkyflatStartTimeOffset = off;
    }

     /**
      * @return Offset from start of astro-twilight when skyflats should be completed.
     */
    public long getMorningSkyflatEndTimeOffset() {
	return morningSkyflatEndTimeOffset;
    }
    
    /** 
     * @param off The offset to use.
     */
    public void setMorningSkyflatEndTimeOffset(long off) {
	this.morningSkyflatEndTimeOffset = off;
    }


    /*
     *  @return Preferred size of evening skyflat window (ms).
     */
    public long getMorningSkyflatPreferredWindowSize() {
	return morningSkyflatPreferredWindowSize;
    }

    public void setMorningSkyflatPreferredWindowSize(long ms) {
	this.morningSkyflatPreferredWindowSize = morningSkyflatPreferredWindowSize;
    }


    // EVENING SKYFLATS
    /**
     * @return the doEveningSkyflatCalibration
     */
    public boolean doEveningSkyflatCalibration() {
	return doEveningSkyflatCalibration;
    }

    /**
     * @param doEveningSkyflatCalibration the doEveningSkyflatCalibration to set
     */
    public void setDoEveningSkyflatCalibration(boolean doEveningSkyflatCalibration) {
	this.doEveningSkyflatCalibration = doEveningSkyflatCalibration;
    }

     /**
     * @return the eveningSkyflatCalibrationInterval
     */
    public long getEveningSkyflatCalibrationInterval() {
	return eveningSkyflatCalibrationInterval;
    }
    
    /**
     * @param eveningSkyflatCalibrationInterval the morningSkyflatCalibrationInterval to set
     */
    public void setEveningSkyflatCalibrationInterval(long eveningSkyflatCalibrationInterval) {
	this.eveningSkyflatCalibrationInterval = eveningSkyflatCalibrationInterval;
    }

    /**
     * @return Offset from start of astro-twilight when skyflats can be started.
     */
    public long getEveningSkyflatStartTimeOffset() {
	return eveningSkyflatStartTimeOffset;
    }
    
    /** 
     * @param off The offset to use.
     */
    public void setEveningSkyflatStartTimeOffset(long off) {
	this.eveningSkyflatStartTimeOffset = off;
    }

     /**
      * @return Offset from start of astro-twilight when skyflats should be completed.
     */
    public long getEveningSkyflatEndTimeOffset() {
	return eveningSkyflatEndTimeOffset;
    }
    
    /** 
     * @param off The offset to use.
     */
    public void setEveningSkyflatEndTimeOffset(long off) {
	this.eveningSkyflatEndTimeOffset = off;
    }

    /*
     *  @return Preferred size of evening skyflat window (ms).
     */
    public long getEveningSkyflatPreferredWindowSize() {
        return eveningSkyflatPreferredWindowSize;
    }

    public void setEveningSkyflatPreferredWindowSize(long ms) {
        this.eveningSkyflatPreferredWindowSize = eveningSkyflatPreferredWindowSize;
    }


    // STANDARDS
    /**
     * @return true if STANDARD calibration should be performed.
     */
    public boolean doStandardCalibration() {
	return doStandardCalibration;
    }

    /**
     * @param doStandardCalibration the doStandardCalibration to set
     */
    public void setDoStandardCalibration(boolean doStandardCalibration) {
	this.doStandardCalibration = doStandardCalibration;
    }

    /**
     * @return the standardCalibrationInterval
     */
    public long getStandardCalibrationInterval() {
	return standardCalibrationInterval;
    }
    
    /**
     * @param standardCalibrationInterval the standardCalibrationInterval to set
     */
    public void setStandardCalibrationInterval(long standardCalibrationInterval) {
	this.standardCalibrationInterval = standardCalibrationInterval;
    }


    /**
     * @return the claibration priority (large is better than small).
     */
    public int getCalibrationPriority() {
	return calibrationPriority;
    }

    /** 
     * @param cp The calibrationPriority to set.
     */
    public void setCalibrationPriority(int cp) {
	this.calibrationPriority = cp;
    }

    // Configure the bastard...
    /** Configure from a properties.*/
    public void configure(ConfigurationProperties config) throws Exception {
	doMorningSkyflatCalibration = (config.getProperty("do.morning.skyflat.calibration") != null);
	morningSkyflatCalibrationInterval = config.getLongValue("morning.skyflat.calibration.interval", DEFAULT_MORNING_SKYFLAT_CALIBRATION_INTERVAL);
	morningSkyflatStartTimeOffset     = config.getLongValue("morning.skyflat.start.offset", DEFAULT_MORNING_SKYFLAT_START_OFFSET);
	morningSkyflatEndTimeOffset       = config.getLongValue("morning.skyflat.end.offset", DEFAULT_MORNING_SKYFLAT_END_OFFSET);				
	morningSkyflatPreferredWindowSize = config.getLongValue("morning.skyflat.preferred.window.size", DEFAULT_MORNING_SKYFLAT_PREF_WINDOW_SIZE);
	
	doEveningSkyflatCalibration = (config.getProperty("do.evening.skyflat.calibration") != null);
	eveningSkyflatCalibrationInterval = config.getLongValue("evening.skyflat.calibration.interval", DEFAULT_EVENING_SKYFLAT_CALIBRATION_INTERVAL);
	eveningSkyflatStartTimeOffset     = config.getLongValue("evening.skyflat.start.offset", DEFAULT_EVENING_SKYFLAT_START_OFFSET);
	eveningSkyflatEndTimeOffset       = config.getLongValue("evening.skyflat.end.offset", DEFAULT_EVENING_SKYFLAT_END_OFFSET);		
	eveningSkyflatPreferredWindowSize = config.getLongValue("evening.skyflat.preferred.window.size", DEFAULT_EVENING_SKYFLAT_PREF_WINDOW_SIZE);

	doStandardCalibration = (config.getProperty("do.standard.calibration") != null);
	standardCalibrationInterval = config.getLongValue("standard.calibration.interval", DEFAULT_STANDARD_CALIBRATION_INTERVAL);
    }
	
    /** Returns a readable description of this calibration.*/
    public String toString() {
	return "InstrumentCalib:"+
	    (doMorningSkyflatCalibration ? " M_FLAT_CAL @"+(morningSkyflatCalibrationInterval/3600000)+"H" : " NO_M_FLATS")+
	    (doEveningSkyflatCalibration ? " E_FLAT_CAL @"+(eveningSkyflatCalibrationInterval/3600000)+"H" : " NO_E_FLATS")+
	    (doStandardCalibration       ? " STD_CAL @"+(standardCalibrationInterval/3600000)+"H" : " NO_STDS")+
	    " Priority: "+calibrationPriority;			
    }
    
	public void configure(Element node) throws Exception {
		
		Element dprtNode = node.getChild("dprt");
		if (dprtNode != null) {
			requiresRealTimeReduction = true;
		}
		
		Element msfNode = node.getChild("morning-skyflats");
		if (msfNode != null) {
			doMorningSkyflatCalibration = true;
			morningSkyflatStartTimeOffset = Long.parseLong(msfNode.getChildTextTrim("start-offset"));
			morningSkyflatEndTimeOffset = Long.parseLong(msfNode.getChildTextTrim("end-offset"));
			morningSkyflatPreferredWindowSize = Long.parseLong(msfNode.getChildTextTrim("window"));
			morningSkyflatCalibrationInterval = Long.parseLong(msfNode.getChildTextTrim("interval"));
		}
		
		Element esfNode = node.getChild("evening-skyflats");
		if (esfNode != null) {
			doEveningSkyflatCalibration = true;
			eveningSkyflatStartTimeOffset = Long.parseLong(esfNode.getChildTextTrim("start-offset"));
			eveningSkyflatEndTimeOffset = Long.parseLong(esfNode.getChildTextTrim("end-offset"));
			eveningSkyflatPreferredWindowSize = Long.parseLong(esfNode.getChildTextTrim("window"));
			eveningSkyflatCalibrationInterval = Long.parseLong(esfNode.getChildTextTrim("interval"));
		}
		
		Element stdNode = node.getChild("standards");
		if (stdNode != null) {
			doStandardCalibration = true;
			standardCalibrationInterval = Long.parseLong(stdNode.getChildTextTrim("interval"));
		}

		Element priorityNode = node.getChild("priority");
		if (priorityNode != null) {
		    calibrationPriority = Integer.parseInt(priorityNode.getTextTrim());
		} else {
		    calibrationPriority = 0;
		}
	}
    
}
