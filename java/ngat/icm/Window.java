/**
 * 
 */
package ngat.icm;

/** Represents a detector window or area.
 * @author snf
 *
 */
public class Window {

	/** Width of window (pixels).*/
	public int width;
	
	/** Height of window (pixels).*/
	public int height;

	/** Create a detector window.
	 * @param width Width of area (pixels).
	 * @param height Height of area (pixels).
	 */
	public Window(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	/** Return a readable description of the window.*/
	public String toString() {
			return width+"x"+height;
	}
	
	
}
