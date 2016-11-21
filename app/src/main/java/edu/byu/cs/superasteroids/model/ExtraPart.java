package edu.byu.cs.superasteroids.model;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class ExtraPart extends VisibleObject
{
    /** Point the part attaches to the ship */
    private Point attachPoint;

    private int imageID;

    public ExtraPart()
    {

    }

    public ExtraPart(ImageObject myImage, int ID, Point attachPoint)
    {
        super(myImage, ID);
        this.attachPoint = attachPoint;
    }

    public int getImageID()
    {
        return imageID;
    }

    public void setImageID(int imageID)
    {
        this.imageID = imageID;
    }

    public ExtraPart(Point attachPoint)
    {
        this.attachPoint = attachPoint;
    }

    public Point getAttachPoint()
    {
        return attachPoint;
    }

    public void setAttachPoint(Point attachPoint)
    {
        this.attachPoint = attachPoint;
    }
}
