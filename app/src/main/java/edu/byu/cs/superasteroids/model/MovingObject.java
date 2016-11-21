package edu.byu.cs.superasteroids.model;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class MovingObject extends PositionedObject
{
    /** speed of the object */
    private double speed;
    /** angle of the object in its movement */
    private double angle;
    /** health of the asteroid */
    private int health;


    public MovingObject()
    {

    }
    public MovingObject(double speed, double angle, int health)
    {
        this.speed = speed;
        this.angle = angle;
        this.health = health;
    }

    public MovingObject(ImageObject myImage, int ID, Point myPosition, double speed, double angle, int health)
    {
        super(myImage, ID, myPosition);
        this.speed = speed;
        this.angle = angle;
        this.health = health;
    }

    public MovingObject(Point myPosition, double speed, double angle, int health)
    {
        super(myPosition);
        this.speed = speed;
        this.angle = angle;
        this.health = health;
    }

    /**
     * The moving object takes damage
     * @param damage
     */
    public void decHealth(int damage)
    {

    }

    /** What happens when two moving objects collide */
    public void collide()
    {

    }

    public int getHealth()
    {
        return health;
    }

    public double getSpeed()
    {
        return speed;
    }

    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    public double getAngle()
    {
        return angle;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }


}
