package cp120.assignments.geo_shape;

import static app.DriverUtils.*;

import java.awt.Color;

public class OvalChecker
{
    public boolean quickCheck()
    {
        boolean result  = true;
        
        result &= ovalCheck1();
        result &= ovalCheck2();
        result &= ovalCheck3();
        result &= ovalCheck4();
        
        return result;
    }
    
    // ctor( GeoPoint, Color, double, double )
    private boolean ovalCheck1()
    {
        boolean result  = true;
        
        GeoPoint    point   = new GeoPoint( 5, 6 );
        Color       color   = Color.RED;
        double      width   = 10;
        double      height  = 20;
        GeoOval     oval    = new GeoOval( point, color, width, height );
        
        result &= assertEqual( point, oval.getOrigin() );
        result &= assertEqual( width, oval.getWidth(), EPSILON );
        result &= assertEqual( height, oval.getHeight(), EPSILON );
        
        return result;
    }
    
    // ctor( GeoPoint, double, double )
    private boolean ovalCheck2()
    {
        boolean result  = true;
        
        GeoPoint    point   = new GeoPoint( 5, 6 );
        double      width   = 10;
        double      height  = 20;
        GeoOval     oval    = new GeoOval( point, width, height );
        
        result &= assertEqual( point, oval.getOrigin() );
        result &= assertEqual( oval.getColor(), GeoShape.DEFAULT_COLOR );
        result &= assertEqual( width, oval.getWidth(), EPSILON );
        result &= assertEqual( height, oval.getHeight(), EPSILON );
        
        return result;
    }
    
    // ctor( double, double )
    private boolean ovalCheck3()
    {
        boolean result  = true;
        
        double      width   = 10;
        double      height  = 20;
        GeoOval     oval    = new GeoOval( width, height );
        
        result &= assertEqual( GeoShape.DEFAULT_ORIGIN, oval.getOrigin() );
        result &= assertEqual( oval.getColor(), GeoShape.DEFAULT_COLOR );
        result &= assertEqual( width, oval.getWidth(), EPSILON );
        result &= assertEqual( height, oval.getHeight(), EPSILON );
        
        return result;
    }
    
    // accessors
    private boolean ovalCheck4()
    {
        boolean result  = true;
        
        double      width   = 10;
        double      height  = 20;
        GeoOval     oval    = new GeoOval( width, height );
        
        GeoPoint    point2  = new GeoPoint( 6, 7 );
        Color       color2  = Color.BLUE;
        double      width2  = 30;
        double      height2 = 40;
        oval.setOrigin( point2 );
        oval.setColor( color2 );
        oval.setWidth( width2 );
        oval.setHeight( height2 );
        
        result &= assertEqual( point2, oval.getOrigin() );
        result &= assertEqual( color2, oval.getColor() );
        result &= assertEqual( width2, oval.getWidth(), EPSILON );
        result &= assertEqual( height2, oval.getHeight(), EPSILON );
        
        return result;
    }
}
