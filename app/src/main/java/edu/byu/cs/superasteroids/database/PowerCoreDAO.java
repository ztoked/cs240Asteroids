package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.byu.cs.superasteroids.model.ImageObject;
import edu.byu.cs.superasteroids.model.Point;
import edu.byu.cs.superasteroids.model.PowerCore;

/**
 * Created by zachhalvorsen on 2/12/16.
 */
public class PowerCoreDAO
{
    /** the database for PowerCores */
    private SQLiteDatabase db;

    /** the constructor */
    public PowerCoreDAO(SQLiteDatabase db)
    {
        this.db = db;
    }

    /** gets all of the PowerCores from an ID number */
    public List<PowerCore> getPowerCores()
    {
        final String SQL = "select * " +
                "from power_cores";

        List<PowerCore> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, null);

        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                PowerCore pc = new PowerCore();

                pc.setID((int) cursor.getLong(0));
                pc.setCannonBoost(cursor.getInt(1));
                pc.setEngineBoost(cursor.getInt(2));
                pc.setMyImage(new ImageObject(cursor.getString(3)));

                result.add(pc);
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return result;
    }

    /** adds PowerCore to the database */
    public boolean addPowerCore(PowerCore pc)
    {
        ContentValues values = new ContentValues();

        values.put("cannonBoost", pc.getCannonBoost());
        values.put("engineBoost", pc.getEngineBoost());
        values.put("image", pc.getMyImage().getAddress());

        int id = (int)db.insert("power_cores", null, values);
        if (id >= 0)
        {
            pc.setID(id);
            return true;
        }
        else
        {
            return false;
        }
    }

    /** empties all of the PowerCores from the database */
    public boolean emptyPowerCores()
    {
        return false;
    }
}
