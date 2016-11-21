package edu.byu.cs.superasteroids.model;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class Cannon extends PositionedObject
{
    /** where the cannon attaches to the ship */
    private Point attachPoint;
    /** where the projectile leaves the cannon */
    private Point emitPoint;
    /** Cannon image and size */
    private ImageObject attackImage;
    private int attackImageID;
    /** Sound when the cannon fires */
    private String attackSound;
    private int attackSoundID;
    /** how much damage the projectile causes */
    private int damage;
    private int imageID;

    public Cannon()
    {

    }

    /** simple constructor */
    public Cannon(Point attachPoint, Point emitPoint, ImageObject attackImage, String attackSound, int damage)
    {
        this.attachPoint = attachPoint;
        this.emitPoint = emitPoint;
        this.attackImage = attackImage;
        this.attackSound = attackSound;
        this.damage = damage;
    }

    public Cannon(ImageObject myImage, int ID, Point attachPoint, Point emitPoint, ImageObject attackImage, String attackSound, int damage)
    {
        super(myImage, ID, new Point(0,0));
        this.attachPoint = attachPoint;
        this.emitPoint = emitPoint;
        this.attackImage = attackImage;
        this.attackSound = attackSound;
        this.damage = damage;
    }


    /** plays the attackSound when the cannon is shot */
    public void playSound()
    {

    }

    public int getImageID()
    {
        return imageID;
    }

    public int getAttackImageID()
    {
        return attackImageID;
    }

    public int getAttackSoundID()
    {
        return attackSoundID;
    }

    public void setAttackSoundID(int attackSoundID)
    {
        this.attackSoundID = attackSoundID;
    }

    public void setAttackImageID(int attackImageID)
    {
        this.attackImageID = attackImageID;
    }

    public void setImageID(int imageID)
    {
        this.imageID = imageID;
    }

    public Point getAttachPoint()
    {
        return attachPoint;
    }

    public void setAttachPoint(Point attachPoint)
    {
        this.attachPoint = attachPoint;
    }

    public Point getEmitPoint()
    {
        return emitPoint;
    }

    public void setEmitPoint(Point emitPoint)
    {
        this.emitPoint = emitPoint;
    }

    public ImageObject getAttackImage()
    {
        return attackImage;
    }

    public void setAttackImage(ImageObject attackImage)
    {
        this.attackImage = attackImage;
    }

    public String getAttackSound()
    {
        return attackSound;
    }

    public void setAttackSound(String attackSound)
    {
        this.attackSound = attackSound;
    }

    public int getDamage()
    {
        return damage;
    }

    public void setDamage(int damage)
    {
        this.damage = damage;
    }
}
