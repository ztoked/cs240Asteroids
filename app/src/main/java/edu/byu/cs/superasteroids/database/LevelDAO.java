package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.byu.cs.superasteroids.model.Level;

/**
 * Created by zachhalvorsen on 2/12/16.
 */
public class LevelDAO
{
    /** the database for the level DAO */
    private SQLiteDatabase db;

    /** the constructor */
    public LevelDAO(SQLiteDatabase db)
    {
        this.db = db;
    }

    /** gets all of the levels by a certain ID number */
    public List<Level> getLevels()
    {
        final String SQL = "select * " +
                "from levels";

        List<Level> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, null);

        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Level l = new Level();

                l.setID((int) cursor.getLong(0));
                l.setNumber(cursor.getInt(1));
                l.setTitle(cursor.getString(2));
                l.setHint(cursor.getString(3));
                l.setWidth(cursor.getInt(4));
                l.setHeight(cursor.getInt(5));
                l.setMusic(cursor.getString(6));

                result.add(l);
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return result;
    }

    /** adds a level to the datbase */
    public boolean addLevel(Level l)
    {
        ContentValues values = new ContentValues();

        values.put("id", l.getID());
        values.put("number", l.getNumber());
        values.put("title", l.getTitle());
        values.put("hint", l.getHint());
        values.put("width", l.getWidth());
        values.put("height", l.getHeight());
        values.put("music", l.getMusic());

        int id = (int)db.insert("levels", null, values);
        if (id >= 0)
        {
            l.setID(id);
            return true;
        }
        else
        {
            return false;
        }
    }

    /** empties all of the levels from the database */
    public boolean emptyLevels()
    {
        return false;
    }
}
