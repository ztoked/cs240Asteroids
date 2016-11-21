package edu.byu.cs.superasteroids.model;


/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class VisibleObject
{
    /** This is the image of the VisibleObject */
    private ImageObject myImage;

    /** ID number from database */
    private int ID;

    public VisibleObject(ImageObject myImage, int ID)
    {
        this.myImage = myImage;
        this.ID = ID;
    }

    public VisibleObject()
    {
    }

    /**
     * Draws the image on the screen
     */
    public void Draw()
    {

    }

    /**
     * Will possible redraw the image in a different place if it has changed
     */
    public void Update()
    {

    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public ImageObject getMyImage()
    {
        return myImage;
    }

    public void setMyImage(ImageObject myImage)
    {
        this.myImage = myImage;
    }
}
