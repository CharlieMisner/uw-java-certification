package cp120.assignments.geo_shape;

import java.awt.Color;

import cp120.assignments.geo_shape.GeoLine;

import static app.DriverUtils.*;

public class LineChecker
{
    public boolean quickCheck()
    {
        boolean result  = true;
        
        result &= lineCheck1();
        result &= lineCheck2();
        
        return result;
    }

    private boolean lineCheck1()
    {
        float       xco1    = 4.4f;
        float       yco1    = 5.5f;
        GeoPoint    point1  = new GeoPoint( xco1, yco1 );
        Color       color1  = Color.RED;
        float       xco2    = 6.6f;
        float       yco2    = 7.7f;
        GeoPoint    point2  = new GeoPoint( xco2, yco2 );
        Color       color2  = Color.BLUE;
        
        GeoLine     line    = new GeoLine( point1, color1, point2 );
        
        GeoPoint    pTemp1  = line.getStart();
        GeoPoint    pTemp2  = line.getEnd();
        Color       cTemp   = line.getColor();
        
        boolean     result  = true;
        result &= assertEqual( point1, pTemp1 );
        result &= assertEqual( point2, pTemp2 );
        result &= assertEqual( color1, cTemp );
        
        line.setStart( point2 );
        line.setEnd( point1 );
        line.setColor( color2 );
        
        pTemp1 = line.getStart();
        pTemp2 = line.getEnd();
        cTemp = line.getColor();
        
        result &= assertEqual( point2, pTemp1 );
        result &= assertEqual( point1, pTemp2 );
        result &= assertEqual( color2, cTemp );
        
        return result;
    }

    private boolean lineCheck2()
    {
        float       xco1    = 4.4f;
        float       yco1    = 5.5f;
        GeoPoint    point1  = new GeoPoint( xco1, yco1 );
        Color       color1  = GeoShape.DEFAULT_COLOR;
        float       xco2    = 4.4f;
        float       yco2    = 5.5f;
        GeoPoint    point2  = new GeoPoint( xco2, yco2 );
        Color       color2  = Color.BLUE;
        
        GeoLine     line    = new GeoLine( point1, point2 );
        
        GeoPoint    pTemp1  = line.getStart();
        GeoPoint    pTemp2  = line.getEnd();
        Color       cTemp   = line.getColor();
        
        boolean     result  = true;
        result &= assertEqual( point1, pTemp1 );
        result &= assertEqual( point2, pTemp2 );
        result &= assertEqual( color1, cTemp );
        
        line.setStart( point2 );
        line.setEnd( point1 );
        line.setColor( color2 );
        
        pTemp1 = line.getStart();
        pTemp2 = line.getEnd();
        cTemp = line.getColor();
        
        result &= assertEqual( point2, pTemp1 );
        result &= assertEqual( point1, pTemp2 );
        result &= assertEqual( color2, cTemp );
        
        return result;
    }
}
