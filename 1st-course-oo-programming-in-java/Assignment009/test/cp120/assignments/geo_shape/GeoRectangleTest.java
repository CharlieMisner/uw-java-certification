package cp120.assignments.geo_shape;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeoRectangleTest {
    @Test
    void testInitializationAndAccessors() {
        GeoRectangle rectangle = new GeoRectangle(0,0);
        rectangle = new GeoRectangle(new GeoPoint(0,0),10,10);
        rectangle = new GeoRectangle(new GeoPoint(0,0),Color.BLUE,10,10);

        rectangle.setHeight(12);
        rectangle.setWidth(12);
        rectangle.setOrigin(new GeoPoint(1,1));
        rectangle.setColor(Color.RED);
        rectangle.setEdgeColor(Color.YELLOW);
        rectangle.setEdgeWidth(10);

        assertEquals(12, rectangle.getWidth());
        assertEquals(12, rectangle.getHeight());
        assertEquals(Color.YELLOW, rectangle.getEdgeColor());
        assertEquals(10, rectangle.getEdgeWidth());
        assertEquals(Color.RED,rectangle.getColor());
        assertEquals(new GeoPoint(1,1), rectangle.getOrigin());
    }

    @Test
    void testToString(){
        GeoRectangle rectangle = new GeoRectangle(0,0);
        assertEquals("origin=(0.0000, 0.0000),color=#0000ff,width=0.0000,height=0.0000", rectangle.toString());
    }

    @Test
    void testArea(){
        GeoRectangle rectangle = new GeoRectangle(10,10);
        assertEquals(100, rectangle.area());
    }

    @Test
    void testPerimeter(){
        GeoRectangle rectangle = new GeoRectangle(10,10);
        assertEquals(40, rectangle.perimeter());
    }

    @Test
    void testDraw(){
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB); // Used to create instance of Graphics2D
        Graphics2D graphics2D = image.createGraphics(); // Used to create instance of Graphics2D
        GeoRectangle rectangle = new GeoRectangle(10,10);
        rectangle.draw(graphics2D);
    }
}
