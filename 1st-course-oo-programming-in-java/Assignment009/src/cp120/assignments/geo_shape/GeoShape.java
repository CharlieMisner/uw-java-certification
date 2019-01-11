package cp120.assignments.geo_shape;

import java.awt.*;

/**
 * Abstract class with properties and methods to describe a 2D shape.
 * @author Charlie Misner
 */
public abstract class GeoShape {

    /** Default origin */
    public static final GeoPoint DEFAULT_ORIGIN = new GeoPoint(0,0);

    /** Default color */
    public static final Color DEFAULT_COLOR = Color.BLUE;

    /** Default edge color */
    public static final Color DEFAULT_EDGE_COLOR = Color.BLUE;

    /** Default edge width */
    public static final double DEFAULT_EDGE_WIDTH	= 1;

    /** Origin of the shape */
    protected GeoPoint origin;

    /** Color of the shape */
    protected Color color;

    /** Width of edge */
    private double edgeWidth = DEFAULT_EDGE_WIDTH;

    /**  */
    private Color edgeColor	= DEFAULT_EDGE_COLOR;

    /**
     * Constructor.
     */
    public GeoShape(GeoPoint origin, Color color) {
        this.color = color;

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
        return String.format(
            "origin=(%.4f, %.4f),color=%s,edgeColor=%s,edgeWidth=%s",
            this.origin.getXco(),
            this.origin.getYco(),
            stringColor,
            this.edgeColor,
            this.edgeWidth);
    }

    /**
     * Abstract method to draw shape.
     * @param gtx
     */
    public abstract void draw(Graphics2D gtx);

    public void draw(Shape shape, Graphics2D gtx) {
        if(color != null){
            gtx.setColor(color);
            gtx.fill(shape);
        }
        BasicStroke stroke = new BasicStroke((float)getEdgeWidth());
        Color drawColor = (getEdgeColor().getRGB() != -1) ? getEdgeColor() : Color.WHITE;
        gtx.setPaint(drawColor);
        gtx.setStroke(stroke);
        gtx.draw(shape);
    };

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
     * Edge width getter.
     * @return color
     */
    public double getEdgeWidth() { return edgeWidth; }

    /**
     * Edge color getter.
     * @return color
     */
    public Color getEdgeColor() { return edgeColor; }

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

    /**
     * Edge width setter
     * @param edgeWidth
     */
    public void setEdgeWidth(double edgeWidth) {
        this.edgeWidth = edgeWidth;
    }

    /**
     * Edge color setter
     * @param edgeColor
     */
    public void setEdgeColor(Color edgeColor) {
        this.edgeColor = edgeColor;
    }
}
