package edu.byu.cs.superasteroids.model;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class ShipBody extends VisibleObject
{
    /** the point that the cannon attaches to the body */
    private Point cannonAttach;
    /** the point that the engine attaches to the body */
    private Point engineAttach;
    /** the point that the extra part attaches to the body */
    private Point extraAttach;
    private int imageID;

    public ShipBody()
    {

    }

    public ShipBody(Point cannonAttach, Point engineAttach, Point extraAttach)
    {
        this.cannonAttach = cannonAttach;
        this.engineAttach = engineAttach;
        this.extraAttach = extraAttach;
    }

    public ShipBody(ImageObject myImage, int ID, Point cannonAttach, Point engineAttach, Point extraAttach)
    {
        super(myImage, ID);
        this.cannonAttach = cannonAttach;
        this.engineAttach = engineAttach;
        this.extraAttach = extraAttach;
    }

    public int getImageID()
    {
        return imageID;
    }

    public void setImageID(int imageID)
    {
        this.imageID = imageID;
    }

    public Point getCannonAttach()
    {
        return cannonAttach;
    }

    public void setCannonAttach(Point cannonAttach)
    {
        this.cannonAttach = cannonAttach;
    }

    public Point getEngineAttach()
    {
        return engineAttach;
    }

    public void setEngineAttach(Point engineAttach)
    {
        this.engineAttach = engineAttach;
    }

    public Point getExtraAttach()
    {
        return extraAttach;
    }

    public void setExtraAttach(Point extraAttach)
    {
        this.extraAttach = extraAttach;
    }
}
