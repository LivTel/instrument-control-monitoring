/**
 * 
 */
package ngat.icm.iss;

import ngat.fits.FitsHeaderCardImage;


/**
 * @author snf
 *
 */
public class DefaultFitsHeaderProvider implements FitsHeaderProvider {
	
	public static final String[] keys = new String[] {"TAG_ID","USER_ID", "PROP_ID", "GROUP_ID", "OBS_ID"};

	/**
	 * @return the Fits header card for the specified keyword.
	 */
	public FitsHeaderCardImage getFitsHeader(String keyword) {
		// look for this key and get its index position if known.
		int index = -1;
		for (int i=0; i< keys.length; i++) {
			if (keys[i].equals(keyword))
					index = i;
		}
		return new FitsHeaderCardImage(keyword,
					"DEFAULT:"+keyword,
					"The "+keyword.toLowerCase(),
					"std_units", 
					index);
	}

}
