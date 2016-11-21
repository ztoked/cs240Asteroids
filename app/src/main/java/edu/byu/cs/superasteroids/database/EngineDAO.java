package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.byu.cs.superasteroids.model.Engine;
import edu.byu.cs.superasteroids.model.ImageObject;
import edu.byu.cs.superasteroids.model.Point;

/**
 * Created by zachhalvorsen on 2/12/16.
 */
public class EngineDAO
{
    /** the database for the Engine DAO */
    private SQLiteDatabase db;

    /** constructor */
    public EngineDAO(SQLiteDatabase db)
    {
        this.db = db;
    }

    /** gets all of the engines by a certain ID number */
    public List<Engine> getEngines()
    {
        final String SQL = "select * " +
                "from engines";

        List<Engine> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, null);

        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Engine e = new Engine();

                e.setID((int) cursor.getLong(0));
                e.setBaseSpeed(cursor.getInt(1));
                e.setBaseTurnRate(cursor.getInt(2));
                e.setAttachPoint(new Point(cursor.getString(3)));
                e.setMyImage(new ImageObject(cursor.getString(4), cursor.getInt(5), cursor.getInt(6)));

                result.add(e);
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return result;    }

    /** add an engine to the database */
    public boolean addEngine(Engine e)
    {
        ContentValues values = new ContentValues();

        values.put("baseSpeed", e.getBaseSpeed());
        values.put("baseTurnRate", e.getBaseTurnRate());
        values.put("attachPoint", e.getAttachPoint().getStringPoint());
        values.put("image", e.getMyImage().getAddress());
        values.put("imageWidth", e.getMyImage().getWidth());
        values.put("imageHeight", e.getMyImage().getHeight());

        int id = (int)db.insert("engines", null, values);
        if (id >= 0)
        {
            e.setID(id);
            return true;
        }
        else
        {
            return false;
        }
    }
    /** empties all of the engines from the database */
    public boolean emptyEngines()
    {
        return false;
    }
}
