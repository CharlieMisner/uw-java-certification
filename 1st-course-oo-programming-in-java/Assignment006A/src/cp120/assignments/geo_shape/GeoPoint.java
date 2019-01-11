package cp120.assignments.geo_shape;
import java.lang.Math;

/**
 * Class that describes a point in 2D space.
 * @author Charlie Misner
 */
public class GeoPoint {

    /** X Coordinate */
    private double xco;

    /** Y Coordinate */
    private double yco;

    /**
     * Constructor (for future)
     */
    public GeoPoint(){}

    /**
     * Calculates the distance between two points.
     * @param other Geopoint
     * @return distance double
     */
    public double distance (GeoPoint other){
        double xDifference = this.xco - other.xco;
        double yDifference = this.yco - other.yco;
        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
    }

    /**
     * Returns coordinates as a formatted string.
     * @return coordinates String
     */
    public String toString(){
        return String.format("(%.4f, %.4f)", this.xco, this.yco);
    }

    /**
     * xco getter.
     * @return xco
     */
    public double getXco() {
        return xco;
    }

    /**
     * yco getter.
     * @return yco
     */
    public double getYco() {
        return yco;
    }

    /**
     * xco setter.
     * @return void
     */
    public void setXco(double pointXCoordinate) {
        this.xco = pointXCoordinate;
    }

    /**
     * yco setter.
     * @return void
     */
    public void setYco(double pointYCoordinate) {
        this.yco = pointYCoordinate;
    }
}
