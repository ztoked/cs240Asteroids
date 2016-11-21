package edu.byu.cs.superasteroids.model;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class Background extends PositionedObject
{
    /** simple constructor */
    public Background()
    {
    }

    public Background(String address, int ID)
    {
        super(new ImageObject(address,-1,-1), ID, new Point(-1,-1));
    }

    public Background(ImageObject myImage, int ID, Point myPosition)
    {
        super(myImage, ID, myPosition);
    }

    public Background(Point myPosition)
    {
        super(myPosition);
    }


}
