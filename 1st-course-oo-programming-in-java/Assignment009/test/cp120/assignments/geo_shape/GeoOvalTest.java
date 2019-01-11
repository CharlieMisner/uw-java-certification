package cp120.assignments.geo_shape;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeoOvalTest {

    @Test
    void testInitializationAndAccessors() {
        GeoOval oval = new GeoOval(0,0);
        oval = new GeoOval(new GeoPoint(0,0),10,10);
        oval = new GeoOval(new GeoPoint(0,0),Color.BLUE,10,10);

        oval.setHeight(12);
        oval.setWidth(12);
        oval.setOrigin(new GeoPoint(1,1));
        oval.setColor(Color.RED);
        oval.setEdgeColor(Color.YELLOW);
        oval.setEdgeWidth(10);
    }

    @Test
    void testArea(){
        GeoOval oval = new GeoOval(10,10);
        assertEquals(314.1592653589793, oval.area());
    }

    @Test
    void testPerimeter(){
        GeoOval oval = new GeoOval(10,10);
        assertEquals(62.83185307179586, oval.perimeter());
    }

    @Test
    void testDraw(){
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB); // Used to create instance of Graphics2D
        Graphics2D graphics2D = image.createGraphics(); // Used to create instance of Graphics2D
        GeoOval oval = new GeoOval(10,10);
        oval.draw(graphics2D);
    }
}
