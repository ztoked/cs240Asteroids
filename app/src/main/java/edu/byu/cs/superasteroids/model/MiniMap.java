package edu.byu.cs.superasteroids.model;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class MiniMap extends PositionedObject
{

    public MiniMap()
    {
    }

    public MiniMap(ImageObject myImage, int ID, Point myPosition)
    {
        super(myImage, ID, myPosition);
    }

    public MiniMap(Point myPosition)
    {
        super(myPosition);
    }

    /** gets all of the positions for everything on the map */
    public void getObjectPositions()
    {

    }
}
