package cp120.assignments.geo_shape;

import java.awt.*;

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
        int rgb = color.getRGB() & 0x00ffffff;
        String stringColor = String.format( "#%06x", rgb );
        String outputString = String.format(
                "Drawing oval: origin=(%.4f, %.4f),color=%s,width=%.4f,height=%.4f",
                this.origin.getXco(),
                this.origin.getYco(),
                stringColor,
                this.width,
                this.height
        );
        System.out.println(outputString);
    }
}
