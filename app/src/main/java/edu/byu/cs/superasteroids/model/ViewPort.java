package edu.byu.cs.superasteroids.model;

import android.graphics.Rect;

import edu.byu.cs.superasteroids.core.AsteroidsGame;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class ViewPort
{
    public Rect view;
    public Point viewPoint;

    public ViewPort()
    {
        view = new Rect(0, 0, DrawingHelper.getGameViewWidth(), DrawingHelper.getGameViewHeight());
    }

    public void updateView()
    {
        AsteroidsModel model = AsteroidsGame.getInstance().getModel();
        int x = model.ship.getMyPosition().getX() - DrawingHelper.getGameViewWidth()/2;
        int y = model.ship.getMyPosition().getY() - DrawingHelper.getGameViewHeight()/2;
        if(!(x + view.width() > model.currentLevel.getWidth() || x < 0))
        {
            viewPoint.setX(x);
        }
        else
        {
            x = viewPoint.x;
        }
        if(!(y + view.height() > model.currentLevel.getHeight() || y < 0))
        {
            viewPoint.setY(y);
        }
        else
        {
            y =viewPoint.y;
        }
        view = new Rect(x,y,x+DrawingHelper.getGameViewWidth(),y + DrawingHelper.getGameViewHeight());
    }
    public Rect getView()
    {
        return view;
    }

    public void setView(Rect view)
    {
        this.view = view;
    }

    public Point getViewPoint()
    {
        return viewPoint;
    }

    public void setViewPoint(Point viewPoint)
    {
        this.viewPoint = viewPoint;
    }
}