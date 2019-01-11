package cp120.assignments.geo_shape;

import static app.DriverUtils.EPSILON;
import static app.DriverUtils.assertEqual;

import java.awt.Color;

public class RectangleChecker
{
    public boolean quickCheck()
    {
        boolean result  = true;
        
        result &= rectCheck1();
        result &= rectCheck2();
        result &= rectCheck3();
        result &= rectCheck4();
        
        return result;
    }
    
    // ctor( GeoPoint, Color, double, double )
    private boolean rectCheck1()
    {
        boolean result  = true;
        
        GeoPoint        point   = new GeoPoint( 5, 6 );
        Color           color   = Color.RED;
        double          width   = 10;
        double          height  = 20;
        GeoRectangle    rect    = new GeoRectangle( point, color, width, height );
        
        result &= assertEqual( point, rect.getOrigin() );
        result &= assertEqual( width, rect.getWidth(), EPSILON );
        result &= assertEqual( height, rect.getHeight(), EPSILON );
        
        return result;
    }
    
    // ctor( GeoPoint, double, double )
    private boolean rectCheck2()
    {
        boolean result  = true;
        
        GeoPoint        point   = new GeoPoint( 5, 6 );
        double          width   = 10;
        double          height  = 20;
        GeoRectangle    rect    = new GeoRectangle( point, width, height );
        
        result &= assertEqual( point, rect.getOrigin() );
        result &= assertEqual( rect.getColor(), GeoShape.DEFAULT_COLOR );
        result &= assertEqual( width, rect.getWidth(), EPSILON );
        result &= assertEqual( height, rect.getHeight(), EPSILON );
        
        return result;
    }
    
    // ctor( double, double )
    private boolean rectCheck3()
    {
        boolean result  = true;
        
        double          width   = 10;
        double          height  = 20;
        GeoRectangle    rect    = new GeoRectangle( width, height );
        
        result &= assertEqual( GeoShape.DEFAULT_ORIGIN, rect.getOrigin() );
        result &= assertEqual( rect.getColor(), GeoShape.DEFAULT_COLOR );
        result &= assertEqual( width, rect.getWidth(), EPSILON );
        result &= assertEqual( height, rect.getHeight(), EPSILON );
        
        return result;
    }
    
    // accessors
    private boolean rectCheck4()
    {
        boolean result  = true;
        
        double          width   = 10;
        double          height  = 20;
        GeoRectangle    rect    = new GeoRectangle( width, height );
        
        GeoPoint    point2  = new GeoPoint( 6, 7 );
        Color       color2  = Color.BLUE;
        double      width2  = 30;
        double      height2 = 40;
        rect.setOrigin( point2 );
        rect.setColor( color2 );
        rect.setWidth( width2 );
        rect.setHeight( height2 );
        
        result &= assertEqual( point2, rect.getOrigin() );
        result &= assertEqual( color2, rect.getColor() );
        result &= assertEqual( width2, rect.getWidth(), EPSILON );
        result &= assertEqual( height2, rect.getHeight(), EPSILON );
        
        return result;
    }
}
