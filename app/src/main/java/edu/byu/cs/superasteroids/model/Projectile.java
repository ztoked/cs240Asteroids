package edu.byu.cs.superasteroids.model;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import edu.byu.cs.superasteroids.core.AsteroidsGame;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.InputManager;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class Projectile extends MovingObject
{
    private boolean firstUpdate;
    private boolean outOfScreen;
    private Rect hitRect;

    private final static float scale = (float).2;

    public Projectile()
    {
        firstUpdate = true;
        outOfScreen = false;
    }
    public Projectile(double speed, double angle, int health)
    {
        super(speed, angle, health);
        firstUpdate = true;
        outOfScreen = false;
    }

    public Rect getHitRect()
    {
        return hitRect;
    }

    public void setHitRect(Rect hitRect)
    {
        this.hitRect = hitRect;
    }

    public void update(double elapsedTime)
    {
        if(!outOfScreen)
        {
            if (firstUpdate)
            {
                firstUpdate = false;
                Cannon cannon = AsteroidsGame.getInstance().getModel().getShip().getMyCannon();
                hitRect = new Rect(getMyPosition().x - cannon.getAttackImage().getWidth()/2, getMyPosition().y - cannon.getAttackImage().getHeight()/2,
                        getMyPosition().x + cannon.getAttackImage().getWidth()/2, getMyPosition().y + cannon.getAttackImage().getHeight()/2);
            }
            else
            {
                Level currentLevel = AsteroidsGame.getInstance().getModel().getCurrentLevel();
                //Log.d("Projectile World x: ", String.valueOf(getMyPosition().getX()));
                //Log.d("Projectile World y: ", String.valueOf(getMyPosition().getY()));
                RectF levelBounds = new RectF(0, 0, currentLevel.getWidth(), currentLevel.getHeight());
                Point newPosition = moveProjectile(getMyPosition(), levelBounds, getSpeed(), Math.toRadians(getAngle()) - Math.PI / 2, elapsedTime);
                setMyPosition(newPosition);
                Cannon cannon = AsteroidsGame.getInstance().getModel().getShip().getMyCannon();
                hitRect = new Rect(newPosition.x - cannon.getAttackImage().getWidth()/2, newPosition.y - cannon.getAttackImage().getHeight()/2,
                        newPosition.x + cannon.getAttackImage().getWidth()/2, newPosition.y + cannon.getAttackImage().getHeight()/2);
                //Log.d("To position", String.valueOf(getMyPosition().getX()));
            }
        }
    }

    private Point moveProjectile(Point objPosition, RectF objBounds, double speed,
                           double angleRadians, double elapsedTime)
    {
        double angleCosine = Math.cos(angleRadians);
        double angleSine = Math.sin(angleRadians);

        double distance = speed * elapsedTime;
        //Log.d("Speed", String.valueOf(speed));

        double deltaX = distance * angleCosine;
        if(deltaX + objPosition.x > objBounds.right + 500 || deltaX + objPosition.x < objBounds.left - 500)
        {
            outOfScreen = true;
        }
        double deltaY = distance * angleSine;
        if(deltaY + objPosition.y > objBounds.bottom + 500 || deltaY + objPosition.y < objBounds.top - 500)
        {
            outOfScreen = true;
        }
        return new Point((int)(objPosition.x + deltaX), (int)(objPosition.y + deltaY));
    }

    public void draw()
    {
        if(!outOfScreen)
        {
            drawProjectile(AsteroidsGame.getInstance().getModel().getShip().getMyCannon().getAttackImageID(), getMyPosition());
        }
    }

    private void drawProjectile(int ID, Point position)
    {
        ViewPort viewPort = AsteroidsGame.getInstance().getModel().viewPort;
        Point drawPoint = new Point(position.x - viewPort.viewPoint.x, position.y - viewPort.viewPoint.y);
        //Log.d("Draw position x: ", String.valueOf(drawPoint.getX()));
        //Log.d("Draw position y: ", String.valueOf(drawPoint.getY()));
        DrawingHelper.drawImage(ID, drawPoint.x,  drawPoint.y ,
                (float)getAngle(), scale, scale,255);
    }

}
