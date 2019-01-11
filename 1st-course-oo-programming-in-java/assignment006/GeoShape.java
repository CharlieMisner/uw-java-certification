package cp120.assignments.geo_shape;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Abstract class with properties and methods to describe a 2D shape.
 * @author Charlie Misner
 */
public abstract class GeoShape {

    /** Origin of the shape */
    protected GeoPoint origin = new GeoPoint();

    /** Color of the shape */
    protected Color color;

    /**
     * Constructor. Not required for this assignment, utilizing it as a good way to ensure that origin is not null.
     */
    public GeoShape() {
        origin.setXco(0);
        origin.setYco(0);
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
