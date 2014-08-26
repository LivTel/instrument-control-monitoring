/**
 * 
 */
package ngat.icm;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jdom.Element;

import ngat.util.XmlConfigurable;

/**
 * @author eng
 *
 */
public class BasicSpectrum implements SpectralEfficiencyFunction, XmlConfigurable, Serializable{

	private SortedMap<Wavelength, Double> spectrum;
	
	/**
	 * 
	 */
	public BasicSpectrum() {
		super();
		spectrum = new TreeMap<Wavelength, Double>();
	}

	/* (non-Javadoc)
	 * @see ngat.icm.SpectralEfficiencyFunction#getEfficiency(ngat.icm.Wavelength)
	 */
	public double getEfficiency(Wavelength wavelength) {
		double ws = spectrum.firstKey().getValueNm();
		double we = spectrum.lastKey().getValueNm();
		
		double w = wavelength.getValueNm();
		if (w < ws || w > we)
			return 0.0;
		
		// search the list
		Iterator<Wavelength> iw = spectrum.keySet().iterator();
		while (iw.hasNext()) {
			Wavelength sw = iw.next();
			
			if (w > sw.getValueNm())
				return spectrum.get(sw);
		}
		return 0.0;
	}

	public void configure(Element specNode) throws Exception {
		List wList = specNode.getChildren("item");
		Iterator iw = wList.iterator();
		while (iw.hasNext()) {
			Element iNode = (Element)iw.next();	
			
			Wavelength w = new WavelengthNm(Double.parseDouble(iNode.getAttributeValue("w"))/10.0);
			double sr = Double.parseDouble(iNode.getAttributeValue("s"));
			
			spectrum.put(w, sr);
			
		}
	}

}
