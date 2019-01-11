package cp120.assignments.geo_shape;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Object that describes a rectangle inheriting from Geoshape.
 * @author Charlie Misner
 */
public class GeoRectangle extends GeoShape {

    /** Width of the shape */
    protected double width;
    /** Height of the shape */
    protected double height;

    public GeoRectangle( double width, double height ) {
        super(DEFAULT_ORIGIN, DEFAULT_COLOR);
        this.width = width;
        this.height = height;
    }

    public GeoRectangle( GeoPoint origin, double width, double height ) {
        super(origin, DEFAULT_COLOR);
        this.width = width;
        this.height = height;
    }

    public GeoRectangle( GeoPoint origin, Color color, double width, double height ) {
            super(origin, color);
            this.width = width;
            this.height = height;
        }



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
     * Draw rectangle.
     * @param gtx
     */
    public void draw(Graphics2D gtx){
        Rectangle2D.Double rectangle = new Rectangle2D.Double();
        rectangle.setRect(origin.getXco(), origin.getYco(), width, height);

        if(color != null){
            gtx.setColor(color);
            gtx.fill(rectangle);
        }
        BasicStroke stroke = new BasicStroke((float)getEdgeWidth());
        Color drawColor = (getEdgeColor().getRGB() != -1) ? getEdgeColor() : Color.WHITE;
        gtx.setPaint(drawColor);
        gtx.setStroke(stroke);
        gtx.draw(rectangle);
    }

    /**
     * Calculates the area of the rectangle.
     * @return area
     */
    public double area(){
        return this.width * this.height;
    }

    /**
     * Calculates the perimeter of the rectangle.
     * @return perimeter
     */
    public double perimeter(){
        return 2 * (this.width + this.height);
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
