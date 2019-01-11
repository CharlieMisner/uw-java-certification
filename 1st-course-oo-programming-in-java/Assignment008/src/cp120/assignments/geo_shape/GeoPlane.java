package cp120.assignments.geo_shape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Represents a graphical portrayal of shapes from the GeoShape library.
 * Shapes to draw on the plane are stored in a list. The shapes will be drawn
 * every time one of the following occurs:
 * <ol>
 * <li>The plane is made visible (i.e., the user calls the show method)</li>
 * <li>The plane is visible and needs to be refreshed due to operator 
 *     activity</li>
 * <li>The plane is visible and the user calls the <em>redraw</em> method</li>
 * </ol>
 * @see #addShape(GeoShape)
 * @see #redraw()
 */
public class GeoPlane
    implements Runnable
{
    
    /** The all shapes. */
    private final   List<GeoShape>  allShapes;
    
    /** The color. */
    private final   Color           color;
    
    /** The panel. */
    private final   Panel           panel;
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
        JFrame  frame   = new JFrame( "GeoPlane" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setContentPane( panel );
        frame.pack();
        frame.setVisible( true );
    }
    
    /**
     * Instantiates a new GeoPlane.
     */
    public GeoPlane()
    {
        this( new Color( .5f, .5f, .5f ) );
    }
    
    /**
     * Instantiates a new GeoPlane with a background of the given color.
     *
     * @param color the given background color
     */
    public GeoPlane( Color color )
    {
        this.color = color;
        allShapes = new ArrayList<>();
        panel = new Panel();
    }
    
    /**
     * Displays the plane. Any shapes stored in the list of shapes to draw
     * will immediately be drawn. If part or all of the plane requires redrawing
     * do to operator activity, the shapes will automatically be redrawn.
     * 
     *  @see #addShape(GeoShape)
     */
    public void show()
    {
        new Thread( this, "GeoPlane Thread" ).start();
    }
    
    /**
     * Adds a geometric shape to the list of shapes drawn in the plane. Note
     * that the shape is not immediately drawn on the plane; to immediately 
     * draw the shape, call the <em>redraw</em> method.
     *
     * @param shape the shape to draw
     * @see #show
     * @see #redraw
     * @see #removeShape(GeoShape)
     */
    public void addShape( GeoShape shape )
    {
        allShapes.add( shape );
    }
    
    /**
     * Removes a geometric shape from the list of shapes drawn in the plane.
     * Note that the plane will not be immediately redrawn; to immediately 
     * update the visible plane, call the <em>redraw</em> method.
     *
     * @param shape the shape to remove
     */
    public void removeShape( GeoShape shape )
    {
        allShapes.remove( shape );
    }
    
    /**
     * Gets the list of shapes to be drawn in the plane. The list is <em>not</em> 
     * modifiable. 
     *
     * @return the shapes
     */
    public List<GeoShape> getShapes()
    {
        List<GeoShape>  list    = Collections.unmodifiableList( allShapes );
        return list;
    }
    
    /**
     * Explicitly draws the shapes in the list of shapes. Calling this method
     * has no effect if the plane is not visible.
     */
    public void redraw()
    {
        panel.repaint();
    }
    
    /**
     * The Class Panel.
     */
    private class Panel extends JPanel
    {        
        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = -7413434451145995808L;

        /**
         * Instantiates a new panel.
         */
        public Panel()
        {
            setPreferredSize( new Dimension( 500, 500 ) );
        }
        
        /* (non-Javadoc)
         * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
         */
        @Override
        public void paintComponent( Graphics graphics )
        {
            super.paintComponent( graphics );
            
            Graphics2D  gtx = (Graphics2D)graphics.create();
            gtx.setColor( color );
            gtx.fillRect( 0,  0,  getWidth(), getHeight() );
            for ( GeoShape shape : allShapes)
                shape.draw( gtx );
        }
    }
}
