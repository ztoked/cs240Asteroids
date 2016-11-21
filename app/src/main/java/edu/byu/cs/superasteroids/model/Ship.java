package edu.byu.cs.superasteroids.model;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.AsteroidsGame;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.InputManager;


/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class Ship extends MovingObject
{
    /** the body attached to the ship */
    private ShipBody myBody;
    /** the engine attached to the ship */
    private Engine myEngine;
    /** the cannon attached to the ship */
    private Cannon myCannon;
    /** the extra part for the ship */
    private ExtraPart myExtra;

    private double currentSpeed;

    private int coolDown;

    private PowerCore myPowerCore;
    private Point engineOffset;
    private Point cannonOffset;
    private Point extraOffset;
    private Point cannonWorldPosition;
    private Rect hitRect;
    private int shieldTimer;
    private int hits;

    private final static float scale = (float).2;
    private final static int hitRectWidth = 175;
    private final static int hitRectHeight = 150;

    public Ship()
    {

    }

    public Ship(double speed, double angle, int health, ShipBody myBody, Engine myEngine, Cannon myCannon, ExtraPart myExtra)
    {
        super(speed, angle, health);
        this.myBody = myBody;
        this.myEngine = myEngine;
        this.myCannon = myCannon;
        this.myExtra = myExtra;
    }

    public Ship(ImageObject myImage, int ID, Point myPosition, double speed, double angle, int health, ShipBody myBody, Engine myEngine, Cannon myCannon, ExtraPart myExtra)
    {
        super(myImage, ID, myPosition, speed, angle, health);
        this.myBody = myBody;
        this.myEngine = myEngine;
        this.myCannon = myCannon;
        this.myExtra = myExtra;
    }

    public Ship(Point myPosition, double speed, double angle, int health, ShipBody myBody, Engine myEngine, Cannon myCannon, ExtraPart myExtra)
    {
        super(myPosition, speed, angle, health);
        this.myBody = myBody;
        this.myEngine = myEngine;
        this.myCannon = myCannon;
        this.myExtra = myExtra;
    }

    /** The ship shoots a projectile */
    public void shoot()
    {
        if(!ContentManager.getInstance().playSound(myCannon.getAttackSoundID(), 1, 1))
        {
            Log.d("Did not play sound", myCannon.getAttackSound());
        }
        Projectile p = new Projectile();
        p.setAngle(getAngle());
        p.setSpeed(getSpeed() * 4);

        ViewPort view = AsteroidsGame.getInstance().getModel().getViewPort();

        int emitOffsetX = (int)((myCannon.getEmitPoint().x - myCannon.getMyImage().getWidth()/2) * scale);
        int emitOffsetY = (int)((myCannon.getEmitPoint().y - myCannon.getMyImage().getHeight()/2) * scale);
        //Log.d("Cannon World x: ", String.valueOf(cannonWorldPosition.x));
        //Log.d("Cannon World y: ", String.valueOf(cannonWorldPosition.y));
        android.graphics.PointF rotatedEmitOffset = GraphicsUtils.rotate(new android.graphics.PointF(emitOffsetX, emitOffsetY),
                Math.toRadians(getAngle()));

        p.setMyPosition(new Point((int)(rotatedEmitOffset.x + cannonWorldPosition.x),
                (int)(rotatedEmitOffset.y + cannonWorldPosition.y)));
        AsteroidsGame.getInstance().getModel().getProjectiles().add(p);
    }

    /** attaches points of the images so the ship is put together */
    public void drawShip()
    {
        ViewPort viewPort = AsteroidsGame.getInstance().getModel().viewPort;
        Point positionOnScreen = new Point(getMyPosition().getX() - viewPort.viewPoint.getX(),
                getMyPosition().getY() - viewPort.viewPoint.getY());
        Point bodyCenter = new Point(myBody.getMyImage().getWidth()/2, myBody.getMyImage().getHeight()/2);
        drawPart(myBody.getImageID(), positionOnScreen);

        engineOffset = getOffset(myBody.getEngineAttach(), bodyCenter,
                new Point(myEngine.getMyImage().getWidth()/2, myEngine.getMyImage().getHeight()/2), myEngine.getAttachPoint());
        android.graphics.PointF rotatedPartOffset = GraphicsUtils.rotate(new android.graphics.PointF(engineOffset.x, engineOffset.y), Math.toRadians(getAngle()));
        drawPart(myEngine.getImageID(),
                new Point((int)rotatedPartOffset.x + positionOnScreen.x,
                        (int)rotatedPartOffset.y + positionOnScreen.y));


        cannonOffset = getOffset(myBody.getCannonAttach(), bodyCenter,
                new Point(myCannon.getMyImage().getWidth() / 2, myCannon.getMyImage().getHeight() / 2), myCannon.getAttachPoint());
        rotatedPartOffset = GraphicsUtils.rotate(new android.graphics.PointF(cannonOffset.x, cannonOffset.y), Math.toRadians(getAngle()));
        cannonWorldPosition = new Point((int)rotatedPartOffset.x + getMyPosition().x, (int)rotatedPartOffset.y + getMyPosition().y);
        Point cannonPosition = new Point((int)rotatedPartOffset.x + positionOnScreen.x,
                (int)rotatedPartOffset.y + positionOnScreen.y);
        myCannon.setMyPosition(cannonPosition);
        drawPart(myCannon.getImageID(), cannonPosition);



        extraOffset = getOffset(myBody.getExtraAttach(), bodyCenter,
                new Point(myExtra.getMyImage().getWidth()/2, myExtra.getMyImage().getHeight()/2), myExtra.getAttachPoint());
        rotatedPartOffset = GraphicsUtils.rotate(new android.graphics.PointF(extraOffset.x, extraOffset.y), Math.toRadians(getAngle()));
        drawPart(myExtra.getImageID(),
                new Point((int) rotatedPartOffset.x + positionOnScreen.x,
                        (int) rotatedPartOffset.y + positionOnScreen.y));
        if(shieldTimer > 0)
        {
            ViewPort view = AsteroidsGame.getInstance().getModel().getViewPort();
            Point shieldPoint = new Point(getMyPosition().x - view.viewPoint.x, getMyPosition().y - view.viewPoint.y);
            //Log.d("Shield x", String.valueOf(shieldPoint.x));
            //Log.d("Shield y", String.valueOf(shieldPoint.y));
            DrawingHelper.drawFilledCircle(new PointF(shieldPoint.x, shieldPoint.y),150,Color.RED, 150);
        }
    }

    private void drawPart(int imageID, Point position)
    {
        DrawingHelper.drawImage(imageID, position.getX(), position.getY(), (float) getAngle(), scale, scale, 255);
    }

    private Point getOffset(Point bodyAttach, Point bodyCenter, Point partCenter, Point partAttach)
    {
        int partOffsetX = (int)(((bodyAttach.getX() - bodyCenter.getX()) + (partCenter.getX() - partAttach.getX()))*scale);
        int partOffsetY = (int)(((bodyAttach.getY() - bodyCenter.getY()) + (partCenter.getY() - partAttach.getY()))*scale);

        return new Point(partOffsetX, partOffsetY);
    }

    public void update(double elapsedTime)
    {
        if(InputManager.movePoint != null)
        {
            double ang = 0;
            ViewPort view = AsteroidsGame.getInstance().getModel().getViewPort();
            Point movePointWorld = new Point((int)InputManager.movePoint.x + view.viewPoint.x,
                    (int)InputManager.movePoint.y + view.viewPoint.y);
            int deltaX = movePointWorld.x - getMyPosition().x;
            int deltaY = movePointWorld.y - getMyPosition().y;
            if(deltaX != 0 && deltaY != 0)
            {
                ang = Math.atan2((double)deltaY, (double)deltaX);
            }
            else if(deltaX != 0)
            {
                ang = ((deltaX > 0) ? 0 : Math.PI);
            }
            else if(deltaY != 0)
            {
                ang = ((deltaY > 0) ? (0.5 * Math.PI) : (1.5 * Math.PI));
            }
            double angInDeg = GraphicsUtils.radiansToDegrees(ang + (Math.PI / 2));
            setAngle(angInDeg);
            Level currentLevel = AsteroidsGame.getInstance().getModel().getCurrentLevel();
            //Log.d("From position", String.valueOf(getMyPosition().getX()));
            RectF levelBounds = new RectF(0,0,currentLevel.getWidth(),currentLevel.getHeight());
            if(currentSpeed < getSpeed())
            {
               currentSpeed = currentSpeed + myPowerCore.getEngineBoost()*2;
            }
            Point newPosition = moveShip(getMyPosition(), levelBounds, currentSpeed, ang, elapsedTime);
            setMyPosition(newPosition);
            hitRect = new Rect(getMyPosition().x - hitRectWidth/2, getMyPosition().y - hitRectHeight/2,
                    getMyPosition().x + hitRectWidth/2, getMyPosition().y + hitRectHeight/2);
            //Log.d("To position", String.valueOf(getMyPosition().getX()));
        }
        else
        {
            if(currentSpeed > 0)
            {
                currentSpeed = currentSpeed - (myPowerCore.getEngineBoost()/2);
                Level currentLevel = AsteroidsGame.getInstance().getModel().getCurrentLevel();
                RectF levelBounds = new RectF(0,0,currentLevel.getWidth(),currentLevel.getHeight());
                Point newPosition = moveShip(getMyPosition(), levelBounds, currentSpeed, Math.toRadians(getAngle()) - Math.PI/2, elapsedTime);
                setMyPosition(newPosition);
            }
        }

        if(InputManager.firePressed && coolDown == 0)
        {
            coolDown = 9;
            shoot();
        }
        else
        {
            if(coolDown > 0)
            {
                coolDown--;
            }
            else
            {
                coolDown = 0;
            }
        }

        hitRect = new Rect(getMyPosition().x - hitRectWidth/2, getMyPosition().y - hitRectHeight/2,
                getMyPosition().x + hitRectWidth/2, getMyPosition().y + hitRectHeight/2);
        if(shieldTimer > 0)
        {
            shieldTimer--;
        }
    }

    private Point moveShip(Point objPosition, RectF objBounds, double speed,
                           double angleRadians, double elapsedTime)
    {
        double angleCosine = Math.cos(angleRadians);
        double angleSine = Math.sin(angleRadians);

        double distance = speed * elapsedTime;
        //Log.d("Speed", String.valueOf(speed));

        double deltaX = distance * angleCosine;
        if(deltaX + objPosition.x > objBounds.right || deltaX + objPosition.x < objBounds.left)
        {
            deltaX = 0;
        }
        double deltaY = distance * angleSine;
        if(deltaY + objPosition.y > objBounds.bottom || deltaY + objPosition.y < objBounds.top)
        {
            deltaY = 0;
        }
        return new Point((int)(objPosition.x + deltaX), (int)(objPosition.y + deltaY));
    }

    public Rect getHitRect()
    {
        return hitRect;
    }

    public void setHitRect(Rect hitRect)
    {
        this.hitRect = hitRect;
    }

    public int getShieldTimer()
    {
        return shieldTimer;
    }

    public void setShieldTimer(int shieldTimer)
    {
        this.shieldTimer = shieldTimer;
    }

    public int getHits()
    {
        return hits;
    }

    public void setHits(int hits)
    {
        this.hits = hits;
    }

    public PowerCore getMyPowerCore()
    {
        return myPowerCore;
    }

    public Point getEngineOffset()
    {
        return engineOffset;
    }

    public void setEngineOffset(Point engineOffset)
    {
        this.engineOffset = engineOffset;
    }

    public Point getCannonOffset()
    {
        return cannonOffset;
    }

    public void setCannonOffset(Point cannonOffset)
    {
        this.cannonOffset = cannonOffset;
    }

    public Point getExtraOffset()
    {
        return extraOffset;
    }

    public void setExtraOffset(Point extraOffset)
    {
        this.extraOffset = extraOffset;
    }

    public void setMyPowerCore(PowerCore myPowerCore)
    {
        this.myPowerCore = myPowerCore;
    }

    public ShipBody getMyBody()
    {
        return myBody;
    }

    public void setMyBody(ShipBody myBody)
    {
        this.myBody = myBody;
    }

    public Engine getMyEngine()
    {
        return myEngine;
    }

    public void setMyEngine(Engine myEngine)
    {
        this.myEngine = myEngine;
    }


    public Cannon getMyCannon()
    {
        return myCannon;
    }

    public void setMyCannon(Cannon myCannon)
    {
        this.myCannon = myCannon;
    }


    public ExtraPart getMyExtra()
    {
        return myExtra;
    }

    public void setMyExtra(ExtraPart myExtra)
    {
        this.myExtra = myExtra;
    }

    public boolean isComplete()
    {
        if(myBody == null || myCannon == null || myEngine == null || myExtra == null || myPowerCore == null)
        {
            return false;
        }
        return true;
    }

    public void clearParts()
    {
        myBody = null;
        myCannon = null;
        myEngine = null;
        myExtra = null;
        myPowerCore = null;
    }

    public void setcurrentSpeed(int currentSpeed)
    {
        this.currentSpeed = currentSpeed;
    }
}
