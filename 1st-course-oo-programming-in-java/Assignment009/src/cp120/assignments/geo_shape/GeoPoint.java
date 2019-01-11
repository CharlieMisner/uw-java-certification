package cp120.assignments.geo_shape;

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
     * Constructor
     */
    public GeoPoint(double xco, double yco){
        this.xco = xco;
        this.yco = yco;
    }

    /**
     * Checks if point coordinates match.
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj){
        GeoPoint otherPoint = (GeoPoint) obj;
        boolean xCoordsMatch = xco == otherPoint.getXco();
        boolean yCoordsMatch = yco == otherPoint.getYco();
        return (xCoordsMatch && yCoordsMatch);
    }

    /**
     * Overrides hashcode method. Same hashcode for coordinates with matching strings.
     * @return
     */
    @Override
    public int hashCode(){
        String coordinateString = this.toString();
        return coordinateString.hashCode();
    }

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
