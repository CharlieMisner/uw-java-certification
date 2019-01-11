package app;

import static app.DriverUtils.doubleString;
import static app.DriverUtils.printf;

import java.awt.Color;
import java.util.Random;

import cp120.assignments.geo_shape.*;

public class ShapeDriver
{
    private static final long   COLOR_SEED      = 1;
    private static final long   DOUBLE_SEED     = 2;

    private Random      randomColors    = new Random( COLOR_SEED );
    private Random      randomDoubles   = new Random( DOUBLE_SEED );
    
    
    public static void main( String[] args )
    {
        String  status;
        
        if ( new PointChecker().quickCheck() )
            status = "PASS";
        else
            status = "FAIL";
        printf( "GeoPoint check: %s%n", status );
        
        if ( new LineChecker().quickCheck() )
            status = "PASS";
        else
            status = "FAIL";
        printf( "GeoLine check: %s%n", status );
          
        if ( new RectangleChecker().quickCheck() )
            status = "PASS";
        else
            status = "FAIL";
        printf( "GeoRectangle check: %s%n", status );
        
        if ( new OvalChecker().quickCheck() )
            status = "PASS";
        else
            status = "FAIL";
        printf( "GeoOval check: %s%n", status );

        new ShapeDriver().execute();
    }
    
    private void execute()
    {
        for ( int inx = 0 ; inx < 5 ; ++inx )
        {
            newLine();
            newOval();
            newRect();
        }
        new Figures().execute();
    }
    
    private Color nextColor()
    {
        float   red     = randomColors.nextFloat();
        float   green   = randomColors.nextFloat();
        float   blue    = randomColors.nextFloat();
        Color   color   = new Color( red, green, blue );
        return color;
    }
    
    private double nextDouble()
    {
        return randomDoubles.nextDouble() * 100;
    }
    
    private GeoPoint nextPoint()
    {
        double      xco     = nextDouble();
        double      yco     = nextDouble();
        GeoPoint    point   = new GeoPoint( xco, yco );
        return point;
    }
    
    private void newOval()
    {
        double      width   = nextDouble();
        double      height  = nextDouble();
        Color       color   = nextColor();
        GeoPoint    origin  = nextPoint();
        GeoOval     oval    = new GeoOval( origin, color, width, height );
        System.out.println( "NEW OVAL>>> " + oval );
        String          area    = "A=" + doubleString( oval.area() );
        String          peri    = "P=" + doubleString( oval.perimeter() );
        System.out.println( "........" + area + "," + peri );
    }
    
    private void 
    newRect()
    {
        double          width   = nextDouble();
        double          height  = nextDouble();
        Color           color   = nextColor();
        GeoPoint        origin  = nextPoint();
        GeoRectangle    rect    = 
            new GeoRectangle( origin, color, width, height );
        String          area    = "A=" + doubleString( rect.area() );
        String          peri    = "P=" + doubleString( rect.perimeter() );
        System.out.println( "RECT>>> " + rect );
        System.out.println( "........" + area + "," + peri );
    }
    
    private void 
    newLine()
    {
        GeoPoint        point1  = nextPoint();
        GeoPoint        point2  = nextPoint();
        Color           color   = nextColor();
        GeoLine         line    = new GeoLine( point1, color, point2 );
        String          slope   = "S=" + doubleString( line.slope() );
        String          len     = "L=" + doubleString( line.length() );
        System.out.println( "NEW LINE>>> " + line );
        System.out.println( "........" + slope + "," + len );
    }
}

