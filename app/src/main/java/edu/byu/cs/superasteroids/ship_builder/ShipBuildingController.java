package edu.byu.cs.superasteroids.ship_builder;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.AsteroidsGame;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.AsteroidsModel;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.Engine;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.ImageObject;
import edu.byu.cs.superasteroids.model.Point;
import edu.byu.cs.superasteroids.model.PowerCore;
import edu.byu.cs.superasteroids.model.Ship;
import edu.byu.cs.superasteroids.model.ShipBody;

/**
 * Created by zachhalvorsen on 2/22/16.
 */
public class ShipBuildingController implements IShipBuildingController
{
    private IShipBuildingView view;
    private AsteroidsModel model;
    private Ship ship;
    private ContentManager content;
    private IShipBuildingView.PartSelectionView currentView; //ShipBuilderState state;
    //These get set when a part has been selected
    private int shipBodyIndex;
    private int engineIndex;
    private int cannonIndex;
    private int extraPartIndex;
    private int powerCoreIndex;

    private static float scale = (float).5;

    public ShipBuildingController(IShipBuildingView view)
    {
        this.view = view;
        model = AsteroidsGame.getInstance().getModel();
        this.ship = model.getShip();
        this.content = null;

        shipBodyIndex = -1;
        engineIndex = -1;
        cannonIndex = -1;
        extraPartIndex = -1;
        powerCoreIndex = -1;
    }

    private void setupStartGameButton()
    {
        view.setStartGameButton(ship.isComplete());
    }

    @Override
    public void onViewLoaded(IShipBuildingView.PartSelectionView partView)
    {
        switch (partView)
        {
            case MAIN_BODY:
                currentView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                view.setArrow(currentView, IShipBuildingView.ViewDirection.UP,true, "Power Cores");
                view.setArrow(currentView, IShipBuildingView.ViewDirection.DOWN,true, "Engines");
                view.setArrow(currentView, IShipBuildingView.ViewDirection.LEFT,true, "Extra Parts");
                view.setArrow(currentView, IShipBuildingView.ViewDirection.RIGHT,true, "Cannons");
                break;
            case EXTRA_PART:
                currentView = IShipBuildingView.PartSelectionView.EXTRA_PART;
                view.setArrow(currentView, IShipBuildingView.ViewDirection.UP,false, "");
                view.setArrow(currentView, IShipBuildingView.ViewDirection.DOWN,false, "");
                view.setArrow(currentView, IShipBuildingView.ViewDirection.LEFT,false, "");
                view.setArrow(currentView, IShipBuildingView.ViewDirection.RIGHT,true, "Ship Bodies");
                break;
            case CANNON:
                currentView = IShipBuildingView.PartSelectionView.CANNON;
                view.setArrow(currentView, IShipBuildingView.ViewDirection.UP,false, "");
                view.setArrow(currentView, IShipBuildingView.ViewDirection.DOWN,false, "");
                view.setArrow(currentView, IShipBuildingView.ViewDirection.LEFT,true, "Ship Bodies");
                view.setArrow(currentView, IShipBuildingView.ViewDirection.RIGHT,false, "");
                break;
            case ENGINE:
                currentView = IShipBuildingView.PartSelectionView.ENGINE;
                view.setArrow(currentView, IShipBuildingView.ViewDirection.UP,true, "Ship Bodies");
                view.setArrow(currentView, IShipBuildingView.ViewDirection.DOWN,false, "");
                view.setArrow(currentView, IShipBuildingView.ViewDirection.LEFT,false, "");
                view.setArrow(currentView, IShipBuildingView.ViewDirection.RIGHT,false, "");
                break;
            case POWER_CORE:
                currentView = IShipBuildingView.PartSelectionView.POWER_CORE;
                view.setArrow(currentView, IShipBuildingView.ViewDirection.UP,false, "");
                view.setArrow(currentView, IShipBuildingView.ViewDirection.DOWN,true, "Ship Bodies");
                view.setArrow(currentView, IShipBuildingView.ViewDirection.LEFT,false, "");
                view.setArrow(currentView, IShipBuildingView.ViewDirection.RIGHT,false, "");
                break;
        }
    }

    @Override
    public void update(double elapsedTime)
    {
        //LEAVE EMPTY
    }

