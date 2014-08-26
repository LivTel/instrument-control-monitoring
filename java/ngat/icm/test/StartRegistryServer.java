/**
 * 
 */
package ngat.icm.test;

import ngat.icm.BasicInstrument;
import ngat.icm.BasicInstrumentRegistry;
import ngat.icm.BinSet;
import ngat.icm.DetectorCapabilities;
import ngat.icm.DualBeamSpectrograph;
import ngat.icm.Filter;
import ngat.icm.FilterSet;
import ngat.icm.Imager;
import ngat.icm.InstrumentDescriptor;
import ngat.icm.Polarimeter;
import ngat.icm.VariableWavelengthSpectrograph;
import ngat.icm.Wavelength;
import ngat.icm.WavelengthNm;
import ngat.phase2.XDualBeamSpectrographInstrumentConfig;
import ngat.util.*;

import java.rmi.*;
import java.io.*;

/**
 * @author snf
 * 
 */
public class StartRegistryServer {

	public static final long REBIND_INTERVAL = 120000L;

	private BasicInstrumentRegistry bir;

	public StartRegistryServer() throws Exception {
		bir = new BasicInstrumentRegistry();
	}

	public void configure(File configFile) throws Exception {
		// set up some ready descriptors.
		InstrumentDescriptor ratcamId = new InstrumentDescriptor("RATCAM");
		ratcamId.setInstrumentClass("camera.optical");
		ratcamId.setInstrumentModel("EEV 42-40");
		ratcamId.setManufacturer("Marconi");
		ratcamId.setSerialNumber("1278-B7");

		InstrumentDescriptor supircamId = new InstrumentDescriptor("SUPIRCAM");
		supircamId.setInstrumentClass("camera.ir");
		supircamId.setInstrumentModel("PICNIC HgCdTe");
		supircamId.setManufacturer("IR Labs");
		supircamId.setSerialNumber("767279884");

		InstrumentDescriptor ringoId = new InstrumentDescriptor("RINGO");
		ringoId.setInstrumentClass("polarimeter");
		ringoId.setInstrumentModel("AIMO E2V CCD42-40");
		ringoId.setManufacturer("Apogee");
		ringoId.setSerialNumber("786868-N76");

		InstrumentDescriptor frodoId = new InstrumentDescriptor("FRODO");
		frodoId.setInstrumentClass("spectrograph.dualbeam");
		frodoId.setInstrumentModel("E2V CCD 42-80");
		frodoId.setManufacturer("IR Labs");
		frodoId.setSerialNumber("BG76-74a");

		InstrumentDescriptor frodoRedId = new InstrumentDescriptor("FRODO_RED");
		frodoRedId.setInstrumentClass("spectrograph.dualbeam.redarm");
		frodoRedId.setInstrumentModel("E2V CCD 42-80");
		frodoRedId.setManufacturer("IR Labs");
		frodoRedId.setSerialNumber("BG76-74a");

		InstrumentDescriptor frodoBlueId = new InstrumentDescriptor("FRODO_BLUE");
		frodoBlueId.setInstrumentClass("spectrograph.dualbeam.bluearm");
		frodoBlueId.setInstrumentModel("E2V CCD 42-80");
		frodoBlueId.setManufacturer("IR Labs");
		frodoBlueId.setSerialNumber("BG76-74b");

		InstrumentDescriptor riseId = new InstrumentDescriptor("RISE");
		riseId.setInstrumentClass("camera.optical.fastreadout");
		riseId.setInstrumentModel("AIMO E2V CCD42-40");
		riseId.setManufacturer("ANDor");
		riseId.setSerialNumber("783468-N76");

		InstrumentDescriptor meabId = new InstrumentDescriptor("MEABURN");
		meabId.setInstrumentClass("spectrograph.varwavelength");
		meabId.setInstrumentModel("AIMO E2V CCD42-40");
		meabId.setManufacturer("Meabcam");
		meabId.setSerialNumber("783468-N76");

		BasicInstrument ratcam = new BasicInstrument(ratcamId);

		Imager ratcap = new Imager();

		DetectorCapabilities ratdet = new DetectorCapabilities();
		BinSet ratbins = new BinSet();
		ratbins.addBinning(1, 1);
		ratbins.addBinning(2, 2);
		ratbins.addBinning(4, 2);
		ratdet.setBinning(ratbins);
		ratdet.setArrayHeight(2048);
		ratdet.setArrayWidth(1024);
		ratdet.setPixelSize(0.135);
		ratdet.setPlateScale(0.135);
		ratcap.setDetectorCapabilities(ratdet);

		ratcap.setSkyModelProvider(true);
		ratcap.setFocusInstrument(true);
		ratcap.setRotatorOffset(Math.toRadians(77.0));

		// #

		FilterSet ratw1 = new FilterSet();
		ratw1.addFilter(new Filter("clear", "Rat-Clear"));
		ratw1.addFilter(new Filter("SDSS-I", "Rat-SDSS-I-01"));
		ratw1.addFilter(new Filter("SDSS-Z", "Rat-SDSS-Z-01"));
		ratw1.addFilter(new Filter("SDSS-R", "Rat-SDSS-R-01"));
		ratw1.addFilter(new Filter("H-Alpha-100", "Rat-Halpha-100-01"));

		ratcap.addFilterSet("lower", ratw1);

		FilterSet ratw2 = new FilterSet();
		ratw2.addFilter(new Filter("Bessell-V", "Rat-Bessell-V-01"));
		ratw2.addFilter(new Filter("SDSS-U", "Rat-SDSS-U-02"));
		ratw2.addFilter(new Filter("SDSS-G", "Rat-SDSS-G-02B"));
		ratw2.addFilter(new Filter("Bessell-B", "Rat-Bessell-B-02"));
		ratw2.addFilter(new Filter("clear", "Rat-Clear"));

		ratcap.addFilterSet("upper", ratw2);
		ratcam.setCapabilities(ratcap);

		bir.addInstrument(ratcamId, ratcam);

		// SUPIRCAM IRCCD x 1 Filterwheel
		BasicInstrument supircam = new BasicInstrument(supircamId);

		Imager supircap = new Imager();
		DetectorCapabilities supdet = new DetectorCapabilities();
		BinSet supbins = new BinSet();
		supbins.addBinning(1, 1);
		supbins.addBinning(2, 2);
		supbins.addBinning(4, 2);
		supdet.setBinning(supbins);
		supircap.setDetectorCapabilities(supdet);

		supircap.setSkyModelProvider(false);
		supircap.setRotatorOffset(Math.toRadians(44.0));

		FilterSet supw1 = new FilterSet();
		supw1.addFilter(new Filter("Barr-K-Prime", "SCam-SDSS-K-01"));
		supw1.addFilter(new Filter("Barr-H", "SCam-SDSS-H-01"));
		supw1.addFilter(new Filter("Barr-J", "SCam-SDSS-J-01"));
		supw1.addFilter(new Filter("clear", "SCam-Clear"));
		supw1.addFilter(new Filter("blank", "SCam-Blank"));
		supircap.addFilterSet("single", supw1);

		supircam.setCapabilities(supircap);

		bir.addInstrument(supircamId,supircam);
		
		// RINGO CCD Polarimeter (No filters)
		BasicInstrument ringo = new BasicInstrument(ringoId);

		Polarimeter ringocap = new Polarimeter();

		DetectorCapabilities ringodet = new DetectorCapabilities();
		BinSet ringobins = new BinSet();
		ringobins.addBinning(1, 1);
		ringobins.addBinning(2, 2);
		ringobins.addBinning(4, 4);
		ringobins.addBinning(8, 8);
		ringodet.setBinning(ringobins);
		ringocap.setDetectorCapabilities(ringodet);

		ringo.setCapabilities(ringocap);

		bir.addInstrument(ringoId,ringo);
		// MEABURN Spec (No filters)
		BasicInstrument meaburn = new BasicInstrument(meabId);

		VariableWavelengthSpectrograph meabcap = new VariableWavelengthSpectrograph(new WavelengthNm(250.0),
				new WavelengthNm(950.0));

		DetectorCapabilities meabdet = new DetectorCapabilities();
		BinSet meabbins = new BinSet();
		meabbins.addBinning(1, 16);
		meabbins.addBinning(2, 16);
		meabbins.addBinning(4, 64);
		meabbins.addBinning(8, 128);
		meabdet.setBinning(meabbins);
		meabcap.setDetectorCapabilities(meabdet);

		meaburn.setCapabilities(meabcap);

		bir.addInstrument(meabId,meaburn);

		// RISE CCD SpeedyCam (No filters)
		BasicInstrument rise = new BasicInstrument(riseId);

		Imager risecap = new Imager();

		DetectorCapabilities risedet = new DetectorCapabilities();
		BinSet risebins = new BinSet();
		risebins.addBinning(1, 1);
		risebins.addBinning(2, 2);
		risebins.addBinning(4, 4);
		risebins.addBinning(8, 8);
		risedet.setBinning(risebins);
		risecap.setDetectorCapabilities(risedet);

		rise.setCapabilities(risecap);

		bir.addInstrument(riseId,rise);

		// FRODO DualBeam Spec BOTH

		BasicInstrument frodo = new BasicInstrument(frodoId);

		DualBeamSpectrograph dual = new DualBeamSpectrograph();

		DetectorCapabilities fdet = new DetectorCapabilities();
		BinSet fbins = new BinSet();
		fbins.addBinning(1, 8);
		fbins.addBinning(2, 16);
		fbins.addBinning(4, 32);
		fdet.setBinning(fbins);
		dual.setDetectorCapabilities(fdet);

		frodo.setCapabilities(dual);

		bir.addInstrument(frodoId,frodo);

		// FRODO DualBeam Spec RED

		BasicInstrument frodoRedArm = new BasicInstrument(frodoRedId);

		DualBeamSpectrograph dualRed = new DualBeamSpectrograph();

		DetectorCapabilities freddet = new DetectorCapabilities();
		BinSet fredbins = new BinSet();
		fredbins.addBinning(1, 8);
		fredbins.addBinning(2, 16);
		fredbins.addBinning(4, 32);
		freddet.setBinning(fredbins);
		dualRed.setDetectorCapabilities(freddet);

		frodoRedArm.setCapabilities(dualRed);

		bir.addInstrument(frodoRedId,frodoRedArm);

		// FRODO DualBeam Spec BLUE

		BasicInstrument frodoBlueArm = new BasicInstrument(frodoBlueId);

		DualBeamSpectrograph dualBlue = new DualBeamSpectrograph();

		DetectorCapabilities fbluedet = new DetectorCapabilities();
		BinSet fbluebins = new BinSet();
		fbluebins.addBinning(1, 8);
		fbluebins.addBinning(2, 16);
		fbluebins.addBinning(4, 32);
		fbluedet.setBinning(fbluebins);
		dualBlue.setDetectorCapabilities(fbluedet);

		frodoBlueArm.setCapabilities(dualBlue);

		bir.addInstrument(frodoBlueId,frodoBlueArm);

		
		// configure monitoring ports
		ConfigurationProperties config = new ConfigurationProperties();
		config.load(new FileInputStream(configFile));

		ratcam.startMonitoring(config.getProperty("ratcam.host"), config.getIntValue("ratcam.port", 6783), 60000L, null);
		supircam
				.startMonitoring(config.getProperty("supircam.host"), config.getIntValue("supircam.port", 8367), 60000L, null);

		frodo.startMonitoring(config.getProperty("frodo.host"), config.getIntValue("frodo.port", 7083), 60000L, null);
		frodoRedArm.startMonitoring(config.getProperty("frodo.host"), config.getIntValue("frodo.port", 7083), 60000L, null);
		frodoBlueArm.startMonitoring(config.getProperty("frodo.host"), config.getIntValue("frodo.port", 7083), 60000L, null);
		rise.startMonitoring(config.getProperty("rise.host"), config.getIntValue("rise.port", 6777), 60000L, null);

	}

	/**
	 * Request the provider to load from registry asynchronously. This method
	 * returns immediately.
	 */
	public void asynchBind() {
		Runnable r = new Runnable() {
			public void run() {
				bind();
			}
		};
		(new Thread(r)).start();
	}

	private void bind() {

		while (true) {

			try {
				// Bind to registry
				Naming.rebind("InstrumentRegistry", bir);
				System.err.println("IREG: Bound...");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// rebind interval
			try {
				Thread.sleep(REBIND_INTERVAL);
			} catch (InterruptedException ix) {
			}
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			StartRegistryServer server = new StartRegistryServer();
			server.configure(new File(args[0]));

			server.asynchBind();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
