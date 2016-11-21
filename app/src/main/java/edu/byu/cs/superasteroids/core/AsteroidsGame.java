package edu.byu.cs.superasteroids.core;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.database.Database;
import edu.byu.cs.superasteroids.database.DbOpenHelper;
import edu.byu.cs.superasteroids.model.AsteroidsModel;

/**
 * Created by zachhalvorsen on 2/18/16.
 */
public class AsteroidsGame
{
    //Singleton pattern
    private static AsteroidsGame instance;
    public static void initialize(Context context, boolean loadFromDb)
    {
        instance = new AsteroidsGame(context,loadFromDb);
    }

    Database db;
    AsteroidsModel model;
    Context context;
    private List<Integer> shipBodyImages;
    private List<Integer> cannonImages;
    private List<Integer> extraPartImages;
    private List<Integer> engineImages;
    private List<Integer> powerCoreImages;
    private List<Integer> asteroidImages;
    private List<Integer> backgroundImages;

    private int spaceImage;

    public AsteroidsGame(Context context, boolean loadFromDb)
    {
        if(loadFromDb)
        {
            this.context = context;
            model = new AsteroidsModel();
            if(!model.isPopulated())
            {
                model.populate();
            }
        }
        else
        {
            this.context = context;
            db = new Database(context);
            model = new AsteroidsModel();
        }
        shipBodyImages = new ArrayList<>();
        cannonImages = new ArrayList<>();
        extraPartImages = new ArrayList<>();
        engineImages = new ArrayList<>();
        powerCoreImages = new ArrayList<>();
        asteroidImages = new ArrayList<>();
        backgroundImages = new ArrayList<>();
    }

    public void loadMyContent(ContentManager content) throws IOException
    {
        if(model.getShipBodyTypes().size() == 0)
        {
            model.populate();
        }
        //get ship body pics
        for(int i = 0; i < model.getShipBodyTypes().size(); i++)
        {
            int ID = content.loadImage(model.getShipBodyTypes().get(i).getMyImage().getAddress());
            shipBodyImages.add(ID);
        }
        //cannons
        for(int i = 0; i < model.getCannonTypes().size(); i++)
        {
            int ID = content.loadImage(model.getCannonTypes().get(i).getMyImage().getAddress());
            cannonImages.add(ID);
            model.getCannonTypes().get(i).setAttackImageID(content.loadImage(model.getCannonTypes().get(i).getAttackImage().getAddress()));
            model.getCannonTypes().get(i).setAttackSoundID(content.loadSound(model.getCannonTypes().get(i).getAttackSound()));
        }
        //extra parts
        for(int i = 0; i < model.getExtraPartTypes().size(); i++)
        {
            int ID = content.loadImage(model.getExtraPartTypes().get(i).getMyImage().getAddress());
            extraPartImages.add(ID);
        }
        //engines
        for(int i = 0; i < model.getEngineTypes().size(); i++)
        {
            int ID = content.loadImage(model.getEngineTypes().get(i).getMyImage().getAddress());
            engineImages.add(ID);
        }
        //power cores
        for(int i = 0; i < model.getPowerCoreTypes().size(); i++)
        {
            powerCoreImages.add(content.loadImage(model.getPowerCoreTypes().get(i).getMyImage().getAddress()));
        }
        //Asteroids
        for(int i = 0; i < model.getAsteroidTypes().size(); i++)
        {
            int ID = content.loadImage(model.getAsteroidTypes().get(i).getMyImage().getAddress());
            asteroidImages.add(ID);
        }
        //Background images
        for(int i = 0; i < model.getBackgroundTypes().size(); i++)
        {
            int ID = content.loadImage(model.getBackgroundTypes().get(i).getMyImage().getAddress());
            backgroundImages.add(ID);
        }

        spaceImage = content.loadImage("images/space.bmp");
    }

    public static AsteroidsGame getInstance()
    {
        return instance;
    }

    public List<Integer> getShipBodyImages()
    {
        return shipBodyImages;
    }

    public void setShipBodyImages(List<Integer> shipBodyImages)
    {
        this.shipBodyImages = shipBodyImages;
    }

    public List<Integer> getCannonImages()
    {
        return cannonImages;
    }

    public void setCannonImages(List<Integer> cannonImages)
    {
        this.cannonImages = cannonImages;
    }

    public List<Integer> getExtraPartImages()
    {
        return extraPartImages;
    }

    public void setExtraPartImages(List<Integer> extraPartImages)
    {
        this.extraPartImages = extraPartImages;
    }

    public List<Integer> getEngineImages()
    {
        return engineImages;
    }

    public void setEngineImages(List<Integer> engineImages)
    {
        this.engineImages = engineImages;
    }

    public List<Integer> getPowerCoreImages()
    {
        return powerCoreImages;
    }

    public void setPowerCoreImages(List<Integer> powerCoreImages)
    {
        this.powerCoreImages = powerCoreImages;
    }

    public List<Integer> getAsteroidImages()
    {
        return asteroidImages;
    }

    public void setAsteroidImages(List<Integer> asteroidImages)
    {
        this.asteroidImages = asteroidImages;
    }

    public List<Integer> getBackgroundImages()
    {
        return backgroundImages;
    }

    public void setBackgroundImages(List<Integer> backgroundImages)
    {
        this.backgroundImages = backgroundImages;
    }

    public int getSpaceImage()
    {
        return spaceImage;
    }

    public void setSpaceImage(int spaceImage)
    {
        this.spaceImage = spaceImage;
    }

    public static void setInstance(AsteroidsGame instance)
    {
        AsteroidsGame.instance = instance;
    }

    public Database getDb()
    {
        return db;
    }

    public void setDb(Database db)
    {
        this.db = db;
    }

    public AsteroidsModel getModel()
    {
        return model;
    }

    public void setModel(AsteroidsModel model)
    {
        this.model = model;
    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }
}
