package cp120.assignments.geo_shape;

import java.awt.Color;

import cp120.assignments.geo_shape.GeoOval;
import cp120.assignments.geo_shape.GeoPlane;
import cp120.assignments.geo_shape.GeoPoint;
import cp120.assignments.geo_shape.GeoRectangle;

public class ShapeDriver
{
    private GeoPlane    plane   = new GeoPlane();
    
    public static void main(String[] args)
    {
        new ShapeDriver().execute();
    }
    
    private void execute()
    {
        newOval( 1.1f, 2.2f, Color.RED );
        newRect( 3.3f, 4.4f, Color.GREEN );
        newOval( 5.5f, 6.6f, Color.BLUE );
        newRect( 7.7f, 8.8f, Color.MAGENTA );
        newOval( 9.9f, 10.01f, Color.CYAN );
        newRect( 10.02f, 10.03f, Color.YELLOW );
        plane.redraw();
    }
    
    private void newOval( float xco, float yco, Color color )
    {
        GeoOval     oval    = new GeoOval();
        GeoPoint    origin  = new GeoPoint();
        origin.setXco( xco );
        origin.setYco( yco );
        oval.setColor( color );
        oval.setOrigin( origin );
        oval.setHeight( 5.5 );
        oval.setWidth( 6.6 );
        System.out.println( oval );
        plane.addShape( oval );
    }
    
    private void newRect( float xco, float yco, Color color )
    {
        GeoRectangle    rect    = new GeoRectangle();
        GeoPoint        origin  = new GeoPoint();
        origin.setXco( xco );
        origin.setYco( yco );
        rect.setColor( color );
        rect.setOrigin( origin );
        rect.setHeight( 5.5 );
        rect.setWidth( 6.6 );
        System.out.println( rect );
        plane.addShape( rect );
    }
}
