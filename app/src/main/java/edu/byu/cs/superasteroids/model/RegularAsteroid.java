package edu.byu.cs.superasteroids.model;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class RegularAsteroid extends Asteroid
{
    public RegularAsteroid(double speed, double angle, int health, String name, String type, int breakPieces)
    {
        super(speed, angle, health, name, type, breakPieces);
    }

    public RegularAsteroid(ImageObject myImage, int ID, Point myPosition, double speed, double angle, int health, String name, String type, int breakPieces)
    {
        super(myImage, ID, myPosition, speed, angle, health, name, type, breakPieces);
    }

    public RegularAsteroid(Point myPosition, double speed, double angle, int health, String name, String type, int breakPieces)
    {
        super(myPosition, speed, angle, health, name, type, breakPieces);
    }
}
