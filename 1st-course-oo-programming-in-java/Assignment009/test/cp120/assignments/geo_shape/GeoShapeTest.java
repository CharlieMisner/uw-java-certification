package cp120.assignments.geo_shape;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class GeoShapeTest {

    @Test
    void testInitializationAndAccessors(){
        GeoShape geoShape = new GeoShape(new GeoPoint(1,2), Color.BLUE) {
            @Override
            public void draw(Graphics2D gtx) {

            }
        };

        geoShape.setColor(Color.RED);
        geoShape.setOrigin(new GeoPoint(3,4));
        geoShape.setEdgeColor(Color.GREEN);
        geoShape.setEdgeWidth(1);
        assertEquals(Color.GREEN, geoShape.getEdgeColor());
        assertEquals(1, geoShape.getEdgeWidth());
        assertEquals(Color.RED,geoShape.getColor());
        assertEquals(new GeoPoint(3,4), geoShape.getOrigin());
    }

    @Test
    void testToString(){
        GeoShape geoShape = new GeoShape(new GeoPoint(1,2), Color.BLUE) {
            @Override
            public void draw(Graphics2D gtx) {

            }
        };

        assertEquals(
                "origin=(1.0000, 2.0000),color=#0000ff,edgeColor=java.awt.Color[r=0,g=0,b=255],edgeWidth=1.0"
                , geoShape.toString()
        );
    }

    @Test
    void testDraw(){
        GeoShape geoShape = new GeoShape(new GeoPoint(1,2), Color.BLUE) {
            @Override
            public void draw(Graphics2D gtx) {

            }
        };
        Rectangle2D.Double rectangle = new Rectangle2D.Double();
        rectangle.setRect(0, 0, 10, 10);
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB); // Used to create instance of Graphics2D
        Graphics2D graphics2D = image.createGraphics(); // Used to create instance of Graphics2D

        geoShape.draw(rectangle, graphics2D);
    }

    @Test
    void testProperties(){
        GeoShape geoShape = new GeoShape(new GeoPoint(1,2), Color.BLUE) {
            @Override
            public void draw(Graphics2D gtx) {

            }
        };
        GeoPoint defaultOrign = geoShape.DEFAULT_ORIGIN;
        Color defaultColor = geoShape.DEFAULT_COLOR;
        Color defaultEdgeColor = geoShape.DEFAULT_EDGE_COLOR;
        double defaultEdgeWidth = geoShape.DEFAULT_EDGE_WIDTH;

        assertEquals(new GeoPoint(0,0), defaultOrign);
        assertEquals(Color.BLUE, defaultColor);
        assertEquals(Color.BLUE, defaultEdgeColor);
        assertEquals(1, defaultEdgeWidth);
    }

    @Test
    void testNullOrigin(){
        GeoShape geoShape = new GeoShape(null, Color.BLUE) {
            @Override
            public void draw(Graphics2D gtx) {

            }
        };
        geoShape.setOrigin(null);

        assertEquals(new GeoPoint(0,0), geoShape.getOrigin());
    }

}
