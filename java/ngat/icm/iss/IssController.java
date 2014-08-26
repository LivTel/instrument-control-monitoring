package ngat.icm.iss;

import java.util.*;

public interface IssController {

    public void addFitsHeaderProvider(List keywords, FitsHeaderProvider provider);

    public FitsHeaderProvider getFitsHeaderProvider(String keyword);

}
