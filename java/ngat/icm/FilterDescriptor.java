/**
 * 
 */
package ngat.icm;

/** Describes a filter.
 * @author snf
 *
 */
public class FilterDescriptor {

	/** The name of the filter.*/
	protected String filterName;
	
	/** The type/class of filter.*/
	protected String filterClass;

	
	
	/** Create a FilterDescriptor. Either parameter can be null.
	 * @param filterName
	 * @param filterClass
	 */
	public FilterDescriptor(String filterName, String filterClass) {
		this.filterName = filterName;
		this.filterClass = filterClass;
	}

	/**
	 * @return the filterClass
	 */
	public String getFilterClass() {
		return filterClass;
	}

	/**
	 * @return the filterName
	 */
	public String getFilterName() {
		return filterName;
	}
	
}
