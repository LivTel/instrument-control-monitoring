package ngat.icm;

import java.util.*;

/** Base class for all ISS events.*/
public class InstrumentEvent extends EventObject {

    private Object another;

    public InstrumentEvent(Object source, Object another) {
	super(source);
	this.another = another;
    }

    public Object getAnother() { return another;}

    public String toString() {
	return "InstrumentEvent: "+source+", "+another;
    }

}
