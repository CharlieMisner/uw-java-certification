package cp120.assignments.geo_shape;


import java.util.ArrayList;
import java.util.List;

/**
 * Stores shapes in a list. Enables drawing of multiple shapes.
 * @author Charlie Misner
 */
public class GeoPlane {

    /** Array list of shapes */
    private List<GeoShape> shapes = new ArrayList<GeoShape>();

    /**
     * Adds shape to list.
     * @param shape
     * @return void
     */
    public void addShape( GeoShape shape){
        shapes.add(shape);
    }

    /**
     * Removes shape from list.
     * @param shape
     * @return void
     */
    public void removeShape( GeoShape shape){
        shapes.remove(shape);
    }

    /**
     * Draws shapes in list.
     */
    public void redraw(){
        for(GeoShape shape:shapes){
            shape.draw(null);
        }
    }

    /**
     * Shapes getter.
     * @return shapes
     */
    public List<GeoShape> getShapes(){
        return shapes;
    }
}
