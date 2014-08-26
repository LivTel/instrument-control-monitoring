package ngat.icm.iss;

import ngat.fits.*;
public interface FitsHeaderProvider {

    public FitsHeaderCardImage getFitsHeader(String keyword);

}
