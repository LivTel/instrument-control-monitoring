package ngat.icm;

import java.util.*;
import ngat.message.base.*;

/** Base class for all ISS events. */
public class CompletionEvent extends InstrumentEvent {

	COMMAND_DONE done;

	public CompletionEvent(Object source, COMMAND_DONE done) {
		super(source, done);
		this.done = done;
	}

	public String toString() {
		return "CompletionEvent: Encapsulated Done reply: Class: "
				+ done.getClass().getName()+ ", Status="
				+ (done.getSuccessful() ? "Success" : "Failed: "
						+ done.getErrorNum() + ":" + done.getErrorString());
	}

	/**
	 * @return Returns the done.
	 */
	public COMMAND_DONE getDone() {
		return done;
	}

}