    @Override
    public void loadContent(ContentManager content)
    {
        if(this.content == null)
        {
            try
            {
                AsteroidsGame.getInstance().loadMyContent(content);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            view.setPartViewImageList(IShipBuildingView.PartSelectionView.MAIN_BODY, AsteroidsGame.getInstance().getShipBodyImages().subList(0,2));
            view.setPartViewImageList(IShipBuildingView.PartSelectionView.CANNON, AsteroidsGame.getInstance().getCannonImages().subList(0,2));
            view.setPartViewImageList(IShipBuildingView.PartSelectionView.EXTRA_PART, AsteroidsGame.getInstance().getExtraPartImages().subList(0,2));
            view.setPartViewImageList(IShipBuildingView.PartSelectionView.ENGINE, AsteroidsGame.getInstance().getEngineImages().subList(0,2));
            view.setPartViewImageList(IShipBuildingView.PartSelectionView.POWER_CORE, AsteroidsGame.getInstance().getPowerCoreImages().subList(0,2));

            ship.clearParts();
        }
    }

    @Override
    public void unloadContent(ContentManager content)
    {

    }

    @Override
    public void draw()
    {
        setupStartGameButton();
        Point gameViewCenter = new Point(DrawingHelper.getGameViewWidth()/2,
                DrawingHelper.getGameViewHeight()/2);

        if(shipBodyIndex >= 0)
        {
            ShipBody body = model.getShipBodyTypes().get(shipBodyIndex);
            model.getShip().setMyBody(body);
            int bodyImageID = AsteroidsGame.getInstance().getShipBodyImages().get(shipBodyIndex);
            Point bodyCenter = new Point(body.getMyImage().getWidth()/2, body.getMyImage().getHeight()/2);
            drawImage(bodyImageID, gameViewCenter);
            model.getShip().getMyBody().setImageID(AsteroidsGame.getInstance().getShipBodyImages().get(shipBodyIndex));


            if(engineIndex >= 0)
            {
                Engine engine = model.getEngineTypes().get(engineIndex);
                model.getShip().setMyEngine(engine);
                Point partCenter = new Point((engine.getMyImage().getWidth())/2, (engine.getMyImage().getHeight())/2);
                Point partOffset = getOffsetPoint(gameViewCenter, bodyCenter, body.getEngineAttach(), partCenter, engine.getAttachPoint());
                model.getShip().getMyEngine().setImageID(AsteroidsGame.getInstance().getEngineImages().get(engineIndex));
                drawImage(model.getShip().getMyEngine().getImageID(), partOffset);
            }

            if(cannonIndex >= 0)
            {
                Cannon cannon = model.getCannonTypes().get(cannonIndex);
                model.getShip().setMyCannon(cannon);
                Point partCenter = new Point(cannon.getMyImage().getWidth()/2, cannon.getMyImage().getHeight()/2);
                Point partOffset = getOffsetPoint(gameViewCenter, bodyCenter, body.getCannonAttach(), partCenter, cannon.getAttachPoint());
                model.getShip().getMyCannon().setImageID(AsteroidsGame.getInstance().getCannonImages().get(cannonIndex));
                drawImage(AsteroidsGame.getInstance().getCannonImages().get(cannonIndex), partOffset);
            }

            if(extraPartIndex >= 0)
            {
                ExtraPart extra = model.getExtraPartTypes().get(extraPartIndex);
                model.getShip().setMyExtra(extra);
                Point partCenter = new Point(extra.getMyImage().getWidth()/2, extra.getMyImage().getHeight()/2);
                Point partOffset = getOffsetPoint(gameViewCenter, bodyCenter, body.getExtraAttach(), partCenter, extra.getAttachPoint());
                model.getShip().getMyExtra().setImageID(AsteroidsGame.getInstance().getExtraPartImages().get(extraPartIndex));
                drawImage(model.getShip().getMyExtra().getImageID(), partOffset);
            }

            if(powerCoreIndex >= 0)
            {
                PowerCore power = model.getPowerCoreTypes().get(powerCoreIndex);
                model.getShip().setMyPowerCore(power);
            }
        }
    }

    private void drawImage(int imageID, Point drawPoint)
    {
        DrawingHelper.drawImage(imageID,drawPoint.getX(),drawPoint.getY(),0,scale,scale,255);
    }

    private Point getOffsetPoint(Point bodyLocation, Point bodyCenter, Point bodyAttach, Point partCenter, Point partAttach)
    {
        int partOffsetX = bodyLocation.getX() + (int)(((bodyAttach.getX() - bodyCenter.getX()) + (partCenter.getX() - partAttach.getX()))*scale);
        int partOffsetY = bodyLocation.getY() + (int)(((bodyAttach.getY() - bodyCenter.getY()) + (partCenter.getY() - partAttach.getY()))*scale);

        return new Point(partOffsetX, partOffsetY);
    }

    @Override
    public void onSlideView(IShipBuildingView.ViewDirection direction)
    {
        switch (currentView)
        {
            case MAIN_BODY:
                switch (direction)
                {
                    case UP:
                        currentView = IShipBuildingView.PartSelectionView.ENGINE;
                        view.animateToView(IShipBuildingView.PartSelectionView.ENGINE,
                                IShipBuildingView.ViewDirection.DOWN);
                        break;
                    case DOWN:
                        currentView = IShipBuildingView.PartSelectionView.POWER_CORE;
                        view.animateToView(IShipBuildingView.PartSelectionView.POWER_CORE,
                                IShipBuildingView.ViewDirection.UP);
                        break;
                    case LEFT:
                        currentView = IShipBuildingView.PartSelectionView.CANNON;
                        view.animateToView(IShipBuildingView.PartSelectionView.CANNON,
                                IShipBuildingView.ViewDirection.RIGHT);
                        break;
                    case RIGHT:
                        currentView = IShipBuildingView.PartSelectionView.EXTRA_PART;
                        view.animateToView(IShipBuildingView.PartSelectionView.EXTRA_PART,
                                IShipBuildingView.ViewDirection.LEFT);
                        break;
                }
                break;
            case EXTRA_PART:
                switch (direction)
                {
                    case UP:
                        break;
                    case DOWN:
                        break;
                    case LEFT:
                        currentView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                        view.animateToView(IShipBuildingView.PartSelectionView.MAIN_BODY,
                                IShipBuildingView.ViewDirection.RIGHT);
                        break;
                    case RIGHT:
                        break;
                }
                break;
            case CANNON:
                switch (direction)
                {
                    case UP:
                        break;
                    case DOWN:
                        break;
                    case LEFT:
                        break;
                    case RIGHT:
                        currentView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                        view.animateToView(IShipBuildingView.PartSelectionView.MAIN_BODY,
                                IShipBuildingView.ViewDirection.LEFT);
                        break;
                }
                break;
            case ENGINE:
                switch (direction)
                {
                    case UP:
                        break;
                    case DOWN:
                        currentView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                        view.animateToView(IShipBuildingView.PartSelectionView.MAIN_BODY,
                                IShipBuildingView.ViewDirection.UP);
                        break;
                    case LEFT:
                        break;
                    case RIGHT:
                        break;
                }
                break;
            case POWER_CORE:
                switch (direction)
                {
                    case UP:
                        currentView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                        view.animateToView(IShipBuildingView.PartSelectionView.MAIN_BODY,
                                IShipBuildingView.ViewDirection.DOWN);
                        break;
                    case DOWN:
                        break;
                    case LEFT:
                        break;
                    case RIGHT:
                        break;
                }
                break;
        }
    }

    @Override
    public void onPartSelected(int index)
    {
        switch (currentView)
        {
            case MAIN_BODY:
                shipBodyIndex = index;
                break;
            case EXTRA_PART:
                extraPartIndex = index;
                break;
            case CANNON:
                cannonIndex = index;
                break;
            case ENGINE:
                engineIndex = index;
                break;
            case POWER_CORE:
                powerCoreIndex = index;
                break;
        }
    }

    @Override
    public void onStartGamePressed()
    {
        model.getStarted();
        view.startGame();
    }

    @Override
    public void onResume()
    {

    }

    @Override
    public IView getView()
    {
        return null;
    }

    @Override
    public void setView(IView view)
    {
        this.view = (IShipBuildingView)view;
    }
}


//    enum PartSelectionView {MAIN_BODY, EXTRA_PART, CANNON, ENGINE, POWER_CORE}
//
//    enum ViewDirection {LEFT, RIGHT, UP, DOWN}
//
//    /**
//     * Instructs the ShipBuildingView to animate to a new part selection screen. This function is
//     * generally called as a response to a call to the controller's onSlideView() function.
//     * @param view The part selection view to animate into view
//     * @param animationDirection The direction to use while animating the new view in. EXAMPLE: If
//     *                           LEFT is chosen, the view will animate in from the right, moving left
//     *                           as it does so.
//     */
//    void animateToView(PartSelectionView view, ViewDirection animationDirection);
//
//    /**
//     * Sets a part selection views list of image IDs. EXAMPLE: To set the list of image IDs for the
//     * Cannon selection view, the parameter view would be PartSelectionView.CANNON and partImageIds
//     * would be a list of cannon part image IDs.
//     * @param view The view to set the images for.
//     * @param partImageIds The list of image IDs to set.
//     */
//    void setPartViewImageList(PartSelectionView view, List<Integer> partImageIds);
//
//    /**
//     * Instructs the ShipBuildingView to start the game.
//     */
//    void startGame();
//
//    /**
//     * Enables or disables the start game button. This function should not be called in the
//     * ShipBuildingController's constructor. The start game button is initially disabled. The start
//     * game button should be enabled when a complete has been constructed by the user.
//     * @param enabled TRUE if the button should be enabled, FALSE otherwise
//     */
//    void setStartGameButton(boolean enabled);
//
//    /**
//     * Sets the visibility and text of an arrow for a part selection view. To ensure the partView is
//     * prepared, make sure to only call this function from the controller's onViewLoaded() function.
//     * @param partView The part selection view to set the arrow for
//     * @param arrow The arrow to set
//     * @param visible TRUE - The arrow and text are visible, FALSE - The arrow and text are invisible
//     * @param text The text to display alongside the arrow. NULL or an empty string if there is
//     *             no text to display.
//     */
//    void setArrow(PartSelectionView partView, ViewDirection arrow, boolean visible, String text);
