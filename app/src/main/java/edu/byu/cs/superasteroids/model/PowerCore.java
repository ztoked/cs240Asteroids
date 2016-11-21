package edu.byu.cs.superasteroids.model;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class PowerCore extends VisibleObject
{

    /** the boost of the cannon */
    private int cannonBoost;
    /** the boost of the engine */
    private int engineBoost;

    public PowerCore()
    {

    }

    public PowerCore(ImageObject myImage, int ID, int cannonBoost, int engineBoost)
    {
        super(myImage, ID);
        this.cannonBoost = cannonBoost;
        this.engineBoost = engineBoost;
    }

    public PowerCore(int cannonBoost, int engineBoost)
    {
        this.cannonBoost = cannonBoost;
        this.engineBoost = engineBoost;
    }

    public int getCannonBoost()
    {
        return cannonBoost;
    }

    public void setCannonBoost(int cannonBoost)
    {
        this.cannonBoost = cannonBoost;
    }

    public int getEngineBoost()
    {
        return engineBoost;
    }

    public void setEngineBoost(int engineBoost)
    {
        this.engineBoost = engineBoost;
    }
}
