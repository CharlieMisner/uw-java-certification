package cp120.assignments.geo_shape;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Abstract class with properties and methods to describe a 2D shape.
 * @author Charlie Misner
 */
public abstract class GeoShape {

    /** Origin of the shape */
    protected GeoPoint origin;

    /** Default origin */
    public static final GeoPoint DEFAULT_ORIGIN = new GeoPoint(0,0);

    /** Color of the shape */
    protected Color color;

    /** Default color */
    public static final Color DEFAULT_COLOR = Color.BLUE;

    /**
     * Constructor.
     */
    public GeoShape(GeoPoint origin, Color color) {
        this.color = (color == null) ? this.DEFAULT_COLOR : color;

        try {
            if (origin == null){
                this.origin = DEFAULT_ORIGIN;
                throw new IllegalArgumentException();
            } else {
                this.origin = origin;
            }
        } catch(IllegalArgumentException exception) {
            System.out.println("Origin cannot be null.");
        }

    }

    /**
     * Over-ride toString() method. Return formatted string describing shape.
     * @return string
     */
    public String toString() {
        int rgb = color.getRGB() & 0x00ffffff;
        String stringColor = String.format( "#%06x", rgb );
        return String.format("origin=(%.4f, %.4f),color=%s", this.origin.getXco(), this.origin.getYco(), stringColor);
    }

    /**
     * Abstract method to draw shape.
     * @param gtx
     */
    public abstract void draw(Graphics2D gtx);

    /**
     * Origin getter.
     * @return origin
     */
    public GeoPoint getOrigin() {
        return origin;
    }

    /**
     * Color getter.
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Origin setter. Null is illegal.
     * @param origin
     * @throws IllegalArgumentException
     * @return void
     */
    public void setOrigin(GeoPoint origin) {
        try {
            if (origin == null){
                throw new IllegalArgumentException();
            } else {
                this.origin = origin;
            }
        } catch(IllegalArgumentException exception) {
            System.out.println("Origin cannot be null.");
        }
    }

    /**
     * Color setter
     * @param color
     * @return void
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
