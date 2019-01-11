package cp120.assignments.geo_shape;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Geopoint class.
 */
class GeoPointTest {

    /**
     * Tests the initialization of the class.
     */
    @Test
    void testClassInitialization() {
        GeoPoint testPoint = new GeoPoint(5, 10);
        assertEquals(5, testPoint.getXco());
        assertEquals(10, testPoint.getYco());
    }

    /**
     * Test the equals override.
     */
    @Test
    void testEqualsOverride() {
        GeoPoint testPoint1 = new GeoPoint(5, 10);
        GeoPoint testPoint2 = new GeoPoint(5, 10);
        assertTrue(testPoint1.equals(testPoint2));
    }

    /**
     * Test hashCode overide.
     */
    @Test
    void testHashCodeOverride() {
        GeoPoint testPoint = new GeoPoint(5, 10);
        assertEquals(-2060572657, testPoint.hashCode());
    }

    /**
     * Test the distance method.
     */
    @Test
    void testDistance() {
        GeoPoint testPoint1 = new GeoPoint(5, 10);
        GeoPoint testPoint2 = new GeoPoint(10, 10);
        assertEquals(5.0, testPoint1.distance(testPoint2));
    }

    /**
     * Test to string override.
     */
    @Test
    void testToStringOverride() {
        GeoPoint testPoint = new GeoPoint(5, 10);
        assertEquals("(5.0000, 10.0000)", testPoint.toString());
    }

    /**
     * Test accessors.
     */
    @Test
    void testAccessors() {
        GeoPoint testPoint = new GeoPoint(5, 10);
        testPoint.setXco(6);
        testPoint.setYco(12);
        assertEquals(6, testPoint.getXco());
        assertEquals(12, testPoint.getYco());
    }
}