package cp120.assignments.geo_shape;

import static app.DriverUtils.*;

import cp120.assignments.geo_shape.GeoPoint;

public class PointChecker
{
    public boolean quickCheck()
    {
        boolean     result  = true;
        
        float       xco     = 1.5f;
        float       yco     = 2.5f;
        GeoPoint    point   = new GeoPoint( xco, yco );
        
        result &= assertEqual( xco, point.getXco(), EPSILON );
        result &= assertEqual( yco, point.getYco(), EPSILON );
        
        float   xco1    = 4.5f;
        float   yco1    = 3.5f;
        point.setXco( xco1 );
        point.setYco( yco1 );
        
        result &= assertEqual( xco1, point.getXco(), EPSILON );
        result &= assertEqual( yco1, point.getYco(), EPSILON );
        
        return result;
    }
}
