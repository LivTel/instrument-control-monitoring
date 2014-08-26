/**
 * 
 */
package ngat.icm;

import ngat.phase2.ICalibration;
import ngat.phase2.IExposure;
import ngat.phase2.IInstrumentConfig;


/**
 * Describes the configuration capabilities of an instrument.
 * @author snf
 *
 */
public interface InstrumentCapabilities {

	/** Returns true only if the given instrument-config is valid.*/
	public boolean isValidConfiguration(IInstrumentConfig config);

    // Extra stuff

	/** @return True if the instrument provides SkyModel information.*/
    public boolean isSkyModelProvider();
    
    /** @return True if this instrument can be used for focussing the telescope.*/
    public boolean isFocusInstrument();
    
  /**
   * 
   * @return The focal plane aperture offset for the instrument.
   */
    public FocalPlaneOffset getApertureOffset();
    
    // TODO getAcquisitionTargetPositionUsing(InstDescriptor acquisitionInstrumentID);
    // TODO (maybe) getSelfAcquisitionTargetPosition(); ??
    
    /**
     * @return The target position on this (acquisition) instrument's detector array.
     */
    public DetectorArrayPosition getAcquisitionTargetPosition(InstrumentDescriptor tid) throws UnknownInstrumentException;
    
    
    /**
     * @return The acquisition priority of this instrument (1 = best).
     */
    public int getAcquisitionPriority();
    
    /**
     * @param tid The instrument we may acquire for.
     * @return True if this instrument can acquire the specified target instrument.
     * @throws UnknownInstrumentException
     */
    public boolean canAcquire(InstrumentDescriptor tid);
    
    /**
     * @return The rotator offset of the instrument (rads).
     */
    public double getRotatorOffset();

    /**
     * Calculates the nominal observing wavelength when configured using 
     * the supplied instrument config
     * @param config The configuration to assume.
     * @return Nominal wavelength when configured as per config (W).
     */
    public Wavelength getWavelength(IInstrumentConfig config);

    /** Calculate how long it will take to perform the supplied exposure.
     * Including any specific setup and readout time
     * @param exposure The exposure to perform.
     * @return The calculated time to execute the given exposure (ms).
     */
    public long getExposureTime(IExposure exposure);
    
    /**Calculate how long it will take to perform the supplied calibration.
     * @param calib The calibration to perform.
     * @return The calculated time to execute the given calibration (ms).
     */
    public long getCalibrationTime(ICalibration calib);

    /** Calculate how long it will take to reconfigure from config1 to config2.
     * If either config is null the results should indicate a general configuration time.
     * @param config1 The start configuration.
     * @param config2 The end configuration
     * @return The calculated time to perform the re-configuration (ms).
     */
    public long getReconfigurationTime(IInstrumentConfig config1, IInstrumentConfig config2);
    
    /** Returns the capabilities of the instrument component's detector.*/
	public DetectorCapabilities getDetectorCapabilities();
	
	/** Determine the spectral response of the instrument.
	 * @return The spectral response of the instrument.
	 */
	//public SpectralEfficiencyFunction getSpectralResponse();
	
}
