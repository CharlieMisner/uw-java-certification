package cp120.assignments.geo_shape;

import java.awt.*;

/**
 * Class that describes a line.
 * @author Charlie Misner
 */
public class GeoLine extends GeoShape{

    /** Line start point. */
    private GeoPoint start;

    /** Line end point. */
    private GeoPoint end;

    /**
     * GeoLine constructor without color provided.
     * @param origin
     * @param end
     */
    public GeoLine(GeoPoint origin, GeoPoint end){
        super(origin, DEFAULT_COLOR);
        this.start = origin;
        this.end = end;
    }

    /**
     * GeoLine constructor with all parameters.
     * @param origin
     * @param color
     * @param end
     */
    public GeoLine( GeoPoint origin, Color color, GeoPoint end ) {
        super(origin, color);
        this.start = origin;
        this.end = end;
    }

    /**
     * Draw line. Currently prints string describing object.
     * @param gtx
     */
    public void draw( Graphics2D gtx ) {
        int rgb = color.getRGB() & 0x00ffffff;
        String stringColor = String.format( "#%06x", rgb );
        String outputString = String.format(
            "Drawing line: origin=(%.4f, %.4f),color=%s,end=(%.4f, %.4f)",
            this.start.getXco(),
            this.start.getYco(),
            stringColor,
            this.end.getXco(),
            this.end.getYco()
        );
        System.out.println(outputString);
    }

    public GeoPoint getStart() {
        return start;
    }

    public void setStart(GeoPoint start) {
        this.start = start;
    }

    public GeoPoint getEnd() {
        return end;
    }

    public void setEnd(GeoPoint end) {
        this.end = end;
    }
}
