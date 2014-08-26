/**
 *
 */
package ngat.icm;

/** Provides information about the telescope's calibration requirements.
 * @author snf
 *
 */
public interface InstrumentCalibration {

    /**
     * @return true if morning SKYFLAT calibration should be performed.
     */
    public boolean doMorningSkyflatCalibration();
    

    /**
     * @return interval between morning SKYFLAT calibrations.
     */
    public long getMorningSkyflatCalibrationInterval();
    
    /**
     * @return Offset from start of astro-twilight when skyflats can be started.
     */
    public long getMorningSkyflatStartTimeOffset();
    
     /**
      * @return Offset from start of astro-twilight when skyflats should be completed.
     */
    public long getMorningSkyflatEndTimeOffset();

    /*
     *  @return Preferred size of morning skyflat window (ms).
     */
    public long getMorningSkyflatPreferredWindowSize();
    
     /**
     * @return true if evening SKYFLAT calibration should be performed.
     */
    public boolean doEveningSkyflatCalibration();
    

    /**
     * @return interval between evening SKYFLAT calibrations.
     */
    public long getEveningSkyflatCalibrationInterval();
    
    /**
     * @return Offset from start of astro-twilight when skyflats can be started.
     */
    public long getEveningSkyflatStartTimeOffset();
    
     /**
      * @return Offset from start of astro-twilight when skyflats should be completed.
     */
    public long getEveningSkyflatEndTimeOffset();

    /*
     *  @return Preferred size of evening skyflat window (ms).
     */
    public long getEveningSkyflatPreferredWindowSize();
    
     /**
     * @return true if STANDARD calibration should be performed.
     */
    public boolean doStandardCalibration();
    

    /**
     * @return interval between STANDARD calibrations.
     */
    public long getStandardCalibrationInterval();
    
    /**
     * @return the claibration priority (large is better than small).
     */
    public int getCalibrationPriority();

    /**
     * @return true if real-time reduction required.
     */
    public boolean requiresRealTimeReduction();
    
}
