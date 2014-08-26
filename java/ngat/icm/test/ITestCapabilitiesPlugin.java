package ngat.icm.test;

import ngat.icm.InstrumentCapabilities;
import ngat.phase2.InstrumentConfig;

public interface ITestCapabilitiesPlugin extends InstrumentCapabilities {

    public Class getConfigClass();
    
}
