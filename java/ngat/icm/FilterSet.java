/**
 * 
 */
package ngat.icm;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom.Element;

import ngat.util.XmlConfigurable;

/**
 * @author snf
 *
 */
public class FilterSet implements XmlConfigurable, Serializable {

	/** The list of filters in the FilterSet.*/
	protected List filters;

	/** Name of this filterset.*/
	protected String name;
	
	/**
	 * @param filters
	 */
	public FilterSet() {
		filters = new Vector();
	}
	
	/**
	 * @param name
	 */
	public FilterSet(String name) {
		this();
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** Add a filter into the set.*/
	public void addFilter(Filter filter) {
		filters.add(filter);
	}
	
	/** @return an iterator over the list of filters in this set.*/
	public Iterator listFilters() {
		return filters.iterator();
	}
	
	/** Checks for the presence of a named or typed filter
	 * @param filterName The name of a filter to check against the set
	 * @return True if the named filter is present in this set.
	 */
	public boolean containsFilter(FilterDescriptor fd) {
		
		//System.err.println("FilterSet::containsFilter(): Testing: "+fd);
		Iterator ff = filters.iterator();
		while (ff.hasNext()) {
			Filter f = (Filter)ff.next();
			//System.err.println("FilterSet::Testing with my filter: "+f);
			
			if (fd.getFilterClass() == null){
				// we are looking for a name match only
				//System.err.println("FilterSet::Class not specd try name-matching");
				if (fd.getFilterName().equals(f.getFilterName()))
					return true;
				continue;
			} else {
				// we are looking for either a class or name match or both
				//System.err.println("FilterSet::Class and name specd, try either");
				if (fd.getFilterClass().equals(f.getFilterClass()))
					return true;
				else {
					if (fd.getFilterName().equals(f.getFilterName()))
						return true;
					continue;
				}
			}
			
		}
		return false;
	}
	
	public Filter getFilter(FilterDescriptor fd) {
		Iterator ff = filters.iterator();
		while (ff.hasNext()) {
			Filter f = (Filter)ff.next();
			if (fd.getFilterName().equals(f.getFilterName()))
					return f;
		}
		return null;
	}
	
	
	/** Return a readable description of this FilterSet.*/
	public String toString() {
		StringBuffer buffer = new StringBuffer("FilterSet ("+name+") [");
		Iterator ff = filters.iterator();
		while (ff.hasNext()) {
			Filter f = (Filter)ff.next();		
			buffer.append(" "+f.getFilterName());
		}
		buffer.append("]");
		return buffer.toString();
	}

	public void configure(Element fsNode) throws Exception {	
		String fsname = fsNode.getAttributeValue("name");
		System.err.println("Processing filterset: "+fsname);	
		this.name = fsname;
		List fList = fsNode.getChildren("filter");
		Iterator iff = fList.iterator();
		while (iff.hasNext()) {
			Element fNode = (Element)iff.next();	
			Filter filter = new Filter();	
			filter.configure(fNode);
			addFilter(filter);
			System.err.println("Adding filter: "+filter);
		}	
	}
	
}
