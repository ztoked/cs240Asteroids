package edu.byu.cs.superasteroids.model;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class GrowingAsteroid extends Asteroid
{
    /** How fast the asteroid grows */
    private int growingRate;


    public GrowingAsteroid(double speed, double angle, int health, String name, String type, int breakPieces, int growingRate)
    {
        super(speed, angle, health, name, type, breakPieces);
        this.growingRate = growingRate;
    }

    public GrowingAsteroid(ImageObject myImage, int ID, Point myPosition, double speed, double angle, int health, String name, String type, int breakPieces, int growingRate)
    {
        super(myImage, ID, myPosition, speed, angle, health, name, type, breakPieces);
        this.growingRate = growingRate;
    }

    public GrowingAsteroid(Point myPosition, double speed, double angle, int health, String name, String type, int breakPieces, int growingRate)
    {
        super(myPosition, speed, angle, health, name, type, breakPieces);
        this.growingRate = growingRate;
    }

    /** The method that grows the asteroid */
    public void grow()
    {

    }

    public int getGrowingRate()
    {
        return growingRate;
    }

    public void setGrowingRate(int growingRate)
    {
        this.growingRate = growingRate;
    }
}
