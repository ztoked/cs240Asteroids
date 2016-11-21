package edu.byu.cs.superasteroids.base;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

import java.util.List;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.AsteroidsGame;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.Asteroid;
import edu.byu.cs.superasteroids.model.AsteroidsModel;
import edu.byu.cs.superasteroids.model.Level;
import edu.byu.cs.superasteroids.model.LevelObject;
import edu.byu.cs.superasteroids.model.Point;
import edu.byu.cs.superasteroids.model.ViewPort;

/**
 * Created by zachhalvorsen on 2/25/16.
 */
public class GameDelegate implements IGameDelegate
{
    GameView view;
    AsteroidsModel model = AsteroidsGame.getInstance().getModel();
    Level currentLevel = model.getCurrentLevel();
    ViewPort viewPort = model.getViewPort();
    boolean isGameFinished;

    public GameDelegate(GameView view)
    {
        this.view = view;
    }
    @Override
    public void update(double elapsedTime)
    {
        if(!isGameFinished)
        {
            currentLevel = model.getCurrentLevel();
            if (currentLevel.getLevelAsteroids().size() == 0)
            {
                Log.d("Going from level", String.valueOf(model.getCurrentLevelNumber()));
                isGameFinished = model.goToNextLevel();
                Log.d("To level", String.valueOf(model.getCurrentLevelNumber()));
            }
            else
            {
                model.update(elapsedTime);
                viewPort.updateView();
            }
        }
    }

    @Override
    public void loadContent(ContentManager content)
    {

    }

    @Override
    public void unloadContent(ContentManager content)
    {

    }

    @Override
    public void draw()
    {
        if(isGameFinished)
        {
            DrawingHelper.drawFilledRectangle(new Rect(0,0,AsteroidsGame.getInstance().getModel().getCurrentLevel().getWidth(),
                    AsteroidsGame.getInstance().getModel().getCurrentLevel().getHeight()), Color.BLACK,255);
            DrawingHelper.drawCenteredText("YOU WON", 100, Color.WHITE);
        }
        else if(AsteroidsGame.getInstance().getModel().getShip().getHits() > 3)
        {
            DrawingHelper.drawFilledRectangle(new Rect(0,0,AsteroidsGame.getInstance().getModel().getCurrentLevel().getWidth(),
                    AsteroidsGame.getInstance().getModel().getCurrentLevel().getHeight()), Color.BLACK,255);
            DrawingHelper.drawCenteredText("GAME OVER", 100, Color.WHITE);
        }
        else
        {
            double d = (double) currentLevel.getWidth() / 2048.0;
            double d1 = (double) currentLevel.getHeight() / 2048.0;

            int spaceImageID = AsteroidsGame.getInstance().getSpaceImage();
            DrawingHelper.drawImage(spaceImageID, currentLevel.getWidth() / 2, currentLevel.getHeight() / 2, 0, (float) d, (float) d1, 255);

            for (int i = 0; i < currentLevel.getLevelObjects().size(); i++)
            {
                LevelObject lo = currentLevel.getLevelObjects().get(i);
                Point loPosition = new Point(lo.getPosition().getX() - viewPort.viewPoint.getX(),
                        lo.getPosition().getY() - viewPort.viewPoint.getY());
                int imageID = AsteroidsGame.getInstance().getBackgroundImages().get(lo.getObjectID() - 1);
                //Log.d("level object", String.valueOf(imageID));
                //Log.d("x", String.valueOf(lo.getPosition().getX()));
                //Log.d("y", String.valueOf(lo.getPosition().getY()));
                DrawingHelper.drawImage(imageID, loPosition.getX() - viewPort.viewPoint.getX(), loPosition.getY() - viewPort.viewPoint.getY(),
                        0, (float) lo.getScale(), (float) lo.getScale(), 255);
            }
            model.draw();
            //Draw the minimap
            DrawingHelper.drawFilledRectangle(new Rect(140, DrawingHelper.getGameViewHeight() - (DrawingHelper.getGameViewHeight() / 5) - 160,
                    (DrawingHelper.getGameViewWidth()/5) + 160, DrawingHelper.getGameViewHeight() - 140),
                    Color.RED, 255);
            Point miniMapOrigin = new Point(150, DrawingHelper.getGameViewHeight() - (DrawingHelper.getGameViewHeight() / 5) - 150);
            Rect miniMapRect = new Rect(miniMapOrigin.getX(), miniMapOrigin.getY(),
                    (DrawingHelper.getGameViewWidth()/5) + 150, DrawingHelper.getGameViewHeight() - 150);
            DrawingHelper.drawFilledRectangle(miniMapRect, Color.BLACK, 255);
            List<Asteroid> currentLevelAsteroids = model.getCurrentLevel().getLevelAsteroids();
            for(int i = 0; i < currentLevelAsteroids.size(); i++)
            {
                Point currentWorldPosition = currentLevelAsteroids.get(i).getMyPosition();
                Point currentMiniMapPosition = new Point((int)(miniMapOrigin.getX() + (currentWorldPosition.getX() * .1275)),
                        (int)(miniMapOrigin.getY() + (currentWorldPosition.getY() * .07)));

                //Log.d("Minimap position x", String.valueOf(currentMiniMapPosition.getX()));
                //Log.d("Minimap position y", String.valueOf(currentMiniMapPosition.getY()));

                DrawingHelper.drawPoint(new PointF(currentMiniMapPosition.getX(),currentMiniMapPosition.getY()), 10,Color.BLUE,255);
            }
            Point shipWorldPosition = AsteroidsGame.getInstance().getModel().getShip().getMyPosition();
            Point currentMiniMapPosition = new Point((int)(miniMapOrigin.getX() + (shipWorldPosition.getX() * .1275)),
                    (int)(miniMapOrigin.getY() + (shipWorldPosition.getY() * .07)));

            DrawingHelper.drawPoint(new PointF(currentMiniMapPosition.getX(),currentMiniMapPosition.getY()), 10,Color.GREEN,255);
        }
    }
}
