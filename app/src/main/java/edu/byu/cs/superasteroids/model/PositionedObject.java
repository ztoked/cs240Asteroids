package edu.byu.cs.superasteroids.model;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class PositionedObject extends VisibleObject
{
    private Point myPosition;

    public PositionedObject()
    {

    }

    public PositionedObject(ImageObject myImage, int ID, Point myPosition)
    {
        super(myImage, ID);
        this.myPosition = myPosition;
    }

    /** Default Constructor for PositionedObject*/
    public PositionedObject(Point myPosition)
    {
        this.myPosition = myPosition;
    }

    public Point getMyPosition()
    {
        return myPosition;
    }

    public void setMyPosition(Point myPosition)
    {
        this.myPosition = myPosition;
    }
}
