package ngat.icm;

import java.util.*;

/** Base class for all ISS events.*/
public class ExposureEvent extends InstrumentEvent {

    String filename;

    public ExposureEvent(Object source, String filename) {
	super(source, null);
	this.filename = filename;
    }

    /**
	 * @return Returns the filename.
	 */
	public String getFilename() {
		return filename;
	}

	public String toString() {
	return "ExposureEvent: Raw-filename="+filename;
    }

}
