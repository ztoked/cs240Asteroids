package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.byu.cs.superasteroids.model.LevelObject;
import edu.byu.cs.superasteroids.model.Point;


/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class LevelObjectDAO
{
    /** the database for LevelObject */
    private SQLiteDatabase db;

    /** the constructor */
    public LevelObjectDAO(SQLiteDatabase db)
    {
        this.db = db;
    }

    /** gets all of the LevelObjects by an ID number */
    public List<LevelObject> getLevelObjects()
    {
        final String SQL = "select * " +
                "from level_objects";

        List<LevelObject> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, null);

        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                LevelObject lo = new LevelObject();

                lo.setID((int) cursor.getLong(0));
                lo.setPosition(new Point(cursor.getString(1)));
                lo.setObjectID(cursor.getInt(2));
                lo.setScale(cursor.getInt(3));
                lo.setLevelID(cursor.getInt(4));

                result.add(lo);
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return result;
    }

    /** gets all of the LevelObjects by the LevelID number of a certain level */
    public Set<LevelObject> getLevelObjectsByLevelID(int LevelID)
    {
        return null;
    }

    /** adds a LevelObject to the database */
    public boolean addLevelObject(LevelObject lo)
    {
        ContentValues values = new ContentValues();

        values.put("position", lo.getPosition().getStringPoint());
        values.put("objectId", lo.getObjectID());
        values.put("scale", lo.getScale());
        values.put("levelId", lo.getLevelID());

        int id = (int)db.insert("level_objects", null, values);
        if (id >= 0)
        {
            lo.setID(id);
            return true;
        }
        else
        {
            return false;
        }
    }

    /** empties all of the LevelObjects from the database */
    public boolean emptyLevelObjects()
    {
        return false;
    }
}
