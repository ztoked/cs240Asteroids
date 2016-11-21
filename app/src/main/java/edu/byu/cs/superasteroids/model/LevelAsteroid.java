package edu.byu.cs.superasteroids.model;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class LevelAsteroid
{
    private int ID;
    /** number of asteroids on the level */
    private int number;
    /** type of asteroids */
    private int asteroidID;
    /** what level this pertains to */
    private int levelID;

    public LevelAsteroid()
    {

    }

    public LevelAsteroid(int number, int asteroidID, int levelID)
    {
        this.number = number;
        this.asteroidID = asteroidID;
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

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public int getAsteroidID()
    {
        return asteroidID;
    }

    public void setAsteroidID(int asteroidID)
    {
        this.asteroidID = asteroidID;
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
