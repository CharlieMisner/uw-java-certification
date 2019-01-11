package cp120.assignments.geo_shape;

import java.awt.*;

public class Assignment006Test {

    public static void main (String[] args){
        GeoPoint point1 = new GeoPoint();
        point1.setXco(3.223);
        point1.setYco(9.223);
        GeoPoint point2 = new GeoPoint();
        point2.setXco(-2.0124);
        point2.setYco(1);
        System.out.println(point1.distance(point2));
        System.out.println(point1.toString());
        System.out.println(point2.toString());
        GeoShape shape = new GeoRectangle();
        shape.setOrigin(point1);
        shape.setColor(Color.BLUE);
        System.out.println(shape.toString());
    }
}
