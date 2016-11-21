package edu.byu.cs.superasteroids.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.byu.cs.superasteroids.core.AsteroidsGame;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class AsteroidsModel
{
    Ship ship;
    List<Projectile> projectiles;
    List<Level> levels;
    List<Asteroid> asteroidTypes;
    List<Background> backgroundTypes;
    List<Cannon> cannonTypes;
    List<Engine> engineTypes;
    List<ExtraPart> extraPartTypes;
    List<LevelAsteroid> levelAsteroidTypes;
    List<LevelObject> levelObjectTypes;
    List<PowerCore> powerCoreTypes;
    List<ShipBody> shipBodyTypes;
    boolean isPopulated;
    Level currentLevel;
    int currentLevelNumber;
    ViewPort viewPort;
    int numberOfPopulations;

    public AsteroidsModel()
    {
        ship = new Ship();
        levels = new ArrayList<>();
        projectiles = new ArrayList<>();
        asteroidTypes = new ArrayList<>();
        backgroundTypes = new ArrayList<>();
        cannonTypes = new ArrayList<>();
        engineTypes = new ArrayList<>();
        extraPartTypes = new ArrayList<>();
        levelAsteroidTypes = new ArrayList<>();
        levelObjectTypes = new ArrayList<>();
        powerCoreTypes = new ArrayList<>();
        shipBodyTypes = new ArrayList<>();
        isPopulated = false;
        currentLevelNumber = 0;
        viewPort = new ViewPort();
        numberOfPopulations = 0;
    }

    public void populate()
    {
        levels = AsteroidsGame.getInstance().getDb().getLevelDAO().getLevels();
        asteroidTypes = AsteroidsGame.getInstance().getDb().getAsteroidsDAO().getAsteroids();
        backgroundTypes = AsteroidsGame.getInstance().getDb().getBackgroundDAO().getBackGrounds();
        cannonTypes = AsteroidsGame.getInstance().getDb().getCannonDAO().getCannons();
        engineTypes = AsteroidsGame.getInstance().getDb().getEngineDAO().getEngines();
        extraPartTypes = AsteroidsGame.getInstance().getDb().getExtraPartDAO().getExtraParts();
        levelAsteroidTypes = AsteroidsGame.getInstance().getDb().getLevelAsteroidsDAO().getLevelAsteroids();
        levelObjectTypes = AsteroidsGame.getInstance().getDb().getLevelObjectDAO().getLevelObjects();
        powerCoreTypes = AsteroidsGame.getInstance().getDb().getPowerCoreDAO().getPowerCores();
        shipBodyTypes = AsteroidsGame.getInstance().getDb().getShipBodyDAO().getShipBodies();
        isPopulated = true;
    }

    private void startNewLevel()
    {
        currentLevel = levels.get(currentLevelNumber);
        List<Asteroid> currentLevelAsteroids = new ArrayList<>();
        for(int i = 0; i < levelAsteroidTypes.size(); i++)
        {
            LevelAsteroid la = levelAsteroidTypes.get(i);
            if(la.getLevelID() == currentLevelNumber + 1)
            {
                for(int j = 0; j < la.getNumber(); j++)
                {
                    Asteroid a = new Asteroid();
                    a.setType(la.getAsteroidID());
                    a.setSpeed(200);
                    Random rand = new Random();
                    int randomNumX = Math.abs(rand.nextInt() % currentLevel.getWidth());
                    int randomNumY = Math.abs(rand.nextInt() % currentLevel.getHeight());
                    if(randomNumX < currentLevel.getWidth()/2 + 500 && randomNumX > currentLevel.getWidth()/2 - 500
                            && randomNumY < currentLevel.getHeight()/2 + 500 && randomNumY > currentLevel.getHeight()/2 - 500 )
                    {
                        randomNumX += 500;
                        randomNumY += 500;
                    }
                    a.setMyPosition(new Point(randomNumX, randomNumY));
                    int ang = Math.abs(rand.nextInt() % 360);
                    if(ang % 90 <= 2)
                    {
                        ang = ang + 10;
                    }
                    a.setAngle(ang);
                    a.setImageID(AsteroidsGame.getInstance().getAsteroidImages().get(la.getAsteroidID()-1));
                    currentLevelAsteroids.add(a);
                }
            }
        }
        currentLevel.levelAsteroids = currentLevelAsteroids;

        List<LevelObject> currentLevelObjects = new ArrayList<>();
        for(int i = 0; i < levelObjectTypes.size(); i++)
        {
            LevelObject lo = levelObjectTypes.get(i);
            if(lo.getLevelID() == currentLevelNumber + 1)
            {
                currentLevelObjects.add(lo);
            }
        }
        currentLevel.levelObjects = currentLevelObjects;

        ship.setMyPosition(new Point(currentLevel.getWidth() / 2, currentLevel.getHeight() / 2));
        ship.setAngle(0);
        ship.setcurrentSpeed(0);
        ship.setHits(0);
        viewPort.viewPoint = new Point(currentLevel.getWidth()/2 - viewPort.view.width()/2,
                currentLevel.getHeight()/2-viewPort.view.height()/2);
    }

    public void getStarted()
    {
        startNewLevel();
        ship.setSpeed(ship.getMyEngine().getBaseSpeed() + ship.getMyPowerCore().getEngineBoost());
    }

    public boolean goToNextLevel()
    {
        boolean isGameOver = false;
        currentLevelNumber++;
        if(currentLevelNumber >= levels.size())
        {
            isGameOver = true;
            return isGameOver;
        }
        else
        {
            startNewLevel();
            isGameOver = false;
            return isGameOver;
        }
    }

    public void update(double elapsedTime)
    {
        ship.update(elapsedTime);
        for(int i = 0; i < projectiles.size(); i++)
        {
            projectiles.get(i).update(elapsedTime);
        }
        currentLevel.update(elapsedTime);
    }

    public void draw()
    {
        ship.drawShip();
        currentLevel.draw();
        for(int i = 0; i < projectiles.size(); i++)
        {
            projectiles.get(i).draw();
        }
    }


    public Ship getShip()
    {
        return ship;
    }

    public void setShip(Ship ship)
    {
        this.ship = ship;
    }

    public List<Level> getLevels()
    {
        return levels;
    }

    public void setLevels(List<Level> levels)
    {
        this.levels = levels;
    }

    public Level getCurrentLevel()
    {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel)
    {
        this.currentLevel = currentLevel;
    }

    public int getCurrentLevelNumber()
    {
        return currentLevelNumber;
    }

    public void setCurrentLevelNumber(int currentLevelNumber)
    {
        this.currentLevelNumber = currentLevelNumber;
    }

    public ViewPort getViewPort()
    {
        return viewPort;
    }

    public void setViewPort(ViewPort viewPort)
    {
        this.viewPort = viewPort;
    }

    public List<Asteroid> getAsteroidTypes()
    {
        return asteroidTypes;
    }

    public boolean isPopulated()
    {
        return isPopulated;
    }

    public void setIsPopulated(boolean isPopulated)
    {
        this.isPopulated = isPopulated;
    }

    public void setAsteroidTypes(List<Asteroid> asteroidTypes)
    {
        this.asteroidTypes = asteroidTypes;
    }

    public List<Background> getBackgroundTypes()
    {
        return backgroundTypes;
    }

    public void setBackgroundTypes(List<Background> backgroundTypes)
    {
        this.backgroundTypes = backgroundTypes;
    }

    public List<Cannon> getCannonTypes()
    {
        return cannonTypes;
    }

    public void setCannonTypes(List<Cannon> cannonTypes)
    {
        this.cannonTypes = cannonTypes;
    }

    public List<Engine> getEngineTypes()
    {
        return engineTypes;
    }

    public void setEngineTypes(List<Engine> engineTypes)
    {
        this.engineTypes = engineTypes;
    }

    public List<ExtraPart> getExtraPartTypes()
    {
        return extraPartTypes;
    }

    public void setExtraPartTypes(List<ExtraPart> extraPartTypes)
    {
        this.extraPartTypes = extraPartTypes;
    }

    public List<LevelAsteroid> getLevelAsteroidTypes()
    {
        return levelAsteroidTypes;
    }

    public void setLevelAsteroidTypes(List<LevelAsteroid> levelAsteroidTypes)
    {
        this.levelAsteroidTypes = levelAsteroidTypes;
    }

    public List<LevelObject> getLevelObjectTypes()
    {
        return levelObjectTypes;
    }

    public void setLevelObjectTypes(List<LevelObject> levelObjectTypes)
    {
        this.levelObjectTypes = levelObjectTypes;
    }

    public List<PowerCore> getPowerCoreTypes()
    {
        return powerCoreTypes;
    }

    public void setPowerCoreTypes(List<PowerCore> powerCoreTypes)
    {
        this.powerCoreTypes = powerCoreTypes;
    }

    public List<ShipBody> getShipBodyTypes()
    {
        return shipBodyTypes;
    }

    public List<Projectile> getProjectiles()
    {
        return projectiles;
    }

    public void setProjectiles(List<Projectile> projectiles)
    {
        this.projectiles = projectiles;
    }

    public void setShipBodyTypes(List<ShipBody> shipBodyTypes)
    {
        this.shipBodyTypes = shipBodyTypes;
    }
}
