package edu.byu.cs.superasteroids.model;

import android.util.Log;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class Engine extends VisibleObject
{
    /** This is the speed particular for this engine */
    private int baseSpeed;
    /** This engine's turn rate */
    private int baseTurnRate;
    /** The place that the engine is attached */
    private Point attachPoint;

    private int imageID;

    public Engine()
    {

    }

    public Engine(ImageObject myImage, int ID, int baseSpeed, int baseTurnRate, Point attachPoint)
    {
        super(myImage, ID);
        this.baseSpeed = baseSpeed;
        this.baseTurnRate = baseTurnRate;
        this.attachPoint = attachPoint;
    }

    public Engine(int baseSpeed, int baseTurnRate, Point attachPoint)
    {
        this.baseSpeed = baseSpeed;
        this.baseTurnRate = baseTurnRate;
        this.attachPoint = attachPoint;
    }

    public int getImageID()
    {
        return imageID;
    }

    public void setImageID(int imageID)
    {
        this.imageID = imageID;
        //Log.d("Engine set image ID", String.valueOf(imageID));
    }


    public int getBaseSpeed()
    {
        return baseSpeed;
    }

    public void setBaseSpeed(int baseSpeed)
    {
        this.baseSpeed = baseSpeed;
    }

    public int getBaseTurnRate()
    {
        return baseTurnRate;
    }

    public void setBaseTurnRate(int baseTurnRate)
    {
        this.baseTurnRate = baseTurnRate;
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
