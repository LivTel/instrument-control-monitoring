package ngat.icm;

import java.util.*;

/** Base class for all ISS events. */
public class ReductionEvent extends InstrumentEvent {

	String filename;
	double seeing;
	double photom;
	double skybright;
	boolean sat;

	public ReductionEvent(Object source, String filename, double seeing,
			double photom, double skybright, boolean sat) {
		super(source, null);
		this.filename = filename;
		this.seeing = seeing;
		this.photom = photom;
		this.skybright = skybright;

	}

	public String toString() {
		return "ReductionEvent: Reduced-filename=" + filename + ", Seeing="
				+ seeing + ", photom=" + photom + ", skyb=" + skybright
				+ (sat ? "saturated" : "clear");
	}

}
