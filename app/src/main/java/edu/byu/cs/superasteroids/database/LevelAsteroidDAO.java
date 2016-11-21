package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.byu.cs.superasteroids.model.ImageObject;
import edu.byu.cs.superasteroids.model.LevelAsteroid;
import edu.byu.cs.superasteroids.model.Point;

/**
 * Created by zachhalvorsen on 2/12/16.
 */
public class LevelAsteroidDAO
{
    /** The database for LevelAsteroidDAO */
    private SQLiteDatabase db;

    /** the constructor */
    public LevelAsteroidDAO(SQLiteDatabase db)
    {
        this.db = db;
    }

    /** gets all of the LevelAsteroids by the ID number */
    public List<LevelAsteroid> getLevelAsteroids()
    {
        final String SQL = "select * " +
                "from level_asteroids";

        List<LevelAsteroid> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, null);

        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                LevelAsteroid la = new LevelAsteroid();

                la.setID((int) cursor.getLong(0));
                la.setNumber(cursor.getInt(1));
                la.setAsteroidID(cursor.getInt(2));
                la.setLevelID(cursor.getInt(3));

                result.add(la);
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return result;
    }

    /** adds a LevelAsteroid to the database */
    public boolean addLevelAsteroid(LevelAsteroid la)
    {
        ContentValues values = new ContentValues();

        values.put("number", la.getNumber());
        values.put("asteroidId", la.getAsteroidID());
        values.put("levelId", la.getLevelID());

        int id = (int)db.insert("level_asteroids", null, values);
        if (id >= 0)
        {
            la.setID(id);
            return true;
        }
        else
        {
            return false;
        }
    }
    /** empties all of the LevelAsteroids from the database */
    public boolean emptyLevelAsteroids()
    {
        return false;
    }
}
