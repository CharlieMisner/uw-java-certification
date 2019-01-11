package cp120.assignments.geo_shape;

import java.awt.Graphics2D;

/**
 * Object that describes a rectangle inheriting from Geoshape.
 * @author Charlie Misner
 */
public class GeoRectangle extends GeoShape {

    /** Width of the shape */
    protected double width;
    /** Height of the shape */
    protected double height;

    /**
     * Overrides toString method. Prints formatted string describing rectangle.
     * @return
     */
    public String toString() {
        int rgb = color.getRGB() & 0x00ffffff;
        String stringColor = String.format( "#%06x", rgb );
        String outputString = String.format(
                "origin=(%.4f, %.4f),color=%s,width=%.4f,height=%.4f",
                this.origin.getXco(),
                this.origin.getYco(),
                stringColor,
                this.width,
                this.height
        );
        return outputString;
    }

    /**
     * Draw rectangle. Currently prints string describing object.
     * @param gtx
     */
    public void draw(Graphics2D gtx){
        int rgb = color.getRGB() & 0x00ffffff;
        String stringColor = String.format( "#%06x", rgb );
        String outputString = String.format(
                "Drawing rectangle: origin=(%.4f, %.4f),color=%s,width=%.4f,height=%.4f",
                this.origin.getXco(),
                this.origin.getYco(),
                stringColor,
                this.width,
                this.height
        );
        System.out.println(outputString);
    }

    /**
     * Width getter.
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Height getter.
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Width setter.
     * @param width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Height setter.
     * @param height
     */
    public void setHeight(double height) {
        this.height = height;
    }
}
