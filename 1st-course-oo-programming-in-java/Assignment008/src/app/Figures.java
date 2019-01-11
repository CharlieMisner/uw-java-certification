package app;

import java.awt.Color;

import cp120.assignments.geo_shape.GeoLine;
import cp120.assignments.geo_shape.GeoPlane;
import cp120.assignments.geo_shape.GeoPoint;
import cp120.assignments.geo_shape.GeoRectangle;

public class Figures
{
    private double      xOffset     = 50;
    private double      yOffset     = 50;
    private double      areaWidth   = 400;
    private double      areaHeight  = areaWidth;   
    private double      width       = .333 * areaWidth;
    private double      height      = .25 * areaHeight;
    private GeoPlane    plane       = new GeoPlane();
    
    private GeoPoint    nwCorner    = new GeoPoint( xOffset, yOffset );
    private GeoPoint    neCorner    = 
        new GeoPoint( xOffset + areaWidth, yOffset );
    private GeoPoint    seCorner    =
        new GeoPoint( xOffset + areaWidth, yOffset + areaHeight );
    private GeoPoint    swCorner    =
        new GeoPoint( xOffset, yOffset + areaHeight );
    
    private double      westRectX   = xOffset;
    private double      westRectY   = areaHeight / 2;
    private GeoPoint    westRectOr  = new GeoPoint( westRectX, westRectY );
    
    private double      eastRectX   = areaWidth + xOffset - width;
    private double      eastRectY   = westRectY;
    private GeoPoint    eastRectOr  = new GeoPoint( eastRectX, eastRectY );
    
    private double      northRectX  = areaWidth / 2 - width / 2 + xOffset;
    private double      northRectY  = yOffset;
    private GeoPoint    northRectOr = new GeoPoint( northRectX, northRectY );
    
    public static void main( String[] args )
    {
        new Figures().execute();
    }
    
    public void execute()
    {
        guides();
        cross();
        westRect();
        eastRect();
        northRect();
        plane.show();
    }
    
    private void guides()
    {
        Color           color   = Color.WHITE;
        GeoRectangle    rect    = 
            new GeoRectangle( nwCorner, areaWidth, areaHeight );
        rect.setColor( null );
        rect.setEdgeColor( color );
        rect.setEdgeWidth( 1 );
        plane.addShape( rect );
        
        double      xco     = xOffset + areaWidth / 2;
        double      yco     = yOffset;
        GeoPoint    start   = new GeoPoint( xco, yco );
        
        yco = yOffset + areaHeight;
        GeoPoint    end     = new GeoPoint( xco, yco );
        plane.addShape( new GeoLine( start, color, end ) );
        
        xco = xOffset;
        yco = yOffset + areaHeight / 2;
        start = new GeoPoint( xco, yco );
        
        xco = xOffset + areaWidth;
        end = new GeoPoint( xco, yco );
        plane.addShape( new GeoLine( start, color, end ) );
    }

    private void cross()
    {
        plane.addShape( new GeoLine( nwCorner, Color.GREEN, seCorner ) );
        plane.addShape( new GeoLine( swCorner, Color.YELLOW, neCorner ) );
    }
    
    private void westRect()
    {
        GeoRectangle    rect    = 
            new GeoRectangle( westRectOr, Color.CYAN, width, height );
        plane.addShape( rect );
    }
    
    private void eastRect()
    {
        GeoRectangle    rect    = 
            new GeoRectangle( eastRectOr, Color.YELLOW, width, height );
        rect.setEdgeColor( Color.RED );
        rect.setEdgeWidth( 4 );
        plane.addShape( rect );
    }
    
    private void northRect()
    {
        GeoRectangle    rect    = 
                new GeoRectangle( northRectOr, null, width, height );
        rect.setEdgeColor( Color.YELLOW );
        rect.setEdgeWidth( 1 );
        plane.addShape( rect );
    }
}
