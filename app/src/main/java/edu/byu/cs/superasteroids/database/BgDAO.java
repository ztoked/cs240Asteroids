package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.byu.cs.superasteroids.model.Asteroid;
import edu.byu.cs.superasteroids.model.Background;
import edu.byu.cs.superasteroids.model.ImageObject;


/**
 * Created by zachhalvorsen on 2/12/16.
 */
public class BgDAO
{
    /** the BgDAO database */
    private SQLiteDatabase db;

    /** constructor */
    public BgDAO(SQLiteDatabase db)
    {
        this.db = db;
    }

    /** gets all of the backgrounds from a certaian ID number */
    public List<Background> getBackGrounds()
    {
        final String SQL = "select * " +
                "from background_objects";

        List<Background> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, null);

        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Background bg = new Background();

                bg.setID((int)cursor.getLong(0));
                ImageObject newImage = new ImageObject(cursor.getString(1));
                bg.setMyImage(newImage);

                result.add(bg);

                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return result;
    }

    /** adds a background to the database */
    public boolean addBackground(Background bg)
    {
        ContentValues values = new ContentValues();

        values.put("image", bg.getMyImage().getAddress());

        int id = (int)db.insert("background_objects", null, values);
        if (id >= 0)
        {
            bg.setID(id);
            return true;
        }
        else
        {
            return false;
        }
    }

    /** empties all of the backgrounds from the database */
    public boolean emptyBackgrounds()
    {
        return false;
    }
}
