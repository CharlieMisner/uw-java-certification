package cp120.assignments.geo_shape;

import java.awt.*;
import java.awt.geom.Line2D;

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
     * Overrides toString method. Prints formatted string describing line.
     * @return
     */
    public String toString() {
        int rgb = color.getRGB() & 0x00ffffff;
        String stringColor = String.format( "#%06x", rgb );
        String outputString = String.format(
                "origin=(%.4f, %.4f),color=%s,end=(%.4f, %.4f)",
                this.start.getXco(),
                this.start.getYco(),
                stringColor,
                this.end.getXco(),
                this.end.getYco()
        );
        return outputString;
    }

    /**
     * Determines the length of a line.
     * @return length
     */
    public double length(){
        return this.start.distance(this.end);
    }

    /**
     * Determines the slope of a line.
     * @return slope
     */
    public double slope(){
        return (this.end.getYco() - this.start.getYco()) / (this.end.getXco() - this.start.getXco());
    }

    /**
     * Draw line.
     * @param gtx
     */
    public void draw( Graphics2D gtx ) {
        Line2D.Double line = new Line2D.Double();
        line.setLine(start.getXco(),start.getYco(), end.getXco(), end.getYco());

        BasicStroke stroke = new BasicStroke((float)getEdgeWidth());
        Color drawColor = (getColor().getRGB() != -1) ? getColor() : Color.WHITE;
        gtx.setPaint(drawColor);

        gtx.setStroke(stroke);
        gtx.draw(line);
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
