package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class Database
{
    DbOpenHelper dbOpenHelper;
    SQLiteDatabase database;
    Context baseContext;
    public LevelObjectDAO levelObjectDAO;
    public BgDAO backgroundDAO;
    public AsteroidDAO asteroidsDAO;
    public LevelDAO levelDAO;
    public LevelAsteroidDAO levelAsteroidsDAO;
    public ShipBodyDAO shipBodyDAO;
    public CannonDAO cannonDAO;
    public ExtraPartDAO extraPartDAO;
    public EngineDAO engineDAO;
    public PowerCoreDAO powerCoreDAO;

    /** the constructor */
    public Database(Context baseContext)
    {
        dbOpenHelper = new DbOpenHelper(baseContext);
        database = dbOpenHelper.getWritableDatabase();
        this.baseContext = baseContext;
        levelObjectDAO = new LevelObjectDAO(database);
        backgroundDAO = new BgDAO(database);
        asteroidsDAO = new AsteroidDAO(database);
        levelDAO = new LevelDAO(database);
        levelAsteroidsDAO = new LevelAsteroidDAO(database);
        shipBodyDAO = new ShipBodyDAO(database);
        cannonDAO = new CannonDAO(database);
        extraPartDAO = new ExtraPartDAO(database);
        engineDAO = new EngineDAO(database);
        powerCoreDAO = new PowerCoreDAO(database);
    }

    /** in depth constructor */
    public Database(DbOpenHelper dbOpenHelper, SQLiteDatabase database, Context baseContext, LevelObjectDAO levelObjectDAO, BgDAO backgroundDAO, AsteroidDAO asteroidsDAO, LevelDAO levelDAO, LevelAsteroidDAO levelAsteroidsDAO, ShipBodyDAO shipBodyDAO, CannonDAO cannonDAO, ExtraPartDAO extraPartDAO, EngineDAO engineDAO, PowerCoreDAO powerCoreDAO)
    {
        this.dbOpenHelper = dbOpenHelper;
        this.database = database;
        this.baseContext = baseContext;
        this.levelObjectDAO = levelObjectDAO;
        this.backgroundDAO = backgroundDAO;
        this.asteroidsDAO = asteroidsDAO;
        this.levelDAO = levelDAO;
        this.levelAsteroidsDAO = levelAsteroidsDAO;
        this.shipBodyDAO = shipBodyDAO;
        this.cannonDAO = cannonDAO;
        this.extraPartDAO = extraPartDAO;
        this.engineDAO = engineDAO;
        this.powerCoreDAO = powerCoreDAO;
    }

    public void startTransaction()
    {
        database.beginTransaction();
    }

    public void endTransaction(boolean wasSuccessful)
    {
        if(wasSuccessful)
        {
            database.setTransactionSuccessful();
            database.endTransaction();
            return;
        }
        Toast toast = Toast.makeText(baseContext,"Database transaction was unsuccessful",Toast.LENGTH_LONG);
        toast.show();
        database.endTransaction();
    }

    public void onCreate()
    {
        dbOpenHelper.onCreate(database);
    }
    public DbOpenHelper getDbOpenHelper()
    {
        return dbOpenHelper;
    }

    public void setDbOpenHelper(DbOpenHelper dbOpenHelper)
    {
        this.dbOpenHelper = dbOpenHelper;
    }

    public SQLiteDatabase getDatabase()
    {
        return database;
    }

    public void setDatabase(SQLiteDatabase database)
    {
        this.database = database;
    }

    public Context getBaseContext()
    {
        return baseContext;
    }

    public void setBaseContext(Context baseContext)
    {
        this.baseContext = baseContext;
    }

    public LevelObjectDAO getLevelObjectDAO()
    {
        return levelObjectDAO;
    }

    public void setLevelObjectDAO(LevelObjectDAO levelObjectDAO)
    {
        this.levelObjectDAO = levelObjectDAO;
    }

    public BgDAO getBackgroundDAO()
    {
        return backgroundDAO;
    }

    public void setBackgroundDAO(BgDAO backgroundDAO)
    {
        this.backgroundDAO = backgroundDAO;
    }

    public AsteroidDAO getAsteroidsDAO()
    {
        return asteroidsDAO;
    }

    public void setAsteroidsDAO(AsteroidDAO asteroidsDAO)
    {
        this.asteroidsDAO = asteroidsDAO;
    }

    public LevelDAO getLevelDAO()
    {
        return levelDAO;
    }

    public void setLevelDAO(LevelDAO levelDAO)
    {
        this.levelDAO = levelDAO;
    }

    public LevelAsteroidDAO getLevelAsteroidsDAO()
    {
        return levelAsteroidsDAO;
    }

    public void setLevelAsteroidsDAO(LevelAsteroidDAO levelAsteroidsDAO)
    {
        this.levelAsteroidsDAO = levelAsteroidsDAO;
    }

    public ShipBodyDAO getShipBodyDAO()
    {
        return shipBodyDAO;
    }

    public void setShipBodyDAO(ShipBodyDAO shipBodyDAO)
    {
        this.shipBodyDAO = shipBodyDAO;
    }

    public CannonDAO getCannonDAO()
    {
        return cannonDAO;
    }

    public void setCannonDAO(CannonDAO cannonDAO)
    {
        this.cannonDAO = cannonDAO;
    }

    public ExtraPartDAO getExtraPartDAO()
    {
        return extraPartDAO;
    }

    public void setExtraPartDAO(ExtraPartDAO extraPartDAO)
    {
        this.extraPartDAO = extraPartDAO;
    }

    public EngineDAO getEngineDAO()
    {
        return engineDAO;
    }

    public void setEngineDAO(EngineDAO engineDAO)
    {
        this.engineDAO = engineDAO;
    }

    public PowerCoreDAO getPowerCoreDAO()
    {
        return powerCoreDAO;
    }

    public void setPowerCoreDAO(PowerCoreDAO powerCoreDAO)
    {
        this.powerCoreDAO = powerCoreDAO;
    }
}
