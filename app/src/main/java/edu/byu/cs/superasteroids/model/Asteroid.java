package edu.byu.cs.superasteroids.model;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.List;

import edu.byu.cs.superasteroids.core.AsteroidsGame;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.InputManager;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class Asteroid extends MovingObject
{
    /** Asteroid type name */
    private String type;
    /** the number of pieces that the asteroid breaks into when blown up */
    private int breakPieces;

    private int imageID;
    private String name;

    private double scale;
    Rect hitRect;
    int imageWidth;
    int imageHeight;
    int asteroidID;
    int growDelay;

    public Asteroid()
    {

    }

    public Asteroid(double speed, double angle, int health, String name, String type, int breakPieces)
    {
        super(speed, angle, health);
        this.type = type;
        this.name = name;
        this.breakPieces = breakPieces;
    }

    public Asteroid(ImageObject myImage, int ID, Point myPosition, double speed, double angle, int health, String name, String type, int breakPieces)
    {
        super(myImage, ID, myPosition, speed, angle, health);
        this.type = type;
        this.name = name;
        this.breakPieces = breakPieces;
    }

    public Asteroid(Point myPosition, double speed, double angle, int health, String name, String type, int breakPieces)
    {
        super(myPosition, speed, angle, health);
        this.type = type;
        this.name = name;
        this.breakPieces = breakPieces;
    }

    public void draw()
    {
        //Log.d("Draw Asteroid", String.valueOf(imageID));
        //Log.d("X position", String.valueOf(getMyPosition().x));
        //Log.d("Y position", String.valueOf(getMyPosition().y));

        ViewPort viewPort = AsteroidsGame.getInstance().getModel().viewPort;
        Point positionOnScreen = new Point(getMyPosition().x - viewPort.viewPoint.x,
                getMyPosition().y - viewPort.viewPoint.y);
        DrawingHelper.drawImage(imageID, positionOnScreen.x, positionOnScreen.y, 0, (float)scale, (float)scale, 255);
    }

    public void update(double elapsedTime)
    {
        if(type.equals("growing"))
        {
            if(growDelay < 60)
            {
                //Log.d("growDelay", String.valueOf(growDelay));
                growDelay++;
            }
            else if(growDelay >= 60)
            {
                Log.d("Asteroid is growing", String.valueOf(scale));
                growDelay = 0;
                scale += .1;
                imageWidth = (int)(161 * scale);
                imageHeight = (int)(178 * scale);
            }

        }
        Level currentLevel = AsteroidsGame.getInstance().getModel().getCurrentLevel();
        RectF levelBounds = new RectF(0,0,currentLevel.getWidth(),currentLevel.getHeight());
        //Log.d("Angle", String.valueOf(getAngle()));
        Point newPosition = moveAsteroid(getMyPosition(), levelBounds, getSpeed(), Math.toRadians(getAngle()) - Math.PI/2, elapsedTime);
        setMyPosition(newPosition);
        hitRect = new Rect(newPosition.x - imageWidth/2, newPosition.y - imageHeight/2,
                newPosition.x + imageWidth/2, newPosition.y + imageHeight/2);
        for(int i = 0; i < AsteroidsGame.getInstance().getModel().getProjectiles().size(); i++)
        {
            Projectile p = AsteroidsGame.getInstance().getModel().getProjectiles().get(i);
            if(hitRect.intersect(p.getHitRect()))
            {
                AsteroidsGame.getInstance().getModel().getProjectiles().remove(i);
                blowUp();
                return;
            }
        }
        Ship ship = AsteroidsGame.getInstance().getModel().getShip();
        if(ship.getShieldTimer() == 0)
        {
            if (hitRect.intersect(ship.getHitRect()))
            {
                blowUp();
                ship.setHits(ship.getHits() + 1);
                ship.setShieldTimer(60);
            }
        }
    }

    private Point moveAsteroid(Point objPosition, RectF objBounds, double speed,
                           double angleRadians, double elapsedTime)
    {

        double angleCosine = Math.cos(angleRadians);
        double angleSine = Math.sin(angleRadians);
        double distance = speed * elapsedTime;
        //Log.d("Speed", String.valueOf(speed));

        double deltaX = distance * angleCosine;
        if(deltaX + objPosition.x > objBounds.right || deltaX + objPosition.x < objBounds.left)
        {
            deltaX = deltaX * -1;
            setAngle(360 - getAngle());
        }
        double deltaY = distance * angleSine;
        if(deltaY + objPosition.y > objBounds.bottom || deltaY + objPosition.y < objBounds.top)
        {
            deltaY = deltaY * -1;
            setAngle(180 - getAngle());
        }
        return new Point((int)(objPosition.x + deltaX), (int)(objPosition.y + deltaY));
    }


    /** This is what happens when the asteroid blows up*/
    public void blowUp()
    {
        List<Asteroid> asteroids = AsteroidsGame.getInstance().getModel().getCurrentLevel().getLevelAsteroids();
        for(int i = 0; i < asteroids.size(); i++)
        {
            if(asteroids.get(i) == this)
            {
                if(scale >= 1)
                {
                    for(int j = 0; j < breakPieces; j++)
                    {
                        Asteroid a = new Asteroid();
                        if(asteroidID+3 > 6)
                        {
                            a.setType(asteroidID);
                        }
                        else
                        {
                            a.setType(asteroidID + 3);
                        }
                        a.setMyPosition(getMyPosition());
                        a.setImageID(imageID);
                        a.setAngle(getAngle() + ((365 / breakPieces) * j));
                        a.setSpeed(getSpeed());
                        asteroids.add(a);
                    }
                }
                asteroids.remove(i);
                return;
            }
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getScale()
    {
        return scale;
    }

    public void setScale(double scale)
    {
        this.scale = scale;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int getImageID()
    {
        return imageID;
    }

    public void setImageID(int imageID)
    {
        this.imageID = imageID;
    }

    public String getType()
    {
        return type;
    }

    public void setType(int AsteroidID)
    {
        asteroidID = AsteroidID;
        if(AsteroidID == 1)
        {
            type = "regular";
            breakPieces = 2;
            scale = 1;
            imageWidth = 169;
            imageHeight = 153;
        }
        else if(AsteroidID == 2)
        {
            type = "growing";
            breakPieces = 2;
            scale = 1;
            imageWidth = 161;
            imageHeight = 178;
        }
        else if(AsteroidID == 3)
        {
            type = "octeroid";
            breakPieces = 8;
            scale = 1;
            imageWidth = 169;
            imageHeight = 153;
        }
        //small regular asteroid
        else if(AsteroidID == 4)
        {
            type = "regular";
            breakPieces = 0;
            scale = 1.0/2.0;
            imageWidth = (int)(169 * scale);
            imageHeight = (int)(153 * scale);
        }
        //small growing
        else if(AsteroidID == 5)
        {
            type = "growing";
            breakPieces = 2;
            scale = 1.0/2.0;
            imageWidth = (int)(161 * scale);
            imageHeight = (int)(178 * scale);
        }
        //small octeroid
        else if(AsteroidID == 6)
        {
            type = "octeroid";
            breakPieces = 0;
            scale = 1.0/3.0;
            imageWidth = (int)(169 * scale);
            imageHeight = (int)(153 * scale);
        }
    }

    public int getBreakPieces()
    {
        return breakPieces;
    }

    public void setBreakPieces(int breakPieces)
    {
        this.breakPieces = breakPieces;
    }
}
