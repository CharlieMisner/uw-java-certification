package cp120.assignments.geo_shape;

import java.awt.*;
import java.awt.geom.Arc2D;

/**
 * Class to describe an oval.
 */
public class GeoOval extends GeoRectangle {

    public GeoOval( double width, double height ) {
        super(width, height);
    }

    public GeoOval( GeoPoint origin, double width, double height ) {
        super(origin, width, height);
    }

    public GeoOval( GeoPoint origin, Color color, double width, double height ) {
        super(origin, color, width, height);
    }


    /**
     * Draw oval. Currently prints string describing object.
     * @param gtx
     */
    public void draw(Graphics2D gtx){
        Arc2D.Double oval = new Arc2D.Double();
        oval.setArc(origin.getXco(), origin.getYco(), width, height, 90, 360, Arc2D.CHORD);
        draw(oval, gtx);
    }

    /**
     * Determines the approximate perimeter of an oval.
     * @return
     */
    public double perimeter() {
        return 2*Math.PI*Math.sqrt((Math.pow(width, 2)+Math.pow(height, 2))/2);
    }

    /**
     * Determines the approximate area of an oval.
     * @return
     */
    public double area(){
        return width*height*Math.PI;
    }
}
