package cp120.assignments.geo_shape;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test GeoLine.
 */
public class GeoLineTest {

    /**
     * Tests the initialization of the class.
     */
    @Test
    void testInitializationAndAccessors(){
        GeoLine line = new GeoLine(new GeoPoint(2,2), new GeoPoint(5,6));
        GeoLine line2 = new GeoLine(new GeoPoint(2,2), Color.BLUE, new GeoPoint(5,6));
        line.setStart(new GeoPoint(1,2));
        line.setEnd(new GeoPoint(1,10));
        assertTrue(line.getStart().equals(new GeoPoint(1,2)));
        assertTrue(line.getEnd().equals(new GeoPoint(1,10)));
        assertFalse(line.getIsDrawn());
        assertEquals(Color.BLUE ,line2.getColor());
    }

    /**
     * Tests the toString override.
     */
    @Test
    void testToString(){
        GeoLine line = new GeoLine(new GeoPoint(2,2), new GeoPoint(5,6));
        assertEquals("origin=(2.0000, 2.0000),color=#0000ff,end=(5.0000, 6.0000)" ,line.toString());
    }

    /**
     * Tests the length method.
     */
    @Test
    void testLength(){
        GeoLine line = new GeoLine(new GeoPoint(2,2), new GeoPoint(5,6));
        assertEquals(5.0,line.length());
    }

    /**
     * Tests the slope method.
     */
    @Test
    void testSlope(){
        GeoLine line = new GeoLine(new GeoPoint(2,2), new GeoPoint(5,6));
        assertEquals(1.3333333333333333,line.slope());
    }

    /**
     * Instantiates a mock Graphics2D instance and tests the draw method.
     */
    @Test
    void testDraw(){
        GeoLine line = new GeoLine(new GeoPoint(2,2), new GeoPoint(5,6));
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB); // Used to create instance of Graphics2D
        Graphics2D graphics2D = image.createGraphics(); // Used to create instance of Graphics2D
        line.draw(graphics2D);
        assertTrue(line.getIsDrawn());
    }

}
