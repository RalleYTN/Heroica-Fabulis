package de.ralleytn.engine.caveman.rendering.geom;

import static java.lang.Math.*;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 07.09.2018/0.4.0
 * @since 07.09.2018/0.4.0
 */
public class OrientedBoundingBox {

	public Quad bottom;
	public Quad top;
	
	/**
	 * @since 07.09.2018/0.4.0
	 */
	public OrientedBoundingBox() {
		
		this.bottom = new Quad();
		this.top = new Quad();
	}
	
	/**
	 * 
	 * @param bottom
	 * @param top
	 * @since 07.09.2018/0.4.0
	 */
	public OrientedBoundingBox(Quad bottom, Quad top) {
		
		this.set(bottom, top);
	}
	
	/**
	 * 
	 * @param bottom
	 * @param top
	 * @since 07.09.2018/0.4.0
	 */
	public void set(Quad bottom, Quad top) {
		
		this.bottom = bottom;
		this.top = top;
	}
	
	/**
	 * 
	 * @param bottom
	 * @since 07.09.2018/0.4.0
	 */
	public void setBottom(Quad bottom) {
		
		this.bottom = bottom;
	}
	
	/**
	 * 
	 * @param top
	 * @since 07.09.2018/0.4.0
	 */
	public void setTop(Quad top) {
		
		this.top = top;
	}
	
	/**
	 * 
	 * @return
	 * @since 07.09.2018/0.4.0
	 */
	public AxisAlignedBoundingBox createAABB() {
		
		float xn = min(min(min(this.bottom.p1.x, this.bottom.p2.x), min(this.bottom.p3.x, this.bottom.p4.x)), min(min(this.top.p1.x, this.top.p2.x), min(this.top.p3.x, this.top.p4.x)));
		float yn = min(min(min(this.bottom.p1.y, this.bottom.p2.y), min(this.bottom.p3.y, this.bottom.p4.y)), min(min(this.top.p1.y, this.top.p2.y), min(this.top.p3.y, this.top.p4.y)));
		float zn = min(min(min(this.bottom.p1.z, this.bottom.p2.z), min(this.bottom.p3.z, this.bottom.p4.z)), min(min(this.top.p1.z, this.top.p2.z), min(this.top.p3.z, this.top.p4.z)));
		
		float xf = max(max(max(this.bottom.p1.x, this.bottom.p2.x), max(this.bottom.p3.x, this.bottom.p4.x)), max(max(this.top.p1.x, this.top.p2.x), max(this.top.p3.x, this.top.p4.x)));
		float yf = max(max(max(this.bottom.p1.y, this.bottom.p2.y), max(this.bottom.p3.y, this.bottom.p4.y)), max(max(this.top.p1.y, this.top.p2.y), max(this.top.p3.y, this.top.p4.y)));
		float zf = max(max(max(this.bottom.p1.z, this.bottom.p2.z), max(this.bottom.p3.z, this.bottom.p4.z)), max(max(this.top.p1.z, this.top.p2.z), max(this.top.p3.z, this.top.p4.z)));
	
		float width = xf - xn;
		float height = yf - yn;
		float depth = zf - zn;
		
		return new AxisAlignedBoundingBox(xn, yn, zn, width, height, depth);
	}
}
