/**
 * 
 */
package ngat.icm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * @author snf
 * 
 */
public class InstrumentCalibrationHistory implements Serializable {

	/** The instrument ID - temp for debugging. */
	String instId;

	/** Time last morning SKYFLAT calibration was performed. */
	long lastMorningSkyflatCalibration;

	/** Time last evening SKYFLAT calibration was performed. */
	long lastEveningSkyflatCalibration;

	/** Time last STANDARD calibration was performed. */
	long lastStandardCalibration;

	/**
     * 
     */
	public InstrumentCalibrationHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
     * 
     */
	public InstrumentCalibrationHistory(String instId) {
		super();
		// TODO Auto-generated constructor stub
		this.instId = instId;
	}

	/**
	 * @return the lastMorningSkyflatCalibration
	 */
	public long getLastMorningSkyflatCalibration() {
		return lastMorningSkyflatCalibration;
	}

	/**
	 * @param lastMorningSkyflatCalibration
	 *            the lastMorningSkyflatCalibration to set
	 */
	public void setLastMorningSkyflatCalibration(long lastMorningSkyflatCalibration) {
		this.lastMorningSkyflatCalibration = lastMorningSkyflatCalibration;
	}

	/**
	 * @return the lastEveningSkyflatCalibration
	 */
	public long getLastEveningSkyflatCalibration() {
		return lastEveningSkyflatCalibration;
	}

	/**
	 * @param lastEveningSkyflatCalibration
	 *            the lastEveningSkyflatCalibration to set
	 */
	public void setLastEveningSkyflatCalibration(long lastEveningSkyflatCalibration) {
		this.lastEveningSkyflatCalibration = lastEveningSkyflatCalibration;
	}

	/**
	 * @return the lastStandardCalibration
	 */
	public long getLastStandardCalibration() {
		return lastStandardCalibration;
	}

	/**
	 * @param lastStandardCalibration
	 *            the lastStandardCalibration to set
	 */
	public void setLastStandardCalibration(long lastStandardCalibration) {
		this.lastStandardCalibration = lastStandardCalibration;
	}

	/**
	 * Load a InstrumentCalibrationHistory from a file.
	 * 
	 * @param file
	 *            The file to load the serialized InstrumentCalibrationHistory
	 *            from,
	 * @return An instance of InstrumentCalibrationHistory de-serialized from
	 *         file.
	 * @throws Exception
	 *             If anything goes wrong.
	 */
	public static InstrumentCalibrationHistory load(File file) throws Exception {
		FileInputStream fin = new FileInputStream(file);
		ObjectInputStream oin = new ObjectInputStream(fin);
		InstrumentCalibrationHistory ich = (InstrumentCalibrationHistory) oin.readObject();
		return ich;
	}

	/**
	 * Save a InstrumentCalibrationHistory to a file.
	 * 
	 * @param ich
	 *            The instance of InstrumentCalibrationHistory to save to file.
	 * @param file
	 *            The file to serialize the InstrumentCalibrationHistory to.
	 * @throws Exception
	 *             If anything goes wrong.
	 */
	public static void save(InstrumentCalibrationHistory ich, File file) throws Exception {
		FileOutputStream fout = new FileOutputStream(file);
		ObjectOutputStream oout = new ObjectOutputStream(fout);
		oout.flush();
		oout.writeObject(ich);
		oout.flush();
	}

	/** Returns a readable description of this tel calib history. */
	public String toString() {
		return "InstrumentCalibHistory: " + instId + " LastStandard: "
				+ (new Date(lastStandardCalibration)).toGMTString() + " Last MorningSkyflat: "
				+ (new Date(lastMorningSkyflatCalibration)).toGMTString() + " Last EveningSkyflat: "
				+ (new Date(lastEveningSkyflatCalibration)).toGMTString();
	}

}
