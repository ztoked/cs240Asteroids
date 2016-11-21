package edu.byu.cs.superasteroids.model;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class LevelObject
{
    private int ID;
    private Point position;
    /** what object it is */
    private int objectID;
    /** how big it is */
    private double scale;
    /** what level it pertains to */
    private int levelID;

    public LevelObject()
    {

    }

    public LevelObject(Point position, int objectID, double scale, int levelID)
    {
        this.position = position;
        this.objectID = objectID;
        this.scale = scale;
        this.levelID = levelID;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public Point getPosition()
    {
        return position;
    }

    public void setPosition(Point position)
    {
        this.position = position;
    }

    public int getObjectID()
    {
        return objectID;
    }

    public void setObjectID(int objectID)
    {
        this.objectID = objectID;
    }

    public double getScale()
    {
        return scale;
    }

    public void setScale(double scale)
    {
        this.scale = scale;
    }

    public int getLevelID()
    {
        return levelID;
    }

    public void setLevelID(int levelID)
    {
        this.levelID = levelID;
    }
}
