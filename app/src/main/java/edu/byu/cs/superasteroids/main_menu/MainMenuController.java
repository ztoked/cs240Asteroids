package edu.byu.cs.superasteroids.main_menu;

import android.widget.Toast;

import java.io.IOException;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.AsteroidsGame;
import edu.byu.cs.superasteroids.model.AsteroidsModel;

/**
 * Created by zachhalvorsen on 2/22/16.
 */
public class MainMenuController implements IMainMenuController
{

    IMainMenuView view;

    public MainMenuController(IMainMenuView view)
    {
        this.view = view;
    }

    @Override
    public void onQuickPlayPressed()
    {
        AsteroidsModel model = AsteroidsGame.getInstance().getModel();

        if(!model.isPopulated())
        {
            try
            {
                AsteroidsGame.getInstance().loadMyContent(ContentManager.getInstance());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        if(model.getShipBodyTypes().size() > 0)
        {
            model.getShip().setMyBody(model.getShipBodyTypes().get(0));
            model.getShip().getMyBody().setImageID(AsteroidsGame.getInstance().getShipBodyImages().get(0));

            model.getShip().setMyPowerCore(model.getPowerCoreTypes().get(0));

            model.getShip().setMyExtra(model.getExtraPartTypes().get(0));
            model.getShip().getMyExtra().setImageID(AsteroidsGame.getInstance().getExtraPartImages().get(0));

            model.getShip().setMyCannon(model.getCannonTypes().get(0));
            model.getShip().getMyCannon().setImageID(AsteroidsGame.getInstance().getCannonImages().get(0));

            model.getShip().setMyEngine(model.getEngineTypes().get(0));
            model.getShip().getMyEngine().setImageID(AsteroidsGame.getInstance().getEngineImages().get(0));

            model.getStarted();
            view.startGame();
        }
    }

    @Override
    public IView getView()
    {
        return view;
    }

    @Override
    public void setView(IView view)
    {
        this.view = (IMainMenuView)view;
    }
}
