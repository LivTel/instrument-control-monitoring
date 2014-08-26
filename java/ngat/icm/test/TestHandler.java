package ngat.icm.test;

import ngat.icm.*;
import ngat.net.*;
import ngat.message.base.*;
import ngat.message.ISS_INST.*;

public class TestHandler implements JMSClientProtocolResponseHandler {

    private COMMAND_DONE reply;
    InstrumentEventListener listener;

    public TestHandler(InstrumentEventListener listener) {
	this.listener = listener;
    }
    
    public void handleAck(ACK ack) throws Exception {
	System.err.println("TH::Received ack: "+ack);

	if (ack instanceof MULTRUN_ACK) {
	    MULTRUN_ACK mack = (MULTRUN_ACK)ack;
	    listener.instrumentEventOccurred(new ExposureEvent("MACK-FileName", 
							mack.getFilename()));
	} else if
	    (ack instanceof MULTRUN_DP_ACK) {
	    MULTRUN_DP_ACK dack = (MULTRUN_DP_ACK)ack;
	    listener.instrumentEventOccurred(new ReductionEvent("DPACK-FileName", 
							 dack.getFilename(), 
							 dack.getSeeing(), 
							 dack.getPhotometricity(), 
							 dack.getSkyBrightness(),
							 dack.getSaturation()));
	    
	} else {
		// this is not really neccessary as the client wouldnt know what to do with them anyway..
	    //listener.issEventOccurred(new InstrumentEvent(this, ack));
	}
    }

    public void handleDone(COMMAND_DONE done) throws Exception {
	System.err.println("TH::Received done: "+done+" - notify encapsulated listener");
	listener.instrumentEventOccurred(new CompletionEvent("DONE", done));
	this.reply = done;
    }

    public COMMAND_DONE getReply() { 
	return reply;
    }


}
